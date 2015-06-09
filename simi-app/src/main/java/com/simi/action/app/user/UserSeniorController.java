package com.simi.action.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictSeniorType;
import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.user.Users;
import com.simi.service.dict.DictSeniorTypeService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.user.UserRefSeniorService;
import com.simi.service.user.UsersService;
import com.meijia.utils.OneCareUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserSeniorController extends BaseController {

	@Autowired
	private OrderSeniorService orderSeniorService;
	@Autowired
	private DictSeniorTypeService dictSeniorTypeService;
	@Autowired
	private OrderPayService orderPayService;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UserRefSeniorService userRefSeniorService;

	// 18. 管家卡购买接口
	/**
	 * mobile true string 手机号 card_type true int 充值卡类型 pay_type true int 支付类型 0
	 * = 余额支付 1 = 支付宝
	 */
	@RequestMapping(value = "senior_buy", method = RequestMethod.POST)
	public AppResultData<Object> seniorBuy(
			@RequestParam("mobile") String mobile,
			@RequestParam("senior_type") Long seniorType,
			@RequestParam("pay_type") Short payType) {

		AppResultData<Object> resultFail = new AppResultData<Object>(
				Constants.ERROR_100, ConstantMsg.ERROR_100_MSG, "");

		AppResultData<Object> resultSuccess = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");


		DictSeniorType dictSeniorType = dictSeniorTypeService
				.selectByPrimaryKey(seniorType);

		Users users = usersService.getUserByMobile(mobile);


		if (users == null) {
			return resultFail;
		}

		//如果是余额支付，先判断余额是否足够
		if (payType == Constants.PAY_TYPE_0 &&
			users.getRestMoney().compareTo(dictSeniorType.getSeniorPay()) < 0) {
			resultFail.setStatus(Constants.ERROR_999);
			resultFail.setMsg(ConstantMsg.ERROR_999_MSG_5);
			return resultFail;
		}

		//保存相应的管家卡订单
		OrderSenior orderSenior = orderPayService.orderSeniorPayMoney(mobile, dictSeniorType, payType);
		resultSuccess.setData(orderSenior);
		return resultSuccess;
	}

	// 19. 管家卡在线支付成功同步接口
	/**
	 * mobile true string 手机号 card_type true int 充值卡类型 pay_type true int 支付类型 0
	 * = 余额支付 1 = 支付宝
	 */
	@RequestMapping(value = "senior_online_pay", method = RequestMethod.POST)
	public AppResultData<Object> seniorPay(
			@RequestParam("mobile") String mobile,
			@RequestParam("senior_order_no") String seniorOrderNo,
			@RequestParam("pay_type") Short payType,
			@RequestParam("notify_id") String notifyId,
			@RequestParam("notify_time") String notifyTime,
			@RequestParam("trade_no") String tradeNo,
			@RequestParam("trade_status") String tradeStatus,
			@RequestParam(value = "pay_account", required = false, defaultValue="") String payAccount) {
		AppResultData<Object> resultSuccess = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		AppResultData<Object> resultFail = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		// 判断如果不是正确支付状态，则直接返回.
		Boolean paySuccess = OneCareUtil.isPaySuccess(tradeStatus);
		if (paySuccess == false) {
			resultFail.setStatus(Constants.ERROR_999);
			resultFail.setMsg(ConstantMsg.ORDER_PAY_NOT_SUCCESS_MSG);
			return resultFail;
		}

		if(payType==Constants.PAY_TYPE_0) {
			Users users = usersService.getUserByMobile(mobile);
			OrderSenior orderSenior = orderSeniorService.selectByOrderSeniorNo(seniorOrderNo);
			if(orderSenior.getSeniorPay().compareTo(users.getRestMoney())<0){
				AppResultData<Object> result = new AppResultData<Object>(Constants.ERROR_999, ConstantMsg.ERROR_999_MSG_5, new OrderSenior());
				return result;
			}
		}

		if (orderPayService.updateSeniorByAlipay(mobile, payType,
				seniorOrderNo, tradeNo, tradeStatus, payAccount) > 0) {
			return resultSuccess;
		} else {
			return resultFail;
		}
	}
	

	/** 
	 * 测试分配管家接口
	 * 
	 */
	@RequestMapping(value = "allot_senior", method = RequestMethod.GET)
	public AppResultData<Object> allotSeniorPay(
			@RequestParam("mobile") String mobile
			) {
		
		AppResultData<Object> resultSuccess = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		if (mobile.equals("18612514665") || mobile.equals("18610807136")) {
			Users users = usersService.getUserByMobile(mobile);
			userRefSeniorService.allotSenior(users);
		}
		return resultSuccess;
	}
}
