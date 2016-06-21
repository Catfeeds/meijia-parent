package com.simi.service.resume;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.resume.po.model.rule.HrRuleFrom;
import com.simi.vo.resume.HrRuleFromVo;
import com.simi.vo.resume.ResumeRuleSearchVo;

public interface HrRuleFromService {
	
	int deleteByPrimaryKey(Long id);

    int insert(HrRuleFrom record);

    HrRuleFrom selectByPrimaryKey(Long id);

    int updateByPrimaryKey(HrRuleFrom record);
    
    PageInfo selectByListPage(ResumeRuleSearchVo searchVo, int pageNo, int pageSize);
	
    HrRuleFrom initHrDictFrom();

	int updateByPrimaryKeySelective(HrRuleFrom record);

	List<HrRuleFrom> selectBySearchVo(ResumeRuleSearchVo searchVo);

	List<HrRuleFromVo> getVos(List<HrRuleFrom> list);
     
}
