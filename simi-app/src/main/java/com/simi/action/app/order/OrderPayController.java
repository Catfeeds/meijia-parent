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
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.UserDetailPay;
import com.simi.po.model.user.Users;
import com.simi.service.dict.CouponService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UsersService;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.order.OrderViewVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderPayController extends BaseController {

	@Autowired
	UsersService userService;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
	private OrderPayService orderPayService;

	@Autowired
    private OrderQueryService orderQueryService;

	@Autowired
	OrderLogService orderLogService;
	
	@Autowired
	private UserDetailPayService userDetailPayService;

	// 17.订单支付钱接口
	/**
	 * @param mobile true string 手机号 
	 * @param order_id true int 订单id 
	 * @param order_no true string 订单号
	 * @param pay_type true int 支付方式： 0 = 余额支付 1 = 支付宝 2 = 微信支付 3 = 智慧支付(保留,暂不开发) 4 = 上门刷卡（保留，暂不开发） 
	 * @return  OrderViewVo
	 */
	@RequestMapping(value = "post_pay", method = RequestMethod.POST)
	public AppResultData<Object> postPay(
			@RequestParam("user_id") Long userId, 
			@RequestParam("order_id") Long orderId, 
			@RequestParam("order_no") String orderNo, 
			@RequestParam("pay_type") Short payType) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}		
		
		Orders order = orderQueryService.selectByPrimaryKey(orderId);
		
		if (order == null) {
			return result;
		}
		
		OrderPrices orderPrice = orderPricesService.selectByOrderId(orderId);
		long updateTime = TimeStampUtil.getNowSecond();
				

		orderPrice.setOrderPay(orderPrice.getOrderMoney());

		BigDecimal orderPay = orderPrice.getOrderMoney();
		BigDecimal orderMoney = orderPrice.getOrderMoney();

		if (payType.equals(Constants.PAY_TYPE_0)) {
			//1.先判断用户余额是否够支付
			if(u.getRestMoney().compareTo(orderPay) < 0) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.ERROR_999_MSG_5);
				return result;
			}
		}
		
		
		orderPrice.setPayType(payType);
		orderPrice.setUpdateTime(updateTime);
		orderPricesService.updateByPrimaryKey(orderPrice);
		
		if (payType.equals(Constants.PAY_TYPE_0)) {
			// 1. 扣除用户余额.
			// 2. 用户账号明细增加.
			// 3. 订单状态变为已支付.
			// 4. 订单日志
			
			u.setRestMoney(u.getRestMoney().subtract(orderPay));
			u.setUpdateTime(updateTime);
			userService.updateByPrimaryKeySelective(u);
			
			order.setOrderStatus(Constants.ORDER_STATUS_4_PAY_DONE);//已支付
			order.setUpdateTime(TimeStampUtil.getNowSecond());
			ordersService.updateByPrimaryKeySelective(order);
			
			//记录订单日志.
			OrderLog orderLog = orderLogService.initOrderLog(order);
			orderLogService.insert(orderLog);
			
			//记录用户消费明细
			userDetailPayService.addUserDetailPayForOrder(u, order, orderPrice, "", "", "");
			
			//订单支付成功后
			orderPayService.orderPaySuccessToDo(order);
		}
		
		OrderViewVo orderViewVo = orderQueryService.getOrderView(order);
		result.setData(orderViewVo);
		
		return result;
	}

}
