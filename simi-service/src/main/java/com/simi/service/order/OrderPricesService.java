package com.simi.service.order;

import java.math.BigDecimal;

import com.simi.po.model.order.OrderPrices;

public interface OrderPricesService {

	OrderPrices initOrderPrices();

	int deleteByPrimaryKey(Long id);

	int insert(OrderPrices record);

	int insertSelective(OrderPrices record);

	OrderPrices selectByOrderId(Long id);

	OrderPrices selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderPrices record);

	int updateByPrimaryKey(OrderPrices record);

	BigDecimal getPayByOrder(String orderNo, String cardPasswd);

}