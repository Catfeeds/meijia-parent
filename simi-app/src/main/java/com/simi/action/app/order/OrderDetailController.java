package com.simi.action.app.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.order.OrderViewVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderDetailController extends BaseController {
	@Autowired
	private UsersService userService;
	
	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;
	
	// 19.订单详情接口
	/**
	 * mobile:手机号 order_id订单ID
	 */
	@RequestMapping(value = "get_detail", method = RequestMethod.GET)
	public AppResultData<Object> detail(
			@RequestParam("user_id") Long userId, 
			@RequestParam("order_no") String order_no) {
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}		
		
		Orders orders = orderQueryService.selectByOrderNo(String.valueOf(order_no));
		
		if (orders == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);			
			return result;
		}
		
		OrderViewVo orderViewVo = orderQueryService.getOrderView(orders);
		
		result.setData(orderViewVo);
		
		return result;
	}
}
