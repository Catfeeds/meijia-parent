package com.simi.service.impl.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.card.CardLogService;
import com.simi.service.user.UsersService;
import com.simi.po.model.card.CardLog;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.card.CardLogMapper;

@Service
public class CardLogServiceImpl implements CardLogService {
	@Autowired
	CardLogMapper cardLogMapper;
	
	@Autowired
	UsersService usersService;
	
	@Override
	public CardLog initCardLog() {
		CardLog record = new CardLog();
		record.setId(0L);
		record.setCardId(0L);
		record.setUserId(0L);
		record.setUserName("");
		record.setStatus((short) 0);
		record.setRemarks("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	@Override
	public CardLog selectByPrimaryKey(Long id) {
		return cardLogMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int insertSelective(CardLog record) {
		return cardLogMapper.insertSelective(record);
	}
	
	@Override
	public Long insert(CardLog record) {
		return cardLogMapper.insert(record);
	}
	
	@Override
	public List<CardLog> selectByCardId(Long cardId) {
		return cardLogMapper.selectByCardId(cardId);
	}
	
}