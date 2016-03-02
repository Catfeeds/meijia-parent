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

import com.meijia.utils.MathBigDeciamlUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderExtGreen;
import com.simi.po.model.order.OrderExtWater;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderExtGreenService;
import com.simi.service.order.OrderExtWaterService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.order.OrderExtGreenListVo;
import com.simi.vo.order.OrderExtWaterListVo;
import com.simi.vo.user.UserAddrVo;

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
	

	/**送水订单列表接口
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_list_water", method = RequestMethod.GET)
	public AppResultData<Object> list(
			@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		Users u = userService.selectByPrimaryKey(userId);
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null ) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		List<OrderExtWaterListVo> listVo = new ArrayList<OrderExtWaterListVo>();
		List<OrderExtWater> list = orderExtWaterService.selectByUserId(userId);
		
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
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null ) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		//如果城市不是北京市，则提示无法服务
		UserAddrs userAddr = userAddrsService.selectByPrimaryKey(addrId);
		if (userAddr == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("目前仅支持北京市区服务范围，敬请谅解！");
			return result;	
		}
		
		String cityName = userAddr.getCity();
		if (!cityName.equals("北京市")) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("目前仅支持北京市区服务范围，敬请谅解！");
			return result;			
		}
		
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
		PartnerServiceType servicePrice = partnerServiceTypeService.selectByPrimaryKey(servicePriceId);
		PartnerServicePriceDetail servicePriceDetail = partnerServicePriceDetailService.selectByServicePriceId(servicePriceId);
		
		BigDecimal orderMoney = new BigDecimal(0.0);//原价
		BigDecimal orderPay = new BigDecimal(0.0);//折扣价
		
		BigDecimal disPrice = servicePriceDetail.getDisPrice();
		BigDecimal serviceNumDe = BigDecimal.valueOf(serviceNum.doubleValue());
		orderMoney = MathBigDeciamlUtil.mul(disPrice, serviceNumDe);
		orderPay = orderMoney;
		
//		if (payType.equals(Constants.PAY_TYPE_0)) {
//			//1.先判断用户余额是否够支付
//			if(u.getRestMoney().compareTo(orderPay) < 0) {
//				result.setStatus(Constants.ERROR_999);
//				result.setMsg(ConstantMsg.ERROR_999_MSG_5);
//				return result;
//			}
//		}
		
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
		//order.setPartnerUserId(partnerUserId);
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
	//	orderPrice.setPayType(payType);
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
		
		
//		if (payType.equals(Constants.PAY_TYPE_0)) {
//			// 1. 扣除用户余额.
//			// 2. 用户账号明细增加.
//			// 3. 订单状态变为已支付.
//			// 4. 订单日志
//			
//			u.setRestMoney(u.getRestMoney().subtract(orderPay));
//			u.setUpdateTime(TimeStampUtil.getNowSecond());
//			userService.updateByPrimaryKeySelective(u);
//			
//			order.setOrderStatus(Constants.ORDER_STATUS_2_PAY_DONE);//已支付
//			order.setUpdateTime(TimeStampUtil.getNowSecond());
//			ordersService.updateByPrimaryKeySelective(order);
//			
//			//记录订单日志.
//			orderLog = orderLogService.initOrderLog(order);
//			orderLogService.insert(orderLog);
//			
//			//记录用户消费明细
//			userDetailPayService.userDetailPayForOrder(u, order, orderPrice, "", "", "");
//			
//			//订单支付成功后
//			orderPayService.orderPaySuccessToDo(order);
//		}
			
		OrderExtWaterListVo vo = orderExtWaterService.getListVo(water);
		result.setData(vo);
				
		return result;
	}
	
}
