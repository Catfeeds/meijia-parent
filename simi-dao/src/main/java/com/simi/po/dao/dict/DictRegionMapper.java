package com.simi.po.dao.dict;

import java.util.List;

import com.simi.po.model.dict.DictRegion;

public interface DictRegionMapper {
    int deleteByPrimaryKey(Long regionId);

    int insert(DictRegion record);

    int insertSelective(DictRegion record);

    DictRegion selectByPrimaryKey(Long regionId);

    int updateByPrimaryKeySelective(DictRegion record);

    int updateByPrimaryKey(DictRegion record);
    
    List<DictRegion> selectAll();
    
    List<DictRegion> selectByCityId(Long cityId);
    
    List<DictRegion> selectByProvinceId(Long provinceId);
    
    List<DictRegion> selectByCityIds(List<Long> cityIds);
}