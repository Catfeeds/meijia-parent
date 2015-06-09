package com.simi.po.dao.dict;

import java.util.List;

import com.simi.po.model.dict.DictCardType;

public interface DictCardTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictCardType record);

    int insertSelective(DictCardType record);

    DictCardType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictCardType record);

    int updateByPrimaryKey(DictCardType record);

	List<DictCardType> selectAll();
}