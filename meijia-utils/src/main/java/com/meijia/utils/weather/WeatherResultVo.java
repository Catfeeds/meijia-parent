package com.meijia.utils.weather;

import java.util.List;

public class WeatherResultVo {
	public String currentCity;
	public String pm25;
	public List<WeatherDataVo> weather_data;
	public List<WeatherIndexVo> index;

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public List<WeatherDataVo> getWeather_data() {
		return weather_data;
	}

	public void setWeather_data(List<WeatherDataVo> weather_data) {
		this.weather_data = weather_data;
	}

	public List<WeatherIndexVo> getIndex() {
		return index;
	}

	public void setIndex(List<WeatherIndexVo> index) {
		this.index = index;
	}
}