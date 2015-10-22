package com.simi.vo.card;

import com.simi.po.model.card.Cards;

public class CardRemindVo {
	
	private Long cardId;
	
    private Short cardType;
    
    private String cardTypeName;
    
    private Long serviceTime;
    
    private Long remindTime;
    
    private String remindContent;

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

	public Long getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Long remindTime) {
		this.remindTime = remindTime;
	}

	public String getRemindContent() {
		return remindContent;
	}

	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}
	
}
