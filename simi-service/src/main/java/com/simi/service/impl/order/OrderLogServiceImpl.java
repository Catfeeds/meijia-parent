package com.simi.service.impl.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderLogService;
import com.simi.po.dao.order.OrderLogMapper;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.Orders;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderLogServiceImpl implements OrderLogService {

	@Autowired
	private OrderLogMapper orderLogMapper;
	@Override
	public OrderLog initOrderLog(Orders orders) {
		OrderLog orderLog = new OrderLog();
		orderLog.setAddTime(TimeStampUtil.getNow()/1000);
		orderLog.setUserId(orders.getUserId());
		orderLog.setOrderId(orders.getOrderId());
		orderLog.setOrderNo(orders.getOrderNo());
		orderLog.setOrderStatus(orders.getOrderStatus());
		orderLog.setRemarks(" ");
		orderLog.setId(0L);
		return orderLog;
	}

	@Override
	public int insert(OrderLog record) {
		return orderLogMapper.insert(record);
	}

	@Override
	public List<OrderLog> selectByOrderNo(String orderNo) {
		return orderLogMapper.selectByOrderNo(orderNo);
	}
}