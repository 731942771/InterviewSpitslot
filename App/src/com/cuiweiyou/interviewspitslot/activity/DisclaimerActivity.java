package com.cuiweiyou.interviewspitslot.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cuiweiyou.interviewspitslot.R;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

/** *
 * <b>类名</b>: DisclaimerActivity.java，用户协议 <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class DisclaimerActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disclaimer);
		
//		getHtml();
		
		WebView web = (WebView) findViewById(R.id.wv);
		// 访问 /assets/ 中的 a.html 文件。如果页面引用了不在 /assets/ 里的多媒体文件，很可能加载失败。 
		web.loadUrl("file:///android_asset/disclaimer.html");

		findViewById(R.id.back).setOnClickListener(this);
	}

	private String getHtml() {
		String path = getExternalCacheDir().getAbsolutePath() + "/disclaimer.html";
		File f = new File(path);
		
		if(f.exists())
			return path;
		
		FileOutputStream fos = null;
		try {
		    AssetManager am = getAssets();
		    InputStream is = am.open("disclaimer.html");
		    int stream = 0;
		    byte[] buffer = new byte[1024];
		    
		    fos = new FileOutputStream(f);
		    while ((stream = is.read(buffer)) != -1) {
		        fos.write(buffer, 0, stream);
		    }
		    fos.flush();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (fos != null)
		            fos.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		return path;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		}
	}
}
