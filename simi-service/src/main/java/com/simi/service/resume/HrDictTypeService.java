package com.simi.service.resume;

import java.util.List;

import com.resume.po.model.dict.HrDictType;



public interface HrDictTypeService {
	
	int deleteByPrimaryKey(Long id);

    Long insert(HrDictType record);

    HrDictType selectByPrimaryKey(Long id);

    int updateByPrimaryKey(HrDictType record);
    
    HrDictType initHrDictFrom();

	int updateByPrimaryKeySelective(HrDictType record);

	List<HrDictType> selectByAll();
     
}
