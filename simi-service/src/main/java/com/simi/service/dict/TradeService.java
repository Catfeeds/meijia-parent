package com.simi.service.dict;

import java.util.List;

import com.simi.po.model.dict.DictTrade;

public interface TradeService {

	List<DictTrade> selectAll();

	DictTrade selectByPrimaryKey(Long id);

}
