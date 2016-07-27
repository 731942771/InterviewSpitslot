package com.cuiweiyou.interviewspitslot.activity;

import java.util.List;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.adapter.ArticleListAdapter;
import com.cuiweiyou.interviewspitslot.back.ArticleBack;
import com.cuiweiyou.interviewspitslot.bean.ArticleBean;
import com.cuiweiyou.interviewspitslot.task.ArticleTask;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ArticleListActivity extends Activity implements OnClickListener, OnItemClickListener, ArticleBack {

	private ListView mArticles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_articlelist);
		
		initView();
		initEvent();
		initDate();
	}

	private void initView() {
		mArticles = (ListView) findViewById(R.id.list);
	}

	private void initEvent() {
		findViewById(R.id.back).setOnClickListener(this);
		mArticles.setOnItemClickListener(this);
	}
	
	private void initDate() {
		new ArticleTask(this, this).execute("0");
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.back:
				finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ArticleBean bean = (ArticleBean) mArticles.getAdapter().getItem(position);
		
		Intent i = new Intent();
		i.putExtra("result", JsonUtil.bean2Json(bean));
		setResult(33, i);
		
		finish();
	}

	@Override
	public void getArticles(List<ArticleBean> spitslots) {
		if(null != spitslots && spitslots.size() > 0){
			mArticles.setAdapter(new ArticleListAdapter(spitslots));
		} else {
			Toast.makeText(this, "善良的人那么多，敢于喷的没几个...", 0).show();
		}
	}
}
