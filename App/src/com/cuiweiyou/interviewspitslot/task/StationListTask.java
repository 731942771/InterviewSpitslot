package com.cuiweiyou.interviewspitslot.task;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.cuiweiyou.interviewspitslot.back.StationListBack;
import com.cuiweiyou.interviewspitslot.bean.StationBean;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * <b>类名</b>: StationListTask.java， 根据公司id请求旗下职位集合<br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class StationListTask extends AsyncTask<String, Void, List<StationBean>> {
	
	private StationListBack back;
	private Activity aty;

	/** <b>类名</b>: StationListTask.java， 根据公司id请求旗下职位集合<br/> */
	public StationListTask(Activity aty, StationListBack back) {
		this.aty = aty;
		this.back = back;
	}

	@Override
	protected List<StationBean> doInBackground(String... params) {
		List<StationBean> list = null;
		
		try {
			String json = HttpRequestAndPostUtil.getJsonObject(Configuration.HOST + "/getstationlist.php?company_id=" + params[0]);
			list = JsonUtil.getStationList(json);
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
	protected void onPostExecute(List<StationBean> result) {
		super.onPostExecute(result);

		back.getStationList(result);
		
		aty = null;
		back = null;
	}
}
