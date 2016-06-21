package com.resume.po.dao.rule;

import java.util.List;

import com.resume.po.model.rule.HrRuleFrom;
import com.simi.vo.resume.ResumeRuleSearchVo;

public interface HrRuleFromMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HrRuleFrom record);

    int insertSelective(HrRuleFrom record);

    HrRuleFrom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HrRuleFrom record);

    int updateByPrimaryKeyWithBLOBs(HrRuleFrom record);

    int updateByPrimaryKey(HrRuleFrom record);

	List<HrRuleFrom> selectByListPage(ResumeRuleSearchVo searchVo);
	
	List<HrRuleFrom> selectBySearchVo(ResumeRuleSearchVo searchVo);
}