package com.cuiweiyou.interviewspitslot.back;

import java.util.List;

import com.cuiweiyou.interviewspitslot.bean.ArticleBean;

/**
 * 
 * <b>类名</b>: ArticleBack.java，远程请求骚文集合回调器 <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public interface ArticleBack {

	/** 
	 * <b>功能</b>：getArticles， 获取骚文数据集合<br/>
	 * <b>说明</b>: <br/>
	 * 
	 * @param spitslots
	 */
	public void getArticles(List<ArticleBean> spitslots);
}
