package com.simi.action.app.order;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserBaiduBind;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderPricesService;
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
	OrderPricesService orderPricesService;
	
	@Autowired
	UserAddrsService addrsService;

	// 1. 订单提交接口
	/**
	 *  @param user_id			int		用户id
	 *  @param mobile			string	手机号
	 *	@param service_type		int		服务类型
	 *	@param order_pay_type	int		支付方式 0 = 无需支付 1 = 线上支付
	 *	@param remarks			string	备注,urlencode
	 *	@param order_from		int		来源 0 = APP 1 = 微网站 2 = 管理后台
	 *  @param
	 *	@param order_pay		DECIMAL(9,2)	支付金额
	 *	@param service_date		int		服务开始日期， 时间戳，精确到秒, 2014-12-17 00:00:00 变成的时间戳
	 *	@param start_time		int		服务开始时间, 时间戳，精确到秒,2014-12-17 07:00:00 变成时间戳的
	 *	@param addr_id	false	int		服务地址ID 
	 *
	 *  @return  OrderViewVo
	 */
	@RequestMapping(value = "post_add", method = RequestMethod.POST)
	public AppResultData<Object> saveOrder(
			@RequestParam("sec_id") Long secId,
			@RequestParam("user_id") Long userId,
			@RequestParam("service_type") Short serviceType,
			@RequestParam("service_content") String serviceContent,
			@RequestParam("order_pay_type") Short orderPayType,
			@RequestParam("remarks") String remarks, 
			@RequestParam(value = "order_from", required = false, defaultValue = "0") Short orderFrom,
			@RequestParam(value = "is_push", required = false, defaultValue = "0") Short isPush,
			@RequestParam(value = "order_pay", required = false, defaultValue = "0") BigDecimal orderPay,
			@RequestParam(value = "service_date", required = false, defaultValue = "0") Long serviceDate,
			@RequestParam(value = "start_time", required = false, defaultValue = "0") Long startTime,
			@RequestParam(value = "addr_id", required = false, defaultValue = "0") Long addrId,			
			@RequestParam(value = "city_id", required = false, defaultValue = "0") int city_id) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		
		Users u = userService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
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
		
		// 调用公共订单号类，生成唯一订单号
		String orderNo = String.valueOf(OrderNoUtil.genOrderNo());

		Long now = TimeStampUtil.getNow() / 1000;
		
		//保存订单信息
		Orders order = ordersService.initOrders();
		order.setSecId(secId);
		order.setMobile(u.getMobile());
		order.setUserId(u.getId());
		order.setServiceType(serviceType);
		order.setOrderPayType(orderPayType);
		order.setOrderNo(orderNo);
		order.setRemarks(remarks);
		
		ordersService.insert(order);
		
		//保存订单价格信息
		if (orderPay == null) orderPay = new BigDecimal(0.0);
		BigDecimal orderMoney = orderPay;
		
		OrderPrices orderPrice = orderPricesService.initOrderPrices();
		orderPrice.setUserId(u.getId());
		orderPrice.setMobile(u.getMobile());
		orderPrice.setOrderId(order.getId());
		orderPrice.setOrderNo(order.getOrderNo());
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderPay);
		orderPricesService.insert(orderPrice);
		
		
		//推送给用户.通过环信的透传消息
		if (isPush.equals((short)0)) {
			UserBaiduBind userBaiduBind = userBaiduBindService.selectByUserId(u.getId());
			if (userBaiduBind != null) {
				
			}
		}
		
		return result;
	}
}
