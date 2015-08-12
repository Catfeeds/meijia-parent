package com.simi.vo.card;

import com.simi.po.model.card.CardComment;

public class CardCommentViewVo extends CardComment {

	private String name;
	
	private String headImg;

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
}
