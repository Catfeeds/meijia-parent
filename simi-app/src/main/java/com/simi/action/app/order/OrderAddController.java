package com.simi.action.app.order;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meijia.utils.DateUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.huanxin.EasemobMessages;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserBaiduBind;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserBaiduBindService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/order")
public class OrderAddController extends BaseController {
	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserBaiduBindService userBaiduBindService;	
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderQueryService orderQueryService;	
	
	@Autowired
	OrderPricesService orderPricesService;
	
	@Autowired
	UserAddrsService addrsService;

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
	 */
	@RequestMapping(value = "post_add", method = RequestMethod.POST)
	public AppResultData<Object> saveOrder(
			@RequestParam("sec_id") Long secId,
			@RequestParam("user_id") Long userId,
			@RequestParam("mobile") String mobile,
			@RequestParam("service_type") Long serviceType,
			@RequestParam("service_content") String serviceContent,
			@RequestParam("order_pay_type") Short orderPayType,
			@RequestParam(value = "order_id", required = false, defaultValue = "0") Long orderId,
			@RequestParam(value = "remarks", required = false, defaultValue = "") String remarks,
			@RequestParam(value = "order_from", required = false, defaultValue = "0") Short orderFrom,
			@RequestParam(value = "order_money", required = false, defaultValue = "0") BigDecimal orderMoney,
			@RequestParam(value = "service_date", required = false, defaultValue = "0") Long serviceDate,
			@RequestParam(value = "start_time", required = false, defaultValue = "0") Long startTime,
			@RequestParam(value = "addr_id", required = false, defaultValue = "0") Long addrId,			
			@RequestParam(value = "city_id", required = false, defaultValue = "0") int city_id,
			@RequestParam(value = "is_push", required = false, defaultValue = "1") Short isPush) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		
		Users u = userService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		//如果用户没有手机号，则需要更新用户手机号.
		if (StringUtil.isEmpty(u.getMobile())) {
			u.setMobile(mobile);
			userService.updateByPrimaryKeySelective(u);
		}
		
		// 服务内容及备注信息需要进行urldecode;
    	try {
    		serviceContent = URLDecoder.decode(serviceContent,Constants.URL_ENCODE);
    		
    		if (remarks != null && remarks.length() > 0) {
    			remarks = URLDecoder.decode(remarks,Constants.URL_ENCODE);
    		}
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ERROR_100_MSG);
    		return result;
    	}
    	
    	//订单状态判断
    	//1. order_pay_type = 0 无需支付， order_status = 1
    	//2. order_paty_type = 1 线上支付, order_status = 3
    	Short orderStatus = 0;
    	if (orderPayType.equals(Constants.ORDER_PAY_TYPE_0)) {
    		orderStatus = 1;
    	}
		
    	if (orderPayType.equals(Constants.ORDER_PAY_TYPE_1)) {
    		orderStatus = 3;
    	}    	
    	
		// 调用公共订单号类，生成唯一订单号
    	Orders order = null;
    	String orderNo = "";
    	if (orderId > 0L) {
    		order = orderQueryService.selectByPrimaryKey(orderId);
			orderNo = order.getOrderNo();
    	} else {
    		orderNo = String.valueOf(OrderNoUtil.genOrderNo());
			order = ordersService.initOrders();
    	}
		Long now = TimeStampUtil.getNow() / 1000;
		
		//保存订单信息
		
		order.setSecId(secId);
		order.setMobile(u.getMobile());
		order.setUserId(u.getId());
		order.setServiceType(serviceType);
		order.setServiceContent(serviceContent);
		order.setOrderPayType(orderPayType);
		order.setOrderNo(orderNo);
		order.setOrderStatus(orderStatus);
		order.setRemarks(remarks);
		
		if (orderId > 0L) {
			order.setUpdateTime(now);
			ordersService.updateByPrimaryKeySelective(order);
		} else {
			ordersService.insert(order);
		}
		
		
		//保存订单价格信息
		if (orderMoney == null) orderMoney = new BigDecimal(0.0);

		OrderPrices orderPrice = null;
		if (orderId > 0L) {
			orderPrice = orderPricesService.selectByOrderId(orderId);
		} else {
			orderPrice = orderPricesService.initOrderPrices();
		}
		
		orderPrice.setUserId(u.getId());
		orderPrice.setMobile(u.getMobile());
		orderPrice.setOrderId(order.getId());
		orderPrice.setOrderNo(order.getOrderNo());
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderMoney);
		
		if (orderId > 0L) {
			orderPrice.setUpdateTime(now);
			orderPricesService.updateByPrimaryKeySelective(orderPrice);
		} else {
			orderPricesService.insert(orderPrice);
		}
		
		
		
		//推送给用户.通过环信的透传消息
		if (isPush.equals((short)1)) {
			ordersService.orderSuccessTodo(orderNo);
		}
		
		return result;
	}

	/**
	 * 再次推送订单给用户接口
	 */
	@RequestMapping(value = "push_order", method = RequestMethod.POST)
	public AppResultData<Object> pushOrder(
			@RequestParam("user_id") Long userId,
			@RequestParam("order_id") Long orderId
			) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		
		Users u = userService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}		
		
		Orders order = orderQueryService.selectByPrimaryKey(orderId);
		String orderNo = order.getOrderNo();
		
		ordersService.orderSuccessTodo(orderNo);
		
		
		return result;
	}
	
	/**
	 * 透传消息测试
	 */
	@RequestMapping(value = "im_tc", method = RequestMethod.GET)
	public AppResultData<Object> imTcTest(
			@RequestParam("to") String toIm
			) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		JsonNodeFactory factory = new JsonNodeFactory(false);
		 String from = "simi-sec-2";
        String targetTypeus = "users";
        ObjectNode ext = factory.objectNode();
        ArrayNode targetusers = factory.arrayNode();
        targetusers.add(toIm);
        
        // 给用户发一条透传消息
        ObjectNode cmdmsg = factory.objectNode();
        cmdmsg.put("action", "order");
        cmdmsg.put("type","cmd");
        
        ext.put("order_id", 1);
        ext.put("order_no", "611081901792296960");
        ext.put("order_pay_type", 0);
        ext.put("add_time", DateUtil.getNow());
        ext.put("service_type_name", "通用");
        ext.put("service_content", "下午2点叫快递");
        ext.put("service_time", DateUtil.getNow());
        ext.put("service_addr", "北京东直门外大街42号宇飞大厦612");
        ext.put("order_money", "0.0");
        ext.put("order_status", 1);
        
        ObjectNode sendcmdMessageusernode = EasemobMessages.sendMessages(targetTypeus, targetusers, cmdmsg, from, ext);
  
        
		return result;
	}
			
}
