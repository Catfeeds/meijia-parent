package com.simi.service.order;

import java.util.List;

import com.simi.po.model.order.OrderScore;

public interface OrderScoreService {

	int deleteByPrimaryKey(Long id);

	Long insert(OrderScore record);

	Long insertSelective(OrderScore record);

	int updateByPrimaryKey(OrderScore record);

	int updateByPrimaryKeySelective(OrderScore record);

	List<OrderScore> selectByUserId(Long userId);

	OrderScore selectByOrderNo(String orderNo);

	OrderScore selectByPrimaryKey(Long orderId);

	OrderScore selectByOrderId(Long orderId);

	OrderScore selectByOrderNum(String orderNum);

	OrderScore initOrderScore();

}
