package com.simi.action.app.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/order")
public class OrderAddController extends BaseController {
	@Autowired
	private UsersService userService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	OrderPricesService orderPricesService;
	@Autowired
	UserAddrsService addrsService;

	// 1. 订单提交接口
	/**
	 * 
	 */
	@RequestMapping(value = "post_add", method = RequestMethod.POST)
	public AppResultData<Object> saveOrder(
			@RequestParam("user_id") String userId, 
			@RequestParam("mobile") String mobile,
			@RequestParam("service_type") Short serviceType,
			@RequestParam("service_date") Long service_date, 
			@RequestParam("start_time") Long start_time,
			@RequestParam("addr_id") int addr_id, 
			@RequestParam("remarks") String remarks, 
			@RequestParam("order_from") int order_from,
			@RequestParam(value = "city_id", required = false, defaultValue = "0") int city_id) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		return result;
	}
}
