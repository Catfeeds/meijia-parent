package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UsersService;
import com.simi.po.dao.order.OrderCardsMapper;
import com.simi.po.dao.order.OrderSeniorMapper;
import com.simi.po.dao.user.UserCouponsMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.dict.DictSeniorType;
import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserDetailPay;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;
import com.meijia.utils.DateUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderPayServiceImpl implements OrderPayService {
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderPricesService orderPricesService;
	
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
	private UserRefSecService userRefSecService;	
		
	/**
	 * 订单支付成功,后续通知功能
	 * 1. 如果为
	 */
	@Override
	public void orderPaySuccessToDo(Orders orders) {
		
//		String serviceDate = TimeStampUtil.timeStampToDateStr(orders.getStartTime()*1000);
//		String serviceTypeName = OneCareUtil.getserviceTypeName(orders.getServiceType());
//		String[] content = new String[] { serviceDate, serviceTypeName };
//		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
//				Constants.PAY_SUCCESS_SMS_TEMPLE_ID, content);
//		System.out.println(sendSmsResult.get(sendSmsResult.get(Constants.SMS_STATUS_CODE)));
	}
	
	@Override
	public boolean orderSeniorPaySuccessTodo(OrderSenior orderSenior) {

		Long userId = orderSenior.getUserId();
		Long secId = orderSenior.getSecId();
		//分配秘书
		UserRefSec userRefSec  = userRefSecService.selectByUserId(userId);
		
		if (userRefSec == null) {
			userRefSec = userRefSecService.initUserRefSec();
			userRefSec.setUserId(userId);
			userRefSec.setSecId(secId);
			userRefSecService.insert(userRefSec);
		} else {
			userRefSec.setUserId(userId);
			userRefSec.setSecId(secId);
			userRefSecService.updateByPrimaryKeySelective(userRefSec);
		}
		
		//给秘书发送信息.
		
		return true;
	}
	
}