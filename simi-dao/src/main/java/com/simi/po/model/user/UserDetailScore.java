package com.simi.po.model.user;

public class UserDetailScore {
    private Long id;

    private Long userId;

    private String mobile;

    private Integer score;

    private Short actionId;

    private Short isConsume;

    private Long addTime;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Short getActionId() {
        return actionId;
    }

    public void setActionId(Short actionId) {
        this.actionId = actionId;
    }

    public Short getIsConsume() {
        return isConsume;
    }

    public void setIsConsume(Short isConsume) {
        this.isConsume = isConsume;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}