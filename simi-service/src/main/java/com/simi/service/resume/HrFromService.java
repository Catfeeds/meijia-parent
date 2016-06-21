package com.simi.service.resume;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.resume.po.model.dict.HrFrom;
import com.simi.vo.resume.ResumeRuleSearchVo;



public interface HrFromService {
	
	int deleteByPrimaryKey(Long id);

    Long insert(HrFrom record);

    HrFrom selectByPrimaryKey(Long id);

    int updateByPrimaryKey(HrFrom record);
    
    HrFrom initHrDictFrom();

	int updateByPrimaryKeySelective(HrFrom record);

	PageInfo selectByListPage(ResumeRuleSearchVo searchVo, int pageNo, int pageSize);

	List<HrFrom> selectBySearchVo(ResumeRuleSearchVo searchVo);

	List<HrFrom> selectByAll();
     
}
