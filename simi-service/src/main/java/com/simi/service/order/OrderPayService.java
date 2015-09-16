package com.simi.service.order;

import com.simi.po.model.dict.DictSeniorType;
import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.Users;


public interface OrderPayService {

		void orderPaySuccessToDo(Orders orders);

		boolean orderSeniorPaySuccessTodo(OrderSenior orderSenior);
		
}