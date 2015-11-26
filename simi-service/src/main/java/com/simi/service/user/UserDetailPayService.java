package com.simi.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.vo.UserSearchVo;
import com.simi.vo.user.UserDetailPayVo;
import com.simi.po.model.order.OrderCards;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.OrderSenior;
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

	PageInfo searchUserFeedbackListPage(int pageNo, int pageSize);

	UserDetailPay userDetailPayForOrder(Users user, Orders order, OrderPrices orderPrice, String tradeStatus, String tradeNo, String payAccount);

	UserDetailPay userDetailPayForOrderSenior(Users user, OrderSenior orderSenior, String tradeStatus, String tradeNo, String payAccount);

	UserDetailPay userDetailPayForOrderCard(Users user, OrderCards orderCard, String tradeStatus, String tradeNo, String payAccount);

	List<UserDetailPay> selectByUserIdPage(Long userId, int page);

	UserDetailPayVo getUserDetailPayVo(UserDetailPay userDetailPay);

}