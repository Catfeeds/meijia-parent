package com.simi.action.app.order;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserAddrs;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserAddrsService;
import com.simi.vo.AppResultData;
import com.simi.vo.order.OrderViewVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderDetailController extends BaseController {

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
    private OrderQueryService orderQueryService;

	// 19.订单详情接口
	/**
	 * mobile:手机号 order_id订单ID
	 */
	@RequestMapping(value = "get_detail", method = RequestMethod.GET)
	public AppResultData<OrderViewVo> detail(
			@RequestParam("mobile") String mobile, 
			@RequestParam("order_no") String order_no) {
		
		OrderViewVo orderViewVo = new OrderViewVo();
		AppResultData<OrderViewVo> result = new AppResultData<OrderViewVo>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, orderViewVo);
		
		Orders orders = orderQueryService.selectByOrderNo(String.valueOf(order_no));
		
		if (orders == null) {
			return result;
		}
		
		OrderPrices orderPrices = orderPricesService.selectByOrderId(orders.getId());

		UserAddrs userAddrs = userAddrsService.selectByPrimaryKey(orders.getAddrId());

		orderViewVo.setUserAddrs(userAddrs.getName() + userAddrs.getAddr());

		orderViewVo.setOrderMoney(orderPrices == null ? new BigDecimal(0) : orderPrices.getOrderMoney());
		orderViewVo.setOrderPay(orderPrices == null ? new BigDecimal(0) : orderPrices.getOrderPay());
		orderViewVo.setPayType(orderPrices == null ? (short) 0 : orderPrices.getPayType());

		result.setData(orderViewVo);
		
		return result;
	}
}
