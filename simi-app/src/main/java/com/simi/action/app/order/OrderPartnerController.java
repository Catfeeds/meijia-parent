package com.simi.action.app.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.Users;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderDetailVo;
import com.simi.vo.order.OrderListVo;
import com.simi.vo.order.OrderLogVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderPartnerController extends BaseController {
	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;
	
	@Autowired
	private OrderLogService orderLogService;
	
	@Autowired
	OrderPricesService orderPricesService;
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private UsersAsyncService usersAsyncService;	
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private NoticeAppAsyncService noticeAppAsyncService;

	// 18.订单列表接口
	/**
	 * mobile:手机号 page分页页码
	 */
	@RequestMapping(value = "get_partner_list", method = RequestMethod.GET)
	public AppResultData<Object> list(
			@RequestParam("partner_user_id") Long partnerUserId, 
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		
		List<OrderListVo> orderListVo = new ArrayList<OrderListVo>();
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(partnerUserId);
		
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.PARTNER_NOT_EXIST_MG);
			return result;
		}
		OrderSearchVo searchVo = new OrderSearchVo();
		searchVo.setPartnerUserId(partnerUserId);
		PageInfo list = orderQueryService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<Orders> orderList = list.getList();
		
		/*服务人员信息：头像，姓名
		     订单信息：订单状态   ，  订单价格   ,,   服务类型名称  ,, 服务时间service_data,,服务地址     ，，， 客户姓名*/
		
		for (Orders item : orderList) {
			OrderListVo listVo = new OrderListVo();
			listVo = orderQueryService.getOrderListVo(item);
			
			OrderDetailVo detailVo = orderQueryService.getOrderDetailVo(item, listVo);
			orderListVo.add(detailVo);
		}
		result.setData(orderListVo);
		
		return result;

	}
	
	// 19.订单详情接口
	/**
	 * mobile:手机号 order_id订单ID
	 */
	@RequestMapping(value = "get_partner_detail", method = RequestMethod.GET)
	public AppResultData<Object> detail(
			@RequestParam("partner_user_id") Long partnerUserId, 
			@RequestParam("order_id") Long orderId) {
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(partnerUserId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.PARTNER_NOT_EXIST_MG);
			return result;
		}		
		
		Orders order = orderQueryService.selectByPrimaryKey(orderId);
		
		if (order == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);			
			return result;
		}
		
		OrderListVo listVo = orderQueryService.getOrderListVo(order);
		OrderDetailVo detailVo = orderQueryService.getOrderDetailVo(order, listVo);
		
		result.setData(detailVo);
		
		return result;
	}	
	
	/**
	 * 服务商人员生成订单。
	 */
	@RequestMapping(value = "parnter_order", method = RequestMethod.POST)
	public AppResultData<Object> partnerOrder(
			@RequestParam("partner_user_id") Long partnerUserId, 
			@RequestParam("mobile") String mobile, 
			@RequestParam("service_type_id") Long serviceTypeId, 
			@RequestParam("service_price_name") String servicePriceName,
			@RequestParam("order_money") BigDecimal orderMoney,
			@RequestParam(value = "user_id", required = false, defaultValue = "0") Long userId,
			@RequestParam(value = "service_price_id", required = false, defaultValue = "0") Long servicePriceId,
			@RequestParam(value = "remarks", required = false, defaultValue = "0") String remarks) {	
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (partnerUserId.equals(0L) || serviceTypeId.equals(0L)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("数据错误");
			return result;
		}
		
		//判断是否需要新增用户.
		Users u = userService.selectByMobile(mobile);
		
		if (u == null) {
			u = userService.genUser(mobile, "", (short) 0, "");					
			usersAsyncService.genImUser(u.getId());
		}
		
		userId = u.getId();
		
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
		String serviceTypeName = serviceType.getName();
		//创建订单    
    	String orderNo = String.valueOf(OrderNoUtil.genOrderNo());
    	Orders order = ordersService.initOrders();
    	
    	order.setOrderNo(orderNo);
		order.setPartnerUserId(partnerUserId);
		order.setServiceTypeId(serviceTypeId);
		order.setUserId(userId);
		order.setMobile(mobile);
		order.setServiceContent(serviceTypeName + " " + servicePriceName);
		order.setRemarks(remarks);
		order.setOrderStatus(Constants.ORDER_STATUS_1_PAY_WAIT);
		ordersService.insert(order);
		Long orderId = order.getOrderId();
		
		//记录订单日志.
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLog.setAction("order-create");
		orderLog.setRemarks("创建订单");
		orderLogService.insert(orderLog);
		
		//保存订单价格信息
		OrderPrices orderPrice = orderPricesService.initOrderPrices();
		orderPrice.setOrderId(orderId);
		orderPrice.setOrderNo(orderNo);
		
		if (servicePriceId == null) servicePriceId = 0L;
		orderPrice.setServicePriceId(servicePriceId);
		orderPrice.setServicePriceName(servicePriceName);
		orderPrice.setPartnerUserId(partnerUserId);
		orderPrice.setUserId(userId);
		orderPrice.setMobile(mobile);
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderMoney);
		orderPricesService.insert(orderPrice);
		
		//推送消息到app
		noticeAppAsyncService.pushMsgToDevice(userId, "新的订单", serviceTypeName + "" + servicePriceName, "app", "order_detail", orderId.toString(), "");

		return result;
	}
	
	/**
	 * 服务商人员添加进度。
	 */
	@RequestMapping(value = "parnter_order_process", method = RequestMethod.POST)
	public AppResultData<Object> partnerOrder(
			@RequestParam("partner_user_id") Long partnerUserId, 
			@RequestParam("order_id") Long orderId, 
			@RequestParam("remarks") String remarks) {	
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (partnerUserId.equals(0L) || orderId.equals(0L)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("数据错误");
			return result;
		}
		
		Orders order = ordersService.selectByPrimaryKey(orderId);
		
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLog.setAction("order-process");
		orderLog.setRemarks(remarks);
		orderLogService.insert(orderLog);
		
		OrderLogVo vo = new OrderLogVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(orderLog, vo);
		Long addTime = vo.getAddTime();
		String addTimeStr = TimeStampUtil.fromTodayStr(addTime * 1000);
		vo.setAddTimeStr(addTimeStr);
		result.setData(vo);
		
		//发送订单进程通知. 推送消息到app
		Long userId = order.getUserId();
		
		if (remarks.length() > 20) {
			remarks = remarks.substring(0, 20) ;
		}
		noticeAppAsyncService.pushMsgToDevice(userId, "订单进度更新", "您的订单有了新的进展:" + remarks + "...", "app", "order_detail", orderId.toString(), "");
		
		return result;
	}	
	
	
}
