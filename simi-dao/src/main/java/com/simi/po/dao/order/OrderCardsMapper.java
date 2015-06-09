package com.simi.po.dao.order;

import com.simi.po.model.order.OrderCards;

public interface OrderCardsMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(OrderCards record);

    int insertSelective(OrderCards record);

    OrderCards selectByOrderCardsNo(String cardOrderNo);

    OrderCards selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderCards record);

    int updateByPrimaryKey(OrderCards record);
}