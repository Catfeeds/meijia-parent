package com.simi.action.app.order;

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
import com.simi.po.model.user.Users;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UsersService;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;
@Controller
@RequestMapping(value = "/app/order")
public class OrderOnlinePayController extends BaseController {

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
	private OrderPayService orderPayService;

	@Autowired
    private OrderQueryService orderQueryService;
	
	@Autowired
	UsersService userService;

	@Autowired
    private UserDetailPayService userDetailPayService;
	
	@Autowired
	OrderLogService orderLogService;

	// 7. 订单在线支付成功同步接口
	/**
	 * 订单在线支付成功同步接口 
	 * @param mobile true string 手机号 
	 * @param order_no true string 订单号 
	 * @param pay_type true int 0 = 余额支付 1 = 支付宝 2 = 微信支付 3 = 智慧支付 4 = 上门刷卡（保留，站位） 
	 * @param notify_id true string 通知ID 
	 * @param notify_time true string 通知时间 
	 * @param trade_no true string 流水号
	 * @param trade_status true string 支付状态 
	 * 				支付宝客户端成功状态为: TRADE_FINISHED 或者为 TRADE_SUCCESS 
	 * 				支付宝网页版成功状态为: success 
	 * 				微信支付成功的状态为: SUCCESS
	 */
	@RequestMapping(value = "online_pay_notify", method = RequestMethod.POST)
	public AppResultData<Object> cardOnlinePay(
			@RequestParam(value = "user_id", defaultValue = "0") Long userId,
			@RequestParam(value = "mobile", defaultValue = "0")	String mobile,
			@RequestParam("order_no") String orderNo,
			@RequestParam("pay_type") Short payType,
			@RequestParam("notify_id") String notifyId,
			@RequestParam("notify_time") String notifyTime,
			@RequestParam("trade_no") String tradeNo,
			@RequestParam("trade_status") String tradeStatus,
			@RequestParam(value = "pay_account", required = false, defaultValue="") String payAccount) {
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		// 判断如果不是正确支付状态，则直接返回.
		Boolean paySuccess = MeijiaUtil.isPaySuccess(tradeStatus);
		if (paySuccess == false) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_PAY_NOT_SUCCESS_MSG);
			return result;
		} else if(tradeStatus.equals("WAIT_BUYER_PAY")) {
			result.setStatus(Constants.SUCCESS_0);
			result.setMsg(ConstantMsg.ORDER_PAY_WAIT_MSG);
			return result;
		}

		Orders order = orderQueryService.selectByOrderNo(orderNo);

		if (order == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
			return result;
		}
		
		Users u = userService.selectByPrimaryKey(order.getUserId());
		
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		
		if (order != null && order.getOrderStatus().equals(Constants.ORDER_STATUS_2_PAY_DONE)) {
			//更新付款用户账号名
			if (payAccount != null && !payAccount.equals("")) {
				userDetailPayService.updateByPayAccount(tradeNo, payAccount);
			}

			return result;// 订单已支付
		}
		
		Long updateTime = TimeStampUtil.getNow() / 1000;
		
		OrderPrices orderPrice = orderPricesService.selectByOrderId(order.getOrderId());
		
		//更新订单状态.
		order.setOrderStatus(Constants.ORDER_STATUS_2_PAY_DONE);//支付状态
		order.setUpdateTime(updateTime);
		ordersService.updateByPrimaryKey(order);
		
		orderPrice.setPayType(payType);
		orderPricesService.updateByPrimaryKey(orderPrice);
		
		//插入订单日志
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLog.setAction("order-pay");
		orderLog.setRemarks("订单支付成功:"+ MathBigDecimalUtil.round2(orderPrice.getOrderPay()) + "元");
		orderLogService.insert(orderLog);
		
		
		//记录用户消费明细
		userDetailPayService.userDetailPayForOrder(u, order, orderPrice, tradeStatus, tradeNo, payAccount);
		
		//订单支付成功后
		orderPayService.orderPaySuccessToDo(order);
		
		return result;

	}
}
