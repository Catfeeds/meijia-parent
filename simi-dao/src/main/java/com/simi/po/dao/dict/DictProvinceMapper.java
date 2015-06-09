package com.simi.po.dao.dict;

import java.util.List;

import com.simi.po.model.dict.DictProvince;

public interface DictProvinceMapper {
    int deleteByPrimaryKey(Long provinceId);

    int insert(DictProvince record);

    int insertSelective(DictProvince record);

    DictProvince selectByPrimaryKey(Long provinceId);

    int updateByPrimaryKeySelective(DictProvince record);

    int updateByPrimaryKey(DictProvince record);
    
    List<DictProvince> selectAll();
}