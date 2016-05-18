package com.simi.action.app.car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserCar;
import com.simi.po.model.user.Users;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.async.NoticeSmsAsyncService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserCarService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderDetailVo;
import com.simi.vo.order.OrderListVo;

@Controller
@RequestMapping(value = "/app/car")
public class UserCarController extends BaseController {
	@Autowired
	private UsersService userService;

	@Autowired
	private UserCarService userCarService;

	@Autowired
	private OrderQueryService orderQueryService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderPricesService orderPricesService;
	
	@Autowired
	private OrderLogService orderLogService;
	
	@Autowired
	private NoticeSmsAsyncService noticeSmsAsyncService;
	
	@Autowired
	private NoticeAppAsyncService noticeAppAsyncService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	@Autowired
	private UserDetailPayService userDetailPayService;

	// 用户车牌信息
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "get_car", method = RequestMethod.GET)
	public AppResultData<Object> getCar(@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		UserCar userCar = userCarService.selectByUserId(userId);

		if (userCar == null) {
			userCar = userCarService.initUserCar();
		}

		result.setData(userCar);

		return result;
	}

	// 用户车牌信息 保存
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_car_no", method = RequestMethod.POST)
	public AppResultData<Object> postImgs(@RequestParam("user_id") Long userId, @RequestParam("car_no") String carNo) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		UserCar userCar = userCarService.selectByCarNo(carNo);
		if (userCar != null) {
			if (userCar.getUserId().equals(userId)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("车牌号已经绑定过.");
				return result;
			}
		}

		userCar = userCarService.selectByUserId(userId);

		if (userCar == null) {
			userCar = userCarService.initUserCar();
		}

		userCar.setUserId(userId);
		userCar.setCarNo(carNo);

		if (userCar.getId() > 0L) {
			userCarService.updateByPrimaryKey(userCar);
		} else {
			userCarService.insert(userCar);
		}

		return result;
	}

	@RequestMapping(value = "get_order_list", method = RequestMethod.GET)
	public AppResultData<Object> list(@RequestParam("user_id") Long userId, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		List<OrderListVo> orderListVo = new ArrayList<OrderListVo>();

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		if (u == null) {
			return result;
		}

		Long serviceTypeId = 258L;
		OrderSearchVo searchVo = new OrderSearchVo();
		searchVo.setUserId(userId);
		searchVo.setServiceTypeId(serviceTypeId);
		PageInfo list = orderQueryService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<Orders> orderList = list.getList();

		for (Orders item : orderList) {
			OrderListVo listVo = new OrderListVo();
			listVo = orderQueryService.getOrderListVo(item);

			OrderDetailVo detailVo = orderQueryService.getOrderDetailVo(item, listVo);
			orderListVo.add(detailVo);
		}

		result.setData(orderListVo);

		return result;

	}

	@RequestMapping(value = "cap_car", method = RequestMethod.POST)
	public AppResultData<Object> capCard(@RequestParam("car_no") String carNo, @RequestParam(value = "cap_img", required = false, defaultValue = "") String capImg) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (StringUtil.isEmpty(capImg)) {
			capImg = "http://img.51xingzheng.cn/9790863671ef3249011398e23f137fc6";
		}
		
		
		UserCar userCar = userCarService.selectByCarNo(carNo);

		if (userCar == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("车牌号信息未绑定");
			return result;
		}
			

		Long userId = userCar.getUserId();

		Users u = userService.selectByPrimaryKey(userId);
		
		//两次订单间隔为10秒
		Long serviceTypeId = 258L;
		OrderSearchVo searchVo = new OrderSearchVo();
		searchVo.setUserId(userId);
		searchVo.setServiceTypeId(serviceTypeId);
		PageInfo list = orderQueryService.selectByListPage(searchVo, 1, 1);
		List<Orders> orderList = list.getList();
		if (!orderList.isEmpty()) {
			Orders order = orderList.get(0);
			Long addTime = order.getAddTime();
			Long nowTime = TimeStampUtil.getNowSecond();
			if ( (nowTime - addTime) < 10 ) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("10秒内重复操作.");
				return result;
			}
		}
		
		
		
		BigDecimal orderMoney = new BigDecimal(1.0);// 原价
		BigDecimal orderPay = new BigDecimal(1.0);// 折扣价
		// 查询用户余额
		if (u.getRestMoney().compareTo(orderPay) < 0) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("余额不足");
			noticeAppAsyncService.pushMsgToDevice(userId, "速通宝扣款", "余额不足");
			return result;
		}
		// 扣除用户余额
		u.setRestMoney(u.getRestMoney().subtract(orderPay));
		u.setUpdateTime(TimeStampUtil.getNowSecond());
		userService.updateByPrimaryKeySelective(u);

		
		// 生成订单扣款
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);

		// 调用公共订单号类，生成唯一订单号
		Orders order = null;
		String orderNo = "";

		orderNo = String.valueOf(OrderNoUtil.genOrderNo());
		order = ordersService.initOrders();

		order.setOrderNo(orderNo);
		order.setServiceTypeId(serviceTypeId);
		order.setUserId(userId);
		order.setMobile(u.getMobile());
		order.setServiceContent(serviceType.getName());

		order.setOrderStatus(Constants.ORDER_TYPE_2);

		String remarks = serviceType.getName() + "扣款" + MathBigDecimalUtil.round2(orderMoney) + "元";
		order.setRemarks(remarks);

		ordersService.insert(order);
		Long orderId = order.getOrderId();

		// 记录订单日志.
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLog.setAction("create");
		orderLog.setAction("创建订单");
		orderLogService.insert(orderLog);

		// 保存订单价格信息
		OrderPrices orderPrice = orderPricesService.initOrderPrices();

		orderPrice.setOrderId(orderId);
		orderPrice.setOrderNo(orderNo);
		orderPrice.setUserId(userId);
		orderPrice.setMobile(u.getMobile());
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderPay);
		orderPricesService.insert(orderPrice);

		// 记录用户消费明细
		userDetailPayService.userDetailPayForOrder(u, order, orderPrice, "", "", "");

		// 发送用户短信
		noticeSmsAsyncService.noticeOrderCarUser(orderId);

		// 生成用户首页消息信息
		userMsgAsyncService.newActionAppMsg(userId, orderId, "expy", serviceType.getName(), remarks, "");

		// 推送信息
		if (StringUtil.isEmpty(capImg)) {
			capImg = "http://img.51xingzheng.cn/9790863671ef3249011398e23f137fc6";
		}
		noticeAppAsyncService.pushMsgToExpr(orderId, carNo, "白色", capImg, "成功");

		return result;

	}

}
