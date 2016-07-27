package com.cuiweiyou.interviewspitslot.task;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.cuiweiyou.interviewspitslot.back.CompanyListBack;
import com.cuiweiyou.interviewspitslot.bean.CompanyBean;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

/** 拉去全部公司 */
public class CompanyListTask extends AsyncTask<Void, Void, List<CompanyBean>> {
	
	private CompanyListBack back;
	private Activity aty;

	public CompanyListTask(Activity aty, CompanyListBack back) {
		this.aty = aty;
		this.back = back;
	}

	@Override
	protected List<CompanyBean> doInBackground(Void... params) {
		List<CompanyBean> list = null;
		
		try {
			String json = HttpRequestAndPostUtil.getJsonObject(Configuration.HOST + "/getcompanylist.php");
			list = JsonUtil.getCompanyList(json);
		} catch (SocketTimeoutException e){
			aty.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(aty, "还喷个啥，屌丝作者的服务器超时了", 0).show();
				}
			});
			e.printStackTrace();
		} catch (EOFException e) {
			aty.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(aty, "作者不是富二代，serEOFE累觉不爱", 0).show();
				}
			});
			e.printStackTrace();
		} catch (IOException e) {
			aty.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(aty, "说实话，作者没错，是烂服务器IOE了", 0).show();
				}
			});
			e.printStackTrace();
		}

		return list;
	}

	@Override
	protected void onPostExecute(List<CompanyBean> result) {
		super.onPostExecute(result);

		back.getCompanyList(result);
		
		aty = null;
		back = null;
	}
}
