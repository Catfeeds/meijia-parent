package com.simi.action.app.order;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderExtRecycle;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.service.ValidateService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.order.OrderExtRecycleService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.utils.OrderUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.order.OrderExtRecycleListVo;
import com.simi.vo.user.UserAddrVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderExtRecycleController extends BaseController {
	
	@Autowired
	private UsersService userService;
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderExtRecycleService orderExtRecycleService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private OrderLogService orderLogService;
	
	@Autowired
	private OrderPricesService orderPricesService;
	
	@Autowired
	private OrderQueryService orderQueryService;
	
	@Autowired
	private UserAddrsService userAddrsService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private UsersService usersService;
	

	/**废品回收订单列表接口
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_list_recycle", method = RequestMethod.GET)
	public AppResultData<Object> cancelOrder(
			@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		List<OrderExtRecycleListVo> listVo = new ArrayList<OrderExtRecycleListVo>();
		List<OrderExtRecycle> list = orderExtRecycleService.selectByUserId(userId);
		for (OrderExtRecycle item : list) {
			OrderExtRecycleListVo vo = orderExtRecycleService.getOrderExtRecycleListVo(item);
			listVo.add(vo);
		}
		result.setData(listVo);
		return result;
	}	
	/**
	 * 废品回收订单详情
	 * @param userId
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "get_detail_recycle", method = RequestMethod.GET)
	public AppResultData<Object> detail(
			@RequestParam("user_id") Long userId,
			@RequestParam("order_id") Long orderId) {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		Users u = userService.selectByPrimaryKey(userId);
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null ) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		OrderExtRecycle recycle = orderExtRecycleService.selectByOrderId(orderId);
		if (recycle == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
			return result;
		}
		OrderExtRecycleListVo vo = orderExtRecycleService.getOrderExtRecycleListVo(recycle);
		result.setData(vo);
		return result;
	}		
	
	@RequestMapping(value = "get_user_addr_list", method = RequestMethod.GET)
	public AppResultData<Object> getServiceTypeList(
			@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>( 
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
	    List<UserAddrs> userAddrsList = userAddrsService.selectByUserId(userId);
	    /*if (userAddrsList.isEmpty()) {
	    	result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}*/
	    List<UserAddrVo> voList = new ArrayList<UserAddrVo>();
	   for (int i = 0; i < userAddrsList.size(); i++) {
		    UserAddrs addrs = userAddrsList.get(i);
		    UserAddrVo vo = new UserAddrVo();
			vo.setAddrId(addrs.getId());
			vo.setAddrName(addrs.getAddress() + addrs.getAddr());
		   
		   voList.add(vo);
	}
	   
		result.setData(voList);

		return result;
	}

	/**
	 * 废品回收订单下单接口
	 * @param userId
	 * @param serviceTypeId
	 * @param mobile
	 * @param addrId
	 * @param totalNum
	 * @param totalBudget
	 * @param remarks
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "post_add_recycle", method = RequestMethod.POST)
	public AppResultData<Object> postRecycle(
			@RequestParam("user_id") Long userId,
			@RequestParam("addr_id") Long addrId,
			@RequestParam("recycle_type") Short recycleType,
			@RequestParam(value = "link_man",required = false,defaultValue = "") String linkMan,
			@RequestParam(value = "link_tel",required = false,defaultValue = "") String linkTel,
			@RequestParam(value = "remarks",required = false,defaultValue = "") String remarks
			//@RequestParam(value = "",required = false,defaultValue= "")
			) throws UnsupportedEncodingException {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
	
		Long serviceTypeId = (long) 246;
		Users users  = usersService.selectByPrimaryKey(userId);
		// 判断是否为注册用户，非注册用户返回 999		
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
				
		//如果城市不是北京市，则提示无法服务
		v = validateService.validateUserAddr(userId, addrId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		List<UserAddrs> userAddrs = userAddrsService.selectByUserId(userId);
	
		BigDecimal orderMoney = new BigDecimal(0.0);//原价
		BigDecimal orderPay = new BigDecimal(0.0);//折扣价
		// 调用公共订单号类，生成唯一订单号
    	Orders order = null;
    	String orderNo = "";
    
    	orderNo = String.valueOf(OrderNoUtil.genOrderNo());
		order = ordersService.initOrders();
    	
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
		// 服务内容及备注信息需要进行urldecode;
		String serviceContent = serviceType.getName() ;
		
		if (!StringUtil.isEmpty(remarks)) {
			remarks = URLDecoder.decode(remarks,Constants.URL_ENCODE);
		}
		//保存订单信息
		order.setOrderNo(orderNo);
		//order.setPartnerUserId(partnerUserId);
		order.setServiceTypeId(serviceTypeId);
		order.setUserId(userId);
		order.setMobile(users.getMobile());
		//order.setOrderType(servicePrice.getOrderType());
		//order.setOrderDuration(servicePrice.getOrderDuration());
		order.setServiceContent(serviceContent);
		//order.setServiceDate(serviceDate);
		order.setAddrId(addrId);
		if (!StringUtil.isEmpty(remarks)) {
			order.setRemarks(remarks);	
		}
		//order.setOrderFrom(orderFrom);
		order.setOrderStatus(Constants.ORDER_STATUS_3_PROCESSING);
		//order.setCityId(cityId);
		ordersService.insert(order);
		Long orderId = order.getOrderId();
		
		//记录订单日志.
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLog.setAction("order-create");
		orderLog.setRemarks("订单创建");
		orderLogService.insert(orderLog);
		
		//保存订单价格信息
		OrderPrices orderPrice = orderPricesService.initOrderPrices();

		orderPrice.setOrderId(orderId);
		orderPrice.setOrderNo(orderNo);
	//	orderPrice.setServicePriceId(servicePriceId);
	//	orderPrice.setPartnerUserId(partnerUserId);
		orderPrice.setUserId(userId);
		orderPrice.setMobile(users.getMobile());
	//	orderPrice.setPayType(payType);
	//	orderPrice.setUserCouponId(userCouponId);
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderPay);
		orderPricesService.insert(orderPrice);
		
		//保存废品回收订单扩展表信息
		OrderExtRecycle recycle = orderExtRecycleService.initOrderExtRecycle();
		recycle.setOrderId(orderId);
		recycle.setOrderNo(orderNo);
		recycle.setUserId(userId);
		recycle.setMobile(users.getMobile());
		recycle.setLinkMan(linkMan);
		recycle.setLinkTel(linkTel);
		recycle.setRecycleType(recycleType);
		orderExtRecycleService.insert(recycle);
		
		OrderExtRecycleListVo vo = orderExtRecycleService.getOrderExtRecycleListVo(recycle);
		result.setData(vo);
		
		
		//异步产生首页消息信息.
		String title = serviceType.getName();
		String summary =  OrderUtil.getOrderStausMsg(order.getOrderStatus());
		userMsgAsyncService.newActionAppMsg(userId, orderId, "recycle", title, summary, "");
		
		return result;
	}	
	
}
