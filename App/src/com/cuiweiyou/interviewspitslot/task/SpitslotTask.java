package com.cuiweiyou.interviewspitslot.task;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.back.SpitslotBack;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;
import com.cuiweiyou.interviewspitslot.util.IntentStateUtil;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

public class SpitslotTask extends AsyncTask<Void, Void, List<SpitslotBean>> {
	
	private SpitslotBack back;
	private Activity aty;

	public SpitslotTask(Activity aty, SpitslotBack back) {
		this.aty = aty;
		this.back = back;
	}

	@Override
	protected List<SpitslotBean> doInBackground(Void... params) {
		List<SpitslotBean> list = null;
		
		try {
			String spitslot = HttpRequestAndPostUtil.getJsonObject(Configuration.HOST + "/getspitslot.php");
			list = JsonUtil.getSpitslotList(spitslot);
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
	protected void onPostExecute(List<SpitslotBean> result) {
		super.onPostExecute(result);
		
		back.getSpitslots(result);
		
		aty = null;
		back = null;
	}
}
