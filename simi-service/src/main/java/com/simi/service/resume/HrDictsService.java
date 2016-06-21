package com.simi.service.resume;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.resume.po.model.dict.HrDicts;
import com.simi.vo.resume.HrDictSearchVo;

public interface HrDictsService {
	
	int deleteByPrimaryKey(Long id);

    Long insert(HrDicts record);

    HrDicts selectByPrimaryKey(Long id);

    int updateByPrimaryKey(HrDicts record);
    
	int updateByPrimaryKeySelective(HrDicts record);

	PageInfo selectByListPage(HrDictSearchVo searchVo, int pageNo, int pageSize);

	List<HrDicts> selectBySearchVo(HrDictSearchVo searchVo);

	HrDicts initHrDicts();
     
}
