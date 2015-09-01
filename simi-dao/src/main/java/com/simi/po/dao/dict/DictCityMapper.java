package com.simi.po.dao.dict;

import java.util.List;

import com.simi.po.model.dict.DictCity;

public interface DictCityMapper {
    int deleteByPrimaryKey(Long cityId);

	int insert(DictCity record);

	int insertSelective(DictCity record);

	DictCity selectByPrimaryKey(Long cityId);

	int updateByPrimaryKeySelective(DictCity record);

	int updateByPrimaryKey(DictCity record);

	List<DictCity> selectAll();
	
	List<DictCity> selectByCityIds(List<Long> cityIds);

	List<DictCity> selectByT(Long t);

}