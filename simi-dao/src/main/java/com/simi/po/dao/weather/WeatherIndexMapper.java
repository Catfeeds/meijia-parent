package com.simi.po.dao.weather;

import java.util.List;

import com.simi.po.model.weather.WeatherIndex;

public interface WeatherIndexMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WeatherIndex record);

    int insertSelective(WeatherIndex record);

    WeatherIndex selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WeatherIndex record);

    int updateByPrimaryKey(WeatherIndex record);

	List<WeatherIndex> selectByAreaIdAndPublishTime(Long areaId, String publishTime);

	int deleteByAreaIdAndPublishTime(Long areaId, Long publishTime);

	int deleteByAreaId(Long areaId);
}