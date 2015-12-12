package com.simi.po.model.data;

import java.util.Date;

public class Weathers {
    private Long id;

    private Long cityId;

    private String cityName;

    private Date weatherDate;

    private String pm25;

    private String weatherData;
    
    private String weatherIndex;
    
    private String lastTime;

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

    public String getWeatherIndex() {
        return weatherIndex;
    }

    public void setWeatherIndex(String weatherIndex) {
        this.weatherIndex = weatherIndex == null ? null : weatherIndex.trim();
    }

	public String getWeatherData() {
		return weatherData;
	}

	public void setWeatherData(String weatherData) {
		this.weatherData = weatherData;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
}