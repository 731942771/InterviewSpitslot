package com.cuiweiyou.interviewspitslot.util;

import com.cuiweiyou.interviewspitslot.app.RootApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * <b>类名</b>: SharedPrefUtil.java，小数据存储SP工具 <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class SharedPrefUtil {

	private static SharedPreferences mSP;

	private SharedPrefUtil(){
	}

	private static void getSP() {
		mSP = RootApplication.getAppContext().getSharedPreferences("cuiweiyou", Context.MODE_PRIVATE);
	}

	/** 保存当前用户id，id须要联网获取，有则get无则新建 */
	public static void setUserID(int id) {
		if(null == mSP)
			getSP();
		
		Editor edit = mSP.edit();
		edit.putInt("user_id", id);
		edit.commit();
	}
	
	/** 查询当前用户id。返回0表示没有用户id */
	public static int getUserID() {
		if(null == mSP)
			getSP();
		
		return mSP.getInt("user_id", 0);
	}

	/** 保存当前用户name */
	public static void setUserName(String name) {
		if(null == mSP)
			getSP();

		Editor edit = mSP.edit();
		edit.putString("user_name", name);
		edit.commit();
	}

	/** 查询当前用户name。返回""表示没有用户 */
	public static String getUserName() {
		if(null == mSP)
			getSP();
		
		return mSP.getString("user_name", "");
	}

	/** 启动区分wifi与非wifi。1-true不区分，0区分 */
	public static void setCheckWifi(int i) {
		if(null == mSP)
			getSP();

		Editor edit = mSP.edit();
		edit.putInt("check_wifi", i);
		edit.commit();
	}
	
	/** 检查是否区分wifi与非wifi。1 true 不区分，0区分(默认) */
	public static int getCheckWifi() {
		if(null == mSP)
			getSP();
		
		return mSP.getInt("check_wifi", 0);
	}

	/** 启动自动检测更新。1-true自动检测，0不自动 */
	public static void setCheckUpdate(int i) {
		if(null == mSP)
			getSP();

		Editor edit = mSP.edit();
		edit.putInt("check_update", i);
		edit.commit();
	}
	
	/** 检查是否启动自动检测更新。1-true检测(默认)，0不检测 */
	public static int getCheckUpdate() {
		if(null == mSP)
			getSP();
		
		return mSP.getInt("check_update", 1);
	}
	
	/** 仅wifi下启动自动检测更新。1-true是，0不必 */
	public static void setCheckUpdateOnlyWifi(int i) {
		if(null == mSP)
			getSP();
		
		Editor edit = mSP.edit();
		edit.putInt("check_update_onlywifi", i);
		edit.commit();
	}
	
	/** 检查是否仅wifi下启动自动检测更新。1-true是(默认)，0非wifi也可以 */
	public static int getCheckUpdateOnlyWifi() {
		if(null == mSP)
			getSP();
		
		return mSP.getInt("check_update_onlywifi", 1);
	}
	
	/** 是否接收更新推送。1-true是，0否 */
	public static void setAcceptUpdatePush(int i) {
		if(null == mSP)
			getSP();
		
		Editor edit = mSP.edit();
		edit.putInt("accept_update_push", i);
		edit.commit();
	}
	
	/** 是否接收更新推送。1是(默认)，0否 */
	public static int getAcceptUpdatePush() {
		if(null == mSP)
			getSP();
		
		return mSP.getInt("accept_update_push", 1);
	}

	/** 保存服务端ip地址 */
	public static void setSerAddr(String string) {
		if(null == mSP)
			getSP();
		
		Editor edit = mSP.edit();
		edit.putString("ser_addr", string);
		edit.commit();
	}
	
	/** 提取服务端ip */
	public static String getSerAddr() {
		if(null == mSP)
			getSP();
		
		return mSP.getString("ser_addr", "");
	}

	/** 是否是第一次安装应用 */
	public static void setIsFirstInstall(Boolean is_first) {
		if(null == mSP)
			getSP();
		
		Editor edit = mSP.edit();
		edit.putBoolean("is_first", is_first);
		edit.commit();
	}
	
	/** 是否是第一次安装应用。第一次返回ture */
	public static Boolean getIsFirstInstall() {
		if(null == mSP)
			getSP();
		
		return mSP.getBoolean("is_first", true);
	}
}
