package com.simi.po.model.xcloud;

public class TotalCompany {
    private Long id;

    private Long companyId;

    private Integer totalBarcode;

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

    public Integer getTotalBarcode() {
        return totalBarcode;
    }

    public void setTotalBarcode(Integer totalBarcode) {
        this.totalBarcode = totalBarcode;
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