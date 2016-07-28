package com.cuiweiyou.interviewspitslot.task;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.List;

import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.DatetimeUtil;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class FeedbackPostTask extends AsyncTask<String, Void, Integer> {
	
	private Context ctx;
	private Activity aty;

	public FeedbackPostTask(Activity aty, Context ctx) {
		this.aty = aty;
		this.ctx = ctx;
	}

	@Override
	protected Integer doInBackground(String... params) {
		String arg = params[0];
		String args = 
				"feed=" + arg + 
				"&postdate=" + DatetimeUtil.getNowDate();// + 
//				"&user_id=" + "" + 
//				"&description=" + "" + 
//				"&note=" + "";
		int result = 0;
		
		try {
			result = HttpRequestAndPostUtil.post(Configuration.HOST + "/postfeedback.php", args);
		}  catch (MalformedURLException e) {
			aty.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(aty, "MURLE，你懂的", 0).show();
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
		}
		
		return result;
	}


	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		
		if(0 != result){
			Toast.makeText(aty, "感谢反馈", 0).show();
		} else {
			Toast.makeText(aty, "感谢反馈", 0).show();
		}
		
		aty = null;
		ctx = null;
	}
}
