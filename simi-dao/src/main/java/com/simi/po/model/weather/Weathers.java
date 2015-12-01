package com.simi.po.model.weather;

public class Weathers {
    private Long id;

    private Long areaId;

    private String areaCn;

    private String publicTime;

    private String weatherDay;

    private String weatherNight;

    private String tempDay;

    private String tempNight;

    private String fxDay;

    private String fxNight;

    private String flDay;

    private String flNight;

    private String sunRiseDown;

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

    public String getWeatherDay() {
        return weatherDay;
    }

    public void setWeatherDay(String weatherDay) {
        this.weatherDay = weatherDay == null ? null : weatherDay.trim();
    }

    public String getWeatherNight() {
        return weatherNight;
    }

    public void setWeatherNight(String weatherNight) {
        this.weatherNight = weatherNight == null ? null : weatherNight.trim();
    }

    public String getTempDay() {
        return tempDay;
    }

    public void setTempDay(String tempDay) {
        this.tempDay = tempDay == null ? null : tempDay.trim();
    }

    public String getTempNight() {
        return tempNight;
    }

    public void setTempNight(String tempNight) {
        this.tempNight = tempNight == null ? null : tempNight.trim();
    }

    public String getFxDay() {
        return fxDay;
    }

    public void setFxDay(String fxDay) {
        this.fxDay = fxDay == null ? null : fxDay.trim();
    }

    public String getFxNight() {
        return fxNight;
    }

    public void setFxNight(String fxNight) {
        this.fxNight = fxNight == null ? null : fxNight.trim();
    }

    public String getFlDay() {
        return flDay;
    }

    public void setFlDay(String flDay) {
        this.flDay = flDay == null ? null : flDay.trim();
    }

    public String getFlNight() {
        return flNight;
    }

    public void setFlNight(String flNight) {
        this.flNight = flNight == null ? null : flNight.trim();
    }

    public String getSunRiseDown() {
        return sunRiseDown;
    }

    public void setSunRiseDown(String sunRiseDown) {
        this.sunRiseDown = sunRiseDown == null ? null : sunRiseDown.trim();
    }
}