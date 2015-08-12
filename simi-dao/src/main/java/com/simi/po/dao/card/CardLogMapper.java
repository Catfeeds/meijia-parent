package com.simi.po.dao.card;

import com.simi.po.model.card.CardLog;

public interface CardLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CardLog record);

    int insertSelective(CardLog record);

    CardLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CardLog record);

    int updateByPrimaryKey(CardLog record);
}