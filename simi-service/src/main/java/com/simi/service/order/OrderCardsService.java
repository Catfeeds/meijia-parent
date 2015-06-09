package com.simi.service.order;

import com.simi.po.model.dict.DictCardType;
import com.simi.po.model.order.OrderCards;
import com.simi.po.model.user.Users;

public interface OrderCardsService {
	int deleteByPrimaryKey(Long id);

	int updateOrderByAlipay(OrderCards orderCards, long updateTime,
			Short orderStatus, Short payType, String trade_no,
			String trade_status, String payAccount);

	Long insert(OrderCards record);

	int insertSelective(OrderCards record);

	OrderCards selectByOrderCardsNo(String cardOrderNo);

	OrderCards selectByPrimaryKey(Long id);

	OrderCards initOrderCards(String mobile, int card_type, Users users,
			DictCardType dictCardType, int pay_type);

	int updateByPrimaryKeySelective(OrderCards record);

	int updateByPrimaryKey(OrderCards record);
}