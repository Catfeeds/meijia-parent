package com.simi.po.dao.card;

import java.util.List;

import com.simi.po.model.card.CardLog;

public interface CardLogMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(CardLog record);

    int insertSelective(CardLog record);

    CardLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CardLog record);

    int updateByPrimaryKey(CardLog record);

	List<CardLog> selectByCardId(Long cardId);
}