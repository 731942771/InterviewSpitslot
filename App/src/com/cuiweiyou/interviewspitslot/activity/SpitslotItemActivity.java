package com.cuiweiyou.interviewspitslot.activity;

import java.util.List;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.adapter.SpitslotItemAdapter;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.back.SpitslotBack;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;
import com.cuiweiyou.interviewspitslot.task.SpitslotListTask;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <b>类名</b>: SpitslotItemActivity.java，口水祥页 <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class SpitslotItemActivity extends Activity implements SpitslotBack, OnClickListener, OnItemClickListener {

	/** 公司 */
	private TextView mCompany;
	/** 职位 */
	private TextView mStation;
	/** 地址 */
	private TextView mAddress;
	/** 吐槽者 */
	private TextView mInter;
	/** 吐槽日期 */
	private TextView mDate;
	/** 口水 */
	private TextView mDesc;

	/** 口水列表 */
	private ListView mSpitslots;
	/** 当前主展示口水 */
	private SpitslotBean mCurrent;
	private int resultCode = 0;
	private String extra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_spitslot);

		extra = getIntent().getStringExtra("bean");
		mCurrent = (SpitslotBean) JsonUtil.json2Bean(extra, SpitslotBean.class);
		
		initView(mCurrent);
		getDate(mCurrent.getStation_id(), mCurrent.getId());
		initEvent();
	}

	private void getDate(int s_station_id, int s_id) {
		new SpitslotListTask(SpitslotItemActivity.this, SpitslotItemActivity.this).execute(s_station_id + "", s_id + "");
	}

	private void initView(SpitslotBean bean) {
		mCompany = (TextView) findViewById(R.id.company);
		mStation = (TextView) findViewById(R.id.station);
		mAddress = (TextView) findViewById(R.id.address);

		mCompany.setText(bean.getCompany_name());
		mStation.setText(bean.getStation_name());
		mAddress.setText(bean.getAddress());

		mInter = (TextView) findViewById(R.id.inter);
		mDate = (TextView) findViewById(R.id.date);
		mDesc = (TextView) findViewById(R.id.desc);

		mInter.setText(bean.getUser_name());
		mDate.setText(bean.getDate_view());
		mDesc.setText(bean.getDescription());

		mSpitslots = (ListView) findViewById(R.id.spitslots);
	}

	private void initEvent() {
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.companygroup).setOnClickListener(this);
		findViewById(R.id.yetu).setOnClickListener(this);
		
		mSpitslots.setOnItemClickListener(this);
	}

	@Override
	public void getSpitslots(List<SpitslotBean> spitslots) {
		if (null == spitslots || spitslots.size() < 1) {
			Toast.makeText(RootApplication.getAppContext(), "这个点，能get到的骚年太少~", 0).show();
			findViewById(R.id.spitslots_mask).setVisibility(View.VISIBLE);

			return;
		} else {
			findViewById(R.id.spitslots_mask).setVisibility(View.GONE);
		}

		mSpitslots.setAdapter(new SpitslotItemAdapter(spitslots));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			if(0 != resultCode){
				setResult(30);
			}
			
			finish();

			break;
		case R.id.companygroup:
			Intent ic = new Intent(SpitslotItemActivity.this, CompanyActivity.class);
			ic.putExtra("bean", extra);
			startActivityForResult(ic, 77);
			
			break;
		case R.id.yetu:
			String curr = JsonUtil.bean2Json(mCurrent);
			
			Intent iss = new Intent(SpitslotItemActivity.this, SpitslotAddActivity.class);
			iss.putExtra("target", curr);
			
			startActivityForResult(iss, 30);
			
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(30 == requestCode && 30 == resultCode) {
			this.resultCode  = resultCode;
			getDate(mCurrent.getStation_id(), mCurrent.getId());
		}
		
		else if(77 == requestCode && 77 == resultCode) {
			if(null != data){
				int company_id = data.getIntExtra("company_id", 0);
				String company_name = data.getStringExtra("company_name");
				String company_addr = data.getStringExtra("company_addr");
				int station_id = data.getIntExtra("station_id", 0);
				String station_name = data.getStringExtra("station_name");

				mCompany.setText(company_name);
				mStation.setText(station_name);
				mAddress.setText(company_addr);
				
				findViewById(R.id.titile_spitslot).setVisibility(View.GONE);
				
				mSpitslots.setAdapter(new SpitslotItemAdapter(null));
				mSpitslots.setOnItemClickListener(null);
				findViewById(R.id.yetu).setVisibility(View.GONE);
				
				new SpitslotListTask(SpitslotItemActivity.this, SpitslotItemActivity.this).execute(station_id + "", "");
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mCurrent = (SpitslotBean) parent.getAdapter().getItem(position);
		initView(mCurrent);
		getDate(mCurrent.getStation_id(), mCurrent.getId());
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(0 != resultCode){
				setResult(30);
			}
			
			finish();
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
