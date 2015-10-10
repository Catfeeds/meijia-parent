package com.simi.service.card;

import java.util.List;

import com.simi.po.model.card.CardLog;

public interface CardLogService {

	CardLog initCardLog();

	CardLog selectByPrimaryKey(Long id);

	int insertSelective(CardLog record);

	Long insert(CardLog record);

	List<CardLog> selectByCardId(Long cardId);

}
