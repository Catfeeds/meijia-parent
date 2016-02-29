package com.simi.action.app.order;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.DateUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderExtGreen;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderExtGreenService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.order.OrderExtGreenListVo;
import com.simi.vo.order.OrderListVo;
import com.simi.vo.user.UserAddrVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderExtGreenController extends BaseController {
	
	@Autowired
	private UsersService userService;
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderExtGreenService orderExtGreenService;
	
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
	

	/**绿植订单列表接口
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_list_green", method = RequestMethod.GET)
	public AppResultData<Object> cancelOrder(
			@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		List<OrderExtGreenListVo> listVo = new ArrayList<OrderExtGreenListVo>();
		List<OrderExtGreen> list = orderExtGreenService.selectByUserId(userId);
		for (OrderExtGreen item : list) {
			OrderExtGreenListVo vo = orderExtGreenService.getOrderExtGreenListVo(item);
			listVo.add(vo);
		}
		result.setData(listVo);
		return result;
	}	
	
	@RequestMapping(value = "get_user_addr_list", method = RequestMethod.GET)
	public AppResultData<Object> getServiceTypeList(
			@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
	    List<UserAddrs> userAddrsList = userAddrsService.selectByUserId(userId);
	    List<UserAddrVo> voList = new ArrayList<UserAddrVo>();
	   for (int i = 0; i < userAddrsList.size(); i++) {
		    UserAddrs addrs = userAddrsList.get(i);
		    UserAddrVo vo = new UserAddrVo();
			vo.setAddrId(addrs.getId());
			vo.setAddrName(addrs.getName() + addrs.getAddr());
		   
		   voList.add(vo);
	}
	   
		result.setData(voList);

		return result;
	}

	/**
	 * 绿植订单下单接口
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
	@RequestMapping(value = "post_add_green", method = RequestMethod.POST)
	public AppResultData<Object> postGreen(
			@RequestParam("user_id") Long userId,
			@RequestParam("addr_id") Long addrId,
			@RequestParam("total_num") Long totalNum,
			@RequestParam("total_budget") BigDecimal totalBudget,
			@RequestParam(value = "remarks",required = false,defaultValue = "") String remarks
			//@RequestParam(value = "",required = false,defaultValue= "")
			) throws UnsupportedEncodingException {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
	
		Long serviceTypeId = (long) 238;
		Users u = userService.selectByPrimaryKey(userId);
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null ) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		List<UserAddrs> userAddrs = userAddrsService.selectByUserId(userId);
		//如果用户没有手机号，则需要更新用户手机号,并且判断是否唯一.
	/*	if (StringUtil.isEmpty(u.getMobile())) {
			Users existUser = userService.selectByMobile(mobile);
			if (!existUser.getId().equals(u.getId())) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("手机号在其他用户已经存在");
				return result;
			}
			u.setMobile(mobile);
			userService.updateByPrimaryKeySelective(u);
		}*/
		
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
		order.setMobile(u.getMobile());
		//order.setOrderType(servicePrice.getOrderType());
		//order.setOrderDuration(servicePrice.getOrderDuration());
		order.setServiceContent(serviceContent);
		//order.setServiceDate(serviceDate);
		order.setAddrId(addrId);
		if (!StringUtil.isEmpty(remarks)) {
			order.setRemarks(remarks);	
		}
		//order.setOrderFrom(orderFrom);
		order.setOrderStatus(Constants.ORDER_STATUS_1_PAY_WAIT);
		//order.setCityId(cityId);
		ordersService.insert(order);
		Long orderId = order.getOrderId();
		
		//记录订单日志.
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLogService.insert(orderLog);
		
		//保存订单价格信息
		OrderPrices orderPrice = orderPricesService.initOrderPrices();

		orderPrice.setOrderId(orderId);
		orderPrice.setOrderNo(orderNo);
	//	orderPrice.setServicePriceId(servicePriceId);
	//	orderPrice.setPartnerUserId(partnerUserId);
		orderPrice.setUserId(userId);
		orderPrice.setMobile(u.getMobile());
	//	orderPrice.setPayType(payType);
	//	orderPrice.setUserCouponId(userCouponId);
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderPay);
		orderPricesService.insert(orderPrice);
		
		//保存绿植订单扩展表信息
		OrderExtGreen green = orderExtGreenService.initOrderExtGreen();
		green.setOrderId(orderId);
		green.setOrderNo(orderNo);
		green.setUserId(userId);
		green.setMobile(u.getMobile());
		green.setTotalNum(totalNum);
		green.setTotalBudget(totalBudget);
		orderExtGreenService.insert(green);
		
		OrderExtGreenListVo vo = orderExtGreenService.getOrderExtGreenListVo(green);
		result.setData(vo);
		return result;
	}	
	
}
