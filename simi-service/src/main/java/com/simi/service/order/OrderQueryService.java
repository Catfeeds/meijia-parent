package com.simi.service.order;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.OrdersListVo;
import com.simi.vo.order.OrderDetailVo;
import com.simi.vo.order.OrderListVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.po.model.order.Orders;

public interface OrderQueryService {

    Orders selectByOrderNo(String orderNo);

    Orders selectByPrimaryKey(Long id);

	List<OrderViewVo> getOrderViewList(List<Orders> list);
	
	List<Orders> selectByListPageList(OrderSearchVo orderSearchVo, int pageNo, int pageSize);
	PageInfo selectByListPage(OrderSearchVo orderSearchVo, int pageNo, int pageSize);

	OrderViewVo getOrderView(Orders order);

	OrderListVo getOrderListVo(Orders order);

	OrderDetailVo getOrderDetailVo(Orders order, OrderListVo listVo);

	Date getSeniorRangeDate(Long userId);

	OrdersListVo completeVo(Orders orders);

	List<Orders> selectByUserIdsListPageList(List<Long> partnerUserIdList,
			int pageNo, int pageSize);

}