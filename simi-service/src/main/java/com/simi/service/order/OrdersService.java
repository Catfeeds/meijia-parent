package com.simi.service.order;

import java.util.List;

import com.simi.po.model.order.Orders;

public interface OrdersService {
	
	Orders initOrders();
	
	Long insert(Orders record);
	
	int insertSelective(Orders record);
	 
	int updateByPrimaryKeySelective(Orders record);
	
    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Orders record);

	void orderRatedTodo(Orders orders);

	List<Orders> selectByUserId(Long userId);

	List<Orders> selectByOrderStatus();

	List<Orders> selectByOrderStatus7Days();

	List<Orders> selectByorder1Hour();

	Orders selectByOrderNo(String orderNo);

	List<Orders> selectByOrderIds(List<Long> orderIds);

	Orders selectByPrimaryKey(Long orderId);

	List<Long> getByPartnerUserId(Long partnerUseId);



	

}