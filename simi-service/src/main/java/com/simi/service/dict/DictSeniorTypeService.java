package com.simi.service.dict;

import com.simi.po.model.dict.DictSeniorType;

public interface DictSeniorTypeService {
    int deleteByPrimaryKey(Long id);

    int insert(DictSeniorType record);

    int insertSelective(DictSeniorType record);

    DictSeniorType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictSeniorType record);

    int updateByPrimaryKey(DictSeniorType record);
}