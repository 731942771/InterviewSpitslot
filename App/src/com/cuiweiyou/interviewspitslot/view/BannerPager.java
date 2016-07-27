package com.cuiweiyou.interviewspitslot.view;

import java.lang.reflect.Field;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * banner广告ViewPager<br/>
 * 自动切换幻灯片
 * @author cuiweiyou.com
 */
public class BannerPager extends ViewPager {
	
	/** 通过适配器得到幻灯片数量 **/
	private int count = 0;
	/** 幻灯片索引 **/
	private int index = 0;
	private BannerThread bannerThread;
	
	public BannerPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(this.getContext(), new AccelerateInterpolator());
            field.set(this, scroller);
            scroller.setmDuration(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

		bannerThread = new BannerThread();
		bannerThread.start();
	}
	
	/** <b>功能</b>：updateIndex，根据方向加减索引 <br/> */
	public void updateIndex(int index){
		this.index = index;
	}
	
	/**
	 * 开启banner广告自动切换幻灯片<br/>
	 * 须在setAdapter之后执行
	 */
	public void startCarousel(){
		PagerAdapter adapter = getAdapter();
		if(null != adapter)
			count = adapter.getCount();
		
		bannerThread.canRunning = true;
	}
	
	/** 停止轮播 */
	public void stopCarousel(){

		bannerThread.canRunning = false;
	}
	
	//接受定时器消息handler
	private Handler hand = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			++index;
			
			if(index == getCurrentItem())	//** 手动更新后
				++index;
			
			if(index >= count){
				index = 0;
			}
			
			// 自动切换
			setCurrentItem(index);
		};
	};
	
	/**
	 * viewpager自动切换定时线程
	 * @author Administrator
	 */
	private class BannerThread extends Thread{
		public boolean canRunning = false;
		@Override
		public void run(){
			while(true){
				try {
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(canRunning){
					hand.sendEmptyMessage(0);
				}
			}
		}
	}
	
	private class FixedSpeedScroller extends Scroller {
	    private int mDuration = 1500;

	    public FixedSpeedScroller(Context context) {
	        super(context);
	    }

	    public FixedSpeedScroller(Context context, Interpolator interpolator) {
	        super(context, interpolator);
	    }

	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
	        super.startScroll(startX, startY, dx, dy, mDuration);
	    }

	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy) {
	        super.startScroll(startX, startY, dx, dy, mDuration);
	    }

	    public void setmDuration(int time) {
	        mDuration = time;
	    }

	    public int getmDuration() {
	        return mDuration;
	    }
	}
}
