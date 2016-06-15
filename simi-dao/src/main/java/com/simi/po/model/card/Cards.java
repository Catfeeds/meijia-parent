package com.simi.po.model.card;

public class Cards {
    private Long cardId;

    private Long createUserId;

    private Long userId;

    private Short cardType;
    
    private String title;

    private Long serviceTime;
    
    private Short period;
    
    private String periodName;

    private String serviceAddr;

    private String serviceContent;

    private Short setRemind;

    private Short setNowSend;

    private Short setSecDo;

    private String setSecRemarks;
    
    private String secRemarks;

    private Short status;
    
    private Short setFriendView;
       
    private Long addTime;

    private Long updateTime;
    
    private String cardExtra;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getCardType() {
        return cardType;
    }

    public void setCardType(Short cardType) {
        this.cardType = cardType;
    }

    public Long getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Long serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getServiceAddr() {
        return serviceAddr;
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr == null ? null : serviceAddr.trim();
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public Short getSetRemind() {
        return setRemind;
    }

    public void setSetRemind(Short setRemind) {
        this.setRemind = setRemind;
    }

    public Short getSetNowSend() {
        return setNowSend;
    }

    public void setSetNowSend(Short setNowSend) {
        this.setNowSend = setNowSend;
    }

    public Short getSetSecDo() {
        return setSecDo;
    }

    public void setSetSecDo(Short setSecDo) {
        this.setSecDo = setSecDo;
    }

    public String getSetSecRemarks() {
        return setSecRemarks;
    }

    public void setSetSecRemarks(String setSecRemarks) {
        this.setSecRemarks = setSecRemarks == null ? null : setSecRemarks.trim();
    }

    public Short getStatus() {
        return status;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Short getSetFriendView() {
		return setFriendView;
	}

	public void setSetFriendView(Short setFriendView) {
		this.setFriendView = setFriendView;
	}

	public void setStatus(Short status) {
        this.status = status;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

	public String getSecRemarks() {
		return secRemarks;
	}

	public void setSecRemarks(String secRemarks) {
		this.secRemarks = secRemarks;
	}

	public String getCardExtra() {
		return cardExtra;
	}

	public void setCardExtra(String cardExtra) {
		this.cardExtra = cardExtra;
	}

	public Short getPeriod() {
		return period;
	}

	public void setPeriod(Short period) {
		this.period = period;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
}