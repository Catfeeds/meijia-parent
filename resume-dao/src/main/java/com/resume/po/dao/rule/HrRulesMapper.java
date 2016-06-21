package com.resume.po.dao.rule;

import java.util.List;

import com.resume.po.model.rule.HrRules;
import com.simi.vo.resume.ResumeRuleSearchVo;

public interface HrRulesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HrRules record);

    int insertSelective(HrRules record);

    HrRules selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HrRules record);

    int updateByPrimaryKeyWithBLOBs(HrRules record);

    int updateByPrimaryKey(HrRules record);

	List<HrRules> selectByListPage(ResumeRuleSearchVo searchVo);

	List<HrRules> selectBySearchVo(ResumeRuleSearchVo searchVo);
}