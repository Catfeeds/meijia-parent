package com.simi.service.order;

import com.simi.po.model.dict.DictSeniorType;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserCoupons;


public interface OrderPayService {
		int updateOrderByAlipay(Orders orders, OrderPrices orderPrices,
			long updateTime, Short orderStatus, Short pay_type,
			String trade_no, String payAccount);

	    int updateOrderByRestMoney(String mobile, int order_id, Orders orders,
				OrderPrices orderPrices,  short payType, long updateTime, UserCoupons userCoupons);

		int updateOrderByRestMoney(String mobile, int order_id, Orders orders,
				OrderPrices orderPrices, short payType, long updateTime,
				String trade_no, String payAccount);

		int updateSeniorByAlipay(String mobile, short payType,
				String seniorOrderNo, String tradeNo, String tradeStatus,
				String payAccount);

		OrderSenior orderSeniorPayMoney(String mobile, DictSeniorType seniorType,
				Short payType);


}