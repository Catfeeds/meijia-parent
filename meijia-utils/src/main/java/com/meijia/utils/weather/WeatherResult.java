package com.meijia.utils.weather;

import java.util.List;

public class WeatherResult {
	public String currentCity;
	public String pm25;
	public List<WeatherData> weather_data;
	public List<WeatherIndex> index;

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

	public List<WeatherData> getWeather_data() {
		return weather_data;
	}

	public void setWeather_data(List<WeatherData> weather_data) {
		this.weather_data = weather_data;
	}

	public List<WeatherIndex> getIndex() {
		return index;
	}

	public void setIndex(List<WeatherIndex> index) {
		this.index = index;
	}
}