package com.cuiweiyou.interviewspitslot.task;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;

import com.cuiweiyou.interviewspitslot.conf.Configuration;
import com.cuiweiyou.interviewspitslot.util.HttpRequestAndPostUtil;

import android.os.AsyncTask;
import android.util.Log;

public class BugPostTask extends AsyncTask<String, Void, Void> {

	@Override
	protected Void doInBackground(String... params) {

		try {
			HttpRequestAndPostUtil.post(Configuration.HOST + "/postbug.php", params[0]);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
