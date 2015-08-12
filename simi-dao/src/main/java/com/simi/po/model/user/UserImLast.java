package com.simi.po.model.user;

public class UserImLast {
    private Long id;

    private Long userId;

    private String imUserName;

    private Long fromUserId;

    private String fromImUserName;

    private String lastIm;

    private Long lastImTime;

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

    public String getImUserName() {
        return imUserName;
    }

    public void setImUserName(String imUserName) {
        this.imUserName = imUserName == null ? null : imUserName.trim();
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromImUserName() {
        return fromImUserName;
    }

    public void setFromImUserName(String fromImUserName) {
        this.fromImUserName = fromImUserName == null ? null : fromImUserName.trim();
    }

    public String getLastIm() {
        return lastIm;
    }

    public void setLastIm(String lastIm) {
        this.lastIm = lastIm == null ? null : lastIm.trim();
    }

    public Long getLastImTime() {
        return lastImTime;
    }

    public void setLastImTime(Long lastImTime) {
        this.lastImTime = lastImTime;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}