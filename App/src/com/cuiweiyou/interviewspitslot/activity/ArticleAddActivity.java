package com.cuiweiyou.interviewspitslot.activity;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.back.CheckUserBack;
import com.cuiweiyou.interviewspitslot.back.PostArticleBack;
import com.cuiweiyou.interviewspitslot.task.ArticlePostTask;
import com.cuiweiyou.interviewspitslot.util.SharedPrefUtil;
import com.cuiweiyou.interviewspitslot.util.UserCheckUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <b>类名</b>: ArticleAddActivity.java，添加骚文 <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class ArticleAddActivity extends Activity implements OnClickListener, TextWatcher, CheckUserBack, PostArticleBack {

	private InputMethodManager imm;
	private TextView mCount;
	private EditText mPen;
	private EditText mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_article);
		
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  

		initView();
		initEvent();
	}

	private void initView() {
		mCount = (TextView) findViewById(R.id.count);
		mTitle = (EditText) findViewById(R.id.title);
		mPen = (EditText) findViewById(R.id.pen);
	}

	private void initEvent() {
		findViewById(R.id.pen_containear).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);
		findViewById(R.id.more).setOnClickListener(this);
		
		mPen.addTextChangedListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pen_containear:
			mPen.setFocusable(true);
			mPen.requestFocus();
			
			imm.showSoftInput(mPen, InputMethodManager.SHOW_IMPLICIT);
			
			break;
		case R.id.back:
			finish();
			
			break;
		case R.id.ok:
			doPost();

			break;
		case R.id.more:
			mTitle.setText("");
			mPen.setText("");
			
			mTitle.setEnabled(true);
			mPen.setEnabled(true);
			findViewById(R.id.ok).setClickable(true);
			findViewById(R.id.more).setClickable(false);
			findViewById(R.id.pen_containear).setClickable(true);
			
			break;
		}
	}

	private void doPost() {
		String textttt = mTitle.getText().toString().replace(" ", "");
		if(textttt.length() < 1){
			Toast.makeText(this, "一个字都不给我！", 0).show();
			
			return;
		} else if (textttt.length() > 60){
			textttt = textttt.substring(0, 60);
		}
		
		String text = mPen.getText().toString().replace(" ", "");
		if(text.length() < 233){
			Toast.makeText(this, "不到800个字算不得极品骚文", 0).show();
			
			return;
		} else if (text.length() > 1233){
			text = text.substring(0, 1233);
		}
		
		if(0 == SharedPrefUtil.getUserID()){
			UserCheckUtil.checkUser(this, this, this, 1);
		} else {
			new ArticlePostTask(this, this).execute(textttt, text);
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	public void onTextChanged(CharSequence s, int start, int before, int count) {}

	@Override
	public void afterTextChanged(Editable s) {
		int count = mPen.getText().toString().length();
		mCount.setText((1500 - count) + ""); // 最长字数1500

		if(count > 1500){
			Toast.makeText(RootApplication.getAppContext(), "2333333333", 0).show();
		}
	}

	@Override
	public void getUserStatus(int flag) {
		String textttt = mTitle.getText().toString().replace(" ", "");
		if(textttt.length() < 1){
			Toast.makeText(this, "一个字都不给我！", 0).show();
			
			return;
		} else if (textttt.length() > 60){
			textttt = textttt.substring(0, 60);
		}
		
		String text = mPen.getText().toString().replace(" ", "");
		if(text.length() < 233){
			Toast.makeText(this, "不到800个字算不得极品骚文", 0).show();
			
			return;
		} else if (text.length() > 1233){
			text = text.substring(0, 1233);
		}
		
		new ArticlePostTask(this, this).execute(textttt, text);
	}

	@Override
	public void getResult(Integer result) {
		if(result != null && result != 0){
			Toast.makeText(ArticleAddActivity.this, "美文！美文！简直了！等待审核...", 0).show();
			
			mTitle.setEnabled(false);
			mPen.setEnabled(false);
			findViewById(R.id.ok).setClickable(false);
			findViewById(R.id.more).setClickable(true);
			findViewById(R.id.pen_containear).setClickable(false);
		}
	}
}
