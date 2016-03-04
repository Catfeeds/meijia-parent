package com.simi.po.dao.order;

import java.util.List;

import com.simi.po.model.order.OrderExtClean;
import com.simi.vo.OrderSearchVo;

public interface OrderExtCleanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderExtClean record);

    int insertSelective(OrderExtClean record);

    OrderExtClean selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderExtClean record);

    int updateByPrimaryKey(OrderExtClean record);
    
    List<OrderExtClean> selectByUserId(Long userId);

    OrderExtClean selectByOrderId(Long orderId);

	List<OrderExtClean> selectByListPage(OrderSearchVo orderSearchVo);
}