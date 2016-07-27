package com.cuiweiyou.interviewspitslot.task;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;

import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.back.CheckUserBack;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;
import com.cuiweiyou.interviewspitslot.util.SharedPrefUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

/** 校验吐槽者的id */
public class UserCheckTask extends AsyncTask<String, Void, Integer> {
	
	private String username;
	private int userid;
	
	private CheckUserBack back;
	private int flag;
	private Activity aty;
	
	public UserCheckTask(Activity aty, CheckUserBack back, int flag) {
		this.aty = aty;
		this.back = back;
		this.flag = flag;
	}

	@Override
	protected Integer doInBackground(String... params) {
		username = params[0];
		
		try {
			String id = HttpRequestAndPostUtil.getJsonObject(Configuration.HOST + "/getuserid.php?name=" + username);
			userid = Integer.parseInt(id);
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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		
		SharedPrefUtil.setUserID(userid);
		SharedPrefUtil.setUserName(username);
		
		if(null != back)
			back.getUserStatus(flag);
		else
			Toast.makeText(RootApplication.getAppContext(), "此昵称相当犀利", 0).show();
		
		aty = null;
		back = null;
	}
}
