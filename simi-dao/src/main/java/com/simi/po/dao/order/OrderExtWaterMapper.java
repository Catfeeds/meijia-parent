package com.simi.po.dao.order;

import java.util.List;

import com.simi.po.model.order.OrderExtWater;

public interface OrderExtWaterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderExtWater record);

    int insertSelective(OrderExtWater record);

    OrderExtWater selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderExtWater record);

    int updateByPrimaryKey(OrderExtWater record);

	List<OrderExtWater> selectByUserId(Long userId);

	OrderExtWater selectByOrderId(Long orderId);
}