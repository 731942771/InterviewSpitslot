package com.cuiweiyou.interviewspitslot.activity;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.bean.VersionBean;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.task.UserCheckTask;
import com.cuiweiyou.interviewspitslot.util.ApkUtil;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;
import com.cuiweiyou.interviewspitslot.util.SharedPrefUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

/** 设置aty界面 */
public class SettingActivity extends Activity implements OnClickListener, CompoundButton.OnCheckedChangeListener {

	private EditText mNickname;
	private Handler handler;
	private View mContainear;
	private int mLocalVersionCode;
	private VersionBean mNewVersion;
	
	private NotificationManager nm;
	private InputMethodManager imm;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		initView();
		initEvent();
		createHandler();
	}

	private void initView() {
		mNickname = (EditText) findViewById(R.id.nickname);
		mNickname.setText(SharedPrefUtil.getUserName());
		
		int checkWifi = SharedPrefUtil.getCheckWifi();
		int checkUpdate = SharedPrefUtil.getCheckUpdate();
		int checkUpdateOnlyWifi = SharedPrefUtil.getCheckUpdateOnlyWifi();
		int acceptUpdatePush = SharedPrefUtil.getAcceptUpdatePush();
		
		((CheckBox)findViewById(R.id.check_wifi)).setChecked(1 == checkWifi);
		((CheckBox)findViewById(R.id.check_update)).setChecked(1 == checkUpdate);
		((CheckBox)findViewById(R.id.check_update_onlywifi)).setChecked(1 == checkUpdateOnlyWifi);
		((CheckBox)findViewById(R.id.accept_updatepull)).setChecked(1 == acceptUpdatePush);
		
		mContainear = findViewById(R.id.containear);
	}

	private void initEvent() {
		mContainear.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mContainear.setFocusable(true);
				mContainear.setFocusableInTouchMode(true);
				mContainear.requestFocus();
				
				mNickname.clearFocus();
				imm.hideSoftInputFromWindow(mNickname.getWindowToken(),0);

				return true; // true，原焦点控件才能失去焦点
			}
		});
		
		mNickname.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String name = mNickname.getText().toString().replace(" ", "");
				if (!hasFocus && !"".equals(name)) {
					new UserCheckTask(SettingActivity.this, null, 0).execute(name);
				}
			}
		});
		
		mNickname.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) { }
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		((CheckBox)findViewById(R.id.check_wifi)).setOnCheckedChangeListener(this);
		((CheckBox)findViewById(R.id.check_update)).setOnCheckedChangeListener(this);
		((CheckBox)findViewById(R.id.check_update_onlywifi)).setOnCheckedChangeListener(this);
		((CheckBox)findViewById(R.id.accept_updatepull)).setOnCheckedChangeListener(this);

		findViewById(R.id.checkupdate).setOnClickListener(this);
		findViewById(R.id.feedback).setOnClickListener(this);
		findViewById(R.id.about).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
	}

	private void createHandler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				int newcode = msg.arg1; // 提示更新版本
				int newvers = msg.arg2; // 可更新版本
				
				mLocalVersionCode = ApkUtil.getVersionCode();
				
				if (mLocalVersionCode < newvers) {             // apk更新
					String desc = mNewVersion.getDescAble();
					
					Toast.makeText(RootApplication.getAppContext(), desc, 0).show();
					
					getNewApk( mNewVersion.getUrl(), mNewVersion.getNameAble());
					
					return;
				}

				else if (mLocalVersionCode < newcode) {       // 提示内容更新
					String desc = mNewVersion.getDescription();
					
					Toast.makeText(RootApplication.getAppContext(), desc, 0).show();
				}
				
				else {
					Toast.makeText(RootApplication.getAppContext(), "没有更新相关信息", 0).show();
				}
			}
		};
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.checkupdate:
			new Thread(){
				public void run() {
					getJson();
				};
			}.start();
			
			break;
		case R.id.feedback:
			Intent ifb = new Intent(this, FeedbackActivity.class);
			startActivity(ifb);
			
			break;
		case R.id.about:
			Intent i = new Intent(this, AboutActivity.class);
			startActivity(i);
			
			break;
		case R.id.back:
			finish();

			break;
		}

	}
	
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
			SettingActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(SettingActivity.this, "设置个啥，屌丝作者的服务器超时了", 0).show();
				}
			});
			e.printStackTrace();
		} catch (EOFException e) {
			SettingActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(SettingActivity.this, "作者不是富二代，serEOFE累觉不爱", 0).show();
				}
			});
			e.printStackTrace();
		} catch (IOException e) {
			SettingActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(SettingActivity.this, "说实话，作者没错，是烂服务器IOE了", 0).show();
				}
			});
			e.printStackTrace();
		} finally {
			msg.arg1 = newcode;
			msg.arg2 = newvers;
			
			handler.sendMessage(msg);
		}
	}
	
	public void getNewApk(String apkurl, final String apkname) {
		
		// /storage/emulated/0/Android/data/com.cuiweiyou.interviewspitslot/cache
		final String catchPath = RootApplication.getAppContext().getExternalCacheDir().getAbsolutePath();
		
		// 1.创建下载器
		HttpUtils http = new HttpUtils();
		// 2.最大开启线程数量
		http.configRequestThreadPoolSize(1);
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

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int id = buttonView.getId();
		switch(id){
			case R.id.check_wifi: // 启动不提示非wifi，直接链接
				if(isChecked){
					SharedPrefUtil.setCheckWifi(1); // 选中。1不提示，不区分
				} else {
					SharedPrefUtil.setCheckWifi(0);
				}
				
			break;
			case R.id.check_update: // 启动自动检测更新
				if(isChecked){
					SharedPrefUtil.setCheckUpdate(1); // 选中。检测
				} else {
					SharedPrefUtil.setCheckUpdate(0);
				}
			break;
			case R.id.check_update_onlywifi: // 仅wifi下自动检测更新
				if(isChecked){
					SharedPrefUtil.setCheckUpdateOnlyWifi(1);
				} else {
					SharedPrefUtil.setCheckUpdateOnlyWifi(0);
				}
			break;
			case R.id.accept_updatepull: // 接收更新推送
				if(isChecked){
					SharedPrefUtil.setAcceptUpdatePush(1);
				} else {
					SharedPrefUtil.setAcceptUpdatePush(0);
				}
			break;
		}
		
		mNickname.clearFocus();
		imm.hideSoftInputFromWindow(mNickname.getWindowToken(),0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(99 == requestCode){ // 针对下载成功进入安装界面但“取消”。resultCode为0，可能是默认的。
			Toast.makeText(RootApplication.getAppContext(), "竟然不更新\n你不知道新版本有多高的逼格吗", 1).show();
		}
	}
}
