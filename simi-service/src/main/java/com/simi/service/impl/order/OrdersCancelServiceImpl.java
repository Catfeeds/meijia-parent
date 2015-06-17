package com.simi.service.impl.order;

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

		return result;
	}		
}