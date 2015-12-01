package com.simi.po.dao.weather;

import com.simi.po.model.weather.Weathers;

public interface WeathersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Weathers record);

    int insertSelective(Weathers record);

    Weathers selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Weathers record);

    int updateByPrimaryKey(Weathers record);

	Weathers selectByAreaIdAndPublishTime(Long areaId, String publishTime);

	Weathers selectByAreaId(Long areaId);
}