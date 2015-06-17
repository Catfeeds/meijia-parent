package com.simi.action.job.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simi.action.app.BaseController;
import com.simi.common.Constants;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserCoupons;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserCouponService;
import com.meijia.utils.TimeStampUtil;

/**
 * 定时清理超过1小时未付款的订单
 *
 * 执行频率，每小时第15分钟执行一次
 *
 * @author kerry
 *
 */
@Controller
public class CancelOrderJob extends BaseController {
	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;

	@Autowired
	private UserCouponService userCouponService;

	public void cancelOrder() {
		// 查询所用未付款的订单 order_state=1
		List<Orders> list = orderQueryService.selectByStatus(Constants.ORDER_STATUS_3_PAY_WAIT);
		Long now = TimeStampUtil.getNow()/1000;
		Long addTime = TimeStampUtil.getNow()/1000;
		// 当未付款的订单时间超过60分钟时，将订单状态置为以关闭 order_state=7
		for (Orders orders : list) {
			addTime = orders.getAddTime();
			if ((now - addTime) / 60 >= 60) {
				orders.setOrderStatus(Constants.ORDER_STATUS_0_CLOSE);
			}
			ordersService.updateByPrimaryKey(orders);
			UserCoupons userCoupons = userCouponService.selectByUserIdOrderNo(orders.getUserId(),orders.getOrderNo());
			if(userCoupons!=null){
				userCoupons.setOrderNo("0");
				userCouponService.updateByPrimaryKeySelective(userCoupons);
			}

		}
	}
}
