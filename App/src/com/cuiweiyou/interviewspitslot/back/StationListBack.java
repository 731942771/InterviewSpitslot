package com.cuiweiyou.interviewspitslot.back;

import java.util.List;

import com.cuiweiyou.interviewspitslot.bean.StationBean;

/**
 * <b>类名</b>: StationListBack.java，拉去职位集合回调 <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public interface StationListBack {

	/**
	 * <b>类名</b>: StationListBack.java，拉去职位集合回调 <br/>
	 * <b>说明</b>: <br/>
	 * 
	 * @author cuiweiyou.com <br/>
	 */
	public void getStationList(List<StationBean> result);

}
