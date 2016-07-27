package com.cuiweiyou.interviewspitslot.activity;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.task.FeedbackPostTask;
import com.cuiweiyou.interviewspitslot.util.SharedPrefUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends Activity implements OnClickListener, TextWatcher {

	private TextView mCount;
	private EditText mFeed;
	private InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  

		initView();
		initEvent();
	}

	private void initView() {
		mCount = (TextView) findViewById(R.id.count);
		mFeed = (EditText) findViewById(R.id.feed);
	}

	private void initEvent() {
		findViewById(R.id.feed_containear).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);
		
		mFeed.addTextChangedListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.feed_containear:
			mFeed.setFocusable(true);
			mFeed.requestFocus();
			
			imm.showSoftInput(mFeed, InputMethodManager.SHOW_IMPLICIT);
			
			break;
		case R.id.back:
			finish();
			
			break;
		case R.id.ok:
			doPost();

			break;
		}
	}

	private void doPost() {
		String text = mFeed.getText().toString().replace(" ", "");
		if(text.length() < 8){
			Toast.makeText(this, "惜字如金啊真是，至少来8个字行不", 0).show();
			
			return;
		}
		
		new FeedbackPostTask(this, this).execute(text);
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	public void onTextChanged(CharSequence s, int start, int before, int count) {}

	@Override
	public void afterTextChanged(Editable s) {
		int count = mFeed.getText().toString().length();
		mCount.setText((233 - count) + "");

		if(count > 232){
			Toast.makeText(RootApplication.getAppContext(), "骚年你是一直在夸我吗", 0).show();
		}
	}
}
