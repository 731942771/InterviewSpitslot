package com.cuiweiyou.interviewspitslot.activity;

import java.util.List;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.adapter.StationAdapter;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.back.CheckUserBack;
import com.cuiweiyou.interviewspitslot.back.CompanyListBack;
import com.cuiweiyou.interviewspitslot.back.SpitslotPostBack;
import com.cuiweiyou.interviewspitslot.back.StationListBack;
import com.cuiweiyou.interviewspitslot.bean.CompanyBean;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;
import com.cuiweiyou.interviewspitslot.bean.StationBean;
import com.cuiweiyou.interviewspitslot.task.CompanyListTask;
import com.cuiweiyou.interviewspitslot.task.SpitslotPostTask;
import com.cuiweiyou.interviewspitslot.task.StationListTask;
import com.cuiweiyou.interviewspitslot.util.DatetimeUtil;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;
import com.cuiweiyou.interviewspitslot.util.ScreenUtil;
import com.cuiweiyou.interviewspitslot.util.SharedPrefUtil;
import com.cuiweiyou.interviewspitslot.util.UserCheckUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <b>类名</b>: SpitslotAddActivity.java，新建口水 <br/>
 * <b>说明</b>: <br/>
 * 根据getintent中能否拿到target，判断来源于home页还是口水祥页 <br/>
 * 来源于home页，须手动填写公司、岗位；否则自动填写
 * 
 * @author cuiweiyou.com <br/>
 */
public class SpitslotAddActivity extends Activity implements CompanyListBack, OnItemClickListener, StationListBack, OnClickListener, SpitslotPostBack, CheckUserBack{

	/** 公司 */
	private AutoCompleteTextView mCompany;
	/** 岗位 */
	private AutoCompleteTextView mStation;
	/** 地址 */
	private EditText mAddress;
	/** 剩余字数 */
	private TextView mWordcount;
	/** 口水正文 */
	private EditText mSpitslot;
	/** 发表口水按钮 */
	private Button mBtnyetu;
//	private CompanyBean mCurrentCompany;
	private List<CompanyBean> mCompanyList;
	private SpitslotBean mTarget;
	private SpitslotBean mPostBean;
	private List<StationBean> mStationList;
	private View mSpinnerStation;
	private View mSpinnerCompany;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_spitslot);

		String extra = getIntent().getStringExtra("target");
		if(null != extra && !"".equals(extra))
			mTarget = (SpitslotBean) JsonUtil.json2Bean(extra, SpitslotBean.class);

		initView(mTarget);
		initEvent();
		initDate(mTarget);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(0 == SharedPrefUtil.getUserID()){
			UserCheckUtil.checkUser(this, this, null, 0);
		}
	}

	/** 初始化控件 */
	private void initView(SpitslotBean bean) {
		mCompany = (AutoCompleteTextView) findViewById(R.id.et_company);
		mStation = (AutoCompleteTextView) findViewById(R.id.et_station);
		mAddress = (EditText) findViewById(R.id.et_address);
		
		mSpinnerCompany = findViewById(R.id.spinner_company);
		mSpinnerStation = findViewById(R.id.spinner_station);

		if (null != bean) {
			mCompany.setText(bean.getCompany_name());
			mStation.setText(bean.getStation_name());
			mAddress.setText(bean.getAddress());

			mCompany.setKeyListener(null);
			mStation.setKeyListener(null);
			mAddress.setKeyListener(null);
		}

		mWordcount = (TextView) findViewById(R.id.wordcount);

		mSpitslot = (EditText) findViewById(R.id.et_spitslot);

		mBtnyetu = (Button) findViewById(R.id.btnyetu);
	}

	/** 控件事件 */
	private void initEvent() {
//		mSpitslot.addTextChangedListener(this);
//		mCompany.addTextChangedListener(new CompanyWatcher());
		mCompany.setOnItemClickListener(this);
		
		mSpinnerCompany.setOnClickListener(this);
		mSpinnerStation.setOnClickListener(this);
		
//		mStation.addTextChangedListener(new StationWatcher());
//		mStation.addTextChangedListener(new StationWatcher());
//		mStation.setOnFocusChangeListener(this);
//		mStation.setOnEditorActionListener(this);
		mBtnyetu.setOnClickListener(this);
		
		findViewById(R.id.back).setOnClickListener(this);
	}

	/** 加载数据 */
	private void initDate(SpitslotBean bean) {
		if (null == bean)
			new CompanyListTask(SpitslotAddActivity.this, SpitslotAddActivity.this).execute();
	}

