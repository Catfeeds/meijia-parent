package com.resume.po.dao.dict;

import java.util.List;

import com.resume.po.model.dict.HrDictType;

public interface HrDictTypeMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(HrDictType record);

    Long insertSelective(HrDictType record);

    HrDictType selectByPrimaryKey(Long id);
    
    List<HrDictType> selectByAll();

    int updateByPrimaryKeySelective(HrDictType record);

    int updateByPrimaryKey(HrDictType record);
}