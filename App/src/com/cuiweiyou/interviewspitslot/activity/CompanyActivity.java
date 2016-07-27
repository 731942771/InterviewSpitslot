package com.cuiweiyou.interviewspitslot.activity;

import java.util.List;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.adapter.StationAdapter;
import com.cuiweiyou.interviewspitslot.back.CountBack;
import com.cuiweiyou.interviewspitslot.back.StationListBack;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;
import com.cuiweiyou.interviewspitslot.bean.StationBean;
import com.cuiweiyou.interviewspitslot.task.CountTask;
import com.cuiweiyou.interviewspitslot.task.StationListTask;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * <b>类名</b>: CompanyActivity.java，公司页 <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class CompanyActivity extends Activity implements StationListBack, OnItemClickListener, CountBack {

	private SpitslotBean mBean;
	private ListView mStations;
	private TextView mCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);

		String extra = getIntent().getStringExtra("bean");
		if (null != extra && !"".equals(extra)) {
			mBean = (SpitslotBean) JsonUtil.json2Bean(extra, SpitslotBean.class);
		}

		initView();
		initDate();
	}

	private void initView() {
		TextView mCompany = (TextView) findViewById(R.id.company);
		TextView mAddress = (TextView) findViewById(R.id.address);
		mCount = (TextView) findViewById(R.id.spitslotcount);
		
		if(null != mBean){
			mCompany.setText(mBean.getCompany_name());
			mAddress.setText(mBean.getAddress());
			mCount.setText(mBean.getPraise_count());
			
			// TODO  根据公司id请求label
			// 请求职位列表
			new StationListTask(this, this).execute(mBean.getCompany_id() + "");
		}
		
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mStations = (ListView) findViewById(R.id.stations);
		mStations.setOnItemClickListener(this);
	}

	/** 此公司被吐槽的次数 */
	private void initDate() {
		if(null != mBean){
			int company_id = mBean.getCompany_id();
			new CountTask(this, this, "/getspitslotcount.php?company_id=").execute(company_id + "");
		}
	}

	@Override
	public void getCount(Integer result) {
		mCount.setText(result.toString());
	}

	@Override
	public void getStationList(List<StationBean> result) {
		if (null == result || result.size() < 1) {
			return;
		}
		
		mStations.setAdapter(new StationAdapter(result, 1));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		StationBean item = (StationBean) mStations.getAdapter().getItem(position);
		
		Intent data = new Intent();
		data.putExtra("company_id", mBean.getCompany_id());
		data.putExtra("company_name", mBean.getCompany_name());
		data.putExtra("company_addr", mBean.getAddress());
		data.putExtra("station_id", item.getId());
		data.putExtra("station_name", item.getName());
		
		setResult(77, data);
		finish();
	}
}
