package com.simi.po.model.partners;

public class PartnerUserPrices {
    private Long id;

    private Long partnerId;

    private Long userId;

    private Long servicePriceId;

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

    public Long getServicePriceId() {
        return servicePriceId;
    }

    public void setServicePriceId(Long servicePriceId) {
        this.servicePriceId = servicePriceId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}