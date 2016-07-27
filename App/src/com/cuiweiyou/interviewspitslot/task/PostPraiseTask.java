package com.cuiweiyou.interviewspitslot.task;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;

import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;

import android.os.AsyncTask;

/** 点赞数量发送。参数：骚文id和赞的数量 */
public class PostPraiseTask extends AsyncTask<String, Void, Integer> {

	@Override
	protected Integer doInBackground(String... params) {
		String id = params[0];
		String count = params[1];
		
		String url = Configuration.HOST + "/putarticlepraisecount.php?id=" + id + "&count=" + count;
		
		int i = 0; // 此sql，返回1为成功
		
		try {
			String json = HttpRequestAndPostUtil.getJsonObject(url);
			i = Integer.parseInt(json);
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return i;
	}

}
