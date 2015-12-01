package com.simi.service.weather;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.simi.po.model.weather.Weathers;

public interface WeatherService {

	Weathers initWeather();

	int deleteByPrimaryKey(Long id);

	int insert(Weathers record);

	int insertSelective(Weathers record);

	Weathers selectByPrimaryKey(Long id);

	int updateByPrimaryKey(Weathers record);

	int updateByPrimaryKeySelective(Weathers record);

	Weathers selectByAreaIdAndPublishTime(Long areaId, String publishTime);

	boolean getWeather(String areaId, String areaCn);

	Weathers selectByAreaId(Long areaId);

}
