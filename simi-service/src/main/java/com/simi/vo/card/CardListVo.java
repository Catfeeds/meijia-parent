package com.simi.vo.card;

import java.util.List;

import com.simi.po.model.card.CardAttend;

public class CardListVo {
	
	private Long cardId;
	
	private Short cardType;
	
	private String cardTypeName;
	
	private List<CardAttend> attends;
	
	private String headImgCreateUser;
	
	private Long createUserId;
	
	private String title;
	
	private Long serviceTime;
	
	private String serviceContent;
	
	private String serviceAddr;
	
	private String addTimeStr;
	
	private int totalZan;
	
	private int totalComment;	
	
	private String cardExtra;
	
	private Short status;

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public Short getCardType() {
		return cardType;
	}

	public void setCardType(Short cardType) {
		this.cardType = cardType;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public Long getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Long serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
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

	public String getCardExtra() {
		return cardExtra;
	}

	public void setCardExtra(String cardExtra) {
		this.cardExtra = cardExtra;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<CardAttend> getAttends() {
		return attends;
	}

	public void setAttends(List<CardAttend> attends) {
		this.attends = attends;
	}

	public String getServiceAddr() {
		return serviceAddr;
	}

	public void setServiceAddr(String serviceAddr) {
		this.serviceAddr = serviceAddr;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getHeadImgCreateUser() {
		return headImgCreateUser;
	}

	public void setHeadImgCreateUser(String headImgCreateUser) {
		this.headImgCreateUser = headImgCreateUser;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
		
}
