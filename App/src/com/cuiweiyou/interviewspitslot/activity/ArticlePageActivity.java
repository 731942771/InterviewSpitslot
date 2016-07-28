package com.cuiweiyou.interviewspitslot.activity;

import java.util.HashMap;
import java.util.Map;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.back.CountBack;
import com.cuiweiyou.interviewspitslot.bean.ArticleBean;
import com.cuiweiyou.interviewspitslot.task.CountTask;
import com.cuiweiyou.interviewspitslot.task.PostPraiseTask;
import com.cuiweiyou.interviewspitslot.util.EaseOutCubicInterpolator;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;
import com.cuiweiyou.interviewspitslot.util.ScreenUtil;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * <b>类名</b>: ArticlePageActivity.java， 骚文祥页<br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class ArticlePageActivity extends Activity implements CountBack {

	private TextView mTitle;
	private TextView mAuthor;
	private TextView mDateadd;
	private TextView mPraisecount;
	private TextView mArticle;
	private ImageView mGood;
	private Animation ta;
	private RelativeLayout mRoot;
	private RelativeLayout.LayoutParams mParams;
	private Map<String, String> map;
	private ArticleBean bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page_article);

		String extra = getIntent().getStringExtra("bean");
		bean = (ArticleBean) JsonUtil.json2Bean(extra, ArticleBean.class);

		initView(bean);
		initEvent();
	}

	private void initView(ArticleBean bean) {
		mRoot = (RelativeLayout) findViewById(R.id.root);

		mTitle = (TextView) findViewById(R.id.title);
		mAuthor = (TextView) findViewById(R.id.author);
		mDateadd = (TextView) findViewById(R.id.dateadd);
		mPraisecount = (TextView) findViewById(R.id.praisecount);
		mArticle = (TextView) findViewById(R.id.article);

		if (bean != null) {
			mTitle.setText(bean.getTitle());
			mAuthor.setText(bean.getUser_name());
			mDateadd.setText(bean.getDate_add());
			mPraisecount.setText(bean.getPraise_count() + "");
			mArticle.setText(bean.getArticle());
		
			new CountTask(this, this, "/getarticlepraisecount.php?id=").execute(bean.getId() + "");
		}

		mGood = (ImageView) findViewById(R.id.good);

		ta = AnimationUtils.loadAnimation(ArticlePageActivity.this, R.anim.in_good);
		ta.setFillAfter(true);
		ta.setAnimationListener(new Animation.AnimationListener() {
			public void onAnimationStart(Animation animation) { }
			public void onAnimationRepeat(Animation animation) { }

			@Override
			public void onAnimationEnd(Animation animation) {
				int left = ScreenUtil.getScreenWidth() / 2;
				int top = mGood.getTop();
				int width = mGood.getWidth();
				int height = mGood.getHeight();
				mGood.clearAnimation();
				mGood.layout(left, top, left + width, top + height);
			}
		});
		
		mParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		mParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		
		map = new HashMap<String, String>();
	}

	private void initEvent() {
		mGood.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String count = mPraisecount.getText().toString();
				
				try {
					int cc = Integer.parseInt(count);
					cc += 1 ;
					mPraisecount.setText(cc + "");
					
					map.put("count", count);
					map.put("post", "false");
			
				} catch (NumberFormatException e) {
				}
				
				ImageView iv = new ImageView(ArticlePageActivity.this);
				iv.setScaleX(0);
				iv.setScaleY(0);
				iv.setRotation((float) (Math.random() * 180));
				iv.setImageResource(R.drawable.plus);
				iv.setLayoutParams(mParams);//设置布局参数
				
				createFireworks(iv);
			}
		});
		
		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				postGood();
				
				finish();
			}
		});
		
		findViewById(R.id.articles).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				postGood();
				
				Intent i = new Intent(ArticlePageActivity.this, ArticleListActivity.class);
				startActivityForResult(i, 33);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(33 == requestCode && 33 == resultCode && null != data){
			String result = data.getStringExtra("result");
			bean = (ArticleBean) JsonUtil.json2Bean(result, ArticleBean.class);
			
			mTitle.setText(bean.getTitle());
			mAuthor.setText(bean.getUser_name());
			mDateadd.setText(bean.getDate_add());
			mPraisecount.setText(bean.getPraise_count() + "");
			mArticle.setText(bean.getArticle());
		}
	}

	/** 爆菊动画 */
	protected void createFireworks(final ImageView iv) {
		
		mRoot.addView(iv);
		
		ValueAnimator mAnimator = ValueAnimator.ofFloat(1.0f);
		mAnimator.setInterpolator(new EaseOutCubicInterpolator());
		mAnimator.setDuration(500); // 时长
		mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
//				float timeFraction = animation.getCurrentPlayTime() / (float) animation.getDuration();
				// 进行值。此受差值器的影响，可产生缓动
				float animFraction = ((Float) animation.getAnimatedValue()).floatValue();
				iv.setScaleX(animFraction);
				iv.setScaleY(animFraction);
			}
		});
		
		mAnimator.addListener(new AnimatorListener() {
			public void onAnimationStart(Animator animation) { }
			public void onAnimationRepeat(Animator animation) { }
			public void onAnimationCancel(Animator animation) { }
			@Override
			public void onAnimationEnd(Animator animation) {
				mRoot.removeView(iv);
			}
		});
		
		mAnimator.start();
	}

	@Override
	protected void onResume() {
		super.onResume();

		mGood.startAnimation(ta);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			postGood();
			finish();
		}
		
		return true;
	}

	/** 异步发送点赞数量。此处留个bug先... */
	protected void postGood() {

		if("false".equals(map.get("post"))){
			String count = map.get("count");
			new PostPraiseTask().execute(bean.getId()+"", count);
		}
	}

	@Override
	public void getCount(Integer result) {
		mPraisecount.setText(result.toString());
	}
}