//	@Override
//	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//	}
//
//	public void onTextChanged(CharSequence s, int start, int before, int count) {
//	}

	public void afterTextChanged(Editable s) {
		int count = mSpitslot.getText().toString().length();
		mWordcount.setText((120 - count) + "");
		
		if(count > 119){
			Toast.makeText(RootApplication.getAppContext(), "话不在多，多说无益", 0).show();
		}
	}

	@Override
	public void getCompanyList(List<CompanyBean> list) {
		if (null == list || list.size() < 1) {
			return;
		}
		
		mCompanyList = list;

		// list转str集合
		String[] strArr = new String[list.size()];
		for (int i = 0; i < strArr.length; i++) {
			strArr[i] = list.get(i).getName();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(SpitslotAddActivity.this, R.layout.item_autoc, R.id.item_autoc_name, strArr);
		mCompany.setThreshold(1);
		mCompany.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String selectedItem = parent.getItemAtPosition(position).toString();
		
		int size = mCompanyList.size();
		for (int i = 0; i < size; i++) {
			CompanyBean b = mCompanyList.get(i);
			if(selectedItem.equals(b.getName())){
				mAddress.setText(b.getAddress());
				new StationListTask(SpitslotAddActivity.this, SpitslotAddActivity.this).execute(b.getId() + "");
				
				break;
			}
		}
	}

//	@Override
//	public void onFocusChange(View v, boolean hasFocus) {
//		if (hasFocus) { // 获得焦点
//			if (null != mCurrentCompany)
//				if(null == mPopw)
//					new StationListTask(SpitslotAddActivity.this, SpitslotAddActivity.this).execute(mCurrentCompany.getId() + "");
//		}
//	}

	@Override
	public void getStationList(List<StationBean> result) {
		if (null == result || result.size() < 1) {
			return;
		}
		
		mStationList = result;
		
		// list转str集合
		String[] strArr = new String[result.size()];
		for (int i = 0; i < strArr.length; i++) {
			strArr[i] = result.get(i).getName();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(SpitslotAddActivity.this, R.layout.item_autoc, R.id.item_autoc_name, strArr);
		mStation.setThreshold(1);
		mStation.setAdapter(adapter);

//		popStations(mStation, result);
	}

	private void popStations(View root, final List<?> list, final int flag) {
		final AlertDialog dialog = new AlertDialog.Builder(this).create();

		View view = View.inflate(SpitslotAddActivity.this, R.layout.view_popstation, null);
		ListView poplist = (ListView) view.findViewById(R.id.poplist);
		poplist.setAdapter(new StationAdapter(list, flag));
		poplist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(0 == flag){
					CompanyBean b = (CompanyBean) list.get(position);
					mCompany.setText(b.getName());
					mAddress.setText(b.getAddress());
					
					new StationListTask(SpitslotAddActivity.this, SpitslotAddActivity.this).execute(b.getId() + "");
				} else {
					StationBean b = (StationBean) list.get(position);
					mStation.setText(b.getName());
				}
				
	            dialog.dismiss();
			}
		});

		dialog.setView(view);
		dialog.show();
		
		View item = poplist.getAdapter().getView(0, null, poplist); 
		item.measure(0, 0); 
		int mh = item.getMeasuredHeight();
		int ls = mh * (list.size() + 1);

		int wid = ScreenUtil.getScreenWidth() / 4 * 3;
		int hei = ScreenUtil.getScreenHeight() / 4 * 3;
		
		if(hei > ls)
			hei = ls;
		
		WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
		p.height = hei;
		p.width = wid;
		dialog.getWindow().setAttributes(p);
	}

	/** 公司名称自动填充类 */
