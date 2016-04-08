package com.simi.service.impl.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.sec.SecService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UsersService;
import com.simi.vo.order.OrderViewVo;
import com.simi.po.dao.order.OrderPricesMapper;
import com.simi.po.dao.order.OrdersMapper;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.Orders;
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
	
	@Autowired
	OrderQueryService orderQueryService;	
	

	@Override
	public int deleteByPrimaryKey(Long id) {
		return ordersMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Orders initOrders() {

		Orders record = new Orders();
		record.setOrderId(0L);
		record.setOrderNo("");
		record.setPartnerUserId(0L);
		record.setServiceTypeId(0L);
		record.setUserId(0L);
		record.setMobile("");
		record.setProvinceId(0L);
		record.setCityId(0L);
		record.setRegionId(0L);
		record.setOrderType((short) 0);
		record.setOrderDuration((short) 0);
		record.setServiceContent("");
		record.setServiceDate(0L);
		record.setAddrId(0L);
		record.setRemarks("");
		record.setOrderFrom(Constants.USER_APP);
		record.setOrderStatus(Constants.ORDER_STATUS_1_PAY_WAIT);
		record.setOrderRate((short) 0);
		record.setOrderRateContent("");
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
	
	/**
	 * 下单成功后的操作
	 * 1. 记录订单日志.
	 * 2. 环信透传功能.
	 * 3. 百度推送功能.(todo)
	 */
	@Override
	public Boolean orderSuccessTodo(String orderNo) {
		
		Orders order = ordersMapper.selectByOrderNo(orderNo);
		
		if (order == null) {
			return false;
		}
		
		//记录订单日志.
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLogService.insert(orderLog);
		
		OrderViewVo orderViewVo = orderQueryService.getOrderView(order);

		Users u = usersService.selectByPrimaryKey(order.getUserId());
		
		return true;
		
	}
		
	/**
	 * 订单评价成功后的操作
	 * 1. 更新用户的积分，并添加到积分明细
	 */
	@Override
	public void orderRatedTodo(Orders orders) {
		
//		Users users = usersService.selectByPrimaryKey(orders.getUserId());
		
//		UserDetailScore userDetailScore = userDetailScoreService.initUserDetailScore();
//		userDetailScore.setUserId(users.getId());
//		userDetailScore.setMobile(users.getMobile());
		
//		userDetailScore.setScore(0);
//		userDetailScore.setActionId(Constants.ACTION_ORDER_RATE);		
//		userDetailScore.setIsConsume(Constants.CONSUME_SCORE_GET);
//		userDetailScoreService.insert(userDetailScore);
		
//		users.setScore(users.getScore()+0);
//		usersService.updateByPrimaryKeySelective(users);				
	}

	@Override
	public List<Orders> selectByUserId(Long userId) {
		
		return ordersMapper.selectByUserId(userId);
	}

	@Override
	public List<Orders> selectByOrderStatus() {
		
		return ordersMapper.selectByOrderStatus();
	}
	@Override
	public List<Orders> selectByOrderStatus7Days() {
		
		return ordersMapper.selectByOrderStatus7Days();
	}

	@Override
	public List<Orders> selectByorder1Hour() {
		
		return ordersMapper.selectByorder1Hour();
				
	}

	@Override
	public Orders selectByOrderNo(String orderNo) {
		
		return ordersMapper.selectByOrderNo(orderNo);
	}
	
	@Override
	public Orders selectByPrimaryKey(Long orderId) {
		
		return ordersMapper.selectByPrimaryKey(orderId);
	}
	
	@Override
	public List<Orders> selectByOrderIds(List<Long> orderIds) {
		return ordersMapper.selectByOrderIds(orderIds);
	}
	



	
}