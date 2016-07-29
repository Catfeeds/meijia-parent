package com.simi.po.model.xcloud;

public class XcompanyCheckinStat {
    private Long id;

    private Long companyId;

    private Long staffId;

    private Long userId;

    private int cyear;

    private int cmonth;

    private Long cday;

    private Long cdayAm;

    private Long cdayPm;

    private Long cdayAmId;

    private Long cdayPmId;

    private Short isLate;

    private Short isEaryly;

    private Short isLeave;

    private Long adminId;

    private String remarks;

    private Long addTime;

    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getCyear() {
        return cyear;
    }

    public void setCyear(int cyear) {
        this.cyear = cyear;
    }

    public int getCmonth() {
        return cmonth;
    }

    public void setCmonth(int cmonth) {
        this.cmonth = cmonth;
    }

    public Long getCday() {
        return cday;
    }

    public void setCday(Long cday) {
        this.cday = cday;
    }

    public Long getCdayAm() {
        return cdayAm;
    }

    public void setCdayAm(Long cdayAm) {
        this.cdayAm = cdayAm;
    }

    public Long getCdayPm() {
        return cdayPm;
    }

    public void setCdayPm(Long cdayPm) {
        this.cdayPm = cdayPm;
    }

    public Long getCdayAmId() {
        return cdayAmId;
    }

    public void setCdayAmId(Long cdayAmId) {
        this.cdayAmId = cdayAmId;
    }

    public Long getCdayPmId() {
        return cdayPmId;
    }

    public void setCdayPmId(Long cdayPmId) {
        this.cdayPmId = cdayPmId;
    }

    public Short getIsLate() {
        return isLate;
    }

    public void setIsLate(Short isLate) {
        this.isLate = isLate;
    }

    public Short getIsEaryly() {
        return isEaryly;
    }

    public void setIsEaryly(Short isEaryly) {
        this.isEaryly = isEaryly;
    }

    public Short getIsLeave() {
        return isLeave;
    }

    public void setIsLeave(Short isLeave) {
        this.isLeave = isLeave;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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