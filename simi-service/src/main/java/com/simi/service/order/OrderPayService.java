package com.simi.service.order;

import com.simi.po.model.dict.DictSeniorType;
import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.order.Orders;


public interface OrderPayService {

		void orderPaySuccessToDo(Orders orders);

		OrderSenior orderSeniorPayMoney(String mobile, DictSeniorType seniorType, Short payType);

		int updateSeniorByAlipay(String mobile, short payType, String seniorOrderNo, String tradeNo, String tradeStatus, String payAccount);


}