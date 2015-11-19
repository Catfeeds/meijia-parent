package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.meijia.utils.SmsUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.common.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.admin.AdminAccountService;
import com.simi.service.async.OrderAsyncService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UsersService;
import com.simi.po.dao.order.OrderCardsMapper;
import com.simi.po.dao.order.OrderSeniorMapper;
import com.simi.po.dao.user.UserCouponsMapper;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserCoupons;
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
	private UserRefSecService userRefSecService;	
	
	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;	
	
	@Autowired
	PartnerServiceTypeService partnerServiceTypeService;
		
	/**
	 * 订单支付成功,后续通知功能
	 * 1. 如果为
	 */
	@Override
	public void orderPaySuccessToDo(Orders order) {
		
		
		Long serviceTypeId = order.getServiceTypeId();
		Long userId = order.getUserId();
		Long partnerUserId = order.getPartnerUserId();
		String orderNo = order.getOrderNo();
		OrderPrices orderPrice = orderPricesService.selectByOrderId(order.getOrderId());
		
		//获取服务报价的信息。
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(orderPrice.getServicePriceId());
				
		
		BigDecimal orderPay = orderPrice.getOrderPay();
		String orderPayStr = orderPay.toString();
		String servicePriceName = serviceType.getName();
		
		Users u = usersService.selectByPrimaryKey(order.getUserId());
		String userName = u.getName();
		String userMobile = order.getMobile();
		
		Users partnerUser = usersService.selectByPrimaryKey(order.getPartnerUserId());
		String partnerUserMobile = partnerUser.getMobile();
		
		//通知相关服务商
		String[] partnerContent = new String[] { orderPayStr, servicePriceName, userName, userMobile , " " };
//		SmsUtil.SendSms(partnerUserMobile, "48147", partnerContent);
		
		//通知用户
		String addTimeStr = TimeStampUtil.timeStampToDateStr(order.getAddTime() * 1000 , "yyyy-MM-dd HH:mm");
		String[] userContent = new String[] { userName, addTimeStr, servicePriceName };
		SmsUtil.SendSms(userMobile, "48132", userContent);
		
		//如果为秘书订单，则需要做指派用户与秘书的绑定信息.
		if (serviceTypeId.equals(75L)) {
			//分配秘书
			UserRefSec userRefSec  = userRefSecService.selectByUserId(userId);
			
			if (userRefSec == null) {
				userRefSec = userRefSecService.initUserRefSec();
				userRefSec.setUserId(userId);
				userRefSec.setSecId(partnerUserId);
				userRefSecService.insert(userRefSec);
			} else {
				userRefSec.setUserId(userId);
				userRefSec.setSecId(partnerUserId);
				userRefSecService.updateByPrimaryKeySelective(userRefSec);
			}			
		}
		
		//todo, 更新user_couonp_id 的使用，变成已使用.
		
		if (orderPrice.getUserCouponId()>0) {
			UserCoupons record = userCouponsMapper.selectByPrimaryKey(orderPrice.getUserCouponId());
			
			record.setIsUsed((short)1);
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			userCouponsMapper.updateByPrimaryKeySelective(record);
		}
		//异步操作
		//订单操作成功后，对应的用户累加积分（1:1）
		orderAsyncService.orderScore(order);
		
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
		
//		List<AdminAccount> adminAccounts = adminAccountService.selectByAll();
			//查出所有运营部的人员（roleId=3）
		Long roleId = 3L;
		List<AdminAccount> adminAccounts = adminAccountService.selectByRoleId(roleId);
		List<String> mobileList = new ArrayList<String>();
		for (AdminAccount item: adminAccounts) {
			if (!StringUtil.isEmpty(item.getMobile())) {
			mobileList.add(item.getMobile());
			}
			
		}
		mobileList.add(secMobile);
		String[] content = new String[] { name,orderPStr,validDayStr };
		for (int i = 0; i < mobileList.size(); i++) {
			
		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobileList.get(i),
				Constants.SEC_REGISTER_ID, content);
		
		System.out.println(sendSmsResult + "00000000000000");
		}
		return true;
	}
	
}