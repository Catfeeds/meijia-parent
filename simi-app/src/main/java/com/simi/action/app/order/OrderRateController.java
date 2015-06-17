package com.simi.action.app.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserDetailScore;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserDetailScoreService;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/order")
public class OrderRateController extends BaseController {


	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;
	
	@Autowired
	OrderLogService orderLogService;

	// 22.订单评价接口
	/**
	 * mobile true string 手机号 order_no true string 订单号 order_rate true int 订单评价
	 * 0 = 好 1 = 一般 2 = 差 order_rate_content false string 评价内容.urlencode
	 */
	@RequestMapping(value = "post_rate", method = RequestMethod.POST)
	public AppResultData<String> postRate(
			@RequestParam("mobile") String mobile,
			@RequestParam("order_no") String order_no,
			@RequestParam("order_rate") Short order_rate,
			@RequestParam("order_rate_content") String order_rate_content) {

		Orders orders = orderQueryService.selectByOrderNo(order_no);

		if (orders == null) {
			AppResultData<Orders> result = new AppResultData<Orders>(
					Constants.ERROR_999, ConstantMsg.ORDER_NO_NOT_EXIST_MG, new Orders());
		}

		orders.setOrderRate(order_rate);

		orders.setOrderRateContent("");
		if (order_rate_content !=null && order_rate_content != "") {
			orders.setOrderRateContent(order_rate_content);
		}


		orders.setOrderStatus(Constants.ORDER_STATUS_9_COMPLETE);
		long now = TimeStampUtil.getNow() / 1000;
		orders.setUpdateTime(now);
		
		//更新订单
		ordersService.updateByPrimaryKeySelective(orders);
		
		//新增订单日志
		OrderLog orderLog = orderLogService.initOrderLog(orders);
		orderLogService.insert(orderLog);

		//评价成功后的操作
		ordersService.ordeRatedTodo(orders);

		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG,  "");
		return result;
	}
}
