package com.cuiweiyou.interviewspitslot.activity;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.adapter.BaseVPAdapter;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.bean.VersionBean;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.ApkUtil;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;
import com.cuiweiyou.interviewspitslot.util.IntentStateUtil;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;
import com.cuiweiyou.interviewspitslot.util.SharedPrefUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * <b>类名</b>: WelcomeActivity.java， <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class WelcomeActivity extends Activity {

	/** 当前版本号 */
	private int mLocalVersionCode;

	/** 子线程通讯器 */
	private Handler handler;
	/** 最新版本数据的封装体 */
	private VersionBean mNewVersion;
	/** 使用非wifi标记 */
	private boolean isUsed = false;
	/** UI开始显示时间 */
	private long startTime;
	
	/** 检查是否区分wifi与非wifi。1不区分，0区分  */
	private int checkWifi;
	/** 检查是否启动自动检测更新。1检测，0不检测 */
	private int checkUpdate;
	/** 检查是否仅wifi下启动自动检测更新。1是，0非wifi也可以  */
	private int checkUpdateOnlyWifi;

	private NotificationManager nm;

	private AnimationDrawable ad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(SharedPrefUtil.getIsFirstInstall()){ // 第一次安装
			guide();
		} else {
			welcome();
		}
	}
	
	private void guide() {
		
		setContentView(R.layout.activity_welcome_vp);
		
        ad = new AnimationDrawable();
        ad.addFrame(getResources().getDrawable(R.drawable.ps01), 150);
        ad.addFrame(getResources().getDrawable(R.drawable.ps02), 150);
        ad.addFrame(getResources().getDrawable(R.drawable.ps03), 150);
        ad.addFrame(getResources().getDrawable(R.drawable.ps04), 150);
        ad.addFrame(getResources().getDrawable(R.drawable.ps05), 150);
        ad.addFrame(getResources().getDrawable(R.drawable.ps06), 150);
		
		View view1 = View.inflate(this, R.layout.vp_guide_1, null);// 关联
		ImageView img = (ImageView) view1.findViewById(R.id.guide);
		img.setImageDrawable(ad);
        
		View view2 = View.inflate(this, R.layout.vp_guide_2, null);
		View view3 = View.inflate(this, R.layout.vp_guide_3, null);
		View view4 = View.inflate(this, R.layout.vp_guide_4, null);
		view4.findViewById(R.id.disclaimer).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(WelcomeActivity.this, DisclaimerActivity.class);
				startActivity(i);
			}
		});
		view4.findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPrefUtil.setIsFirstInstall(false);
				
				welcome();
			}
		});
		
		List<View> list = new ArrayList<View>();
		list.add(view1);
		list.add(view2);
		list.add(view3);
		list.add(view4);
		
		ViewPager mvp = (ViewPager) findViewById(R.id.vp);
		mvp.setAdapter(new BaseVPAdapter(list));
	}

	private void welcome(){
		setContentView(R.layout.activity_welcome);

		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		checkCurrVisi();

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				int newcode = msg.arg1; // 提示更新版本
				int newvers = msg.arg2; // 可更新版本
				
				if (mLocalVersionCode < newvers) {
					String desc = mNewVersion.getDescAble();
					Toast.makeText(RootApplication.getAppContext(), desc, 0).show();
					
					getNewApk( mNewVersion.getUrl(), mNewVersion.getNameAble());
					
					return;
				}

				else if (mLocalVersionCode < newcode) {
					String desc = mNewVersion.getDescription();
					
					Toast.makeText(RootApplication.getAppContext(), desc, 0).show();
					
					gotoHome();
				}
				
				else {
//					Toast.makeText(RootApplication.getAppContext(), "没有更新相关信息", 0).show();
					gotoHome();
				}
			}
		};

		// 子线程请求远程数据
		new Thread() {
			public void run() {
				getNetStatus();
			}

		}.start();
	}

	private void checkCurrVisi() {
		mLocalVersionCode = ApkUtil.getVersionCode();
		
		checkWifi = SharedPrefUtil.getCheckWifi();
		checkUpdate = SharedPrefUtil.getCheckUpdate();
		checkUpdateOnlyWifi = SharedPrefUtil.getCheckUpdateOnlyWifi();
	}
	
	/** 下载新版apk */
	public void getNewApk(String apkurl, final String apkname) {
		
		// /storage/emulated/0/Android/data/com.cuiweiyou.interviewspitslot/cache
		final String catchPath = RootApplication.getAppContext().getExternalCacheDir().getAbsolutePath();
		
		// 1.创建下载器
		HttpUtils http = new HttpUtils();
		// 2.最大开启线程数量
		http.configRequestThreadPoolSize(1); // 不要大于1，否则通过intent直接启动的安装器会报“解析错误”
		//HttpHandler htpHandler = ;
		http.download(
				apkurl,						// 1）源文件地址
				catchPath + "/" + apkname,	// 2）下载保存地址
				false,						// 3）如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
				false,						// 4）如果从请求返回信息中获取到文件名，下载完成后自动重命名。
				new RequestCallBack<File>() { // 5）回调函数，处理整个下载事件

					// （1）事件：开始下载
					public void onStart() {
						createNotification();
					}

					// （2）事件：下载中
					public void onLoading(long total, long current, boolean isUploading) {
					}

					// （3）事件：下载成功
					public void onSuccess(ResponseInfo<File> responseInfo) {
						String ap = responseInfo.result.getAbsolutePath();
						Toast.makeText(RootApplication.getAppContext(), "下载完毕" + ap, 0).show();
						cancelNotification();
						
						Intent intent = new Intent(Intent.ACTION_VIEW);  
						intent.setDataAndType(//
								Uri.fromFile(new File(catchPath + "/" + apkname)),	// apk文件全路径
								"application/vnd.android.package-archive");			// MIME type，明确apk文件类型为安卓程序包
						startActivityForResult(intent, 99);
					}

					// （4）事件：下载失败
					public void onFailure(com.lidroid.xutils.exception.HttpException error, String msg) {
						Log.e("ard", "error:" + error.getMessage() + "，msg:" + msg);
						
						Toast.makeText(RootApplication.getAppContext(), "这次不在状态，下次更新吧", 0).show();
						gotoHome();
					}
				});
	}

	protected void createNotification() {
		Notification nf = new Notification(); 
		nf.icon = R.drawable.ic_notification; 
		nf.flags = Notification.FLAG_NO_CLEAR;
        nf.setLatestEventInfo( this, "正在下载新版本", "新版本真的很有逼格", null);
		
		// 关键一步，发通知
		nm.notify(1, nf);
	}

	protected void cancelNotification() {
		nm.cancel(1);    // 撤销通知
	}

	/**
	 * 判断网络状态<br/>
	 * wifi不提示<br/>
	 * 提示用户是否使用手机流量
	 **/
	private void getNetStatus() {

		// wifi
		if (IntentStateUtil.getWifiState()) {
			if (1 == checkUpdate){ // 启动自检测更新
				getJson(); // 直接请求远程数据
			} else {
				gotoHome();
			}
		}

		// mobile网络
		else if (IntentStateUtil.get3G4GState()) {
			if(1 == checkWifi){ // 不分区wifi
				if (1 == checkUpdate){ // 启动自检测更新
					if(1 == checkUpdateOnlyWifi){ // 仅wifi自动检测
						gotoHome();
					} else {
						getJson();
					}
				} else {
					gotoHome();
				}
			} else {
				getUserSelected();
			}
		}

		// 没有网络连接
		else {
			WelcomeActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(RootApplication.getAppContext(), "木接网怎么喷！\n我先回去补点水", 1).show();
					
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							finish();
						}
					}, 3000);
				}
			});
		}
		
	}

	/** 由用户选择是否使用非wifi */
	private void getUserSelected() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
		builder.setTitle("请确认"); 					// 标题
		// builder.setIcon(R.drawable.ic_launcher); // 图标
		builder.setMessage("当前使用非Wifi网络连接"); 	// 说明
		builder.setPositiveButton("放弃", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();
			}
		});
		builder.setNegativeButton("使用", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				isUsed  = true;

				if (1 == checkUpdate){ // 启动自检测更新
					if(1 == checkUpdateOnlyWifi){ // 仅wifi自动检测
						gotoHome();
					} else {
						new Thread(){ // dialog的create转到了ui线程，so这里的网络访问重新转到子线程
							public void run() {
								getJson(); // 请求远程数据
							};
						}.start();
					}
				} else {
					gotoHome();
				}
			}
		});
		
		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				
				// 2.创建对话框
				AlertDialog dialog = builder.create(); // 非UI的线程中不允许调弹出框，对话框之类
				// 3.显示对话框
				dialog.show();
				dialog.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {

						if(!isUsed) // 如果是点击“放弃”按钮或者灰背景，那么就直接退出
							finish();
					}
				});
				
			}
		});
		
	}

	/** 进入主界面 */
	protected void gotoHome() {
		long now = System.currentTimeMillis();
		long diff = now - startTime;
		if(diff < 3000){
			try {
				Thread.sleep(3000 - diff);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Intent i = new Intent(WelcomeActivity.this, HomeActivity.class);
		startActivity(i);
		
		finish();
	}

	/**
	 * <b>功能</b>：getJson， 请求远程数据<br/>
	 */
	private void getJson() {
		Message msg = handler.obtainMessage();
		int newcode = 0;
		int newvers = 0;

		try {
			String remoteVersion = HttpRequestAndPostUtil.getJsonObject(Configuration.UPDATE);// 远程版本相关json
			if (null != remoteVersion) {
				mNewVersion = JsonUtil.getNewVersion(remoteVersion);
				newcode = mNewVersion.getVersion(); 	// 更新提示版本号
				newvers = mNewVersion.getVersionAble(); // 可更新版本号
			} else {
				newcode = -1;
				newvers = -1;
			}

		} catch (SocketTimeoutException e) {
			WelcomeActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(WelcomeActivity.this, "更新个啥，屌丝作者的服务器超时了", 0).show();
				}
			});
			e.printStackTrace();
		} catch (EOFException e) {
			WelcomeActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(WelcomeActivity.this, "作者不是富二代，serEOFE累觉不爱", 0).show();
				}
			});
			e.printStackTrace();
		} catch (IOException e) {
			WelcomeActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(WelcomeActivity.this, "说实话，作者没错，是烂服务器IOE了", 0).show();
				}
			});
			e.printStackTrace();
		} finally {
			msg.arg1 = newcode;
			msg.arg2 = newvers;
			
			handler.sendMessage(msg);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		startTime = System.currentTimeMillis();
		
		if(null != ad)
			ad.start();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(99 == requestCode){ // 针对下载成功进入安装界面但“取消”。resultCode为0，可能是默认的。
			gotoHome();
		}
	}
}
