package com.cuiweiyou.interviewspitslot.task;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;

import com.cuiweiyou.interviewspitslot.back.PostArticleBack;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.DatetimeUtil;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;
import com.cuiweiyou.interviewspitslot.util.SharedPrefUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * <b>类名</b>: ArticlePostTask.java，骚文发布 <br/>
 * <b>说明</b>: execute参数1为标题，参数2为正文<br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class ArticlePostTask extends AsyncTask<String, Void, Integer> {
	
	private PostArticleBack back;
	private Activity aty;

	/**
	 * <b>功能</b>：ArticlePostTask.java， 发布骚文。execute参数1为标题，参数2为正文<br/>
	 * @author cuiweiyou.com
	 */
	public ArticlePostTask(Activity aty, PostArticleBack back) {
		this.aty = aty;
		this.back = back;
	}

	@Override
	protected Integer doInBackground(String... params) {
		String title = params[0];
		String article = params[1];
		String args = 
				"user_id=" + SharedPrefUtil.getUserID() +
				"&title=" + title + 
				"&article=" + article +
				"&date_add=" + DatetimeUtil.getNowDate() + 
				"&description=" + 
				"&note=";
		int result = 0;
		
		try {
			result = HttpRequestAndPostUtil.post(Configuration.HOST + "/postarticle.php", args);
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
			back.getResult(result);
		}
		
		aty = null;
		back = null;
	}
}
