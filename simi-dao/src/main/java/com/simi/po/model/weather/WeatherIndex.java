package com.simi.po.model.weather;

public class WeatherIndex {
    private Long id;

    private Long areaId;

    private String areaCn;

    private String publicTime;

    private String i1;

    private String i2;

    private String i3;

    private String i4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaCn() {
        return areaCn;
    }

    public void setAreaCn(String areaCn) {
        this.areaCn = areaCn == null ? null : areaCn.trim();
    }

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    public String getI1() {
        return i1;
    }

    public void setI1(String i1) {
        this.i1 = i1 == null ? null : i1.trim();
    }

    public String getI2() {
        return i2;
    }

    public void setI2(String i2) {
        this.i2 = i2 == null ? null : i2.trim();
    }

    public String getI3() {
        return i3;
    }

    public void setI3(String i3) {
        this.i3 = i3 == null ? null : i3.trim();
    }

    public String getI4() {
        return i4;
    }

    public void setI4(String i4) {
        this.i4 = i4 == null ? null : i4.trim();
    }
}