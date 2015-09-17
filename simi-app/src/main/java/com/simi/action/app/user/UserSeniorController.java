package com.simi.action.app.user;

import java.util.Date;
import java.util.Map;

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
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UserRefSeniorService;
import com.simi.service.user.UsersService;
import com.meijia.utils.DateUtil;
import com.meijia.utils.OneCareUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.TimeStampUtil;
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
	
	@Autowired
	private UserDetailPayService userDetailPayService;	

	// 18. 私秘购买接口
	/**
	 * mobile true string 手机号 card_type true int 充值卡类型 pay_type true int 支付类型 0
	 * = 余额支付 1 = 支付宝
	 */
	@RequestMapping(value = "senior_buy", method = RequestMethod.POST)
	public AppResultData<Object> seniorBuy(
			@RequestParam("user_id") Long userId,
			@RequestParam("sec_id") Long secId,
			@RequestParam("senior_type_id") Long seniorTypeId,
			@RequestParam("pay_type") Short payType) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		DictSeniorType dictSeniorType = dictSeniorTypeService.selectByPrimaryKey(seniorTypeId);

		Users u = usersService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		Users sec = usersService.getUserById(secId);
		if (sec == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
				
		if (dictSeniorType == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("无效的购买");
			return result;
		}

		//如果是余额支付，先判断余额是否足够
		if (payType == Constants.PAY_TYPE_0 &&
			u.getRestMoney().compareTo(dictSeniorType.getSeniorPay()) < 0) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.ERROR_999_MSG_5);
				return result;
		}
	
	    //如果当前已经有秘书，且还未过期，则提示不可购买
		Map<String, Date> validSecDate = orderSeniorService.getSeniorRangeDate(userId);
		if (validSecDate != null && validSecDate.size() > 1) {
			Date endDate = validSecDate.get("endDate");
			Date nowDate = DateUtil.getNowOfDate();
			if (endDate.after(nowDate)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("当前已经购买过秘书，服务时间到"+ DateUtil.formatDate(endDate) + "截止.");
				return result;
			}
		}

		String seniorOrderNo = String.valueOf(OrderNoUtil.getOrderSeniorNo());
		OrderSenior orderSenior = orderSeniorService.initOrderSenior();

		orderSenior.setUserId(userId);
		orderSenior.setSecId(secId);
		orderSenior.setMobile(u.getMobile());
		orderSenior.setSeniorOrderNo(seniorOrderNo);
		orderSenior.setSeniorTypeId(seniorTypeId);
		orderSenior.setOrderMoney(dictSeniorType.getSeniorPay());
		orderSenior.setOrderPay(dictSeniorType.getSeniorPay());
		orderSenior.setValidDay(dictSeniorType.getValidDay());
		
		orderSenior.setPayType(payType);
		orderSenior.setOrderStatus((short) 0);

		orderSenior.setStartDate(DateUtil.getNowOfDate());
		
		Date endDate = orderSeniorService.getSeniorStartDate(dictSeniorType);
		orderSenior.setEndDate(endDate);				
		
		orderSeniorService.insert(orderSenior);
		
		if (payType == 0) {// pay_type = 0 余额支付
			
			//扣除用户余额
			u.setRestMoney(u.getRestMoney().subtract(dictSeniorType.getSeniorPay()));
			u.setUpdateTime(TimeStampUtil.getNowSecond());
			usersService.updateByPrimaryKeySelective(u);
			
			//记录用户明细
			
			userDetailPayService.userDetailPayForOrderSenior(u, orderSenior, "", "", "");
			
			//更新订单状态为已支付
			orderSenior.setOrderStatus(Constants.PAY_STATUS_1);
			orderSeniorService.updateByPrimaryKey(orderSenior);
			
			orderPayService.orderSeniorPaySuccessTodo(orderSenior);
		}
				
		result.setData(orderSenior);
		return result;
	}

	// 19. 私秘卡在线支付成功同步接口
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
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		// 判断如果不是正确支付状态，则直接返回.
		Boolean paySuccess = OneCareUtil.isPaySuccess(tradeStatus);
		if (paySuccess == false) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_PAY_NOT_SUCCESS_MSG);
			return result;
		}
		
		OrderSenior orderSenior = orderSeniorService.selectByOrderSeniorNo(seniorOrderNo);
		Users users = usersService.selectVoByUserId(orderSenior.getUserId());

		//记录用户明细
		userDetailPayService.userDetailPayForOrderSenior(users, orderSenior, "", "", "");
		
		//更新订单状态为已支付
		orderSenior.setOrderStatus(Constants.PAY_STATUS_1);
		orderSeniorService.updateByPrimaryKey(orderSenior);
		
		orderPayService.orderSeniorPaySuccessTodo(orderSenior);
		
		return result;
	}

}
