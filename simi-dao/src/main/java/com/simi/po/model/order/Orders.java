package com.simi.po.model.order;

public class Orders {
    private Long id;
    
    private Long secId;

    private Long userId;

    private String mobile;

    private Long cityId;

    private String orderNo;
    
    private Short orderPayType;

    private Long serviceType;
    
    private String serviceContent;

    private Long serviceDate;

    private Long startTime;

    private Long addrId;

    private String remarks;

    private Short orderFrom;

    private Short orderStatus;

    private Short orderRate;

    private Short isScore;

    private String orderRateContent;

    private Long addTime;

    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getServiceType() {
        return serviceType;
    }

    public void setServiceType(Long serviceType) {
        this.serviceType = serviceType;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getAddrId() {
        return addrId;
    }

    public void setAddrId(Long addrId) {
        this.addrId = addrId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Short getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(Short orderFrom) {
        this.orderFrom = orderFrom;
    }

    public Short getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Short getOrderRate() {
        return orderRate;
    }

    public void setOrderRate(Short orderRate) {
        this.orderRate = orderRate;
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

	public String getOrderRateContent() {
		return orderRateContent;
	}

	public void setOrderRateContent(String orderRateContent) {
		this.orderRateContent = orderRateContent;
	}

	public Short getIsScore() {
		return isScore;
	}

	public void setIsScore(Short isScore) {
		this.isScore = isScore;
	}

	public Long getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Long serviceDate) {
		this.serviceDate = serviceDate;
	}

	public Short getOrderPayType() {
		return orderPayType;
	}

	public void setOrderPayType(Short orderPayType) {
		this.orderPayType = orderPayType;
	}

	public Long getSecId() {
		return secId;
	}

	public void setSecId(Long secId) {
		this.secId = secId;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}
}