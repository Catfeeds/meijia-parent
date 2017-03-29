package com.simi.po.dao.order;

import java.util.List;

import com.simi.po.model.order.OrderScore;
import com.simi.vo.OrderSearchVo;

public interface OrderScoreMapper {
    int deleteByPrimaryKey(Long orderId);

    Long insert(OrderScore record);

    Long insertSelective(OrderScore record);

    int updateByPrimaryKeySelective(OrderScore record);

    int updateByPrimaryKey(OrderScore record);
    
    OrderScore selectByPrimaryKey(Long orderId);
    
    OrderScore selectByOrderNo(String orderNo);
    
    OrderScore selectByOrderNum(String orderNum);
    
    List<OrderScore> selectByUserId(Long userId);

	OrderScore selectByOrderId(Long orderId);

	List<OrderScore> selectByListPage(OrderSearchVo searchVo);

	List<OrderScore> selectBySearchVo(OrderSearchVo searchVo);
}