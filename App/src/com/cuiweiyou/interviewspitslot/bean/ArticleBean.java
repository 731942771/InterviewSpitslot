package com.cuiweiyou.interviewspitslot.bean;

public class ArticleBean {
	int id;
	int user_id;
	String user_name;
	String title;
	String article;
	String date_add;
	int praise_count;
	String description;
	String note;
	
	public ArticleBean() {
	}

	public ArticleBean(int id, int user_id, String user_name, String title, String article, String date_add, int praise_count, String description, String note) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.user_name = user_name;
		this.title = title;
		this.article = article;
		this.date_add = date_add;
		this.praise_count = praise_count;
		this.description = description;
		this.note = note;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getDate_add() {
		return date_add;
	}

	public void setDate_add(String date_add) {
		this.date_add = date_add;
	}

	public int getPraise_count() {
		return praise_count;
	}

	public void setPraise_count(int praise_count) {
		this.praise_count = praise_count;
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
		return "ArticleBean [id=" + id + ", user_id=" + user_id + ", user_name=" + user_name + ", title=" + title + ", article=" + article + ", date_add=" + date_add + ", praise_count=" + praise_count + ", description=" + description + ", note=" + note + "]";
	}

}
