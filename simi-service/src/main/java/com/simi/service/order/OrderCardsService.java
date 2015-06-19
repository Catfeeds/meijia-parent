package com.simi.service.order;

import com.simi.po.model.dict.DictCardType;
import com.simi.po.model.order.OrderCards;
import com.simi.po.model.user.Users;

public interface OrderCardsService {
	int deleteByPrimaryKey(Long id);

	Long insert(OrderCards record);

	int insertSelective(OrderCards record);

	OrderCards selectByOrderCardsNo(String cardOrderNo);

	OrderCards selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderCards record);

	int updateByPrimaryKey(OrderCards record);

	OrderCards initOrderCards(Users users, Long cardType, DictCardType dictCardType, Short payType);

	int updateOrderByOnlinePay(OrderCards orderCards, String tradeNo, String tradeStatus, String payAccount);
}