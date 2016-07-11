package com.simi.po.model.record;

public class RecordRates {
    private Long id;

    private Short rateType;

    private Long linkId;

    private Long userId;

    private String name;

    private Short rate;

    private String rateContent;

    private Long addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getRateType() {
        return rateType;
    }

    public void setRateType(Short rateType) {
        this.rateType = rateType;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getRate() {
        return rate;
    }

    public void setRate(Short rate) {
        this.rate = rate;
    }

    public String getRateContent() {
        return rateContent;
    }

    public void setRateContent(String rateContent) {
        this.rateContent = rateContent == null ? null : rateContent.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}