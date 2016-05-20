package com.simi.po.model.op;

public class OpAd {
    private Long id;

    private Short No;

    private String title;

    private String adType;

    private String serviceTypeIds;

    private String imgUrl;

    private String gotoType;

    private String action;

    private String gotoUrl;

    private Long addTime;

    private Long updateTime;

    private Short enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getNo() {
        return No;
    }

    public void setNo(Short No) {
        this.No = No;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType == null ? null : adType.trim();
    }

    public String getServiceTypeIds() {
        return serviceTypeIds;
    }

    public void setServiceTypeIds(String serviceTypeIds) {
        this.serviceTypeIds = serviceTypeIds == null ? null : serviceTypeIds.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getGotoType() {
        return gotoType;
    }

    public void setGotoType(String gotoType) {
        this.gotoType = gotoType == null ? null : gotoType.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getGotoUrl() {
        return gotoUrl;
    }

    public void setGotoUrl(String gotoUrl) {
        this.gotoUrl = gotoUrl == null ? null : gotoUrl.trim();
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

    public Short getEnable() {
        return enable;
    }

    public void setEnable(Short enable) {
        this.enable = enable;
    }

}