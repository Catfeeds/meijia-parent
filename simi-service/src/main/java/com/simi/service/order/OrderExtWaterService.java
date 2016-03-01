package com.simi.service.order;

import java.util.List;

import com.simi.po.model.order.OrderExtWater;
import com.simi.vo.order.OrderExtWaterListVo;

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

	List<OrderExtWaterListVo> getListVos(List<OrderExtWater> list);

	OrderExtWater selectByOrderId(Long orderId);

}