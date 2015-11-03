package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.meijia.utils.SmsUtil;
import com.simi.common.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.admin.AdminAccountService;
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
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;

@Service
public class OrderPayServiceImpl implements OrderPayService {
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private AdminAccountService adminAccountService;
	
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
		
		//购买秘书成功后给同事给运营人员和秘书发送信息.
		Users sec = usersService.selectByPrimaryKey(secId);
		String name = sec.getName();
		short validDay = orderSenior.getValidDay();
		BigDecimal orderPay = orderSenior.getOrderPay();
		
		String orderPStr = orderPay.toString();
		String secMobile = sec.getMobile();
		String validDayStr = String.valueOf(validDay);
		
		List<AdminAccount> adminAccounts = adminAccountService.selectByAll();
		List<String> mobileList = new ArrayList<String>();
		for (AdminAccount item: adminAccounts) {
			mobileList.add(item.getMobile());
			mobileList.add(secMobile);
		}
		String[] content = new String[] { name,orderPStr,validDayStr };
		for (int i = 0; i < mobileList.size(); i++) {
			
		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobileList.get(i),
				Constants.SEC_REGISTER_ID, content);
		
		System.out.println(sendSmsResult + "00000000000000");
		}
		return true;
	}
	
}