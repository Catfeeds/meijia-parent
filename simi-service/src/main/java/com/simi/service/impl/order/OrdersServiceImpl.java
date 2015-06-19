package com.simi.service.impl.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrdersService;
import com.simi.service.sec.SecService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UsersService;
import com.simi.po.dao.order.OrderPricesMapper;
import com.simi.po.dao.order.OrdersMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.sec.SecRef3rd;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersMapper ordersMapper;

	@Autowired
	private OrderPricesMapper orderPricesMapper;

	@Autowired
	UsersService usersService;
	
	@Autowired
	UserRef3rdService userRef3rdService;
	
	@Autowired
	SecService secService;

	@Autowired
	OrderLogService orderLogService;
	
	@Autowired
	UserDetailScoreService userDetailScoreService;
	
	@Autowired
	OrderPricesService orderPricesService;
	

	@Override
	public int deleteByPrimaryKey(Long id) {
		return ordersMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Orders initOrders() {

		Orders record = new Orders();
		record.setId(0L);
		record.setSecId(0L);
		record.setMobile("");
		record.setUserId(0L);
		record.setCityId(0L);
		record.setAddrId(0L);
		record.setServiceType(1L);
		record.setServiceContent("");
		record.setServiceDate(0L);
		record.setStartTime(0L);
		record.setOrderNo("");
		record.setOrderRate((short) 0);
		record.setOrderRateContent("");
		record.setOrderStatus(Constants.ORDER_STATUS_0_CLOSE);
		record.setOrderFrom(Constants.USER_APP);
		record.setIsScore((short) 0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public Long insert(Orders record) {
		return ordersMapper.insert(record);
	}

	@Override
	public int insertSelective(Orders record) {
		return ordersMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKey(Orders record) {
		return ordersMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Orders record) {
		return ordersMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<String> selectByDistinctMobileLists() {
		return ordersMapper.selectByDistinctMobileLists();
	}
	
	
	/**
	 * 下单成功后的操作
	 * 1. 环信透传功能.
	 * 2. 百度推送功能.(todo)
	 */
	@Override
	public Boolean orderSuccessTodo(String orderNo) {
		Orders order = ordersMapper.selectByOrderNo(orderNo);
		
		if (order == null) {
			return false;
		}
		
		OrderPrices orderPrice = orderPricesService.selectByOrderId(order.getId());
		
		Users u = usersService.selectVoByUserId(order.getUserId());
		//1. 环信透传功能.
		orderPushNotify(order, orderPrice, u);
		
		//2. 百度推送功能 todo
		
		return true;
		
	}
	
	/**
	 * 订单推送，触发环信的透传功能
	 * @param orderNo
	 * @return
	 */
	
	public Boolean orderPushNotify(Orders order, OrderPrices orderPrice, Users user) {
		
		//获得发送者的环信账号
		Long secId = order.getSecId();
		SecRef3rd secRef3rd = secService.selectBySecIdForIm(secId);
		
		Long userId = user.getId();
		//获得接收者的环信账号.
		UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(userId);
		
		
		return true;
	}
	
	
	/**
	 * 订单评价成功后的操作
	 * 1. 更新用户的积分，并添加到积分明细
	 */
	@Override
	public void orderRatedTodo(Orders orders) {
		
		Users users = usersService.getUserById(orders.getUserId());
		
		UserDetailScore userDetailScore = userDetailScoreService.initUserDetailScore();
		userDetailScore.setUserId(users.getId());
		userDetailScore.setMobile(users.getMobile());
		
		userDetailScore.setScore(Constants.RATE_CORE);
		userDetailScore.setActionId(Constants.ACTION_ORDER_RATE);		
		userDetailScore.setIsConsume(Constants.CONSUME_SCORE_GET);
		userDetailScoreService.insert(userDetailScore);
		
		
		users.setScore(users.getScore()+Constants.RATE_CORE);
		usersService.updateByPrimaryKeySelective(users);
				
	}
	
}