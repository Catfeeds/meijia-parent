package com.simi.service.order;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.po.model.order.Orders;

public interface OrderQueryService {

    Orders selectByOrderNo(String orderNo);

    Orders selectByPrimaryKey(Long id);

	List<OrderViewVo> getOrderViewList(List<Orders> list);
	
	List<Orders> selectByStatus(Short orderStatus);

	List<Orders> selectByStatuses(List<Short> orderStatus);

	PageInfo selectByListPage(OrderSearchVo orderSearchVo, int pageNo, int pageSize);

	List<OrderViewVo> selectByUserId(Long userId, int pageNo, int pageSize);

	OrderViewVo getOrderView(Orders order);

    List<OrderViewVo> selectBySecId(Long secId, int pageNo, int pageSize);

	OrderViewVo selectByUserId(Long userId);

	List<OrderViewVo> selectByUserIdList(Long userId);

	String getOrderStatusName(Short status);




}