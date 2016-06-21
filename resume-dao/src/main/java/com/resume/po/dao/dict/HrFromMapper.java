package com.resume.po.dao.dict;

import java.util.List;

import com.resume.po.model.dict.HrFrom;
import com.simi.vo.resume.ResumeRuleSearchVo;

public interface HrFromMapper {
    int deleteByPrimaryKey(Long fromId);

    Long insert(HrFrom record);

    Long insertSelective(HrFrom record);

    HrFrom selectByPrimaryKey(Long fromId);

    int updateByPrimaryKeySelective(HrFrom record);

    int updateByPrimaryKey(HrFrom record);

	List<HrFrom> selectByListPage(ResumeRuleSearchVo searchVo);

	List<HrFrom> selectBySearchVo(ResumeRuleSearchVo searchVo);

	List<HrFrom> selectByAll();
}