package com.simi.action.app.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.TimeStampUtil;
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
public class OrderConfirmController extends BaseController {
	@Autowired
	private UsersService userService;
	
	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;
	
	// 订单确认接口
	/**
	 * mobile:手机号 order_id订单ID
	 */
	@RequestMapping(value = "post_confirm", method = RequestMethod.POST)
	public AppResultData<Object> detail(
			@RequestParam("user_id") Long userId, 
			@RequestParam("order_no") String orderNo) {
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}		
		
		Orders orders = orderQueryService.selectByOrderNo(String.valueOf(orderNo));
		
		if (orders == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);			
			return result;
		}
		
		orders.setOrderStatus(Constants.ORDER_STATUS_2_CONFIRM_DONE);
		orders.setUpdateTime(TimeStampUtil.getNowSecond());
		ordersService.updateByPrimaryKeySelective(orders);
		
		//订单确认后的操作
		ordersService.orderConfirmTodo(orderNo);
		
		OrderViewVo orderViewVo = orderQueryService.getOrderView(orders);
		result.setData(orderViewVo);
		
		return result;
	}
}
