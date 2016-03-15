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
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderExtClean;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.Users;
import com.simi.service.ValidateService;
import com.simi.service.async.NoticeSmsAsyncService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.order.OrderExtCleanService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPayService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UsersService;
import com.simi.utils.OrderUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderExtCleanListVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderExtCleanController extends BaseController {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderExtCleanService orderExtCleanService;
	
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
	
	@Autowired
	private NoticeSmsAsyncService noticeSmsAsyncService;
	

	/**保洁订单列表接口
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_list_clean", method = RequestMethod.GET)
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
		
		List<OrderExtCleanListVo> listVo = new ArrayList<OrderExtCleanListVo>();
		OrderSearchVo searchVo = new OrderSearchVo();
		searchVo.setUserId(userId);
		PageInfo plist = orderExtCleanService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<OrderExtClean> list = plist.getList();
		
		if (!list.isEmpty())
			listVo = orderExtCleanService.getListVos(list);
		
		result.setData(listVo);
		return result;
	}	
	
	/**保洁订单详情接口
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_detail_clean", method = RequestMethod.GET)
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
		
		OrderExtClean orderExtClean = orderExtCleanService.selectByOrderId(orderId);
		if (orderExtClean == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
			return result;
		}
		OrderExtCleanListVo vo = orderExtCleanService.getListVo(orderExtClean);
		result.setData(vo);
		return result;
	}		
	


	/**
	 * 保洁订单下单接口
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
	@RequestMapping(value = "post_add_clean", method = RequestMethod.POST)
	public AppResultData<Object> postGreen(
			@RequestParam("user_id") Long userId,
			@RequestParam("clean_type") Short cleanType,
			@RequestParam(value = "addr_id",required = false,defaultValue = "0") Long addrId,
			@RequestParam(value = "clean_area",required = false,defaultValue = "0") Short cleanArea,
			@RequestParam(value = "company_name",required = false,defaultValue = "") String companyName,
			@RequestParam(value = "link_man",required = false,defaultValue = "") String linkMan,
			@RequestParam(value = "link_tel",required = false,defaultValue = "") String linkTel,
			@RequestParam(value = "remarks",required = false,defaultValue = "") String remarks,
			@RequestParam(value = "order_from", required = false, defaultValue = "0") Short orderFrom
			//@RequestParam(value = "",required = false,defaultValue= "")
			) throws UnsupportedEncodingException {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
	
		Long serviceTypeId = 204L;
		Users u = userService.selectByPrimaryKey(userId);
		
		// 判断是否为注册用户，非注册用户返回 999		
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		//如果城市不是北京市，则提示无法服务
		if (addrId.equals(0L)) {
			v = validateService.validateOrderCity(userId);
			if (v.getStatus() == Constants.ERROR_999) {
				return v;
			}
		} else {
			v = validateService.validateUserAddr(userId, addrId);
			if (v.getStatus() == Constants.ERROR_999) {
				return v;
			}
		}
		
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);

		BigDecimal orderMoney = new BigDecimal(0.0);//原价
		BigDecimal orderPay = new BigDecimal(0.0);//折扣价
						
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
		order.setOrderStatus(Constants.ORDER_STATUS_3_PROCESSING);
		ordersService.insert(order);
		Long orderId = order.getOrderId();
		
		//记录订单日志.
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLogService.insert(orderLog);
		
		//保存订单价格信息
		OrderPrices orderPrice = orderPricesService.initOrderPrices();
		
		orderPrice.setOrderId(orderId);
		orderPrice.setOrderNo(orderNo);
		orderPrice.setUserId(userId);
		orderPrice.setMobile(u.getMobile());
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderPay);
		orderPricesService.insert(orderPrice);
		
		//保存保洁订单扩展表信息
		OrderExtClean clean = orderExtCleanService.initOrderExtClean();
		clean.setOrderId(orderId);
		clean.setOrderNo(orderNo);
		clean.setUserId(userId);
		clean.setCompanyName(companyName);
		clean.setCleanArea(cleanArea);
		clean.setCleanType(cleanType);
		clean.setLinkMan(linkMan);
		clean.setLinkTel(linkTel);

		orderExtCleanService.insert(clean);
		
		//通知运营人员，订单支付成功
		noticeSmsAsyncService.noticeOrderOper(orderId);
		
		OrderExtCleanListVo vo = orderExtCleanService.getListVo(clean);
		result.setData(vo);
		
		//异步产生首页消息信息.
		String title = serviceType.getName();
		String summary =  OrderUtil.getOrderStausMsg(order.getOrderStatus());
		userMsgAsyncService.newActionAppMsg(userId, orderId, "clean", title, summary);
				
		
		return result;
	}	
}
