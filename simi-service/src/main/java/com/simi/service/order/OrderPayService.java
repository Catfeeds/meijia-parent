package com.simi.service.order;

import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.order.Orders;


public interface OrderPayService {

		void orderPaySuccessToDo(Orders orders);

		boolean orderSeniorPaySuccessTodo(OrderSenior orderSenior);

		void orderWaterPaySuccessToDo(Orders order);

		void orderGreenPaySuccessToDo(Orders order);

		void orderTeamPaySuccessToDo(Orders order);
		
}