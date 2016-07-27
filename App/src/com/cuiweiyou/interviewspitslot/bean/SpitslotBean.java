package com.cuiweiyou.interviewspitslot.bean;

/**
 * <b>类名</b>: SpitslotBean.java，口水封装 <br/>
 * <b>说明</b>: <br/>
 * 	int id; 口水id<br/>
 *	int company_id; 吐槽的公司id<br/>
 *	String company_name; 公司名称<br/>
 *	String address; 公司地址/面试地址<br/>
 *	int station_id; 职位id<br/>
 *	String station_name; 职位名称<br/>
 *	int user_id; 吐槽者id<br/>
 *	String user_name; 吐槽者nickname<br/>
 *	String date_view; 面试时间<br/>
 *	String description; 口水<br/>
 *	String praise_count; 赞同数量<br/>
 *	String record_time; 发表吐槽时间<br/>
 *	String note; 其它备注<br/>
 * <b>创建</b>: 2016-2016年7月18日_下午6:42:24 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class SpitslotBean {
	int id;
	int company_id;
	String company_name;
	String address;
	int station_id;
	String station_name;
	int user_id;
	String user_name;
	String date_view;
	String description;
	String praise_count;
	String record_time;
	String note;
	
	public SpitslotBean() {
	}

	/**
	 * <b>功能</b>：SpitslotBean.java，口水封装 <br/>
	 * 
	 * 	int id; 口水id<br/>
	 *	int company_id; 吐槽的公司id<br/>
	 *	String company_name; 公司名称<br/>
	 *	String address; 公司地址/面试地址<br/>
	 *	int station_id; 职位id<br/>
	 *	String station_name; 职位名称<br/>
	 *	int user_id; 吐槽者id<br/>
	 *	String user_name; 吐槽者nickname<br/>
	 *	String date_view; 面试时间<br/>
	 *	String description; 口水<br/>
	 *	String praise_count; 赞同数量<br/>
	 *	String record_time; 发表吐槽时间<br/>
	 *	String note; 其它备注<br/>
	 *
	 * @author cuiweiyou.com
	 */
	public SpitslotBean(int id, int company_id, String company_name, String address, int station_id, String station_name, int user_id, String user_name, String date_view, String description, String praise_count, String record_time, String note) {
		super();
		this.id = id;
		this.company_id = company_id;
		this.company_name = company_name;
		this.address = address;
		this.station_id = station_id;
		this.station_name = station_name;
		this.user_id = user_id;
		this.user_name = user_name;
		this.date_view = date_view;
		this.description = description;
		this.praise_count = praise_count;
		this.record_time = record_time;
		this.note = note;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public int getStation_id() {
		return station_id;
	}

	public void setStation_id(int station_id) {
		this.station_id = station_id;
	}

	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getDate_view() {
		return date_view;
	}

	public void setDate_view(String date_view) {
		this.date_view = date_view;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPraise_count() {
		return praise_count;
	}

	public void setPraise_count(String praise_count) {
		this.praise_count = praise_count;
	}

	public String getRecord_tiem() {
		return record_time;
	}

	public void setRecord_tiem(String record_tiem) {
		this.record_time = record_tiem;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "SpitslotBean [id=" + id + ", company_id=" + company_id + ", company_name=" + company_name + ", address=" + address + ", station_id=" + station_id + ", station_name=" + station_name + ", user_id=" + user_id + ", user_name=" + user_name + ", date_view=" + date_view + ", description=" + description
				+ ", praise_count=" + praise_count + ", record_time=" + record_time + ", note=" + note + "]";
	}

}
