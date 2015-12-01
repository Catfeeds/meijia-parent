package com.simi.po.model.weather;

public class WeatherArea {
    private Long areaId;

    private String nameEn;

    private String nameCn;

    private String districtEn;

    private String districtCn;

    private String provEn;

    private String provCn;

    private String nationEn;

    private String nationCn;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn == null ? null : nameCn.trim();
    }

    public String getDistrictEn() {
        return districtEn;
    }

    public void setDistrictEn(String districtEn) {
        this.districtEn = districtEn == null ? null : districtEn.trim();
    }

    public String getDistrictCn() {
        return districtCn;
    }

    public void setDistrictCn(String districtCn) {
        this.districtCn = districtCn == null ? null : districtCn.trim();
    }

    public String getProvEn() {
        return provEn;
    }

    public void setProvEn(String provEn) {
        this.provEn = provEn == null ? null : provEn.trim();
    }

    public String getProvCn() {
        return provCn;
    }

    public void setProvCn(String provCn) {
        this.provCn = provCn == null ? null : provCn.trim();
    }

    public String getNationEn() {
        return nationEn;
    }

    public void setNationEn(String nationEn) {
        this.nationEn = nationEn == null ? null : nationEn.trim();
    }

    public String getNationCn() {
        return nationCn;
    }

    public void setNationCn(String nationCn) {
        this.nationCn = nationCn == null ? null : nationCn.trim();
    }
}