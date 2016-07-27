package com.cuiweiyou.interviewspitslot.bean;

/**
 * <b>类名</b>: VersionBean.java，最新版本数据的封装体 <br/>
 * <b>说明</b>: <br/>
 * <b>创建</b>: 2016-2016年6月22日_下午12:05:04 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class VersionBean {
	int version;
	String description;
	int versionAble;
	String descAble;
	String nameAble;
	String url;

	/**
	 * <b>功能</b>：VersionBean.java，最新版本数据的封装体 <br/>
	 * <b>创建</b>：2016年6月22日下午12:06:50<br/>
	 * 
	 * @param version 更新提示版本号
	 * @param description 更新提示描述
	 * @param versionAble 可更新版本号
	 * @param descAble 可更新描述
	 * @param nameAble 可更新apk的文件名
	 * @param url 新apk地址
	 * @author cuiweiyou.com
	 */
	public VersionBean(int version, String description, int versionAble, String descAble, String nameAble, String url) {
		super();
		this.version = version;
		this.description = description;
		this.versionAble = versionAble;
		this.descAble = descAble;
		this.nameAble = nameAble;
		this.url = url;
	}

	/**
	 * <b>功能</b>：getVersion，获取 更新提示版本号 <br/>
	 */
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * <b>功能</b>：getVersion，获取 可更新版本号 <br/>
	 */
	public int getVersionAble() {
		return versionAble;
	}

	public void setVersionAble(int versionAble) {
		this.versionAble = versionAble;
	}

	public String getDescAble() {
		return descAble;
	}

	public void setDescAble(String descAble) {
		this.descAble = descAble;
	}

	public String getNameAble() {
		return nameAble;
	}

	public void setNameAble(String nameAble) {
		this.nameAble = nameAble;
	}

	@Override
	public String toString() {
		return "VersionBean [version=" + version + ", description=" + description + ", versionAble=" + versionAble + ", descAble=" + descAble + ", nameAble=" + nameAble + ", url=" + url + "]";
	}

}
