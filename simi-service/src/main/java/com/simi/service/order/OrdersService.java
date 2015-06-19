package com.simi.service.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;

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

}