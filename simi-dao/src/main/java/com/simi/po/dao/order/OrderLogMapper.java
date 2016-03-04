package com.simi.po.dao.order;

import java.util.List;

import com.simi.po.model.order.OrderLog;
import com.simi.vo.OrderSearchVo;

public interface OrderLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderLog record);

    int insertSelective(OrderLog record);

    OrderLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderLog record);

    int updateByPrimaryKey(OrderLog record);

    List<OrderLog> selectByOrderNo(String orderNo);

	List<OrderLog> selectBySearchVo(OrderSearchVo searchVo);
}