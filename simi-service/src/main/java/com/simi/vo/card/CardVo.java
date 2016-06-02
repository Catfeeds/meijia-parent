package com.simi.vo.card;

import com.simi.po.model.card.Cards;

public class CardVo extends Cards {

	//用户姓名
	private String name ;
	
	private String mobile;
	
	private int totalZan;
	
	private int totalComment;
		
	private String cardTypeName;
		
	private String addTimeStr;
	
	private String attendUserName;
	
	public String getAttendUserName() {
		return attendUserName;
	}

	public void setAttendUserName(String attendUserName) {
		this.attendUserName = attendUserName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getTotalZan() {
		return totalZan;
	}

	public void setTotalZan(int totalZan) {
		this.totalZan = totalZan;
	}

	public int getTotalComment() {
		return totalComment;
	}

	public void setTotalComment(int totalComment) {
		this.totalComment = totalComment;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

}
