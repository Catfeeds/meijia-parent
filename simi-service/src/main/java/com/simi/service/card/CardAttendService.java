package com.simi.service.card;

import java.util.List;

import com.simi.po.model.card.CardAttend;

public interface CardAttendService {

	int deleteByPrimaryKey(Long id);

	Long insert(CardAttend record);

	CardAttend initCardAttend();

	int insertSelective(CardAttend record);

	List<CardAttend> selectByCardId(Long cardId);

	int deleteByCardId(Long cardId);

	List<CardAttend> selectByCardIds(List<Long> cardIds);

}
