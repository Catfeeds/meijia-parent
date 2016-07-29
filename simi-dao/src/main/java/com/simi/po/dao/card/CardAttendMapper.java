package com.simi.po.dao.card;

import java.util.List;

import com.simi.po.model.card.CardAttend;
import com.simi.vo.card.CardSearchVo;

public interface CardAttendMapper {
    Long insert(CardAttend record);

    int insertSelective(CardAttend record);

	int deleteByPrimaryKey(Long id);

	List<CardAttend> selectByCardId(Long cardId);

	int deleteByCardId(Long cardId);

	List<CardAttend> selectByCardIds(List<Long> cardIds);
	
	int updateByPrimaryKeySelective(CardAttend record);

    int updateByPrimaryKey(CardAttend record);

	List<CardAttend> selectBySearchVo(CardSearchVo searchVo);
}