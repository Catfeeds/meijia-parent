package com.simi.po.model.xcloud;

public class XcompanyBenzTime {
    private Long id;

    private Long companyId;

    private Long benzId;

    private String checkIn;

    private String checkOut;

    private Integer flexibleMin;

    private Long addTime;

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

    public Long getBenzId() {
        return benzId;
    }

    public void setBenzId(Long benzId) {
        this.benzId = benzId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn == null ? null : checkIn.trim();
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut == null ? null : checkOut.trim();
    }

    public Integer getFlexibleMin() {
        return flexibleMin;
    }

    public void setFlexibleMin(Integer flexibleMin) {
        this.flexibleMin = flexibleMin;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}