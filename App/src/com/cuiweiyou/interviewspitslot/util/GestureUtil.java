package com.cuiweiyou.interviewspitslot.util;

import com.cuiweiyou.interviewspitslot.back.GestureBack;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureUtil extends GestureDetector.SimpleOnGestureListener{
	
	private GestureBack back;

	public GestureUtil(GestureBack back) {
		this.back = back;
	}
 
	@Override
	public boolean onDoubleTap(MotionEvent e) {
//		Toast.makeText(RootApplication.getAppContext(), "双击++", 0).show();
		back.clickType(0);
		return true;
	}
	
//	@Override
//	public boolean onSingleTapUp(MotionEvent e) {
////		Toast.makeText(RootApplication.getAppContext(), "单击---", 0).show();
//		return true;
//	}
//	
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
//		Toast.makeText(RootApplication.getAppContext(), "单击confirmed", 0).show();
		back.clickType(1);
		return true;
	}
}
