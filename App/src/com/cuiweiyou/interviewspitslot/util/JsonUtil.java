package com.cuiweiyou.interviewspitslot.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cuiweiyou.interviewspitslot.bean.ArticleBean;
import com.cuiweiyou.interviewspitslot.bean.CompanyBean;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;
import com.cuiweiyou.interviewspitslot.bean.StationBean;
import com.cuiweiyou.interviewspitslot.bean.VersionBean;

/**
 * <b>类名</b>: JsonUtil.java，JSON解析工具 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月22日_上午11:53:19 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class JsonUtil {

	private JsonUtil(){}
	
	/**
	 * <b>功能</b>：getNewVersion，将jsonObject解析为bean <br/>
	 * <b>说明</b>: 解析失败返回null<br/>
	 * <b>创建</b>：2016年6月22日下午12:03:58<br/>
	 * 
	 * @param json jsonObject数据
	 * 
	 * @return VersionBean 版本信息封装体 <br/>
	 */
	public static VersionBean getNewVersion(String json){
		
		try {
			JSONObject jobj = new JSONObject(json);
			int version = jobj.getInt("desc_version");
			String description = jobj.getString("descdesc");
			int versionAble = jobj.getInt("able_version");
			String descAble = jobj.getString("able_descdesc");
			String nameAble = jobj.getString("able_apkname");
			String url = jobj.getString("url");
			
			return new VersionBean(version, description, versionAble, descAble, nameAble, url);
		} catch (JSONException e) {
			e.printStackTrace();
			
			return null;
		}
	}

	/**
	 * <b>功能</b>：getSpitslotList，由json提取口水列表 <br/>
	 * <b>说明</b>: <br/>
	 * 
	 * @param spitslot
	 * @return 口水bean集合
	 */
	public static List<SpitslotBean> getSpitslotList(String spitslot) {
		List<SpitslotBean> list = new ArrayList<SpitslotBean>();
		
		if(null == spitslot || "".equals(spitslot) || "]".equals(spitslot))
			return list;
		
		try {
			JSONArray jarr = new JSONArray(spitslot);
			int length = jarr.length();
			for (int i = 0; i < length; i++) {
				JSONObject jobj = jarr.getJSONObject(i);
				
				int id = jobj.getInt("id");
				int company_id = jobj.getInt("company_id");
				String company_name = jobj.getString("company_name");
				String address = jobj.getString("address");
				int station_id = jobj.getInt("station_id");
				String station_name = jobj.getString("station_name");
				int user_id = jobj.getInt("user_id");
				String user_name = jobj.getString("user_name");
				String date_view = jobj.getString("date_view");
				String description = jobj.getString("description");
				String praise_count = jobj.getString("praise_count");
				String record_time = jobj.getString("record_time");
				String note = jobj.getString("note");
				
				list.add(new SpitslotBean(id, company_id, company_name, address, station_id, station_name, user_id, user_name, date_view, description, praise_count, record_time, note));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static List<CompanyBean> getCompanyList(String json) {
		List<CompanyBean> list = new ArrayList<CompanyBean>();
		
		if(null == json || "".equals(json))
			return list;
		
		try {
			JSONArray jarr = new JSONArray(json);
			int length = jarr.length();
			for (int i = 0; i < length; i++) {
				JSONObject jobj = jarr.getJSONObject(i);
				
				CompanyBean bean = (CompanyBean) json2Bean(jobj.toString(), CompanyBean.class);
				
				list.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static List<StationBean> getStationList(String json) {
		List<StationBean> list = new ArrayList<StationBean>();
		
		if(null == json || "".equals(json))
			return list;
		
		try {
			JSONArray jarr = new JSONArray(json);
			int length = jarr.length();
			for (int i = 0; i < length; i++) {
				JSONObject jobj = jarr.getJSONObject(i);
				
				StationBean bean = (StationBean) json2Bean(jobj.toString(), StationBean.class);
				
				list.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static List<ArticleBean> getArticleList(String article_json) {
		List<ArticleBean> list = new ArrayList<ArticleBean>();
		
		if(null == article_json || "".equals(article_json))
			return list;
		
		try {
			JSONArray jarr = new JSONArray(article_json);
			int length = jarr.length();
			for (int i = 0; i < length; i++) {
				JSONObject jobj = jarr.getJSONObject(i);
				
				int id = jobj.getInt("id");
				int user_id = jobj.getInt("user_id");
				String user_name = jobj.getString("user_name");
				String title = jobj.getString("title");
				String article = jobj.getString("article");
				String date_add = jobj.getString("date_add");
				int praise_count = jobj.getInt("praise_count");
				String description = jobj.getString("description");
				String note = jobj.getString("note");
				
				list.add(new ArticleBean(id, user_id, user_name, title, article, date_add, praise_count, description, note));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * <b>功能</b>：bean2Json， Object(bean)转json字符串(jobj/jarr)<br/>
	 * <b>说明</b>: 通用<br/>
	 * 
	 * @return str
	 */
	public static String bean2Json(Object obj){
		if(null == obj)
			return null;
		
		return com.alibaba.fastjson.JSON.toJSONString(obj);
	}

	/**
	 * <b>功能</b>：json2Bean，字符串json转Object <br/>
	 * <b>说明</b>: 通用<br/>
	 * @param json  字串
	 * @param clazz bean的类文件
	 * 
	 * @return Object
	 */
	public static Object json2Bean(String json, Class clazz) {
		if(null == json || "".equals(json))
			return null;

		return com.alibaba.fastjson.JSONObject.parseObject(json, clazz);
	}
}
