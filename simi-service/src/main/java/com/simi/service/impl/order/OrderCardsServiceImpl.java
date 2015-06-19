package com.simi.service.impl.order;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.order.OrderCardsService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UsersService;
import com.simi.po.dao.order.OrderCardsMapper;
import com.simi.po.dao.user.UserPayStatusMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.dict.DictCardType;
import com.simi.po.model.order.OrderCards;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.user.UserDetailPay;
import com.simi.po.model.user.UserPayStatus;
import com.simi.po.model.user.Users;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderCardsServiceImpl implements OrderCardsService {
	@Autowired
	OrderCardsMapper orderCardsMapper;
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	UserPayStatusMapper userPayStatusMapper;
	
	@Autowired
	private UserDetailPayService userDetailPayService;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderCardsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(OrderCards record) {
		return orderCardsMapper.insert(record);
	}

	@Override
	public int updateOrderByOnlinePay(OrderCards orderCards, String tradeNo, String tradeStatus, String payAccount) {
		// 1) 判断字段order_status = 1
		// 4. 如果该订单为未支付的状态，则需要做如下的操作
		// 1) 操作表user_pay_status ，插入一条新的记录，记录支付的信息
		// 2) 用户的消费明细记录，操作表为user_detail_pay
		// 3) 将card_orders 表的状态改变为 order_status = 2 ,已支付状态, 支付状态pay_type = 1
		// 支付宝支付
		// 4) 用户余额，扣除相应的金额，注意如果有优惠卷的金额，操作表为users
		// 注意以上4个步骤必须为同一个事务。
		Long userId = orderCards.getUserId();
		Long nowTime = TimeStampUtil.getNowSecond();
		Users users = usersService.getUserById(userId);

		UserPayStatus userPayStatus = new UserPayStatus();
		userPayStatus.setMobile(users.getMobile());
		userPayStatus.setOrderId(orderCards.getId());
		userPayStatus.setOrderNo(orderCards.getCardOrderNo());
		userPayStatus.setOrderType(orderCards.getPayType());
		userPayStatus.setTradeId(tradeNo);
		userPayStatus.setTradeStatus(tradeStatus);
		userPayStatus.setUserId(orderCards.getUserId());
		userPayStatus.setPostParams("");// TODO PostParams
		userPayStatus.setIsSync((short) 0);
		userPayStatus.setAddTime(TimeStampUtil.getNowSecond());
		userPayStatusMapper.insert(userPayStatus);

		
		BigDecimal cardMoney = orderCards.getCardMoney();
		BigDecimal restMoney = users.getRestMoney().add(cardMoney);
		
		users.setRestMoney(restMoney);
		users.setUpdateTime(TimeStampUtil.getNowSecond());
		usersService.updateByPrimaryKeySelective(users);

		orderCardsMapper.updateByPrimaryKeySelective(orderCards);

		OrderPrices orderPrices = new OrderPrices();
		orderPrices.setOrderMoney(cardMoney);
		orderPrices.setOrderPay(cardMoney);
		UserDetailPay userDetailPay = userDetailPayService.addUserDetailPayForOrderCard(
				users, orderCards, tradeStatus, tradeNo, payAccount);
		return 1;

	}

	@Override
	public OrderCards initOrderCards(Users users, Long cardType, 
			DictCardType dictCardType, Short payType) {
		OrderCards record = new OrderCards();
		record.setCardMoney(dictCardType.getCardValue());
		record.setCardPay(dictCardType.getCardPay());
		record.setUserId(users.getId());
		record.setMobile(users.getMobile());
		record.setCardType(cardType);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setOrderStatus((short) 0);
		record.setPayType(payType);
		record.setUpdateTime(0L);

		String cardOrderNo = String.valueOf(OrderNoUtil.getOrderCardNo());
		record.setCardOrderNo(cardOrderNo);

		return record;
	}

	@Override
	public int insertSelective(OrderCards record) {
		return orderCardsMapper.insertSelective(record);
	}

	@Override
	public OrderCards selectByPrimaryKey(Long id) {
		return orderCardsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(OrderCards record) {
		return orderCardsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderCards record) {
		return orderCardsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderCards selectByOrderCardsNo(String cardOrderNo) {
		return orderCardsMapper.selectByOrderCardsNo(cardOrderNo);
	}
}