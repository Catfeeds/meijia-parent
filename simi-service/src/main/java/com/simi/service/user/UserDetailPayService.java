package com.simi.service.user;

import com.github.pagehelper.PageInfo;
import com.simi.vo.UserSearchVo;
import com.simi.po.model.order.OrderCards;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserDetailPay;
import com.simi.po.model.user.Users;

public interface UserDetailPayService {
    int deleteByPrimaryKey(Long id);

    int insert(UserDetailPay record);

    int insertSelective(UserDetailPay record);

    UserDetailPay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDetailPay record);

    int updateByPrimaryKey(UserDetailPay record);

	UserDetailPay selectByTradeNo(String tradeNo);

	void updateByPayAccount(String tradeNo, String payAccount);

	PageInfo searchVoListPage(UserSearchVo searchVo,int pageNo,int pageSize);

	UserDetailPay initUserDetail();

	UserDetailPay addUserDetailPayForOrder(Users user, 
										   Orders order, 
										   OrderPrices orderPrice, 
										   String tradeStatus, 
										   String tradeNo, 
										   String payAccount);

	UserDetailPay addUserDetailPayForOrderCard(Users user, 
											   OrderCards orderCard, 
											   String tradeStatus, 
											   String tradeNo, 
											   String payAccount);

}