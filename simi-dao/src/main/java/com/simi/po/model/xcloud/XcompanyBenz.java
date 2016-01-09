package com.simi.po.model.xcloud;

public class XcompanyBenz {
    private Long benzId;

    private Long companyId;

    private String name;

    private Long addTime;

    public Long getBenzId() {
        return benzId;
    }

    public void setBenzId(Long benzId) {
        this.benzId = benzId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}