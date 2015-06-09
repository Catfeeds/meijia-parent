package com.simi.action.job.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simi.action.app.BaseController;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;

/**
 * 提醒订单已付款，但是超过两个小时未派工的
 *
 * 执行频率  每小时第10分钟执行
 * @author kerry
 *
 */
@Controller
public class OrderDispatchCheckJob extends BaseController {
	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;

	public void remindCheck(){

	}
}
