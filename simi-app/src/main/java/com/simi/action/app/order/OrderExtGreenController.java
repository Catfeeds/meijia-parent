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
import com.simi.po.model.order.OrderExtGreen;
import com.simi.service.order.OrderExtGreenService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.order.OrderExtGreenListVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderExtGreenController extends BaseController {
	
	@Autowired
	private UsersService userService;
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderExtGreenService orderExtGreenService;
	


	@RequestMapping(value = "get_list_green", method = RequestMethod.GET)
	public AppResultData<Object> cancelOrder(
			@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		List<OrderExtGreenListVo> listVo = new ArrayList<OrderExtGreenListVo>();
		List<OrderExtGreen> list = orderExtGreenService.selectByUserId(userId);
		for (OrderExtGreen item : list) {
			OrderExtGreenListVo vo = orderExtGreenService.getOrderExtGreenListVo(item);
			listVo.add(vo);
		}
		result.setData(listVo);
		return result;
	}	
	
}
