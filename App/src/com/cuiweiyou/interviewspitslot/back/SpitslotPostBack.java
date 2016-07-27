package com.cuiweiyou.interviewspitslot.back;

/** 发表口水 */
public interface SpitslotPostBack {

	/** 发表口水返回码。0为失败，非0为插入成功的条目id */
	void postResult(Integer result);

}
