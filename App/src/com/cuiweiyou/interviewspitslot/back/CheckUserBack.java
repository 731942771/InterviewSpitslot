package com.cuiweiyou.interviewspitslot.back;

public interface CheckUserBack {

	/** flag=0为onresume时校验用户，1为发表口水时的校验 */
	void getUserStatus(int flag);

}
