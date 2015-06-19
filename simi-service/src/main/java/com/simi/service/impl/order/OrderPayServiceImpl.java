package com.simi.service.impl.order;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UserRefSeniorService;
import com.simi.po.dao.order.OrderCardsMapper;
import com.simi.po.dao.order.OrderSeniorMapper;
import com.simi.po.dao.user.UserCouponsMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.dict.DictSeniorType;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.OrderSenior;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.UserDetailPay;
import com.simi.po.model.user.Users;
import com.meijia.utils.DateUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrderPayServiceImpl implements OrderPayService {
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderPricesService orderPricesService;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private UserDetailPayService userDetailPayService;
	
	@Autowired
	private OrderSeniorMapper orderSeniorMapper;
	
	@Autowired
	UserCouponsMapper userCouponsMapper;
	
	@Autowired
	OrderCardsMapper orderCardsMapper;
	
	@Autowired
	OrderLogService orderLogService;
	
	@Autowired
	OrderSeniorService orderSeniorService;
		
	/**
	 * 订单支付成功,后续通知功能
	 * 1. 如果为
	 */
	@Override
	public void orderPaySuccessToDo(Orders orders) {
		
//		String serviceDate = TimeStampUtil.timeStampToDateStr(orders.getStartTime()*1000);
//		String serviceTypeName = OneCareUtil.getserviceTypeName(orders.getServiceType());
//		String[] content = new String[] { serviceDate, serviceTypeName };
//		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
//				Constants.PAY_SUCCESS_SMS_TEMPLE_ID, content);
//		System.out.println(sendSmsResult.get(sendSmsResult.get(Constants.SMS_STATUS_CODE)));
	}
	
	// 1. 操作表 order_senior
	// 2. 根据senior_type 传递参数从表 dict_senior_type 获取相应的金额
	// 3. 调用生成订单号的util.生成一个order_senior 的订单号
	// 4. 如果是余额支付
	// 1) 用户余额，扣除相应的金额，注意如果有优惠卷的金额，操作表为users
	// 2) 用户的消费明细记录，操作表为user_detail_pay
	// 3) 将 order_senior表的支付状态为 order_status = 1 已支付, 支付方式为 pay_type = 0 余额支付
	// 注意以上三个步骤必须为同一个事务。
	@Override
	public OrderSenior orderSeniorPayMoney(String mobile, DictSeniorType seniorType, Short payType) {

		long nowTime = TimeStampUtil.getNow() / 1000;
		BigDecimal seniorPay = seniorType.getSeniorPay();
		Long seniorTypeId = seniorType.getId();
		Short validMonth = seniorType.getValidMonth();

		Users users = usersMapper.selectByMobile(mobile);
		OrderSenior orderSenior = new OrderSenior();
		orderSenior.setMobile(users.getMobile());
		orderSenior.setAddTime(nowTime);
		orderSenior.setSeniorType(seniorTypeId);
		orderSenior.setPayType(payType);
		orderSenior.setUserId(users.getId());
		orderSenior.setUpdateTime(nowTime);
		orderSenior.setSeniorPay(seniorPay);
		orderSenior.setValidMonth(validMonth);
		orderSenior.setStartDate(new Date());
		orderSenior.setEndDate(new Date());
		String seniorOrderNo = String.valueOf(OrderNoUtil.getOrderSeniorNo());
		orderSenior.setSeniorOrderNo(seniorOrderNo);
		Short orderStatus = 0;
		if (payType == 0) {// 已支付
			orderStatus = 1;

			// 更新当前管家卡订单的开始日期和结束日期
			Date startDate = orderSeniorService.getSeniorStartDate(mobile);
			String endDateStr = DateUtil.addDay(startDate, validMonth, Calendar.MONTH, DateUtil.getDefaultPattern());
			Date endDate = DateUtil.parse(endDateStr);
			orderSenior.setStartDate(startDate);
			orderSenior.setEndDate(endDate);

		}
		orderSenior.setOrderStatus(orderStatus);
		orderSeniorMapper.insert(orderSenior);

		if (payType == 0) {// pay_type = 0 余额支付
			userPayRecord(mobile, payType, users, orderSenior, "rest_money_pay", "success", mobile);

			// 分配真人管家.
//			userRefSeniorService.allotSenior(users);
		}

		orderSenior = orderSeniorMapper.selectByOrderSeniorNo(seniorOrderNo);

		return orderSenior;
	}

	/**
	 * 管家卡购买成功后流程 1. 判断必选参数 2. 根据senior_order_no,从order_seniors找出对应的订单 3.
	 * 判断该订单是否已经支付完成 1) 判断字段order_status = 1 4. 如果该订单为未支付的状态，则需要做如下的操作 1)
	 * 操作表user_pay_status ，插入一条新的记录，记录支付的信息 2) 用户的消费明细记录，操作表为user_detail_pay，
	 * 记录他的消费类型为 Constants.ORDER_TYPE_1 = 1 管家卡购买 3) 将card_orders 表的状态改变为
	 * order_status = 1 ,已支付状态, pay_type 更新为对应的支付方式 注意以上3个步骤必须为同一个事务。 5.
	 * 分配对应的真人管家。原则为平均分配原则。
	 * 
	 * 6. 返回值
	 *
	 */
	@Override
	public int updateSeniorByAlipay(String mobile, short payType, String seniorOrderNo, String tradeNo, String tradeStatus, String payAccount) {

		OrderSenior orderSenior = orderSeniorMapper.selectByOrderSeniorNo(seniorOrderNo);

		if (mobile == null || mobile.equals("")) {
			mobile = orderSenior.getMobile();
		}
		Users users = usersMapper.selectByMobile(mobile);
		orderSenior.setPayType(payType);
		if (orderSenior.getOrderStatus().equals(Constants.PAY_STATUS_1)) {
			return 1;
		}
		orderSenior.setOrderStatus(Constants.PAY_STATUS_1);

		// 更新当前管家卡订单的开始日期和结束日期
		Date startDate = orderSeniorService.getSeniorStartDate(mobile);
		Short validMonth = orderSenior.getValidMonth();
		String endDateStr = DateUtil.addDay(startDate, validMonth, Calendar.MONTH, DateUtil.getDefaultPattern());
		Date endDate = DateUtil.parse(endDateStr);
		orderSenior.setStartDate(startDate);
		orderSenior.setEndDate(endDate);

		orderSeniorMapper.updateByPrimaryKeySelective(orderSenior);

		userPayRecord(mobile, payType, users, orderSenior, tradeStatus, tradeNo, payAccount);

		// 分配真人管家.
//		userRefSeniorService.allotSenior(users);

		return 1;
	}

	/**
	 *
	 * @param mobile
	 *            手机号
	 * @param payType
	 *            支付类型 0 = 余额支付 1 = 支付宝
	 * @param users
	 *            用户对象
	 * @param orderSenior
	 *            管家卡订单对象
	 * @param tradeStatus
	 *            支付的状态
	 * @param tradeNo
	 *            支付的ID
	 * @return
	 */
	private int userPayRecord(String mobile, short payType, Users users, OrderSenior orderSenior, String tradeStatus, String tradeNo, String payAccount) {

		Long nowTime = TimeStampUtil.getNow() / 1000;
		BigDecimal seniorPay = orderSenior.getSeniorPay();

		if (String.valueOf(payType).equals(String.valueOf(Constants.PAY_TYPE_0))) {
			users.setRestMoney(users.getRestMoney().subtract(seniorPay));
		}
		users.setUpdateTime(nowTime);
		usersMapper.updateByPrimaryKeySelective(users);

		UserDetailPay userDetailPay = new UserDetailPay();
		userDetailPay.setAddTime(nowTime);
		userDetailPay.setMobile(mobile);
		userDetailPay.setOrderId(orderSenior.getId());
		userDetailPay.setOrderNo(orderSenior.getSeniorOrderNo());
		userDetailPay.setOrderType(Constants.ORDER_TYPE_1);
		userDetailPay.setOrderMoney(seniorPay);
		userDetailPay.setOrderPay(seniorPay);

		userDetailPay.setPayAccount(payAccount);
		userDetailPay.setTradeNo(tradeNo);
		userDetailPay.setTradeStatus(tradeStatus);
		userDetailPay.setUserId(users.getId());
		userDetailPay.setPayType(payType);

		return userDetailPayService.insert(userDetailPay);
	}

}