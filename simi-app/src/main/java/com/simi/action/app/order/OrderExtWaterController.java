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

import com.github.pagehelper.PageInfo;
import com.meijia.utils.MathBigDeciamlUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.common.ValidateService;
import com.simi.po.model.order.OrderExtWater;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.order.OrderExtWaterService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderExtWaterListVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderExtWaterController extends BaseController {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderExtWaterService orderExtWaterService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
    @Autowired
    private PartnerServicePriceDetailService partnerServicePriceDetailService;	
	
	@Autowired
	private OrderLogService orderLogService;
	
	@Autowired
	private OrderPricesService orderPricesService;
	
	@Autowired
	private OrderPayService orderPayService;
		
	@Autowired
	private UserAddrsService userAddrsService;
	
	@Autowired
	private UserDetailPayService userDetailPayService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	@Autowired
	private ValidateService validateService;
	

	/**送水订单列表接口
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_list_water", method = RequestMethod.GET)
	public AppResultData<Object> list(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		Users u = userService.selectByPrimaryKey(userId);
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null ) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		List<OrderExtWaterListVo> listVo = new ArrayList<OrderExtWaterListVo>();
		OrderSearchVo searchVo = new OrderSearchVo();
		searchVo.setUserId(userId);
		PageInfo plist = orderExtWaterService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<OrderExtWater> list = plist.getList();
		
		if (!list.isEmpty())
			listVo = orderExtWaterService.getListVos(list);
		
		result.setData(listVo);
		return result;
	}	
	
	/**送水订单详情接口
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_detail_water", method = RequestMethod.GET)
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
		
		OrderExtWater orderExtWater = orderExtWaterService.selectByOrderId(orderId);
		if (orderExtWater == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
			return result;
		}
		OrderExtWaterListVo vo = orderExtWaterService.getListVo(orderExtWater);
		result.setData(vo);
		return result;
	}		
	


	/**
	 * 送水订单下单接口
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
	@RequestMapping(value = "post_add_water", method = RequestMethod.POST)
	public AppResultData<Object> postGreen(
			@RequestParam("user_id") Long userId,
			@RequestParam("addr_id") Long addrId,
			@RequestParam("service_price_id") Long servicePriceId,
			@RequestParam("service_num") Integer serviceNum,
//			@RequestParam("pay_type") Short payType,
			@RequestParam(value = "link_man",required = false,defaultValue = "") String linkMan,
			@RequestParam(value = "link_tel",required = false,defaultValue = "") String linkTel,
			@RequestParam(value = "remarks",required = false,defaultValue = "") String remarks,
			@RequestParam(value = "order_from", required = false, defaultValue = "0") Short orderFrom
			//@RequestParam(value = "",required = false,defaultValue= "")
			) throws UnsupportedEncodingException {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
	
		Long serviceTypeId = (long) 239;
		Users u = userService.selectByPrimaryKey(userId);
		
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		//如果城市不是北京市，则提示无法服务
		v = validateService.validateUserAddr(userId, addrId);
		
		
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
//		PartnerServiceType servicePrice = partnerServiceTypeService.selectByPrimaryKey(servicePriceId);
		PartnerServicePriceDetail servicePriceDetail = partnerServicePriceDetailService.selectByServicePriceId(servicePriceId);
		
		BigDecimal orderMoney = new BigDecimal(0.0);//原价
		BigDecimal orderPay = new BigDecimal(0.0);//折扣价
		
		BigDecimal disPrice = servicePriceDetail.getDisPrice();
		BigDecimal serviceNumDe = BigDecimal.valueOf(serviceNum.doubleValue());
		orderMoney = MathBigDeciamlUtil.mul(disPrice, serviceNumDe);
		orderPay = orderMoney;
				
		// 调用公共订单号类，生成唯一订单号
    	Orders order = null;
    	String orderNo = "";
    
    	orderNo = String.valueOf(OrderNoUtil.genOrderNo());
		order = ordersService.initOrders();
    	
		
		// 服务内容及备注信息需要进行urldecode;		
		if (!StringUtil.isEmpty(remarks)) {
			remarks = URLDecoder.decode(remarks,Constants.URL_ENCODE);
		}
		//保存订单信息
		order.setOrderNo(orderNo);
		order.setServiceTypeId(serviceTypeId);
		order.setUserId(userId);
		order.setMobile(u.getMobile());
		order.setOrderFrom(orderFrom);
		order.setServiceContent(serviceType.getName());

		order.setAddrId(addrId);
		if (!StringUtil.isEmpty(remarks)) {
			order.setRemarks(remarks);	
		}
		order.setOrderStatus(Constants.ORDER_STATUS_1_PAY_WAIT);
		ordersService.insert(order);
		Long orderId = order.getOrderId();
		
		//记录订单日志.
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLogService.insert(orderLog);
		
		//保存订单价格信息
		OrderPrices orderPrice = orderPricesService.initOrderPrices();
		

		orderPrice.setOrderId(orderId);
		orderPrice.setOrderNo(orderNo);
		orderPrice.setServicePriceId(servicePriceId);
		orderPrice.setUserId(userId);
		orderPrice.setMobile(u.getMobile());
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderPay);
		orderPricesService.insert(orderPrice);
		
		//保存绿植订单扩展表信息
		OrderExtWater water = orderExtWaterService.initOrderExtWater();
		water.setOrderId(orderId);
		water.setOrderNo(orderNo);
		water.setUserId(userId);
		water.setServicePriceId(servicePriceId);
		water.setServiceNum(serviceNum);
		water.setLinkMan(linkMan);
		water.setLinkTel(linkTel);

		orderExtWaterService.insert(water);
					
		OrderExtWaterListVo vo = orderExtWaterService.getListVo(water);
		result.setData(vo);
		
		//异步产生首页消息信息.
		userMsgAsyncService.newOrderMsg(userId, orderId, "water", "");
				
		return result;
	}
	
	@RequestMapping(value = "post_done_water", method = RequestMethod.POST)
	public AppResultData<Object> postDone(
			@RequestParam("user_id") Long userId,
			@RequestParam("order_id") Long orderId
			) {	
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		Users u = userService.selectByPrimaryKey(userId);
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null ) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		Orders order = ordersService.selectByPrimaryKey(orderId);
		Short orderStatus = order.getOrderStatus();
		if (orderStatus.equals(Constants.ORDER_STATUS_0_CANCEL) || 
			orderStatus.equals(Constants.ORDER_STATUS_1_PAY_WAIT) ||
			orderStatus.equals(Constants.ORDER_STATUS_2_PAY_DONE) ||
			orderStatus.equals(Constants.ORDER_STATUS_9_CLOSE) 
			) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("订单未处理，不能签收");
			return result;
		}
		
		order.setOrderStatus(Constants.ORDER_STATUS_7_RATE_WAIT);
		order.setUpdateTime(TimeStampUtil.getNowSecond());
		
		OrderExtWater orderExtWater = orderExtWaterService.selectByOrderId(orderId);
		
		orderExtWater.setOrderExtStatus((short) 2);
		orderExtWater.setIsDone((short) 1);
		orderExtWater.setIsDoneTime(TimeStampUtil.getNowSecond());
		
		//告知签收成功，产生消息通知
		userMsgAsyncService.newOrderMsg(userId, orderId, "water", "你的订单已经签收成功.");
		return result;
	}
	
}
