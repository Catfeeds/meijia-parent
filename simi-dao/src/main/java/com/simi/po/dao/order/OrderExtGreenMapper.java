package com.simi.po.dao.order;

import java.util.List;

import com.simi.po.model.order.OrderExtGreen;

public interface OrderExtGreenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderExtGreen record);

    int insertSelective(OrderExtGreen record);

    OrderExtGreen selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderExtGreen record);

    int updateByPrimaryKey(OrderExtGreen record);

	List<OrderExtGreen> selectByUserId(Long userId);

	OrderExtGreen selectByOrderId(Long orderId);
}