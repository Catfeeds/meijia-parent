package com.simi.po.dao.weather;

import java.util.List;

import com.simi.po.model.weather.WeatherArea;

public interface WeatherAreaMapper {
    int deleteByPrimaryKey(Long areaId);

    int insert(WeatherArea record);

    int insertSelective(WeatherArea record);

    WeatherArea selectByPrimaryKey(Long areaId);

    int updateByPrimaryKeySelective(WeatherArea record);

    int updateByPrimaryKey(WeatherArea record);

	List<WeatherArea> selectByAll();
}