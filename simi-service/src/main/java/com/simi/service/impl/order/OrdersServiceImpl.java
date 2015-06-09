package com.simi.service.impl.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UserPayRefundsService;
import com.simi.po.dao.order.OrderPricesMapper;
import com.simi.po.dao.order.OrdersMapper;
import com.simi.po.dao.user.UserDetailScoreMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserDetailPay;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;
import com.meijia.utils.OneCareUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	private UserDetailPayService userDetailPayService;

	@Autowired
	private UserDetailScoreMapper userDetailScoreMapper;

	@Autowired
	private OrdersMapper ordersMapper;

	@Autowired
	private OrderPricesMapper orderPricesMapper;

	@Autowired
	UsersMapper usersMapper;

	@Autowired
	UserPayRefundsService userPayRefundsService;

	@Autowired
	OrderLogService orderLogService;

	@Autowired
	AdminAccountService adminAccountService;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return ordersMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Orders initOrders(String mobile, int city_id, int service_type,
			Long service_date, Long start_time, int service_hour, Users u,
			String orderNo, Long now, Orders orders) {
		orders.setMobile(mobile);
		orders.setCustomName("");
		orders.setAgentMobile("0");
		orders.setUserId(u.getId());
		orders.setCityId(Long.valueOf(city_id));
		orders.setServiceType((short) service_type);
		orders.setAddTime(now);
		orders.setUpdateTime(now);
		orders.setServiceDate(service_date);
		orders.setStartTime(start_time);
		orders.setServiceHours((short) service_hour);
		orders.setOrderNo(orderNo);
		orders.setOrderRate((short)0);
		orders.setOrderRateContent("");
		orders.setOrderStatus(Constants.ORDER_STATS_1_PAYING);
		orders.setOrderFrom(Constants.USER_APP);
		orders.setIsScore((short) 0);
		return orders;
	}
	@Override
	public int insert(Orders orders, OrderPrices orderPrices) {
		if(ordersMapper.insert(orders)>0) {
			Long orderId = orders.getId();
			orderPrices.setOrderId(orderId);
			if(orderPricesMapper.insert(orderPrices)>0) {
				//解析保存Order Selected Type and Value
				OrderLog orderLog = orderLogService.initOrderLog(orders);
				orderLogService.insert(orderLog);
			}
		}
		return 0;
	}

	@Override
	public Long insert(Orders record) {
		return ordersMapper.insert(record);
	}

	@Override
	public int insertSelective(Orders record) {
		return ordersMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKey(Orders record) {
		return ordersMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Orders record) {
		return ordersMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void orderPaySuccess(String mobile, Orders orders) {

		String serviceDate = TimeStampUtil.timeStampToDateStr(orders.getStartTime()*1000);
		String serviceTypeName = OneCareUtil.getserviceTypeName(orders.getServiceType());
		String[] content = new String[] { serviceDate, serviceTypeName };
		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
				Constants.PAY_SUCCESS_SMS_TEMPLE_ID, content);
		System.out.println(sendSmsResult.get(sendSmsResult.get(Constants.SMS_STATUS_CODE)));
	}

	@Override
	public void orderPaySuccessSendToAdmin(String mobile, Orders orders) {

		String serviceDate = TimeStampUtil.timeStampToDateStr(orders.getStartTime()*1000);

		String serviceTypeName = OneCareUtil.getserviceTypeName(orders.getServiceType());

		String[] content = new String[] {orders.getMobile() ,serviceDate, serviceTypeName, orders.getOrderNo() };
		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
				Constants.GET_CODE_REMIND_ID, content);
//		System.out.println(sendSmsResult.get(sendSmsResult.get(Constants.SMS_STATUS_CODE)));
	}

	/**
	 * 订单成功后，给管家发送短信
	 */
	@Override
	public void orderPaySuccessSendToSenior(Orders orders) {

		String serviceDate = TimeStampUtil.timeStampToDateStr(orders.getStartTime()*1000);

		String serviceTypeName = OneCareUtil.getserviceTypeName(orders.getServiceType());

		//获取管家列表
		List<AdminAccount> adminAccounts =   adminAccountService.selectByRoleId(2L);

		String[] content = new String[] {orders.getMobile() ,serviceDate, serviceTypeName, orders.getOrderNo() };
//		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
//				Constants.GET_CODE_REMIND_ID, content);
//		System.out.println(sendSmsResult.get(sendSmsResult.get(Constants.SMS_STATUS_CODE)));
	}



	@Override
	public int cancelOrderAbout(Orders orders, OrderPrices orderPrices) {

		orders.setOrderStatus(Constants.ORDER_STATS_0_CANCLEED);

		String mobile = orders.getMobile();
		Long userId = orders.getUserId();
		String trade_status=Constants.CANCEL_ORDER_SATUS;
		Long orderId=orders.getId();
		String trade_no="0";
		String payAccount = "";
//		 2. 退款为原路退回 - 余额支付
//		   1) 操作表 user_detail_pay  , 增加一条退款记录
//		   2) 用户表 users 余额增加相应的金额
//		   3) 操作表 orders ，order_status = 0  已取消
//		   以上三个步骤为同一个事务
//		 3. 退款为原路退回 - 支付宝
//		   1) 操作表 user_detail_pay  , 增加一条退款记录
//		   2) 查找表 user_pay_refunds , 找到对应的支付宝付款记录. 并按照支付宝退款流程进行操作[[BR]]
//		     注意：我觉得是否user_pay_refunds 里面的字段不够，因为很多信息都放在post_params里面，我们可以讨论
//		   3) 操作表 orders ，order_status = 0  已取消
		UserDetailPay userDetailPay = userDetailPayService.initUserDetailPay(mobile, trade_status, orders,
				userId, orderId, Constants.BACK_SUCCESS, orderPrices, trade_no, payAccount);
//		UserPayRefunds userPayRefunds = new UserPayRefunds();
//		userPayRefunds = userPayRefundsService.initUserPayRefunds(orders, mobile, trade_status,
//				orderId, trade_no, Constants.ORDER_TYPE_0, userPayRefunds);

		OrderLog orderLog = orderLogService.initOrderLog(orders);
		orderLogService.insert(orderLog);

		if(userDetailPayService.insert(userDetailPay)>0)
			return ordersMapper.updateByPrimaryKey(orders);
//				return userPayRefundsService.insert(userPayRefunds);
		return 0;
	}

	@Override
	public int upderOrdeRateAbout(Orders orders, UserDetailScore userDetailScore) {
		Users users = usersMapper.selectByPrimaryKey(orders.getUserId());
		if(ordersMapper.updateByPrimaryKey(orders)>0) {
			users.setScore(users.getScore()+Constants.RATE_CORE);

			OrderLog orderLog = orderLogService.initOrderLog(orders);
			orderLogService.insert(orderLog);

			if(usersMapper.updateByPrimaryKey(users)>0)
				return userDetailScoreMapper.insert(userDetailScore);
		}
		return 0;
	}
	@Override
	public List<Map<String,Object>> selectOrdersCountByYearAndMonth(String start, String end) {
			Long startLong = TimeStampUtil.getMillisOfDay(start)/1000;
			Long endLong = TimeStampUtil.getMillisOfDay(end)/1000;
			List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();

			Map<String,Object> conditions = new HashMap<String, Object>();
			conditions.put("startTime", startLong);
			conditions.put("endTime",endLong);
			List<Map<String, Object>> list1 = ordersMapper.totalCountByStartTime(conditions);
			for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				Map<String,Object> map1 = new HashMap<String, Object>();
				map1.put("start",map.get("startTime"));
				map1.put("title","共有"+map.get("total")+"个派工");
				map1.put("url","/onecare-oa/order/dispatch?start="+startLong+"&end="+endLong);
//				map1.put("url","/onecare-oa/order/dispatch?serviceDate="+map.get("serviceDate"));
				listMap.add(map1);
			}
			return listMap;
	}
	@Override
	public List<Orders> selectBySameDateTime(HashMap conditions) {
		return ordersMapper.selectBySameDateTime(conditions);
	}
	@Override
	public List<String> selectDistinctMobileLists() {

		return ordersMapper.selectDistinctMobileLists();
	}
}