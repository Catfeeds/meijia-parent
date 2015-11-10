package com.simi.po.model.partners;

public class PartnerUsers {
    private Long id;

    private Long partnerId;

    private Long userId;
    
    private Long serviceTypeId;
    
    private Short responseTime;

    private Long addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public Short getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Short responseTime) {
		this.responseTime = responseTime;
	}
}