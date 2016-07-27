package com.cuiweiyou.interviewspitslot.back;

import java.util.List;

import com.cuiweiyou.interviewspitslot.bean.CompanyBean;

/** 拉去公司列表回调 */
public interface CompanyListBack {
	
	/** 拉去公司列表回调 */
	public void getCompanyList(List<CompanyBean> list);

}
