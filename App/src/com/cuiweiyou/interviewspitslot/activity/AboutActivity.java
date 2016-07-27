package com.cuiweiyou.interviewspitslot.activity;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.SharedPrefUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * <b>类名</b>: AboutActivity.java，关于此应用 <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class AboutActivity extends Activity implements OnClickListener {

	private long last = 0;
	private int count = 0;
	private long lastPhone = 0;
	private int countPhone = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.disclaimer).setOnClickListener(this);
		findViewById(R.id.email).setOnClickListener(this);
		findViewById(R.id.cvu).setOnClickListener(this);
		findViewById(R.id.cuiweiyou).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.disclaimer:
			Intent i = new Intent(this, DisclaimerActivity.class);
			startActivity(i);

			break;
		case R.id.back:
			finish();

			break;
		case R.id.email:
			Intent data=new Intent(Intent.ACTION_SENDTO);
			data.setData(Uri.parse("mailto:vigiles@163.com"));
			data.putExtra(Intent.EXTRA_SUBJECT, "《面试吐槽》使用反馈"); 
			startActivity(data); 
			
			break;
		case R.id.cvu:
			long now = System.currentTimeMillis();
			if ((now - last) < 200) {

				count++;

				if (count >= 9) {
					setSerAddr();
					count = 0;
				}
			}

			last = now;
			
			break;
		case R.id.cuiweiyou:
			long nowPhone = System.currentTimeMillis();
			if ((nowPhone - lastPhone) < 200) {
				
				countPhone ++;
				
				if (countPhone >= 9) {
					callWriter();
					countPhone = 0;
				}
			}
			
			lastPhone = nowPhone;
			
			break;
		}
	}

	/** 给作者打电话 */
	private void callWriter() {
		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:13241701472"));
		startActivity(intent);
	}

	private void setSerAddr() {
		final AlertDialog dialog = new AlertDialog.Builder(AboutActivity.this).create();
		
		View view = View.inflate(AboutActivity.this, R.layout.view_dialog_setseradd, null);
		final EditText address = (EditText) view.findViewById(R.id.addr);
		address.setText(SharedPrefUtil.getSerAddr());
		TextView mBtnOK = (TextView) view.findViewById(R.id.ok);
		TextView mBtnNO = (TextView) view.findViewById(R.id.cancel);
		
		mBtnOK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String adps = address.getText().toString().replace(" ", "");
				SharedPrefUtil.setSerAddr(adps);
				Configuration.HOST = adps;
				
				dialog.dismiss();
			}
		});
		mBtnNO.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.setView(view);
		dialog.show();
	}
}
