package com.simi.action.app.order;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.Orders;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersCancelService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UsersService;
import com.meijia.utils.DateUtil;
import com.simi.vo.AppResultData;


@Controller
@RequestMapping(value = "/app/order")
public class OrderCancelController extends BaseController {
	@Autowired
	private UsersService userService;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
    private OrderQueryService orderQueryService;

	@Autowired
    private OrdersCancelService orderCancelService;

	// 1. 订单取消接口
	/**
	 * 订单取消第一步，判断应该取消多少金额和是否可以取消.
	 * mobile true string 手机号 order_no true string 订单号
	 */
	@RequestMapping(value = "pre_order_cancel", method = RequestMethod.POST)
	public AppResultData<Object> preCancelOrder(
			@RequestParam("mobile") String mobile, 
			@RequestParam("order_no") String order_no) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());


		// 1. 如果订单的状态为未支付，订单取消直接将orders表 order_status = 0 即可完成流程
		Orders orders = orderQueryService.selectByOrderNo(order_no);
		if (orders.getOrderStatus().equals(1)||orders.getOrderStatus().equals(0)) {//0 = 已取消 1 = 待付款
			orders.setOrderStatus((short)0);
			ordersService.updateByPrimaryKeySelective(orders);

			return result;
		}

		result = orderCancelService.orderCancelBack(order_no);
		return result;
	}	
	
	// 1. 订单取消接口
	/**
	 * 订单取消第二步，需要完成实际的订单取消操作
	 * mobile true string 手机号 order_no true string 订单号
	 */
	@RequestMapping(value = "post_order_cancel", method = RequestMethod.POST)
	public AppResultData<Object> cancelOrder(@RequestParam("mobile")
	String mobile, @RequestParam("order_no")
	String order_no) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());


		// 1. 如果订单的状态为未支付，订单取消直接将orders表 order_status = 0 即可完成流程
		Orders orders = orderQueryService.selectByOrderNo(order_no);
		if (orders.getOrderStatus().equals(Constants.ORDER_STATUS_1)||orders.getOrderStatus().equals(Constants.ORDER_STATS_0_CANCLEED)) {//0 = 已取消 1 = 待付款
			orders.setOrderStatus((short)0);
			ordersService.updateByPrimaryKeySelective(orders);

			return result;
		}

		result = orderCancelService.orderCancel(order_no);
		return result;
	}

	public static void main(String[] args) {
		long startTime = 1418007600l;
		Date startDateTime = new Date(startTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDateTime);

		String fmtStartDate = DateUtil.format(startDateTime, "yyyy-MM-dd HH:mm:ss");

		if(calendar.get(Calendar.YEAR)==DateUtil.getYear()
				&&calendar.get(Calendar.MONTH)==DateUtil.getMonth()
				&&calendar.get(Calendar.DAY_OF_MONTH)==DateUtil.getDay()) {
			String fmtPreStartDate = DateUtil.addDay(startDateTime, -2, Calendar.HOUR, "yyyy-MM-dd HH:mm:ss") ;
			if (DateUtil.compareTimeStr(fmtPreStartDate, fmtStartDate) > 2 * 60 * 60) {
				System.out.println(">0");
			}
		}

		String fmtPreStartDate = DateUtil.addDay(startDateTime, -20, Calendar.MINUTE, "yyyy-MM-dd HH:mm:ss") ;
		if(fmtStartDate.compareTo(fmtPreStartDate)<0) {
			System.out.println("<0");
		}
		System.out.println(fmtStartDate);
		System.out.println(fmtPreStartDate);
	}
}
