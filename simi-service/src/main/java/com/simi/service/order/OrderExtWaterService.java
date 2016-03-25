package com.simi.service.order;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.order.OrderExtWater;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderExtWaterListVo;
import com.simi.vo.order.OrderExtWaterXcloudVo;

public interface OrderExtWaterService {

	int deleteByPrimaryKey(Long id);

	int insert(OrderExtWater record);

	int insertSelective(OrderExtWater record);

	OrderExtWater selectByPrimaryKey(Long id);

	int updateByPrimaryKey(OrderExtWater record);

	int updateByPrimaryKeySelective(OrderExtWater record);

	OrderExtWater initOrderExtWater();

	List<OrderExtWater> selectByUserId(Long userId);

	OrderExtWaterListVo getListVo(OrderExtWater item);
	
	OrderExtWaterXcloudVo getListXcloudVo(OrderExtWater item);
	
	List<OrderExtWaterListVo> getListVos(List<OrderExtWater> list);

	OrderExtWater selectByOrderId(Long orderId);

	PageInfo selectByListPage(OrderSearchVo orderSearchVo, int pageNo, int pageSize);

	PageInfo selectByPage(OrderSearchVo searchVo,  int pageNo, int pageSize);

	String getOrderExtra(Long orderId);

}