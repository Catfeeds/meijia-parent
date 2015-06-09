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
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.order.OrdersDetailVo;

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
	public AppResultData<OrdersDetailVo> detail(@RequestParam("mobile")
	String mobile, @RequestParam("order_no")
	Long order_no) {
		Orders orders = orderQueryService.selectByOrderNo(String.valueOf(order_no));
		OrderPrices orderPrices = orderPricesService.selectByOrderId(orders
				.getId());

		OrdersDetailVo ordersDetailVo = new OrdersDetailVo(orders);
		if (orders != null
				&& (orders.getOrderStatus() == Constants.ORDER_STATS_0_CANCLEED || orders
						.getOrderStatus() == Constants.ORDER_STATS_6_COMPLETE|| orders
						.getOrderStatus() == Constants.ORDER_STATS_5_REMARK
						|| orders.getOrderStatus() == Constants.ORDER_STATS_7_CLOSE)) {
			// is_cancel ok
			// 已经取消，已经完成，待评价不允许取消
			ordersDetailVo.setIs_cancel(Constants.ORDER_CANCLE_NO);
		}else {
			long expLong = TimeStampUtil.getNow() / 1000 - orders.getServiceDate();
			long exp = expLong / 60;
			if(exp<120) {//如果是服务开始前的120分钟，则不能取消订单，此状态为不可用
				ordersDetailVo.setIs_cancel(Constants.ORDER_CANCLE_NO);
			}
		}

		UserAddrs userAddrs = userAddrsService.selectByPrimaryKey(orders.getAddrId());

		ordersDetailVo.setAddr(userAddrs.getAddr());


		ordersDetailVo.setOrder_money(orderPrices == null ? new BigDecimal(0)
				: orderPrices.getOrderMoney());
		ordersDetailVo
				.setPrice_hour_discount(orderPrices == null ? new BigDecimal(0)
						: orderPrices.getPriceHourDiscount());
		ordersDetailVo.setOrder_pay(orderPrices == null ? new BigDecimal(0)
				: orderPrices.getOrderPay());
		ordersDetailVo.setPay_type(orderPrices == null ? new Long(0) : Long
				.valueOf(orderPrices.getPayType()));

		
		AppResultData<OrdersDetailVo> result = new AppResultData<OrdersDetailVo>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, ordersDetailVo);
		return result;
	}
}
