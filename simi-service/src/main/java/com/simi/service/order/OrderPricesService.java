package com.simi.service.order;

import java.math.BigDecimal;
import java.util.List;

import com.simi.po.model.order.OrderPrices;
import com.simi.vo.OrderSearchVo;

public interface OrderPricesService {

	OrderPrices initOrderPrices();

	int deleteByPrimaryKey(Long id);

	int insert(OrderPrices record);

	int insertSelective(OrderPrices record);

	OrderPrices selectByOrderId(Long id);

	OrderPrices selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderPrices record);

	int updateByPrimaryKey(OrderPrices record);

	List<OrderPrices> selectByOrderIds(List<Long> orderIds);

	BigDecimal getPayByOrder(BigDecimal orderPay, Long userCouponId);

	List<OrderPrices> selectBySearchVo(OrderSearchVo searchVo);

}