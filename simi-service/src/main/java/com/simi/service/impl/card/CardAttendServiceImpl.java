package com.simi.service.impl.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.service.card.CardAttendService;
import com.simi.po.model.card.CardAttend;
import com.simi.po.dao.card.CardAttendMapper;

@Service
public class CardAttendServiceImpl implements CardAttendService {
	@Autowired
	CardAttendMapper cardAttendMapper;

	@Override
	public CardAttend initCardAttend() {
		CardAttend record = new CardAttend();

		record.setId(0L);
		record.setCardId(0L);
		record.setMobile("");
		record.setName("");
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}	
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return cardAttendMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(CardAttend record) {
		return cardAttendMapper.insert(record);
	}
	
	@Override
	public int insertSelective(CardAttend record) {
		return cardAttendMapper.insertSelective(record);
	}
	
	@Override
	public List<CardAttend> selectByCardId(Long cardId) {
		return  cardAttendMapper.selectByCardId(cardId);
	}
	
	@Override
	public List<CardAttend> selectByCardIds(List<Long> cardIds) {
		return  cardAttendMapper.selectByCardIds(cardIds);
	}	

	@Override
	public int deleteByCardId(Long cardId) {
		return cardAttendMapper.deleteByCardId(cardId);
	}	


}