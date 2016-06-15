package com.simi.po.model.xcloud;

public class XcompanySetting {
    private Long id;

    private Long companyId;

    private Long userId;

    private String name;

    private String settingType;

    private String settingJson;

    private Object settingValue;

    private Short isEnable;

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

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType == null ? null : settingType.trim();
    }

    public String getSettingJson() {
        return settingJson;
    }

    public void setSettingJson(String settingJson) {
        this.settingJson = settingJson == null ? null : settingJson.trim();
    }

    public Object getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(Object settingValue) {
        this.settingValue = settingValue;
    }

    public Short getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Short isEnable) {
        this.isEnable = isEnable;
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