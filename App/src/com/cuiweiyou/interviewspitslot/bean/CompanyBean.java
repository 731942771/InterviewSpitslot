package com.cuiweiyou.interviewspitslot.bean;

/**
 * 公司数据封装体<br/>
 * 公司还有label-标签、station-职位
 * 
 * @author cuiweiyou.com
 */
public class CompanyBean {

	int id;
	String name;
	String address;
	String description;
	String note;

	public CompanyBean() {
	}

	/**
	 * <b>功能</b>：CompanyBean.java，公司数据封装体 <br/>
	 * 除了这些参数，公司还有label-标签、station-职位
	 * 
	 * @param id 公司id
	 * @param name 名称
	 * @param address 地址
	 * @param description  描述
	 * @param note 备注
	 * 
	 * @author cuiweiyou.com
	 */
	public CompanyBean(int id, String name, String address, String description, String note) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		return "CompanyBean [id=" + id + ", name=" + name + ", address=" + address + ", description=" + description + ", note=" + note + "]";
	}

}
