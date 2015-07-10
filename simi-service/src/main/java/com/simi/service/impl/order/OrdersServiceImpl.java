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
import com.simi.po.model.sec.SecRef3rd;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.huanxin.EasemobMessages;

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

		Users u = usersService.selectVoByUserId(order.getUserId());
		//2. 环信透传功能.
		//获得发送者的环信账号
		Long secId = orderViewVo.getSecId();
		SecRef3rd secRef3rd = secService.selectBySecIdForIm(secId);
		String fromIm = secRef3rd.getUsername();
		Long userId = u.getId();
		//获得接收者的环信账号.
		UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(userId);
		String toIm = userRef3rd.getUsername();
		
		
		orderPushNotify(orderViewVo, fromIm, toIm);
		
		//3. 百度推送功能 todo
		
		return true;
		
	}
	
	
	/**
	 * 用户确认订单后的操作
	 * 1. 记录订单日志
	 * 2. 环信透传功能.
	 * 3. 百度推送功能.(todo)
	 */
	@Override
	public Boolean orderConfirmTodo(String orderNo) {
		Orders order = ordersMapper.selectByOrderNo(orderNo);
		
		if (order == null) {
			return false;
		}
		
		//1.记录订单日志
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLogService.insert(orderLog);
		
		OrderViewVo orderViewVo = orderQueryService.getOrderView(order);

		//2. 环信透传功能.
		//获得接受者的环信账号
		Long secId = orderViewVo.getSecId();
		SecRef3rd secRef3rd = secService.selectBySecIdForIm(secId);
		String toIm = secRef3rd.getUsername();

		orderPushNotify(orderViewVo, "", toIm);
		
		//3. 百度推送功能 todo
		
		return true;
		
	}	
	
	/**
	 * 订单推送，触发环信的透传功能
	 * @param orderNo
	 * @return
	 */
	
	public Boolean orderPushNotify(OrderViewVo orderViewVo, String fromIm, String toIm) {
		

		JsonNodeFactory factory = new JsonNodeFactory(false);		
		String targetTypeus = "users";
		ObjectNode ext = factory.objectNode();
		ArrayNode targetusers = factory.arrayNode();
		targetusers.add(toIm);

		// 给用户发一条透传消息
		ObjectNode cmdmsg = factory.objectNode();
		cmdmsg.put("action", "order");
		cmdmsg.put("type", "cmd");
		

		ext.put("order_id", orderViewVo.getId());
		ext.put("order_no", orderViewVo.getOrderNo());
		ext.put("order_pay_type", orderViewVo.getOrderPayType());
		ext.put("add_time", TimeStampUtil.timeStampToDateStr(orderViewVo.getAddTime() * 1000));
		ext.put("service_type_name", orderViewVo.getServiceTypeName());
		ext.put("service_content", orderViewVo.getServiceContent());
		ext.put("order_status", orderViewVo.getOrderStatus());
		
		if (orderViewVo.getStartTime() > 0L) {
			ext.put("service_time", TimeStampUtil.timeStampToDateStr(orderViewVo.getStartTime()));
		} else {
			ext.put("service_time", "");
		}
		
		ext.put("service_addr", orderViewVo.getServiceAddr());
		ext.put("order_money", orderViewVo.getOrderMoney());

		ObjectNode sendcmdMessageusernode = EasemobMessages.sendMessages(targetTypeus, targetusers, cmdmsg, fromIm, ext);

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

	@Override
	public List<Orders> selectByUserId(Long userId) {
		
		return ordersMapper.selectByUserId(userId);
	}
	



	
}