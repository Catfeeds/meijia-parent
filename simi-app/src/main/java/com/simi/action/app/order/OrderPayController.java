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
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.Users;
import com.simi.service.dict.CouponService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UsersService;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/order")
public class OrderPayController extends BaseController {

	@Autowired
	UsersService usersService;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
	private OrderPayService orderPayService;

	@Autowired
    private OrderQueryService orderQueryService;

	@Autowired
	UserCouponService userCouponService;

	@Autowired
	CouponService couponService;

	// 17.订单支付接口
	/**
	 * mobile true string 手机号 order_id true int 订单id order_no true string 订单号
	 * pay_type true int 支付方式： 0 = 余额支付 1 = 支付宝 2 = 微信支付 3 = 智慧支付(保留,暂不开发) 4 =
	 * 上门刷卡（保留，暂不开发） card_passwd true string 优惠卷， 默认为 0 score true int 暂不开放，保留参数
	 * 会员积分 0 = 不使用 > 1 则为使用多少积分
	 */
	@RequestMapping(value = "post_pay", method = RequestMethod.POST)
	public AppResultData<Object> postPay(
			@RequestParam("mobile") String mobile, 
			@RequestParam("order_id") int order_id, 
			@RequestParam("order_no") String order_no, 
			@RequestParam("pay_type") int pay_type, 
			@RequestParam("card_passwd") String card_passwd, 
			@RequestParam("score") int score) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Orders orders = orderQueryService.selectByPrimaryKey(Long.valueOf(order_id));
		
		if (orders == null) {
			return result;
		}
		OrderPrices orderPrices = orderPricesService.selectByOrderId(Long.valueOf(order_id));
		long updateTime = TimeStampUtil.getNow() / 1000;
				
//		 2. 如果优惠券不为0 ，流程
//	     1) 在表user_coupons判断优惠券是否有效
//	         a. 是否已经使用过  is_used   , 已经使用过，返回提示信息： “优惠券序列号已被兑换，换一个吧。”
//	         b. 是否已经过了有效期  exp_time， 如果已经过去，返回提示信息：“要兑换的优惠券已过期，换一个吧。”
//	         c. 是否已经用于其他的订单  order_no 跟当前的order_no进行比较
//	                select * from user_coupons where mobile = ?  and coupon_id = ? and order_no <> ?;
//	            如果已经被其他的订单使用：“兑换码不正确哦！”
//
//	         d. 该优惠券的服务类型 dict_coupons.service_type 跟当前的订单服务类型是否匹配，  0 =全部服务类型适用
//	            如果不匹配，返回提示信息：“您选择的优惠券类型不能支付本次服务，请换一张哦！”
//	         e. 该优惠卷的渠道类型 dict_coupons.range_from  跟当前的订单来源order_from 是否匹配   999 = 全部渠道适用
//	            如果不匹配，返回提示信息：“兑换码不正确哦！”
//		UserCoupons userCoupons = null;
//		String orderNo = orders.getOrderNo();
//		//验证优惠券是否正确.
//		if (!card_passwd.equals("0")) {
//			// 处理一张优惠劵码，在同一个用户有两张以上的情况
//			userCoupons =userCouponService.selectByMobileCardPwd(mobile, card_passwd);
//			
//			if (userCoupons == null) {
//				AppResultData<Object> resultCouponsValid = new AppResultData<Object>(
//						Constants.ERROR_999, ConstantMsg.COUPON_IS_USED_MSG, "");
//				return resultCouponsValid;
//			}
//			
//			Short orderFrom = orders.getOrderFrom();
//			
//			Short serviceType = orders.getServiceType();
//
//			AppResultData<Object> result = userCouponService.validateCouponAll(mobile, card_passwd, orderNo, orderFrom, serviceType);
//			if(result.getStatus()!=Constants.SUCCESS_0) {
//				return result;
//			}
//		}
//
//		orderPrices.setOrderPay(orderPrices.getOrderMoney());
//
//		BigDecimal orderPay = orderPrices.getOrderMoney();
//		BigDecimal orderMoney = orderPrices.getOrderMoney();
//		BigDecimal couponValue =  new BigDecimal(0);
//
//		//处理有优惠券，并且优惠券金额大于订单总金额的情况
//		if( userCoupons!=null ) {
//			
//			userCoupons.setOrderNo(orderNo);
//			userCoupons.setUpdateTime(TimeStampUtil.getNow()/1000);
//			userCouponService.updateByPrimaryKeySelective(userCoupons);
//			
//			couponValue = userCoupons.getValue();
//			// 如果优惠券金额大于订单总金额
//			if (orderMoney.compareTo(couponValue) == 1 ) {
//				orderPay = orderMoney.subtract(couponValue);
//			} else {
//				orderPay = new BigDecimal(0);
//			}
//			orderPrices.setOrderPay(orderPay);
//		}
//
//		OrdersDetailVo ordersDetailVo = new OrdersDetailVo(orders);
//		// 在线支付（支付宝支付）,则仅记录数据库表orders
//		if (pay_type == Constants.PAY_TYPE_1 ||
//			pay_type == Constants.PAY_TYPE_2) {
//			ordersDetailVo.setOrder_money(orderPrices.getOrderMoney());
//			ordersDetailVo.setPay_type(Long.valueOf(orderPrices.getPayType()));
//			ordersDetailVo.setOrder_pay(orderPay);
//
//			//如果优惠券大于订单金额，直接置为已支付
//			if (userCoupons != null && orderMoney.compareTo(couponValue) <= 0) {
//				orders.setOrderStatus(Constants.ORDER_STATS_2_PAID);//已支付
//			} else {
//				orders.setOrderStatus(Constants.ORDER_STATS_1_PAYING);//正在支付
//			}
//						
//			orderPayService.updateOrderByAlipay(orders, orderPrices,
//					updateTime, orders.getOrderStatus(), (short)pay_type, "", "");
//
//		} else if (pay_type == Constants.PAY_TYPE_0 ) {// 余额支付
//		// 1) 将相应的优惠卷状态变成已使用.操作表为 coupons.
//		// 2) 用户余额，扣除相应的金额，注意如果有优惠卷的金额，操作表为users
//		// 3) 用户的消费明细记录，操作表为user_detail_pay
//			Users users = usersService.getUserByMobile(mobile);
//			orders.setOrderStatus(Constants.ORDER_STATS_2_PAID);//已支付
//
//			ordersDetailVo.setOrder_money(orderPrices.getOrderMoney());
//			ordersDetailVo.setPay_type(Long.valueOf(orderPrices.getPayType()));
//			ordersDetailVo.setOrder_pay(orderPrices.getOrderPay());
//
//			if(users.getRestMoney().compareTo(orderPrices.getOrderPay()) < 0) {
//				AppResultData<Object> result = new AppResultData<Object>(Constants.ERROR_999, ConstantMsg.ERROR_999_MSG_5, ordersDetailVo);
//				return result;
//			}
//
//			int success = orderPayService.updateOrderByRestMoney(mobile, order_id,
//					orders, orderPrices, Constants.PAY_TYPE_0, updateTime, userCoupons);
//			if( success>0 ) {//支付成功，给用户发送短信
//				ordersService.orderPaySuccess(mobile, orders);
//				ordersService.orderPaySuccessSendToAdmin("18610807136", orders);
//				//todo 给相应的管家发送短信.
//			}
//		}

		
		return result;
	}

}
