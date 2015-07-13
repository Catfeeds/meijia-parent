package com.simi.action.app.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.order.OrderViewVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderListController extends BaseController {
	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;
	
	@Autowired
	private UsersService userService;

	// 18.订单列表接口
	/**
	 * mobile:手机号 page分页页码
	 */
	@RequestMapping(value = "get_list", method = RequestMethod.GET)
	public AppResultData<List<OrderViewVo>> list(
			@RequestParam("user_id") Long userId, 
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		
		List<OrderViewVo> orderList = new ArrayList<OrderViewVo>();
		
		AppResultData<List<OrderViewVo>> result = new AppResultData<List<OrderViewVo>>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, orderList);
		
		Users u = userService.getUserById(userId);
		
		if (u == null) {
			return result;
		}
		
		orderList = orderQueryService.selectByUserId(userId, page, Constants.PAGE_MAX_NUMBER);
		
		result.setData(orderList);
		
		return result;

	}
}
