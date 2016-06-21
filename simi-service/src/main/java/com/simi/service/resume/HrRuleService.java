package com.simi.service.resume;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.resume.po.model.rule.HrRules;
import com.simi.vo.resume.ResumeRuleSearchVo;

public interface HrRuleService {
	
	int deleteByPrimaryKey(Long id);

    int insert(HrRules record);

    HrRules selectByPrimaryKey(Long id);

    int updateByPrimaryKey(HrRules record);
    
    PageInfo selectByListPage(ResumeRuleSearchVo searchVo, int pageNo, int pageSize);
	
    HrRules initHrDictFrom();

	int updateByPrimaryKeySelective(HrRules record);

	List<HrRules> selectBySearchVo(ResumeRuleSearchVo searchVo);
     
}
