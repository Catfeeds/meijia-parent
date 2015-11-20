package com.simi.action.job;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.tools.jar.resources.jar;

import com.gexin.rp.sdk.http.utils.Constant;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.TimeStampUtil;
import com.mysql.fabric.xmlrpc.base.Array;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/job/order")
public class JobOrderController extends BaseController {

	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private UserCouponService userCouponService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private OrderPricesService orderPricesService;

	/**
	 *  订单超过1个小时未支付,则关闭订单,
	 *  就是订单的状态变成 9 ,并且把相应的优惠劵的信息置为空
	 */

	@RequestMapping(value = "check_order_more_60min", method = RequestMethod.GET)
	public AppResultData<Object> OrderMore60min(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// 1.获得秘书一小时内未支付的订单列表
	
		List<Orders> list = ordersService.selectByOrderStatus();

		for (int i = 0; i < list.size(); i++) {

			//订单状态设置为0 已关闭
			Orders record = list.get(i);
			//获得下单时间
			Long orderTime = record.getAddTime();
			//获得当前时间
			Long nowTime = TimeStampUtil.getNowSecond();
			
			//获得下单时间和但钱是捡的时间差
			Long addTime = orderTime + 3600 - nowTime;
			
			//若时间差< 0 则说明订单已经超过一小时未支付
			if (addTime < 0) {
			
			record.setOrderStatus(Constants.ORDER_STATUS_0_CLOSE);
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			ordersService.updateByPrimaryKeySelective(record);
			//相应的优惠券信息置为空
			
			if (record.getOrderNo() != ""&& record.getOrderNo() != null) {
				
			UserCoupons userCoupons = userCouponService.selectByOrderNo(record.getOrderNo());
			if (userCoupons != null) {
				userCoupons.setOrderNo("0");
				userCoupons.setUpdateTime(TimeStampUtil.getNowSecond());
				userCouponService.updateByPrimaryKeySelective(userCoupons);
			}
			
			
			//设置orderPrice表中counpnId为0
			OrderPrices orderPrices = orderPricesService.selectByOrderId(record.getOrderId());
			if (orderPrices!=null) {
				if (orderPrices.getUserCouponId()>0) {
					orderPrices.setUserCouponId(0L);
					orderPrices.setUpdateTime(TimeStampUtil.getNowSecond());
					orderPricesService.updateByPrimaryKeySelective(orderPrices);
				}
			}
			
			}
			
			}
		}
		return result;
	}
	
	/**
	 * 订单如果超过7天未评价，则默认给此订单默认5星的评价
	 * ，评价内容写的是  ： 订单超时未评价，系统默认给5星好评
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "check_order_more_7Day", method = RequestMethod.GET)
	public AppResultData<Object> OrderMore7Day(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		//找出未评价的订单
		List<Orders> list = ordersService.selectByOrderStatus7Days();
		
		
		for (int i = 0; i < list.size(); i++) {
		
		Orders record = list.get(i);
		//获得最后一次更新时间
		Long updateTime = record.getUpdateTime();
		
		//获得当前时间
		Long nowTime = TimeStampUtil.getNowSecond();
		
		//获得最后一次更新时间和当前时间差
		//604800===>七天的秒数
		Long addTime = updateTime + 604800 - nowTime;
		
		//若时间差< 0 则说明订单已经超过七天未支付
		if (addTime < 0) {
		
			record.setOrderRate((short)5);
			record.setOrderRateContent("订单超时未评价，系统默认给5星好评");
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			ordersService.updateByPrimaryKeySelective(record);
		
		}
		}
		return result;
		}

	/**
	 * 订单超时未支付通知用户， 找出当前未支付的订单， 
	 * 找到用户手机号，通知用户.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "check_order_more_1hour", method = RequestMethod.GET)
	public AppResultData<Object> OrderOrder1Hour(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
	
		//查找出订单在一小时内未支付的订单集合
		List<Orders> list = ordersService.selectByorder1Hour();
		
		for (int i = 0; i < list.size(); i++) {
			
			Orders orders = list.get(i);
			
			Users users = usersService.selectByPrimaryKey(orders.getUserId());
			//用户名
			String name = users.getName();
			
			Long addTime = orders.getAddTime()*1000;
			//下单时间
			String addTimeStr = TimeStampUtil.timeStampToDateStr(addTime);
			
		    String mobile = orders.getMobile();
		    
			PartnerServiceType partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(orders.getServiceTypeId());
			
			//服务报价名称
			String partnerServiceTypeName = partnerServiceType.getName();
			
			String[] content = new String[] {name,addTimeStr,partnerServiceTypeName};
			
			HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
					Constants.USER_ORDER_MORE_1HOUR, content);
			
			System.out.println(sendSmsResult + "00000000000000");
			
		}
		return result;
		}
	
	
	/**
	 *  优惠劵即将过期通知， 如果优惠劵离过期还有一天，则发送短信.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "check_coupons_1day", method = RequestMethod.GET)
	public AppResultData<Object> OrderCoupons1Day(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
	
		//查出count（*）
		List<HashMap> list = userCouponService.selectCountUserId();
		
		for (int i = 0; i < list.size(); i++) {
		
		    HashMap map = list.get(i);
		    //给用户发短信提醒	
		 	String mobile = map.get("mobile").toString();
		 	String count = map.get("count").toString();
		 	
			String[] content = new String[] {count};
			
			HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
					Constants.USER_COUPON_EXPTIME, content);
			
			System.out.println(sendSmsResult + "00000000000000");
		
		}
		return result;
	}
	
	}
