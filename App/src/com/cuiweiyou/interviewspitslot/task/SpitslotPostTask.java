package com.cuiweiyou.interviewspitslot.task;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;

import com.cuiweiyou.interviewspitslot.back.SpitslotPostBack;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/** 发表口水 */
public class SpitslotPostTask extends AsyncTask<String, Void, Integer>{
	
	private SpitslotPostBack back;
	private Activity aty;

	/** 发表口水 */
	public SpitslotPostTask(Activity aty, SpitslotPostBack back) {
		this.aty = aty;
		this.back = back;
	}

	@Override
	protected Integer doInBackground(String... params) {
		String flag = params[0];
		SpitslotBean bean = (SpitslotBean) JsonUtil.json2Bean(params[1], SpitslotBean.class);
		String company_name = bean.getCompany_name();
		String station_name = bean.getStation_name();
		String address = bean.getAddress();
		int result = 0;
		
		try {
			
			if("0".equals(flag)){ // 手动填写的公司信息，须网络校验
				String comid = HttpRequestAndPostUtil.getJsonObject(Configuration.HOST + "/getcompanyid.php?name=" + company_name + "&address=" + address);
				String staid = HttpRequestAndPostUtil.getJsonObject(Configuration.HOST + "/getstationid.php?name=" + station_name + "&company_id=" + comid);
				int cid = Integer.parseInt(comid);
				int sid = Integer.parseInt(staid);
				
				bean.setCompany_id(cid);
				bean.setStation_id(sid);
			}
			
			String parames = //
					"company_id=" + bean.getCompany_id() + //
					"&station_id=" + bean.getStation_id() + //
					"&user_id=" + bean.getUser_id() + //
					"&date_view=" + bean.getDate_view() + //
					"&description=" + URLEncoder.encode(bean.getDescription(), "UTF-8") + // 口水
					"&praise_count=" + bean.getPraise_count() + //
					"&record_time=" + bean.getRecord_tiem() + //
					"&note=" + URLEncoder.encode(bean.getNote(), "UTF-8");
			
			result = HttpRequestAndPostUtil.post(Configuration.HOST + "/postspitslot.php", parames);
			
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
		} catch (ConnectException e){
			aty.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(aty, "服务器看海去了，连不上", 0).show();
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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		
		back.postResult(result);
		
		aty = null;
		back = null;
	}
}
