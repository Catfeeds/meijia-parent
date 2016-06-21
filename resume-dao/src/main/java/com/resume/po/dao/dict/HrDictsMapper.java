package com.resume.po.dao.dict;

import java.util.List;

import com.resume.po.model.dict.HrDicts;
import com.simi.vo.resume.HrDictSearchVo;

public interface HrDictsMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(HrDicts record);

    Long insertSelective(HrDicts record);

    HrDicts selectByPrimaryKey(Long id);
    
    List<HrDicts> selectByListPage(HrDictSearchVo searchVo);
    
    List<HrDicts> selectBySearchVo(HrDictSearchVo searchVo);

    int updateByPrimaryKeySelective(HrDicts record);

    int updateByPrimaryKey(HrDicts record);
}