package com.cuiweiyou.interviewspitslot.bean;

/** 岗位封装 */
public class StationBean {
	int id;
	String name;
	int company_id;
	String description;
	String note;

	public StationBean() {
	}

	/**
	 * <b>功能</b>：StationBean.java，岗位封装 <br/>
	 * <b>创建</b>：2016年7月19日下午4:09:45<br/>
	 * 
	 * @param id 职位id
	 * @param name 职位名称
	 * @param company_id 所属公司的id
	 * @param description 职位描述
	 * @param note 备注
	 * 
	 * @author cuiweiyou.com
	 */
	public StationBean(int id, String name, int company_id, String description, String note) {
		super();
		this.id = id;
		this.name = name;
		this.company_id = company_id;
		this.description = description;
		this.note = note;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "StationBean [id=" + id + ", name=" + name + ", company_id=" + company_id + ", description=" + description + ", note=" + note + "]";
	}

}
