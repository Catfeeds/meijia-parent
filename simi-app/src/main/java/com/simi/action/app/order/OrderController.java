package com.simi.action.app.order;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.DateUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.common.ValidateService;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.order.OrderListVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderController extends BaseController {
	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserCouponService userCouponService;
		
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderQueryService orderQueryService;	
	
	@Autowired
	private OrderLogService orderLogService;
	
	@Autowired
	OrderPricesService orderPricesService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;	
	
	@Autowired
	UserAddrsService addrsService;
	
	@Autowired
	private UserDetailPayService userDetailPayService;	
	
	@Autowired
	private OrderPayService orderPayService;	
	
	@Autowired
	private ValidateService validateService;	

	// 1. 订单提交接口
	/**
	 *  @param user_id			int		用户id
	 *  @param mobile			string	手机号
	 *	@param service_type		int		服务类型
	 *  @param service_content  string  服务内容
	 *	@param order_pay_type	int		支付方式 0 = 无需支付 1 = 线上支付
	 *	@param remarks			string	备注,urlencode
	 *	@param order_from		int		来源 0 = APP 1 = 微网站 2 = 管理后台
	 *  @param
	 *	@param order_pay		DECIMAL(9,2)	支付金额
	 *	@param service_date		int		服务开始日期， 时间戳，精确到秒, 2014-12-17 00:00:00 变成的时间戳
	 *	@param start_time		int		服务开始时间, 时间戳，精确到秒,2014-12-17 07:00:00 变成时间戳的
	 *	@param addr_id	false	int		服务地址ID 
	 *  @param is_push			int     是否需要推送给用户,  is_push = 0 不推送， 1 = 推送
	 *
	 *  @return  OrderViewVo
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "post_add", method = RequestMethod.POST)
	public AppResultData<Object> saveOrder(
			@RequestParam("partner_user_id") Long partnerUserId,
			@RequestParam("service_type_id") Long serviceTypeId,
			@RequestParam("service_price_id") Long servicePriceId,
			@RequestParam("user_id") Long userId,
			@RequestParam("mobile") String mobile,
			@RequestParam("pay_type") Short payType,
			@RequestParam(value = "user_coupon_id", required = false, defaultValue = "0") Long userCouponId,
			@RequestParam(value = "remarks", required = false, defaultValue = "") String remarks,
			@RequestParam(value = "order_from", required = false, defaultValue = "0") Short orderFrom,
			@RequestParam(value = "service_date", required = false, defaultValue = "0") Long serviceDate,
			@RequestParam(value = "addr_id", required = false, defaultValue = "0") Long addrId,
			@RequestParam(value = "city_id", required = false, defaultValue = "2") Long cityId
			) throws UnsupportedEncodingException {

		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		Users u = userService.selectByPrimaryKey(userId);
		// 判断是否为注册用户，非注册用户返回 999		
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		v = validateService.validateUser(partnerUserId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		//如果用户没有手机号，则需要更新用户手机号,并且判断是否唯一.
		if (StringUtil.isEmpty(u.getMobile())) {
			Users existUser = userService.selectByMobile(mobile);
			if (!existUser.getId().equals(u.getId())) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("手机号在其他用户已经存在");
				return result;
			}
			u.setMobile(mobile);
			userService.updateByPrimaryKeySelective(u);
		}
		
		//加入服务地区限制
		v = validateService.validateOrderCity(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		//获取服务报价的信息。
		PartnerServicePriceDetail servicePrice = partnerServicePriceDetailService.selectByServicePriceId(servicePriceId);
		
		if (servicePrice == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("服务报价不存在!");
			return result;
		}
		
		BigDecimal orderMoney = new BigDecimal(0.0);
		BigDecimal orderPay = new BigDecimal(0.0);
		
		orderMoney = servicePrice.getDisPrice();//原价
		orderPay = servicePrice.getDisPrice();//折扣价
		
		// 调用公共订单号类，生成唯一订单号
    	Orders order = null;
    	String orderNo = "";
    
    	orderNo = String.valueOf(OrderNoUtil.genOrderNo());
		order = ordersService.initOrders();
    	
		
		if (userCouponId > 0L) {
			//todo, 校验优惠劵是否有效.
			result = userCouponService.validateCouponAll(userId, userCouponId, orderNo, serviceTypeId, servicePriceId, orderFrom);
			if (result.getStatus() != Constants.SUCCESS_0) {
				return result;
			}
			
			
		}
		
		orderPay = orderPricesService.getPayByOrder(orderPay, userCouponId);
		
		
		
		if (payType.equals(Constants.PAY_TYPE_0)) {
			//1.先判断用户余额是否够支付
			if(u.getRestMoney().compareTo(orderPay) < 0) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.ERROR_999_MSG_5);
				return result;
			}
		}
		
		//如果服务大类为 云秘书 如果当前已经有秘书，且还未过期，则提示不可购买
		if (serviceTypeId.equals(75L)) {
			
			Date seniorEndDate = orderQueryService.getSeniorRangeDate(userId);

			if (! (seniorEndDate == null) ) {
				
				String endDateStr = DateUtil.formatDate(seniorEndDate);
				String nowStr = DateUtil.getToday();
				if (DateUtil.compareDateStr(nowStr, endDateStr) >= 0) {
					result.setStatus(Constants.ERROR_999);
					result.setMsg("当前已经购买过秘书，服务时间到"+ endDateStr + "截止.");
					return result;
				} 	
			}	
		}
		
		
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
		PartnerServiceType serviceTypePrice = partnerServiceTypeService.selectByPrimaryKey(servicePriceId);
		// 服务内容及备注信息需要进行urldecode;
		String serviceContent = serviceType.getName() + " " + serviceTypePrice.getName();
		
		if (!StringUtil.isEmpty(remarks)) {
			remarks = URLDecoder.decode(remarks,Constants.URL_ENCODE);
		}
		    	
		
		//保存订单信息
		
		order.setOrderNo(orderNo);
		order.setPartnerUserId(partnerUserId);
		order.setServiceTypeId(serviceTypeId);
		order.setUserId(userId);
		order.setMobile(mobile);

		order.setOrderType(servicePrice.getOrderType());
		order.setOrderDuration(servicePrice.getOrderDuration());
		order.setServiceContent(serviceContent);
		order.setServiceDate(serviceDate);
		order.setAddrId(addrId);
		order.setRemarks(remarks);
		order.setOrderFrom(orderFrom);
		order.setOrderStatus(Constants.ORDER_STATUS_1_PAY_WAIT);
		order.setCityId(cityId);
		ordersService.insert(order);
		Long orderId = order.getOrderId();
		
		//记录订单日志.
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLogService.insert(orderLog);
		
		//保存订单价格信息
		OrderPrices orderPrice = orderPricesService.initOrderPrices();

		orderPrice.setOrderId(orderId);
		orderPrice.setOrderNo(orderNo);
		orderPrice.setServicePriceId(servicePriceId);
		orderPrice.setPartnerUserId(partnerUserId);
		orderPrice.setUserId(userId);
		orderPrice.setMobile(mobile);
		orderPrice.setPayType(payType);
		orderPrice.setUserCouponId(userCouponId);
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderPay);
		orderPricesService.insert(orderPrice);
		
		//todo ,保存优惠劵的使用情况,更新 user_coupons 的order_no字段.
		if (userCouponId > 0) {
			UserCoupons userCoupons = userCouponService.selectByPrimaryKey(userCouponId);
			userCoupons.setOrderNo(orderNo);
			userCoupons.setUpdateTime(TimeStampUtil.getNowSecond());
			userCouponService.updateByPrimaryKeySelective(userCoupons);
		}
		
		
		if (payType.equals(Constants.PAY_TYPE_0) || orderPay.compareTo(new BigDecimal(0)) == 0) {
			// 1. 扣除用户余额.
			// 2. 用户账号明细增加.
			// 3. 订单状态变为已支付.
			// 4. 订单日志
			
			u.setRestMoney(u.getRestMoney().subtract(orderPay));
			u.setUpdateTime(TimeStampUtil.getNowSecond());
			userService.updateByPrimaryKeySelective(u);
			
			
			order.setOrderStatus(Constants.ORDER_STATUS_2_PAY_DONE);//已支付
			order.setUpdateTime(TimeStampUtil.getNowSecond());
			ordersService.updateByPrimaryKeySelective(order);
//			System.out.println("================order id =  " + order.getOrderId().toString() );
//			System.out.println("================order status =  " + order.getOrderStatus().toString() );
//			System.out.println();
			//记录订单日志.
			orderLog = orderLogService.initOrderLog(order);
			orderLogService.insert(orderLog);
			
			//记录用户消费明细
			userDetailPayService.userDetailPayForOrder(u, order, orderPrice, "", "", "");
			
			//订单支付成功后
			orderPayService.orderPaySuccessToDo(order);
		}		
		
		OrderListVo vo = orderQueryService.getOrderListVo(order);
		
		result.setData(vo);
		return result;
	}
	
	
	// 1. 订单取消接口
	/**
	 * 订单取消第一步，判断应该取消多少金额和是否可以取消.
	 * mobile true string 手机号 order_no true string 订单号
	 */
	@RequestMapping(value = "pre_order_cancel", method = RequestMethod.POST)
	public AppResultData<Object> preCancelOrder(
			@RequestParam("user_id") Long userId, 
			@RequestParam("order_id") Long order_id) {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		return result;
	}	
	
	// 1. 订单取消接口
	/**
	 * 订单取消第二步，需要完成实际的订单取消操作
	 * mobile true string 手机号 order_no true string 订单号
	 */
	@RequestMapping(value = "post_order_cancel", method = RequestMethod.POST)
	public AppResultData<Object> cancelOrder(
			@RequestParam("user_id") Long userId, 
			@RequestParam("order_id") Long order_id) {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		return result;
	}	
	
	
	// 22.订单评价接口
	/**
	 * mobile true string 手机号 order_no true string 订单号 order_rate true int 订单评价
	 * 0 = 好 1 = 一般 2 = 差 order_rate_content false string 评价内容.urlencode
	 */
	@RequestMapping(value = "post_rate", method = RequestMethod.POST)
	public AppResultData<Object> postRate(
			@RequestParam("user_id") Long userId,
			@RequestParam("order_id") Long orderId,
			@RequestParam("order_rate") Short orderRate,
			@RequestParam("order_rate_content") String orderRateContent) {
		
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		Orders orders = orderQueryService.selectByPrimaryKey(orderId);
		if (orders == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
			return result;
		}

		orders.setOrderRate(orderRate);
		orders.setOrderRateContent("");
		if (StringUtil.isEmpty(orderRateContent)) {
			orders.setOrderRateContent(orderRateContent);
		}
		orders.setOrderStatus(Constants.ORDER_STATUS_8_COMPLETE);
		long now = TimeStampUtil.getNow() / 1000;
		orders.setUpdateTime(now);
		
		//更新订单
		ordersService.updateByPrimaryKeySelective(orders);
		
		//新增订单日志
		OrderLog orderLog = orderLogService.initOrderLog(orders);
		orderLogService.insert(orderLog);

		//评价成功后的操作
		ordersService.orderRatedTodo(orders);

		return result;
	}	


}
