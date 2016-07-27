package com.cuiweiyou.interviewspitslot.task;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.cuiweiyou.interviewspitslot.back.ArticleBack;
import com.cuiweiyou.interviewspitslot.bean.ArticleBean;
import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;
import com.cuiweiyou.interviewspitslot.util.JsonUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * <b>类名</b>: ArticleTask.java，骚文拉取任务 <br/>
 * <b>说明</b>: 参数recommend，0拉取非推荐骚文，1为推荐的<br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class ArticleTask extends AsyncTask<String, Void, List<ArticleBean>> {
	
	private ArticleBack back;
	private Activity aty;

	public ArticleTask(Activity aty, ArticleBack back) {
		this.aty = aty;
		this.back = back;
	}

	@Override
	protected List<ArticleBean> doInBackground(String... params) {
		String recommend = params[0];
		
		List<ArticleBean> list = null;
		
		try {
			String article = HttpRequestAndPostUtil.getJsonObject(Configuration.HOST + "/getarticle.php?recommend=" + recommend);
			list = JsonUtil.getArticleList(article);
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
	protected void onPostExecute(List<ArticleBean> result) {
		super.onPostExecute(result);
		
		back.getArticles(result);
		
		aty = null;
		back = null;
	}
}
