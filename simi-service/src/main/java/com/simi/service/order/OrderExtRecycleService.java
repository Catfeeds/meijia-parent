package com.simi.service.order;

import java.util.List;

import com.simi.po.model.order.OrderExtRecycle;
import com.simi.vo.order.OrderExtGreenListVo;

public interface OrderExtRecycleService {

	OrderExtRecycle initOrderExtGreen();

	int deleteByPrimaryKey(Long id);

	int insert(OrderExtRecycle record);

	int insertSelective(OrderExtRecycle record);

	OrderExtRecycle selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderExtRecycle record);

	int updateByPrimaryKey(OrderExtRecycle record);

	List<OrderExtRecycle> selectByUserId(Long userId);

	OrderExtGreenListVo getOrderExtGreenListVo(OrderExtRecycle item);

	OrderExtRecycle selectByOrderId(Long orderId);


}