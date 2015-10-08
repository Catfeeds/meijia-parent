package com.simi.po.dao.dict;

import java.util.List;

import com.simi.po.model.dict.DictTrade;

public interface DictTradeMapper {
    int deleteByPrimaryKey(Long tradeId);

    int insert(DictTrade record);

    int insertSelective(DictTrade record);

    DictTrade selectByPrimaryKey(Long tradeId);

    int updateByPrimaryKeySelective(DictTrade record);

    int updateByPrimaryKey(DictTrade record);

	List<DictTrade> selectAll();
}