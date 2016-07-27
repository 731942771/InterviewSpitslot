package com.cuiweiyou.interviewspitslot.app;

import com.cuiweiyou.interviewspitslot.util.CrashHandler;

import android.app.Application;
import android.content.Context;

/**
 * <b>类名</b>: RootApplication.java，全局的Application对象 <br/>
 * <b>说明</b>: <br/>
 * &emsp;&emsp; 提供getAppContext()方法获取全局ctx <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class RootApplication extends Application{
	
	public static RootApplication mRootApp;

	@Override
	public void onCreate() {
		super.onCreate();
		
		mRootApp = this;
		
        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
	}
	
	/**
	 * <b>功能</b>：getAppContext，获取全局ctx <br/>
	 * 
	 * @return Context 全局ctx
	 * @author cuiweiyou.com
	 */
	public static Context getAppContext() {
		return mRootApp;
	}
}
