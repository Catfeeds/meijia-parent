package com.simi.action.job.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simi.action.app.BaseController;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserAddrsService;


/**还有2个小时即将进行服务的短信提醒
 *
 * 执行频率 每30分钟和执行一次
 * @author kerry
 *
 */
@Controller
public class OrderWillServiceJob extends BaseController {

	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;

	@Autowired
	private UserAddrsService userAddrsService;

	public void messageRemind(){
		//查询订单中状态为未分配服务的 order_state=3
		
	}

}
