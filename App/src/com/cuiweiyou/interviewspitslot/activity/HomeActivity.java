package com.cuiweiyou.interviewspitslot.activity;

import java.util.ArrayList;
import java.util.List;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.adapter.BaseVPAdapter;
import com.cuiweiyou.interviewspitslot.adapter.SpitslotAdapter;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.back.ArticleBack;
import com.cuiweiyou.interviewspitslot.back.GestureBack;
import com.cuiweiyou.interviewspitslot.back.SpitslotBack;
import com.cuiweiyou.interviewspitslot.bean.ArticleBean;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;
import com.cuiweiyou.interviewspitslot.task.ArticleTask;
import com.cuiweiyou.interviewspitslot.task.SpitslotTask;
import com.cuiweiyou.interviewspitslot.util.EaseOutElasticInterpolator;
import com.cuiweiyou.interviewspitslot.util.GestureUtil;
import com.cuiweiyou.interviewspitslot.util.IntentStateUtil;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;
import com.cuiweiyou.interviewspitslot.util.ScreenUtil;
import com.cuiweiyou.interviewspitslot.view.BannerPager;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <b>类名</b>: HomeActivity.java，主界面 <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class HomeActivity extends Activity implements OnClickListener, OnItemClickListener, GestureBack, ArticleBack, SpitslotBack {

	/** 骚文列表 */
	private BannerPager mArticle;

	/** 口水列表 */
	private ListView mSpitslot;
	/** 弹出底子 */
	private View mDialog;
	/** 骚文按钮 */
	private View mDialogArticle;
	/** 口水按钮 */
	private View mDialogSpitslot;
	/** 添加口水和骚文 */
	private ImageView mAddSpitslotAndArticle;
	/** 头部logo，单击口水列表回顶部，双击刷新 */
	private ImageView mLogo;
	/** 设置 */
	private View mSetting;
	/** 上次点击back键 */
	private long lastBack = 0;
	/** 单击双击识别器 */
	private GestureDetector gd;
	/** Dialog开关标识 */
	private boolean isDialog = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initView();
		getDatas();
		initEvent();
	}

	/** 实例化控件 */
	private void initView() {
		mLogo = (ImageView) findViewById(R.id.logo);
		mSetting = findViewById(R.id.setting);

		findViewById(R.id.search).setOnClickListener(this);

		mArticle = (BannerPager) findViewById(R.id.articlepager);
		mSpitslot = (ListView) findViewById(R.id.lv);

		mAddSpitslotAndArticle = (ImageView) findViewById(R.id.add_spitslot_and_article);
		mDialog = findViewById(R.id.dialog);
		mDialogArticle = findViewById(R.id.ico_article);
		mDialogSpitslot = findViewById(R.id.ico_spitslot);
	}

	/** 远程请求骚文和口水列表 */
	private void getDatas() {
		 new SpitslotTask(HomeActivity.this, HomeActivity.this).execute();
		 new ArticleTask(HomeActivity.this, HomeActivity.this).execute("1");
	}

	/** 添加控件的响应事件 */
	private void initEvent() {
		mSetting.setOnClickListener(this);
		
		mSpitslot.setOnItemClickListener(this);
		mAddSpitslotAndArticle.setOnClickListener(this);
		
		mDialog.setOnClickListener(this);
		mDialogArticle.setOnClickListener(this);
		mDialogSpitslot.setOnClickListener(this);

		gd = new GestureDetector(this, new GestureUtil(HomeActivity.this)); // 单击/双击处理器
		mLogo.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				gd.onTouchEvent(event);
				return true;
			}
		});
		
		mArticle.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int index) {
				mArticle.updateIndex(index);
			}
			public void onPageScrolled(int index, float scale, int pixel) { }
			public void onPageScrollStateChanged(int state) { }
		});
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.search: // 搜索
				Toast.makeText(this, "哈哈你真容易逗乐", 0).show();
