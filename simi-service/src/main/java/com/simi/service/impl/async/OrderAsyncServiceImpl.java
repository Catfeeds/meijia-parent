package com.simi.service.impl.async;

import java.math.BigDecimal;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;
import com.simi.service.async.OrderAsyncService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;
@Service
public class OrderAsyncServiceImpl implements OrderAsyncService{

	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private OrderPricesService orderPricesService;
	
	@Autowired
	private UserDetailScoreService userDetailScoreService;
	
	@Autowired
	private OrdersService ordersService;
	
	//订单支付成功后，将用户的积分增加相应的积分
	@Async
	@Override
	public Future<Boolean> orderScore(Orders order){
		
		
		Users user = usersService.selectByPrimaryKey(order.getUserId());
		
		if (user == null) return new AsyncResult<Boolean>(true);
		
		//获得用户原有的积分
		Integer score = user.getScore();
		
		OrderPrices orderPrice = orderPricesService.selectByOrderId(order.getOrderId());
		//获得用户订单金额（订单金额：积分=1:1）
		
		BigDecimal orderPay = orderPrice.getOrderPay();
		
		
		//若订单支付金额(orderPay)不为0, 则对积分进行累加，并更新User表
		// 若为0则不做操作
		if (orderPay != null) {
			
			//BigDecimal类型转成Integer类型
			Integer orderPayInteger = orderPay.intValue();
			
			//累加后的总积分
			Integer scoreSum = score + orderPayInteger;
			//更新user表数据
			user.setScore(scoreSum);
			user.setUpdateTime(TimeStampUtil.getNow()/1000);
			usersService.updateByPrimaryKeySelective(user);
			//向UserDetailScore表中插入积分明细记录
			UserDetailScore  record = userDetailScoreService.initUserDetailScore();
			
			record.setUserId(order.getUserId());
			record.setMobile(user.getMobile());
			record.setScore(orderPayInteger);
			
			//userDetailScoreService.updateByPrimaryKeySelective(record);
			userDetailScoreService.insert(record);
			
		}
		//更新order 表的 is_score字段放置为 1,积分已返回
		order.setIsScore((short)1);
		ordersService.updateByPrimaryKeySelective(order);

		return new AsyncResult<Boolean>(true);
	}
	
}
