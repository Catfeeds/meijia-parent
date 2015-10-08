package com.simi.service.impl.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.dict.CardTypeService;
import com.simi.service.dict.TradeService;
import com.simi.po.dao.dict.DictCardTypeMapper;
import com.simi.po.dao.dict.DictTradeMapper;
import com.simi.po.model.dict.DictCardType;
import com.simi.po.model.dict.DictTrade;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	private DictTradeMapper tradeMapper;

	/*
	 * 获取表dict_card_types的数据
	 * @param
	 * @return  List<DictCardType>
	 */
	@Override
	public List<DictTrade> selectAll() {
		return tradeMapper.selectAll();
	}

	@Override
	public DictTrade selectByPrimaryKey(Long id) {
		return tradeMapper.selectByPrimaryKey(id);
	}

}
