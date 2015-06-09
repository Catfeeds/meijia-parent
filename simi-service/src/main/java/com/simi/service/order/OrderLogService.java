package com.simi.service.order;

import java.util.List;

import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.Orders;

public interface OrderLogService {
	public OrderLog initOrderLog(Orders orders);
    int insert(OrderLog record);
	List<OrderLog> selectByOrderNo(String orderNo);
}