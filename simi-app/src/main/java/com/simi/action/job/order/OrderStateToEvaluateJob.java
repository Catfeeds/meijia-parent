package com.simi.action.job.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simi.action.app.BaseController;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;

/**
 * 定时将订单状态为已经派工并且开始时间等于当前时间的订单的状态改为即将服务
 *
 * 执行频率，每30分钟执行一次
 *
 * @author kerry
 *
 */
@Controller
public class OrderStateToEvaluateJob extends BaseController {
	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;

	public void changeOrderStateToEvaluate(){

	}

}
