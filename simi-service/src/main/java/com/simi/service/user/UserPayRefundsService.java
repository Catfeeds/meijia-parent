package com.simi.service.user;

import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserPayRefunds;

public interface UserPayRefundsService {
    int deleteByPrimaryKey(Long id);

    int insert(UserPayRefunds record);

    int insertSelective(UserPayRefunds record);

    UserPayRefunds selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPayRefunds record);

    int updateByPrimaryKey(UserPayRefunds record);
    
	public UserPayRefunds initUserPayRefunds(Orders orders, String mobile, String trade_status,
			Long orderId, String trade_no,Short orderType, UserPayRefunds userPayRefunds) ;
}