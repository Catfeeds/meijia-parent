package com.simi.po.dao.resume;

import java.util.List;

import com.simi.po.model.resume.HrJobHunter;
import com.simi.vo.resume.ResumeChangeSearchVo;

public interface HrJobHunterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HrJobHunter record);

    int insertSelective(HrJobHunter record);

    HrJobHunter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HrJobHunter record);

    int updateByPrimaryKey(HrJobHunter record);
    
    List<HrJobHunter> selectBySearchVo(ResumeChangeSearchVo searchVo);

   	List<HrJobHunter> selectByListPage(ResumeChangeSearchVo searchVo);
    
}