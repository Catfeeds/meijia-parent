package com.simi.po.dao.resume;

import java.util.List;

import com.simi.po.model.resume.HrResumeChange;
import com.simi.vo.resume.ResumeSearchVo;

public interface HrResumeChangeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HrResumeChange record);

    int insertSelective(HrResumeChange record);

    HrResumeChange selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HrResumeChange record);

    int updateByPrimaryKey(HrResumeChange record);
    
    List<HrResumeChange> selectBySearchVo(ResumeSearchVo searchVo);

   	List<HrResumeChange> selectByListPage(ResumeSearchVo searchVo);
}