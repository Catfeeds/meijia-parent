package com.simi.po.dao.order;

import java.util.List;

import com.simi.po.model.order.OrderExtRecycle;

public interface OrderExtRecycleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderExtRecycle record);

    int insertSelective(OrderExtRecycle record);

    OrderExtRecycle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderExtRecycle record);

    int updateByPrimaryKey(OrderExtRecycle record);

	List<OrderExtRecycle> selectByUserId(Long userId);

	OrderExtRecycle selectByOrderId(Long orderId);
}