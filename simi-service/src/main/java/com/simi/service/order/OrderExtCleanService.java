package com.simi.service.order;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.order.OrderExtClean;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderExtCleanListVo;

public interface OrderExtCleanService {

	int deleteByPrimaryKey(Long id);

	int insert(OrderExtClean record);

	int insertSelective(OrderExtClean record);

	OrderExtClean selectByPrimaryKey(Long id);

	int updateByPrimaryKey(OrderExtClean record);

	int updateByPrimaryKeySelective(OrderExtClean record);

	OrderExtClean initOrderExtClean();

	List<OrderExtClean> selectByUserId(Long userId);

	OrderExtCleanListVo getListVo(OrderExtClean item);

	List<OrderExtCleanListVo> getListVos(List<OrderExtClean> list);

	OrderExtClean selectByOrderId(Long orderId);

	PageInfo selectByListPage(OrderSearchVo orderSearchVo, int pageNo, int pageSize);

}