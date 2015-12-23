package com.simi.vo.feed;

import com.simi.po.model.feed.FeedComment;

public class FeedCommentViewVo extends FeedComment {

	private String name;
	
	private String headImg;
	
	private String addTimeStr;

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
}
