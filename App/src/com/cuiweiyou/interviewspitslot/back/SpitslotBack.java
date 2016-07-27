package com.cuiweiyou.interviewspitslot.back;

import java.util.List;

import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;

/**
 * <b>类名</b>: SpitslotBack.java，远程数据回调 <br/>
 * <b>说明</b>: 口水集合<br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public interface SpitslotBack {

	/**
	 * <b>类名</b>: SpitslotBack.java，远程数据回调 <br/>
	 * <b>说明</b>: 口水集合<br/>
	 */
	public void getSpitslots(List<SpitslotBean> spitslots);
}
