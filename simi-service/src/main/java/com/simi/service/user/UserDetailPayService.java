package com.simi.service.user;

import com.github.pagehelper.PageInfo;
import com.simi.vo.user.UserSearchVo;
import com.simi.po.model.order.OrderCards;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserDetailPay;

public interface UserDetailPayService {
    int deleteByPrimaryKey(Long id);

    int insert(UserDetailPay record);

    int insertSelective(UserDetailPay record);

    UserDetailPay selectByPrimaryKey(Long id);

	UserDetailPay initUserDetailPay(String mobile, String trade_status,
			OrderCards orderCards, Long userId, Long orderId, short payType,
			OrderPrices orderPrices, String trade_no, String payAccount);

	UserDetailPay initUserDetailPay(String mobile, String trade_status,
			Orders orders, Long userId, Long orderId, short payType,
			OrderPrices orderPrices, String trade_no, String payAccount);

    int updateByPrimaryKeySelective(UserDetailPay record);

    int updateByPrimaryKey(UserDetailPay record);

	UserDetailPay initUserDetailDefault();

	UserDetailPay selectByTradeNo(String tradeNo);

	void updateByPayAccount(String tradeNo, String payAccount);

	PageInfo searchVoListPage(UserSearchVo searchVo,int pageNo,int pageSize);



}