package com.simi.po.dao.order;

import java.util.List;

import com.simi.po.model.order.OrderSenior;

public interface OrderSeniorMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(OrderSenior record);

    int insertSelective(OrderSenior record);

    OrderSenior selectByPrimaryKey(Long id);

    OrderSenior selectByOrderSeniorNo(String orderSeniorNo);

    int updateByPrimaryKeySelective(OrderSenior record);

    int updateByPrimaryKey(OrderSenior record);

    List<OrderSenior> selectByMobileAndPay(String mobile);
    
    List<OrderSenior> selectByUserIdAndPay(Long userId);

}