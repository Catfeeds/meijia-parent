package com.simi.service.order;

import java.util.List;

import com.simi.po.model.order.OrderExtGreen;
import com.simi.vo.order.OrderExtGreenListVo;

public interface OrderExtGreenService {

	OrderExtGreen initOrderExtGreen();

	int deleteByPrimaryKey(Long id);

	int insert(OrderExtGreen record);

	int insertSelective(OrderExtGreen record);

	OrderExtGreen selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderExtGreen record);

	int updateByPrimaryKey(OrderExtGreen record);

	List<OrderExtGreen> selectByUserId(Long userId);

	OrderExtGreenListVo getOrderExtGreenListVo(OrderExtGreen item);


}