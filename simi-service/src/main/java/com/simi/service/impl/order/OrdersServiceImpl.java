package com.simi.service.impl.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.po.dao.order.OrderPricesMapper;
import com.simi.po.dao.order.OrdersMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersMapper ordersMapper;

	@Autowired
	private OrderPricesMapper orderPricesMapper;

	@Autowired
	UsersMapper usersMapper;

	@Autowired
	OrderLogService orderLogService;
	
	@Autowired
	UserDetailScoreService userDetailScoreService;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return ordersMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Orders initOrders() {

		Orders record = new Orders();
		record.setId(0L);
		record.setMobile("");
		record.setUserId(0L);
		record.setCityId(0L);
		record.setServiceType((short) 1);
		
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
	 * 订单评估成功后的操作
	 * 1. 更新用户的积分，并添加到积分明细
	 */
	@Override
	public void ordeRatedTodo(Orders orders) {
		
		Users users = usersMapper.selectByPrimaryKey(orders.getUserId());
		
		UserDetailScore userDetailScore = userDetailScoreService.initUserDetailScore();
		userDetailScore.setUserId(users.getId());
		userDetailScore.setMobile(users.getMobile());
		
		userDetailScore.setScore(Constants.RATE_CORE);
		userDetailScore.setActionId(Constants.ACTION_ORDER_RATE);		
		userDetailScore.setIsConsume(Constants.CONSUME_SCORE_GET);
		userDetailScoreService.insert(userDetailScore);
		
		
		users.setScore(users.getScore()+Constants.RATE_CORE);
		usersMapper.updateByPrimaryKey(users);
				
	}
	
}