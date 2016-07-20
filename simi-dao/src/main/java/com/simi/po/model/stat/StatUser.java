package com.simi.po.model.stat;

public class StatUser {
    private Long userId;

    private Integer totalCards;

    private Integer totalFeeds;

    private Integer totalCompanys;

    private Integer totalOrders;

    private Long addTime;

    private Long updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(Integer totalCards) {
        this.totalCards = totalCards;
    }

    public Integer getTotalFeeds() {
        return totalFeeds;
    }

    public void setTotalFeeds(Integer totalFeeds) {
        this.totalFeeds = totalFeeds;
    }

    public Integer getTotalCompanys() {
        return totalCompanys;
    }

    public void setTotalCompanys(Integer totalCompanys) {
        this.totalCompanys = totalCompanys;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
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
}