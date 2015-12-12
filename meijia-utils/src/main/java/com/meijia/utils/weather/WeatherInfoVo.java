package com.meijia.utils.weather;

import java.util.List;

public class WeatherInfoVo {
	private String error;
	private String status;
	private String date;
	private List<WeatherResultVo> results;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<WeatherResultVo> getResults() {
		return results;
	}

	public void setResults(List<WeatherResultVo> results) {
		this.results = results;
	}
}
