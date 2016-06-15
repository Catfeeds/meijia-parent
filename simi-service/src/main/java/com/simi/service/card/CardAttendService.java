package com.simi.service.card;

import java.util.List;

import com.simi.po.model.card.CardAttend;
import com.simi.vo.card.CardSearchVo;

public interface CardAttendService {

	int deleteByPrimaryKey(Long id);

	Long insert(CardAttend record);

	CardAttend initCardAttend();

	int insertSelective(CardAttend record);

	List<CardAttend> selectByCardId(Long cardId);

	int deleteByCardId(Long cardId);

	List<CardAttend> selectByCardIds(List<Long> cardIds);

	List<CardAttend> selectBySearchVo(CardSearchVo searchVo);

	int updateByPrimaryKey(CardAttend record);

	int updateByPrimaryKeySelective(CardAttend record);

	boolean doCardAttend(Long cardId, Long userId, String attends);

}
