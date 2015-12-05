package com.simi.po.model.data;

import java.util.Date;

public class Weathers {
    private Long id;

    private Long cityId;

    private String cityName;

    private Date weatherDate;

    private String pm25;

    private String dayPictureUrl;

    private String nightPictureUrl;

    private String weather;

    private String wind;

    private String temperature;

    private String realTemp;

    private String weatherIndex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Date getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(Date weatherDate) {
        this.weatherDate = weatherDate;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25 == null ? null : pm25.trim();
    }

    public String getDayPictureUrl() {
        return dayPictureUrl;
    }

    public void setDayPictureUrl(String dayPictureUrl) {
        this.dayPictureUrl = dayPictureUrl == null ? null : dayPictureUrl.trim();
    }

    public String getNightPictureUrl() {
        return nightPictureUrl;
    }

    public void setNightPictureUrl(String nightPictureUrl) {
        this.nightPictureUrl = nightPictureUrl == null ? null : nightPictureUrl.trim();
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather == null ? null : weather.trim();
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind == null ? null : wind.trim();
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature == null ? null : temperature.trim();
    }

    public String getRealTemp() {
        return realTemp;
    }

    public void setRealTemp(String realTemp) {
        this.realTemp = realTemp == null ? null : realTemp.trim();
    }

    public String getWeatherIndex() {
        return weatherIndex;
    }

    public void setWeatherIndex(String weatherIndex) {
        this.weatherIndex = weatherIndex == null ? null : weatherIndex.trim();
    }
}