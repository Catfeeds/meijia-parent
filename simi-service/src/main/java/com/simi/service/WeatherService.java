package com.simi.service;

import java.util.Date;

import com.simi.po.model.common.Weathers;


public interface WeatherService {

	Weathers initWeather();

	int deleteByPrimaryKey(Long id);

	int insert(Weathers record);

	int insertSelective(Weathers record);

	Weathers selectByPrimaryKey(Long id);

	int updateByPrimaryKey(Weathers record);

	int updateByPrimaryKeySelective(Weathers record);

	Weathers selectByCityIdAndDate(Long cityId, Date weatherDate);

	boolean getWeather(Long cityId, String cityName);

	int deleteByDate(Date weatherDate);

}
