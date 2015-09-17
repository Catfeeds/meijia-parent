package com.simi.po.dao.dict;

import java.util.List;

import com.simi.po.model.dict.DictSeniorType;

public interface DictSeniorTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictSeniorType record);

    int insertSelective(DictSeniorType record);

    DictSeniorType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictSeniorType record);

    int updateByPrimaryKey(DictSeniorType record);

	List<DictSeniorType> selectAll();
}