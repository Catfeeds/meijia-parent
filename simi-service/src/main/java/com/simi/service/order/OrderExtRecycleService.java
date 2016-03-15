package com.simi.service.order;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.order.OrderExtRecycle;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderExtRecycleListVo;

public interface OrderExtRecycleService {

	OrderExtRecycle initOrderExtRecycle();

	int deleteByPrimaryKey(Long id);

	int insert(OrderExtRecycle record);

	int insertSelective(OrderExtRecycle record);

	OrderExtRecycle selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderExtRecycle record);

	int updateByPrimaryKey(OrderExtRecycle record);

	List<OrderExtRecycle> selectByUserId(Long userId);

	OrderExtRecycleListVo getOrderExtRecycleListVo(OrderExtRecycle item);

	OrderExtRecycle selectByOrderId(Long orderId);

	PageInfo selectByPage(OrderSearchVo searchVo, int pageNo, int pageSize);


}