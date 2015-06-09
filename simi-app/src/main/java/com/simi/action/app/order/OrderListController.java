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
import com.simi.vo.OrdersList;

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
	public AppResultData<List<OrdersList>> list(@RequestParam("mobile")
		String mobile, @RequestParam("page")
		int page) {
		
		Users u = userService.getUserByMobile(mobile);
		
		List<OrdersList> orderList = new ArrayList<OrdersList>();
		if (u.getUserType().equals(Short.valueOf("1"))) {
			orderList = orderQueryService.selectByAgentMobile(mobile, page);
		} else {
			orderList = orderQueryService.selectByMobile(mobile, page);
		}
		
		List<OrdersList> orderViewList = new ArrayList<OrdersList>();
		OrdersList o = null;
		for(int i = 0; i < orderList.size(); i++) {
			o = orderList.get(i);
			o.setServieDate(o.getServiceDate());
			o.setId(o.getOrderId());
			orderViewList.add(o);
		}
		
		if (orderViewList != null && orderViewList.size() > 0) {
			AppResultData<List<OrdersList>> result = new AppResultData<List<OrdersList>>(Constants.SUCCESS_0,
					ConstantMsg.SUCCESS_0_MSG, orderViewList);
			return result;
		} else {
			AppResultData<List<OrdersList>> result = new AppResultData<List<OrdersList>>(Constants.ERROR_999,
					ConstantMsg.ERROR_999_MSG_10, orderViewList);
			return result;
		}
	}
}
