package com.example.demo2.model;

import java.io.Serializable;
import java.util.Date;


public class Paper implements Serializable {


	private int paperId;

	private int catId;

	private String url;

	private String title;

	private String remarks;

	private Date createTime;


	public Paper() {
	}

	public Paper(int paperId, int catId, String url, String title) {
		this.paperId = paperId;
		this.catId = catId;
		this.url = url;
		this.title = title;
	}

	public Paper( int catId, String url, String title) {
		this.catId = catId;
		this.url = url;
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
