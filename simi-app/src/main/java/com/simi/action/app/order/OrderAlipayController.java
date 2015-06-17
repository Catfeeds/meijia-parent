package com.simi.action.app.order;

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
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserDetailPayService;
import com.meijia.utils.OneCareUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/order")
public class OrderAlipayController extends BaseController {

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
	private OrderPayService orderPayService;

	@Autowired
    private OrderQueryService orderQueryService;

	@Autowired
    private UserDetailPayService userDetailPayService;

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
		
		AppResultData<Object> result_success = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		AppResultData<Object> result_fail = new AppResultData<Object>(
				Constants.ERROR_999, ConstantMsg.SUCCESS_0_MSG, "");
		
		// 判断如果不是正确支付状态，则直接返回.
		Boolean paySuccess = OneCareUtil.isPaySuccess(tradeStatus);
		if (paySuccess == false) {
			result_fail.setStatus(Constants.ERROR_999);
			result_fail.setMsg(ConstantMsg.ORDER_PAY_NOT_SUCCESS_MSG);
			return result_fail;
		}else if(tradeStatus.equals("WAIT_BUYER_PAY")) {
			result_success.setStatus(Constants.SUCCESS_0);
			result_success.setMsg(ConstantMsg.ORDER_PAY_WAIT_MSG);
			return result_success;
		}

		Orders orders = orderQueryService.selectByOrderNo(orderNo);

		if (orders == null) {
			result_fail.setStatus(Constants.ERROR_999);
			result_fail.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
			return result_fail;
		}

		OrderPrices orderPrices = orderPricesService.selectByOrderId(orders.getId());

		//如果mobile没有参数或者等于0,则用orderCards 获得
		if (mobile.equals("0") || mobile.equals("") || mobile == null) {
			mobile = orders.getMobile();
		}

		Long updateTime = TimeStampUtil.getNow() / 1000;
		if (orders != null && orders.getOrderStatus() == Constants.ORDER_STATUS_4_PAY_DONE) {

			//更新付款用户账号名
			if (payAccount != null && !payAccount.equals("")) {
				userDetailPayService.updateByPayAccount(tradeNo, payAccount);
			}

			return result_success;// 订单已支付
		} else {
			// 更新orders,orderPrices,Users,插入消费明细UserDetailPay
			if (orderPayService.updateOrderByAlipay(orders, orderPrices,
					updateTime, Constants.ORDER_STATUS_4_PAY_DONE, payType, tradeNo, payAccount) > 0) {
				return result_success;
			} else {
				return result_fail;
			}
		}

	}
}
