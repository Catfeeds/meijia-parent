package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersCancelService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserDetailPay;
import com.simi.po.model.user.Users;
import com.meijia.utils.DateUtil;
import com.meijia.utils.MathBigDeciamlUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrdersCancelServiceImpl implements OrdersCancelService {

	@Autowired
	private OrdersService orderService;
	
	@Autowired
	private OrderQueryService orderQueryService;

	@Autowired
	private OrderPricesService orderPricesService;
	
	@Autowired
	private OrderLogService orderLogService;
	

	
	@Autowired
	private UsersService userService;	
	
	@Autowired
	private UserDetailPayService userDetailPayService;	
	
	/*
	 * 订单取消
	 * @see com.zrj.service.order.OrdersCancelService#orderCancel(java.lang.String)
	1. 如果订单可以取消，则需要判定该返还多少金额
		1) 提前取消的时间定义：订单时间的前一个自然日的18:00之前视为提前取消. 退订单金额的50%
		2) 当天取消的时间定义：至少在服务时间开始前的240分钟视为当天取消。退2元。
		   
	2. 退款统一为退到余额（支付宝、微信、余额来源的订单退款，统一退到用户余额中）

	3. 退款后的逻辑如下
		1). 订单状态更新为已取消
		2). 用户余额增加返回的金额
		3). 用户明细表增加一条退款记录
		4). 订单日志增加一条订单取消记录
		5). 如果有相应的派工，则需要进行派工取消
		6). 通知相应的派工阿姨.
		7). 通知相应的管理人员.
	  */
	@Override
	public AppResultData<Object> orderCancel(String orderNo) {
	
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		
		result = orderCancelBack(orderNo);
		int status = result.getStatus();
		if (status == Constants.ERROR_999) {
			return result;
		}
		BigDecimal backPayMoney = new BigDecimal(0.0);
		backPayMoney = (BigDecimal) result.getData();
		
		Orders orders = orderQueryService.selectByOrderNo(orderNo);
		Long orderId = orders.getId();
		OrderPrices ordersPrices = orderPricesService.selectByOrderId(orders.getId());
		
		//进行退款后的逻辑操作
		//1). 订单状态更新为已取消
		Short orderStatus = orders.getOrderStatus();
		orders.setOrderStatus(Constants.ORDER_STATS_0_CANCLEED);
		orderService.updateByPrimaryKeySelective(orders);
		
		//2). 用户余额增加返回的金额
		//如果是代理商下单，则余额退到代理商账号下
		String agentMobile = orders.getAgentMobile();
		Users user = null;
		if (!StringUtil.isEmpty(agentMobile) && agentMobile != "0") {
			user = userService.getUserByMobile(agentMobile);
		} else {
			user = userService.getUserById(orders.getUserId());
		}

		BigDecimal restMoney = user.getRestMoney();
		restMoney = MathBigDeciamlUtil.add(restMoney, backPayMoney);
		user.setRestMoney(restMoney);
		userService.updateByPrimaryKeySelective(user);
		
		//3). 用户明细表增加一条退款记录
		UserDetailPay userDetailPay = userDetailPayService.initUserDetailDefault();
		userDetailPay.setUserId(user.getId());
		userDetailPay.setMobile(orders.getMobile());
		userDetailPay.setPayAccount(orders.getMobile());
		userDetailPay.setOrderType(Constants.ORDER_TYPE_3);
		userDetailPay.setOrderId(orders.getId());
		userDetailPay.setOrderNo(orders.getOrderNo());
		userDetailPay.setOrderMoney(ordersPrices.getOrderMoney());
		userDetailPay.setOrderPay(backPayMoney);
		userDetailPay.setPayType(Constants.PAY_TYPE_0);
		userDetailPayService.insert(userDetailPay);
		
		//4). 订单日志增加一条订单取消记录
		OrderLog orderLog = orderLogService.initOrderLog(orders);
		orderLog.setRemarks("订单取消,退款" +  MathBigDeciamlUtil.round2(backPayMoney));
		orderLogService.insert(orderLog);
		
		//5). 如果有相应的派工，则需要进行派工取消
		
		return result;
	}	
	
	/*
	 * 订单取消
	 * @see com.zrj.service.order.OrdersCancelService#orderCancel(java.lang.String)
	1. 如果订单可以取消，则需要判定该返还多少金额
		1) 提前取消的时间定义：订单时间的前一个自然日的18:00之前视为提前取消. 退订单金额的50%
		2) 当天取消的时间定义：至少在服务时间开始前的240分钟视为当天取消。退2元。
	 */
	@Override
	public AppResultData<Object> orderCancelBack(String orderNo) {
	
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Orders orders = orderQueryService.selectByOrderNo(orderNo);
		if (orders == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
			return result;
		}
		
		//如果订单已经取消，则直接返回信息即可
		if (orders.getOrderStatus().equals(Constants.ORDER_STATS_0_CANCLEED)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_IS_CANCELED_MG);
			return result;
		}
		
		Long orderId = orders.getId();
		OrderPrices ordersPrices = orderPricesService.selectByOrderId(orders.getId());
		
		BigDecimal orderPay = ordersPrices.getOrderPay();
		BigDecimal backPayMoney = new BigDecimal(0.0);
		
		//退款的时间判断，需要找出四个时间点
		//startTime = 服务时间点 
		//preDayStartTime = 服务时间点前一个自然日的18:00:00
		//pre2HourStartTime 服务时间点前两个小时的时间点
		//nowTime = 当前时间点
		long nowTime = TimeStampUtil.getNow();
		long startTime = orders.getStartTime()*1000;
		
		Date startDate = TimeStampUtil.timeStampToDateFull(startTime, "yyyy-MM-dd HH:mm:ss");
		
		String preDate = DateUtil.addDay(startDate, -1, Calendar.DAY_OF_MONTH, "yyyy-MM-dd 18:00:00");
		Long preDayStartTime = TimeStampUtil.getMillisOfDayFull(preDate);
		
		String pre2HourStartTimeStr = DateUtil.addDay(startDate, -2, Calendar.HOUR, "yyyy-MM-dd HH:mm:ss");
		Long pre2HourStartTime = TimeStampUtil.getMillisOfDayFull(pre2HourStartTimeStr);
		//先判断当前时间是否大于服务时间点前两个小时的时间点
		if (TimeStampUtil.compareTimeStr(pre2HourStartTime, nowTime) >=0 ) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ERROR_999_MSG_7);
			return result;			
		}
		
		//在判断当前时间在  服务前两个小时到 前一个自然日的18:00:00区间
		if (TimeStampUtil.compareTimeStr(nowTime,pre2HourStartTime) > 0 &&
			TimeStampUtil.compareTimeStr(preDayStartTime, nowTime) >= 0) {
			//开始前的120分钟视为当天取消。退2元
			result.setMsg("您是否确定取消订单？现在取消将全部扣除您的订单金额（我们会支付感谢金2元到您的账户）。");
			backPayMoney = new BigDecimal(2);
		}
		
		//如果微前一个自然日的18:00:00之前，则退款50%
		if (TimeStampUtil.compareTimeStr(nowTime, preDayStartTime) >=0 ) {
			//退款50%
			
			backPayMoney = orderPay.multiply(new BigDecimal(0.5));
			String backPayMoneyStr = MathBigDeciamlUtil.round2(backPayMoney);
			result.setMsg("您是否确定取消订单？现在取消可以退订单金额的50%（"+backPayMoneyStr+"）到您的账户。");
		}
		
		result.setData(backPayMoney);
		
		return result;
	}		
}