//				Intent i = new Intent(HomeActivity.this, SearchActivity.class);
//				startActivity(i);
				
				break;
				
			case R.id.add_spitslot_and_article: // 常规添加按钮
			case R.id.dialog:					// 添加口水按钮背景
				addSpitslotOrArticle();

			break;
			
			case R.id.ico_spitslot: // add口水
				mDialog.setVisibility(View.GONE);
				mDialogArticle.setVisibility(View.INVISIBLE);
				mDialogSpitslot.setVisibility(View.INVISIBLE);
				mAddSpitslotAndArticle.setImageResource(R.drawable.ic_add_spitslot);
				isDialog = false;
				
				Intent iSpitslot = new Intent(HomeActivity.this, SpitslotAddActivity.class);
				startActivityForResult(iSpitslot, 201);
				
				break;
				
			case R.id.ico_article: // add骚文
				mDialog.setVisibility(View.GONE);
				mDialogArticle.setVisibility(View.INVISIBLE);
				mDialogSpitslot.setVisibility(View.INVISIBLE);
				mAddSpitslotAndArticle.setImageResource(R.drawable.ic_add_spitslot);
				isDialog = false;
				
				Intent iArticle = new Intent(HomeActivity.this, ArticleAddActivity.class);
				startActivityForResult(iArticle, 202);
				
				break;
				
			case R.id.setting: // 设置

				Intent iSett = new Intent(HomeActivity.this, SettingActivity.class);
				startActivity(iSett);
				
				break;
		}
	}

	/** 
	 * 添加口水骚文按钮事件<br/>
	 * 显示或隐藏Dialog */
	private void addSpitslotOrArticle() {

		if (isDialog) { // 如果已经显示，就隐藏之
			mArticle.startCarousel();

			hintSpitslot();
			hintArticle();
			
			isDialog = false;
		} else { // 如果已经隐藏，就显示之
			mArticle.stopCarousel();
			
			AlphaAnimation aa = new AlphaAnimation(0, 1);
			aa.setDuration(300);
			aa.setInterpolator(new AccelerateInterpolator());
			aa.setAnimationListener(new AnimationListener() {
				public void onAnimationStart(Animation animation) { }
				public void onAnimationRepeat(Animation animation) { }

				@Override
				public void onAnimationEnd(Animation animation) { // 打开dialog
					showSpitslot();
					showArticle();
				}
			});

			mDialog.setVisibility(View.VISIBLE);
			mDialog.startAnimation(aa);
			isDialog = true;
			
			mAddSpitslotAndArticle.setImageResource(R.drawable.ic_add_spitslot_dialog);
		}
	}

	/** 隐藏add骚文按钮 */
	private void hintArticle() {
		float fxR = 0;
		float txR = ScreenUtil.getScreenWidth() / 2 - mDialogArticle.getX() - mDialogArticle.getWidth() / 2;
		float fyR = 0;
		float tyR = mDialog.getBottom() - mDialogArticle.getY() - mDialogArticle.getHeight();
		
		TranslateAnimation taR = new TranslateAnimation(fxR, txR, fyR, tyR);
		taR.setDuration(300);
		taR.setInterpolator(new AccelerateInterpolator());
		taR.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) { }
			public void onAnimationRepeat(Animation animation) { }
			@Override
			public void onAnimationEnd(Animation animation) {
				mDialogArticle.setVisibility(View.INVISIBLE);
			}
		});

		mDialogArticle.startAnimation(taR);
	}

	/** 隐藏add口水按钮 */
	private void hintSpitslot() {
		float fxR = 0;
		float txR = ScreenUtil.getScreenWidth() / 2 - mDialogSpitslot.getWidth() / 2 - mDialogSpitslot.getX();
		float fyR = 0;
		float tyR = mDialog.getBottom() - mDialogSpitslot.getY() - mDialogSpitslot.getHeight();
		
		TranslateAnimation taR = new TranslateAnimation(fxR, txR, fyR, tyR);
		taR.setDuration(300);
		taR.setInterpolator(new AccelerateInterpolator());
		taR.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) { }
			public void onAnimationRepeat(Animation animation) { }
			@Override
			public void onAnimationEnd(Animation animation) {
				AlphaAnimation aa = new AlphaAnimation(1, 0);
				aa.setDuration(300);
				aa.setInterpolator(new AccelerateInterpolator());
				mDialog.setVisibility(View.GONE);
				mDialog.startAnimation(aa);
				
				mDialogSpitslot.setVisibility(View.INVISIBLE);

				mAddSpitslotAndArticle.setImageResource(R.drawable.ic_add_spitslot);
			}
		});

		mDialogSpitslot.startAnimation(taR);
	}

	/** 弹出add骚文按钮 */
	protected void showArticle() {
		mDialogArticle.setVisibility(View.VISIBLE);
		
		final int btm = mDialog.getBottom() - mDialogArticle.getHeight() / 2;
		final int dsw = mDialogArticle.getWidth();
		final int swh = ScreenUtil.getScreenWidth() / 2 - dsw / 2;
		
		ValueAnimator mAnimator = ValueAnimator.ofFloat(1.0f);
		mAnimator.setDuration(500); // 时长
		mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// 时长进度 = 当前帧播放时间 / 动画时长
				// float timeFraction = animation.getCurrentPlayTime() / (float) animation.getDuration();
				// 进行值。此受差值器的影响，可产生缓动
				float animFraction = ((Float) animation.getAnimatedValue()).floatValue();
				int ff = (int) (animFraction * 500);
				
				int l = swh - ff / 2;
				int r = l + dsw;
				
				int t = btm - ff - mDialogArticle.getHeight();
				int b = btm - ff;
				
				mDialogArticle.layout(l, t, r, b);
			}
		});
		
		mAnimator.setInterpolator(new EaseOutElasticInterpolator());
		mAnimator.start();
	}

	/** 弹出add口水按钮 */
	protected void showSpitslot() {
		mDialogSpitslot.setVisibility(View.VISIBLE);
		
		final int btm = mDialog.getBottom() - mDialogSpitslot.getHeight() / 2;
		final int dsw = mDialogSpitslot.getWidth();
		final int swh = ScreenUtil.getScreenWidth() / 2 - dsw / 2;
		
		ValueAnimator mAnimator = ValueAnimator.ofFloat(1.0f);
		mAnimator.setDuration(500); // 时长
		mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// 时长进度 = 当前帧播放时间 / 动画时长
				// float timeFraction = animation.getCurrentPlayTime() / (float) animation.getDuration();
				// 进行值。此受差值器的影响，可产生缓动
				float animFraction = ((Float) animation.getAnimatedValue()).floatValue();
				int ff = (int) (animFraction * 500);
				
				int l = swh + ff / 2;
				int r = l + dsw;
				
				int t = btm - ff - mDialogSpitslot.getHeight();
				int b = btm - ff;
				
				mDialogSpitslot.layout(l, t, r, b);
			}
		});
		
		mAnimator.setInterpolator(new EaseOutElasticInterpolator());
		mAnimator.start();
	}

	/** 口水列表item点击 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		SpitslotBean item = (SpitslotBean) mSpitslot.getAdapter().getItem(position);
		String bean2Json = JsonUtil.bean2Json(item);
		
		Intent i = new Intent(HomeActivity.this, SpitslotItemActivity.class);
		i.putExtra("bean", bean2Json);
		
		startActivityForResult(i, 30);
	}

	/** 远程请求口水集合回调 */
	@Override
	public void getSpitslots(List<SpitslotBean> spitslots) {
		if(null == spitslots || spitslots.size() < 1){
			Toast.makeText(RootApplication.getAppContext(), "最近口水有的稀缺~", 0).show();
			findViewById(R.id.lv_mask).setVisibility(View.VISIBLE);
			
			return;
		} else {
			findViewById(R.id.lv_mask).setVisibility(View.GONE);
		}
		
		mSpitslot.setAdapter(new SpitslotAdapter(spitslots));
	}

	/** 远程请求骚文集合回调 */
	@Override
	public void getArticles(List<ArticleBean> articles) {
		List<View> list = new ArrayList<View>();
		
		if(null == articles || articles.size() < 1){
			Toast.makeText(RootApplication.getAppContext(), "有文采的骚年像国民岳父一样稀缺~", 0).show();
			mLogo.setImageResource(R.drawable.ic_error);
			findViewById(R.id.articlepager_mask).setVisibility(View.VISIBLE);
			
			return;
		} else {
			mLogo.setImageResource(R.drawable.ic_launcher);
			findViewById(R.id.articlepager_mask).setVisibility(View.GONE);
		}

		int size = articles.size();
		for (int i = 0; i < size; i++) {
			View view = View.inflate(HomeActivity.this, R.layout.vp_page, null);
			TextView title = (TextView) view.findViewById(R.id.title_vp);
			TextView article = (TextView) view.findViewById(R.id.article);

			final ArticleBean bean = articles.get(i);
			String title2 = bean.getTitle();
			String article2 = bean.getArticle();

			title.setText(title2);
			article.setText(article2);
			
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(HomeActivity.this, ArticlePageActivity.class);
					i.putExtra("bean", JsonUtil.bean2Json(bean));
					startActivity(i);
				}
			});

			list.add(view);
		}

		mArticle.setAdapter(new BaseVPAdapter(list));
		mArticle.startCarousel();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			if(isDialog ){
				addSpitslotOrArticle();
				return true;
			}
			
			long now = System.currentTimeMillis();
			if ((now - lastBack) < 500) {
				finish();

			} else {
				// Toast.makeText(RootApplication.getAppContext(), "500ms内再次点击退出应用", 0).show();
				lastBack = now;
			}
		}

		return true;
	}

	@Override
	public void clickType(int type) {
		switch (type) {
		case 1: // 单击
			mSpitslot.smoothScrollToPosition(0);
			break;
		case 0: // 双击
			Toast.makeText(RootApplication.getAppContext(), "灌满痰盂~", 0).show();
			
			new ArticleTask(HomeActivity.this, HomeActivity.this).execute("1");
			new SpitslotTask(HomeActivity.this, HomeActivity.this).execute();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(IntentStateUtil.getNetState()){
			mLogo.setImageResource(R.drawable.ic_launcher);
			findViewById(R.id.lv_mask).setVisibility(View.GONE);
		}
		
		if((201 == requestCode && 201 == resultCode) || (30 == requestCode && 30 == resultCode)) {
			 new SpitslotTask(HomeActivity.this, HomeActivity.this).execute();
		}
	}
}
