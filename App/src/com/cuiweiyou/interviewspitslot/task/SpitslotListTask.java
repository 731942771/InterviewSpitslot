package com.cuiweiyou.interviewspitslot.task;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.cuiweiyou.interviewspitslot.back.SpitslotBack;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * <b>类名</b>: SpitslotListTask.java，点击进入口水item祥页后的剩余列表 <br/>
 * <b>说明</b>: execute须要一个参数，即item口水的id<br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class SpitslotListTask extends AsyncTask<String, Void, List<SpitslotBean>> {
	
	private SpitslotBack back;
	private Activity aty;

	/**
	 * <b>类名</b>: SpitslotListTask.java，点击进入口水item祥页后的剩余列表 <br/>
	 * <b>说明</b>: execute须要一个参数，即item口水的职位id<br/> */
	public SpitslotListTask(Activity aty, SpitslotBack back) {
		this.aty = aty;
		this.back = back;
	}

	@Override
	protected List<SpitslotBean> doInBackground(String... params) {
		List<SpitslotBean> list = null;
		
		String url = Configuration.HOST + "/getspitslotlist.php?s_station_id=" + params[0] + "&s_id=" + params[1];
		
		try {
			String spitslot = HttpRequestAndPostUtil.getJsonObject(url);
			
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
