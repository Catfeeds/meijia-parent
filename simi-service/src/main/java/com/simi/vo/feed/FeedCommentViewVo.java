package com.simi.vo.feed;

import com.simi.po.model.feed.FeedComment;

public class FeedCommentViewVo extends FeedComment {

	private String name;
	
	private String headImg;
	
	private String addTimeStr;
	
	private Integer totalZan;
	
	private Short isZan;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public Integer getTotalZan() {
		return totalZan;
	}

	public void setTotalZan(Integer totalZan) {
		this.totalZan = totalZan;
	}

	public Short getIsZan() {
		return isZan;
	}

	public void setIsZan(Short isZan) {
		this.isZan = isZan;
	}
}
