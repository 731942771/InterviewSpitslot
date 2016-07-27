package com.cuiweiyou.interviewspitslot.util;

import com.cuiweiyou.interviewspitslot.app.RootApplication;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtil {
	
	private ScreenUtil(){}
	
	private static int width = 0;
	private static int height = 0;
	
	public static int getScreenWidth(){
		if(0 == width){
			
			WindowManager wm = (WindowManager) RootApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics outMetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(outMetrics);
			width = outMetrics.widthPixels;
			height = outMetrics.heightPixels;
		}
		
		return width;
	}
	
	public static int getScreenHeight(){
		if(0 == height){
			
			WindowManager wm = (WindowManager) RootApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics outMetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(outMetrics);
			width = outMetrics.widthPixels;
			height = outMetrics.heightPixels;
		}
		
		return height;
	}

}