//	class CompanyWatcher implements TextWatcher {
//
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//		public void onTextChanged(CharSequence s, int start, int before, int count) { }
//		public void afterTextChanged(Editable s) {
//
//		}
//
//	}

	/** 职位名称自动填充类 */
//	class StationWatcher implements TextWatcher {
//
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//		public void onTextChanged(CharSequence s, int start, int before, int count) { }
//		public void afterTextChanged(Editable s) {
//			if(null != mPopw)
//				mPopw.dismiss();
//		}
//
//	}

	/** EditText软键盘敲击字符 */
//	@Override
//	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//		
//		mPopw.dismiss();
//		
//		return true;
//	}

	/** 野兔按钮，保存新口水 */
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btnyetu:
				if(0 == SharedPrefUtil.getUserID()){
					UserCheckUtil.checkUser(this, this, this, 1);
				} else {
					doPost();
				}
			break;
			case R.id.spinner_company:
				if(null != mCompanyList){
					popStations(mCompany, mCompanyList, 0);
				}
				break;
			case R.id.spinner_station:
				if(null != mStationList){
					popStations(mStation, mStationList, 1);
				}
				break;
			case R.id.back:
				finish();
				
				break;
		}
	}
	


	@Override
	public void getUserStatus(int flag) {
		if( 1 == flag){
			doPost();
		}
	}
	
	private void doPost(){
		String com = mCompany.getText().toString();
		String sta = mStation.getText().toString();
		String add = mAddress.getText().toString();
		String spi = mSpitslot.getText().toString().replace(" ", "");
		if(spi.length() > 120)
			spi = spi.substring(0, 120);
		
		if(null == com || null == sta || null == add || null == spi || "".equals(com) || "".equals(sta) || "".equals(add) || "".equals(spi)){
			Toast.makeText(RootApplication.getAppContext(), "矮油 K 丁 me ？", 0).show();
			
			return;
		}
		
		if(spi.length() < 8){
			Toast.makeText(RootApplication.getAppContext(), "喷出逼格，吐出水平，来8个字的！", 0).show();
			return;
		}
		
		int id = 0;
		int company_id = 0;
		String company_name = com;
		String address = add;
		int station_id = 0;
		String station_name = sta;
		int user_id = SharedPrefUtil.getUserID();
		String user_name = SharedPrefUtil.getUserName();
		String date_view = DatetimeUtil.getNowDate();
		String description = spi; // 口水
		String praise_count = "0";
		String record_time = DatetimeUtil.getNowTime();
		String note = "_";
		
		mPostBean = new SpitslotBean(id, company_id, company_name, address, station_id, station_name, user_id, user_name, date_view, description, praise_count, record_time, note);
		
		String flag = "0";
		if(null != mTarget){
			company_id = mTarget.getCompany_id();
			company_name = mTarget.getCompany_name();
			station_id = mTarget.getStation_id();
			station_name = mTarget.getStation_name();
			address = mTarget.getAddress();
			
			mPostBean = new SpitslotBean(id, company_id, company_name, address, station_id, station_name, user_id, user_name, date_view, description, praise_count, record_time, note);
			flag = "1";
		}
		
		String bean2Json = JsonUtil.bean2Json(mPostBean);
		new SpitslotPostTask(SpitslotAddActivity.this, SpitslotAddActivity.this).execute(flag, bean2Json);
	}

	@Override
	public void postResult(Integer result) {
		if(0 == result){
			Toast.makeText(RootApplication.getAppContext(), "这口水噎人，网络堵了！", 0).show();
			
			return;
		}
		
		backHome();
	}

	private void backHome() {
		if(null == mTarget){ // Home页
			setResult(201);
		} else { // 口水祥页
			setResult(30);
		}
		
		finish();
	}
}
