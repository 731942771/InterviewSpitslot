package com.cuiweiyou.interviewspitslot.task;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import com.cuiweiyou.interviewspitslot.back.CountBack;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/** 
 * <b>类名</b>: CountTask.java，请求一个统计数据的异步器 <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class CountTask extends AsyncTask<String, Void, Integer> {
	
	private Activity aty;
	private CountBack back;
	private String url;

	public CountTask(Activity aty, CountBack back, String url) {
		this.aty = aty;
		this.back = back;
		this.url = url;
	}

	@Override
	protected Integer doInBackground(String... params) {
		int i = 0;
		
//		url = Configuration.HOST + "/getspitslotcount.php?company_id=" + params[0]; // 公司被吐槽的次数
//		url = Configuration.HOST + "/getarticlepraisecount?id=" + params[0]; // 骚文被点赞的总数
		url = Configuration.HOST + url + params[0];
		
		try {
			String result = HttpRequestAndPostUtil.getJsonObject(url);
			i = Integer.parseInt(result);
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
		
		return i;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		
		back.getCount(result);
	}
}
