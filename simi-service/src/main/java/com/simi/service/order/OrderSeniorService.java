package com.simi.service.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.simi.po.model.order.OrderSenior;

public interface OrderSeniorService {
    int deleteByPrimaryKey(Long id);

    Long insert(OrderSenior record);

    int insertSelective(OrderSenior record);

    OrderSenior selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderSenior record);

    int updateByPrimaryKey(OrderSenior record);

	OrderSenior selectByOrderSeniorNo(String id);

	List<OrderSenior> selectByMobileAndPay(String mobile);

	Date getSeniorStartDate(String mobile);

	HashMap<String, Date> getSeniorRangeDate(Long userId);
}