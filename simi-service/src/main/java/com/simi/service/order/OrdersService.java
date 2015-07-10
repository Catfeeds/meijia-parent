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

	List<String> selectByDistinctMobileLists();

	Boolean orderSuccessTodo(String orderNo);

	void orderRatedTodo(Orders orders);

	Boolean orderConfirmTodo(String orderNo);

	List<Orders> selectByUserId(Long userId);



	

}