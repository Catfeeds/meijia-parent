package com.simi.service.impl.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.po.dao.card.CardImgsMapper;
import com.simi.po.model.card.CardImgs;
import com.simi.service.card.CardImgsService;

@Service
public class CardImgsServiceImpl implements CardImgsService {
	@Autowired
	CardImgsMapper cardImgsMapper;

	@Override
	public int insert(CardImgs record) {
	
		return cardImgsMapper.insert(record);
	}
	
	
}