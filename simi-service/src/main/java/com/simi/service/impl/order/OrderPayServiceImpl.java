package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.List;

import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.async.NoticeSmsAsyncService;
import com.simi.service.async.OrderAsyncService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.async.UserScoreAsyncService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UserRefService;
import com.simi.service.user.UsersService;
import com.simi.utils.OrderUtil;
import com.simi.vo.user.UserRefSearchVo;
import com.simi.po.dao.order.OrderCardsMapper;
import com.simi.po.dao.order.OrderSeniorMapper;
import com.simi.po.dao.user.UserCouponsMapper;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.UserRef;

@Service
public class OrderPayServiceImpl implements OrderPayService {
	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
	private OrderAsyncService orderAsyncService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private UserDetailPayService userDetailPayService;

	@Autowired
	private OrderSeniorMapper orderSeniorMapper;

	@Autowired
	UserCouponsMapper userCouponsMapper;

	@Autowired
	OrderCardsMapper orderCardsMapper;

	@Autowired
	OrderLogService orderLogService;

	@Autowired
	OrderSeniorService orderSeniorService;

	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;

	@Autowired
	PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	@Autowired
	private NoticeSmsAsyncService noticeSmsAsyncService;
	
	@Autowired
	private NoticeAppAsyncService noticeAppAsyncService;
	
	@Autowired
	private UserScoreAsyncService userScoreAsyncService;
	
	@Autowired
	private UserRefService userRefService;

	/**
	 * 订单支付成功,后续通知功能 1. 如果为
	 */
	@Override
	public void orderPaySuccessToDo(Orders order) {
		
		Long orderId = order.getOrderId();
		Long serviceTypeId = order.getServiceTypeId();
		Long userId = order.getUserId();
		Long partnerUserId = order.getPartnerUserId();

		OrderPrices orderPrice = orderPricesService.selectByOrderId(order.getOrderId());
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
		String serviceTypeName = "";
		if (serviceType != null) {
			serviceTypeName = serviceType.getName();
		}
		//如果为秘书订单，则需要做指派用户与秘书的绑定信息.
		if (serviceTypeId.equals(75L)) {
			//分配秘书
			
			UserRefSearchVo searchVo = new UserRefSearchVo();
			searchVo.setUserId(userId);
			searchVo.setRefType("sec");
			List<UserRef> rs  = userRefService.selectBySearchVo(searchVo);
			
			UserRef userRef = null;
			if (!rs.isEmpty()) userRef = rs.get(0);
			if (userRef == null) {
				userRef = userRefService.initUserRef();
				userRef.setUserId(userId);
				userRef.setRefId(partnerUserId);
				userRef.setRefType("sec");
				userRefService.insert(userRef);
			} else {
				userRef.setUserId(userId);
				userRef.setRefId(partnerUserId);
				userRefService.updateByPrimaryKeySelective(userRef);
			}			
		}
		
		//todo, 更新user_couonp_id 的使用，变成已使用.
		if (orderPrice.getUserCouponId()>0) {
			UserCoupons record = userCouponsMapper.selectByPrimaryKey(orderPrice.getUserCouponId());
			
			record.setIsUsed((short)1);
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			userCouponsMapper.updateByPrimaryKeySelective(record);
		}
		
		//通知服务商
		noticeSmsAsyncService.noticeOrderPartner(orderId);
		
		//通知用户
		noticeSmsAsyncService.noticeOrderUser(orderId);
		
		//通知运营人员，订单支付成功
		noticeSmsAsyncService.noticeOrderOper(orderId);
		 		
		//异步操作
		//订单操作成功后，对应的用户累加积分（1:1）
		orderAsyncService.orderScore(order);	
		
		//送水订单的后续操作
		if (serviceTypeId.equals(239L)) {
			orderWaterPaySuccessToDo(order);
		}
		
		//保洁订单的后续操作
		if (serviceTypeId.equals(204L)) {
			orderWaterPaySuccessToDo(order);
		}
		
		//废品回收后续操作
		if (serviceTypeId.equals(246L)) {
			orderRecyclePaySuccessToDo(order);
		}
		
		//团建扩展后续操作
		if (serviceTypeId.equals(246L)) {
			orderRecyclePaySuccessToDo(order);
		}
		
		//积分赠送
		BigDecimal orderMoney = orderPrice.getOrderMoney();
		if (!orderMoney.equals(new BigDecimal(0))) {
			if (StringUtil.isEmpty(serviceTypeName)) serviceTypeName = "订单支付";
			Integer score = orderMoney.intValue();
			userScoreAsyncService.sendScore(userId, score, "order", orderId.toString(), serviceTypeName);
		}
		
	}

	@Override
	public boolean orderSeniorPaySuccessTodo(OrderSenior orderSenior) {
		
		Long orderId = orderSenior.getId(); 
		Long userId = orderSenior.getUserId();
		Long secId = orderSenior.getSecId();
		// 分配秘书
		UserRefSearchVo searchVo = new UserRefSearchVo();
		searchVo.setUserId(userId);
		searchVo.setRefType("sec");
		List<UserRef> rs  = userRefService.selectBySearchVo(searchVo);
		
		UserRef userRef = null;
		if (!rs.isEmpty()) userRef = rs.get(0);
		if (userRef == null) {
			userRef = userRefService.initUserRef();
			userRef.setUserId(userId);
			userRef.setRefId(secId);
			userRef.setRefType("sec");
			userRefService.insert(userRef);
		} else {
			userRef.setUserId(userId);
			userRef.setRefId(secId);
			userRefService.updateByPrimaryKeySelective(userRef);
		}			
		
		// 购买秘书成功后给同事给运营人员和秘书发送信息.
		noticeSmsAsyncService.noticeOrderPartner(orderId);
		
		return true;
	}

	@Override
	public void orderWaterPaySuccessToDo(Orders order) {
		Long userId = order.getUserId();
		Long orderId = order.getOrderId();
		// 通知运营人员，进行送水服务商的人工派工流程.


		//异步产生首页消息信息.
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(order.getServiceTypeId());
		String title = serviceType.getName();
		String summary =  OrderUtil.getOrderStausMsg(order.getOrderStatus());
		userMsgAsyncService.newActionAppMsg(userId, orderId, "water", title, summary, "");
		
		//推送消息到app
		noticeAppAsyncService.pushMsgToDevice(userId, title, summary);
		
	}
	
	@Override
	public void orderRecyclePaySuccessToDo(Orders order){
		Long userId = order.getUserId();
		Long orderId = order.getOrderId();
		// 通知运营人员，进行废品回收服务商的人工派工流程.
		
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(order.getServiceTypeId());
		String title = serviceType.getName();
		String summary =  OrderUtil.getOrderStausMsg(order.getOrderStatus());
		userMsgAsyncService.newActionAppMsg(userId, orderId, "recycle", title, summary, "");
		
	}
	
	@Override
	public void orderGreenPaySuccessToDo(Orders order) {
		// 通知运营人员，进行绿植服务商的人工派工流程.
	}

	@Override
	public void orderTeamPaySuccessToDo(Orders order) {
		// 通知运营人员，进行绿植服务商的人工派工流程.
	}
}