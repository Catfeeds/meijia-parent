package com.simi.action.order;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.vo.AppResultData;
import com.simi.action.admin.AdminController;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.order.OrderExtRecycle;
import com.simi.po.model.order.OrderExtPartner;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.service.ValidateService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.order.OrderExtGreenService;
import com.simi.service.order.OrderExtPartnerService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerRefServiceTypeService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.OrdersGreenPartnerVo;
import com.simi.vo.OrdersListVo;
import com.simi.vo.order.OrdersRecycleAddOaVo;
import com.simi.vo.user.UserAddrVo;

@Controller
@RequestMapping(value = "/order")
public class OrderGreenController extends AdminController {

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	private OrderQueryService orderQueryService;

	@Autowired
	private OrderPricesService orderPricesService;

	@Autowired
	private OrderLogService orderLogService;

	@Autowired
	private PartnerRefServiceTypeService partnerRefServiceTypeService;

	@Autowired
	private PartnersService partnersService;

	@Autowired
	private OrderExtPartnerService orderExtPartnerService;

	@Autowired
	private OrderExtGreenService orderExtGreenService;

	@Autowired
	private PartnerUserService partnerUserService;

	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	@Autowired
	private ValidateService validateService;
	/**
	 * 废品回收订单列表
	 * 
	 * @param request
	 * @param model
	 * @param searchVo
	 * @param userId
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/greenList", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model,
			OrderSearchVo searchVo,
			@RequestParam(value = "user_id", required = false) Long userId) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		searchVo.setUserId(userId);
		// 绿植服务大类ID
		searchVo.setServiceTypeId(246L);

		// 分页
		PageHelper.startPage(pageNo, pageSize);

		List<Orders> orderList = orderQueryService.selectByListPageList(
				searchVo, pageNo, pageSize);

		Orders orders = null;
		for (int i = 0; i < orderList.size(); i++) {
			orders = orderList.get(i);
			OrdersListVo ordersListVo = orderQueryService.completeVo(orders);

			OrdersGreenPartnerVo vo = new OrdersGreenPartnerVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(ordersListVo, vo);
			OrderExtRecycle green = orderExtGreenService.selectByOrderId(vo
					.getOrderId());
			vo.setPartnerOrderNo("");
			vo.setPartnerOrderMoney(new BigDecimal(0));
			vo.setPartnerId(0L);
			vo.setOrderExtStatus((short) 0);
			vo.setRecycleType((short) 0);

			vo.setLinkMan(green.getLinkMan());
			vo.setLinkTel(green.getLinkTel());
			orderList.set(i, vo);
		}
		PageInfo result = new PageInfo(orderList);

		model.addAttribute("contentModel", result);
		model.addAttribute("oaOrderSearchVoModel", searchVo);
		return "order/orderGreenList";
	}

	/**
	 * 订单详情
	 * 
	 * @param orderNo
	 * @param model
	 * @return
	 */
	// @AuthPassport

	@RequestMapping(value = "/orderGreenView", method = RequestMethod.GET)
	public String orderDetail(String orderNo, Model model) {

		Long serviceTypeId = 246L;
		Orders orders = ordersService.selectByOrderNo(orderNo);

		OrdersListVo listvo = orderQueryService.completeVo(orders);
		OrdersGreenPartnerVo vo = new OrdersGreenPartnerVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(listvo, vo);
		OrderExtRecycle recycle = orderExtGreenService.selectByOrderId(vo
				.getOrderId());
		OrderExtPartner orderExtPartner = orderExtPartnerService
				.selectByOrderId(orders.getOrderId());
		
		if (orderExtPartner == null) {
			vo.setPartnerOrderNo("");
			vo.setPartnerOrderMoney(new BigDecimal(0));
			vo.setPartnerId(0L);
		} else {
			vo.setPartnerOrderNo(orderExtPartner.getPartnerOrderNo());
			vo.setPartnerOrderMoney(orderExtPartner.getPartnerOrderMoney());
			vo.setPartnerId(orderExtPartner.getPartnerId());
		}

		vo.setServiceDate(TimeStampUtil.getNowSecond());
		vo.setOrderExtStatus(recycle.getOrderExtStatus());
		vo.setRecycleType(recycle.getRecycleType());
		vo.setLinkMan(recycle.getLinkMan());
		vo.setLinkTel(recycle.getLinkTel());
		model.addAttribute("contentModel", vo);

		Long addTime = orders.getAddTime();
		String addTimeStr = TimeStampUtil.timeStampToDateStr(addTime * 1000,
				"yyyy-MM-dd HH:mm:ss");
		model.addAttribute("addTimeStr", addTimeStr);
		
		//服务时间
		Long serviceDate1 = orders.getServiceDate();
		String serviceDate1Str = TimeStampUtil.timeStampToDateStr(serviceDate1 * 1000,
				"yyyy-MM-dd");
		model.addAttribute("serviceDate1", serviceDate1Str);

		// 用户信息
		Users user = usersService.selectByPrimaryKey(recycle.getUserId());
		model.addAttribute("user", user);

		// 用户地址列表
		List<UserAddrs> userAddrsList = userAddrsService.selectByUserId(orders
				.getUserId());
		List<UserAddrVo> voList = new ArrayList<UserAddrVo>();
		for (int i = 0; i < userAddrsList.size(); i++) {
			UserAddrs addrs = userAddrsList.get(i);
			UserAddrVo vos = new UserAddrVo();
			vos.setAddrId(addrs.getId());
			vos.setAddrName(addrs.getAddress() + addrs.getAddr());
			voList.add(vos);
		}
		model.addAttribute("userAddrVo", voList);

		// 服务商信息
		// 服务商列表

		List<PartnerRefServiceType> partnerRefServiceType = partnerRefServiceTypeService
				.selectByServiceTypeId(serviceTypeId);
		List<Partners> partnerList = new ArrayList<Partners>();
		for (int i = 0; i < partnerRefServiceType.size(); i++) {
			Long partnerId = partnerRefServiceType.get(i).getPartnerId();
			Partners partners = partnersService.selectByPrimaryKey(partnerId);
			partnerList.add(partners);
		}
		model.addAttribute("partnerList", partnerList);

		// 订单服务商信息
		if (orderExtPartner == null) {
			orderExtPartner = orderExtPartnerService.initOrderExtPartner();
			orderExtPartner.setOrderId(orders.getOrderId());
			orderExtPartner.setOrderNo(orderNo);
		}
		model.addAttribute("orderExtPartner", orderExtPartner);

		return "order/orderGreenViewForm";
	}

	/**
	 * 订单保存修改
	 * 
	 * @param model
	 * @param orders
	 * @param result
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveOrderGreen", method = { RequestMethod.POST })
	public String adForm(Model model,
			@ModelAttribute("contentModel") OrdersGreenPartnerVo vo,
			BindingResult result, HttpServletRequest request)
			throws IOException {

		String serviceDate1 = request.getParameter("serviceDate");	
		Orders order = ordersService.selectByOrderNo(vo.getOrderNo());
		if (order == null)
			return "redirect:/order/greenList";
		
		Long orderId = order.getOrderId();
		//更新订单基础信息
		order.setAddrId(vo.getAddrId());
		order.setOrderStatus(vo.getOrderStatus());
		order.setRemarks(vo.getRemarks());
		order.setServiceDate(TimeStampUtil.getMillisOfDay(serviceDate1)/1000);
		ordersService.updateByPrimaryKeySelective(order);
		
		//更新价格信息
		OrderPrices orderPrice = orderPricesService.selectByOrderId(orderId);
		orderPrice.setOrderMoney(vo.getOrderMoney());
		orderPrice.setOrderPay(vo.getOrderPay());
		orderPricesService.updateByPrimaryKeySelective(orderPrice);

		// 更新绿植表
		OrderExtRecycle recycle = orderExtGreenService.selectByOrderId(orderId);

		recycle.setRecycleType(vo.getRecycleType());
		recycle.setLinkMan(vo.getLinkMan());
		recycle.setLinkTel(vo.getLinkTel());
		recycle.setAddTime(TimeStampUtil.getNowSecond());
		orderExtGreenService.updateByPrimaryKeySelective(recycle);

		return "redirect:/order/greenList";
	}
	@RequestMapping(value = "/saveOrderGreenPartner", method = { RequestMethod.POST })
	public String saveOrderWaterPartner(Model model,
	@ModelAttribute("orderExtPartner") OrderExtPartner vo, 
	BindingResult result, HttpServletRequest request) throws IOException {
		
		Long orderId = vo.getOrderId();
		Orders order = ordersService.selectByPrimaryKey(orderId);
		Long id = vo.getId();
		Long userId = order.getUserId();
		
		Short orderExtStatus = 1;
		if (id.equals(0L)) {
			vo.setAddTime(TimeStampUtil.getNowSecond());
			vo.setUpdateTime(TimeStampUtil.getNowSecond());
			
			orderExtPartnerService.insert(vo);
			//订单状态改完处理中
			order.setOrderStatus(Constants.ORDER_STATUS_3_PROCESSING);
			order.setUpdateTime(TimeStampUtil.getNowSecond());
			ordersService.updateByPrimaryKey(order);
			
			userMsgAsyncService.newOrderMsg(userId, orderId, "water", "");
			
		} else {
			vo.setUpdateTime(TimeStampUtil.getNowSecond());
			orderExtPartnerService.updateByPrimaryKeySelective(vo);
			
			String orderExtStatusStr = request.getParameter("orderExtStatus");
			orderExtStatus = Short.valueOf(orderExtStatusStr);
	
		}
		OrderExtRecycle recycle = orderExtGreenService.selectByOrderId(orderId);
		recycle.setOrderExtStatus(orderExtStatus);
		orderExtGreenService.updateByPrimaryKeySelective(recycle);
		
		return "redirect:/order/greenList";
	}
	/**
	 * 废品回收后台下单表单
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/orderGreenAddForm", method = RequestMethod.GET)
	public String orderGreenAdd(Long id, Model model) {
		OrdersRecycleAddOaVo vo = new OrdersRecycleAddOaVo();
		OrderExtRecycle recycle = orderExtGreenService.initOrderExtGreen();
		BeanUtilsExp.copyPropertiesIgnoreNull(recycle, vo);
		vo.setRemarks("");
		vo.setServiceDate(TimeStampUtil.getNowSecond());
		String serviceDate1Str = TimeStampUtil.timeStampToDateStr(TimeStampUtil.getNowSecond() * 1000,
				"yyyy-MM-dd");
		
		model.addAttribute("serviceDate1", serviceDate1Str);
		
		model.addAttribute("contentModel", vo);

		return "order/orderGreenAddForm";
	}
	/**
	 * 废品回收后台订单保存
	 * @param model
	 * @param vo
	 * @param result
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/saveOrderGreenAdd", method = RequestMethod.POST)
	public String orderTeamSave(Model model,
			@ModelAttribute("contentModel") OrdersRecycleAddOaVo vo, 
			BindingResult result, HttpServletRequest request) throws UnsupportedEncodingException {
		
		Long serviceTypeId = (long) 246;
		Users users = usersService.selectByMobile(vo.getMobile());
		
		List<UserAddrs> userAddrs = userAddrsService.selectByUserId(users.getId());
	
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
		String remarks = vo.getRemarks();
		if (!StringUtil.isEmpty(remarks)) {
			remarks = URLDecoder.decode(remarks,Constants.URL_ENCODE);
		}
		//保存订单信息
		order.setOrderNo(orderNo);
		order.setServiceTypeId(serviceTypeId);
		order.setUserId(users.getId());
		order.setMobile(vo.getMobile());
		order.setServiceContent(serviceContent);
		order.setAddrId(vo.getAddrId());
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
		orderPrice.setUserId(users.getId());
		orderPrice.setMobile(users.getMobile());
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderPay);
		orderPricesService.insert(orderPrice);
		
		//保存废品回收订单扩展表信息
		OrderExtRecycle green = orderExtGreenService.initOrderExtGreen();
		green.setOrderId(orderId);
		green.setOrderNo(orderNo);
		green.setUserId(users.getId());
		green.setMobile(users.getMobile());
		green.setLinkMan(vo.getLinkMan());
		green.setLinkTel(vo.getLinkTel());
		green.setRecycleType(vo.getRecycleType());
		orderExtGreenService.insert(green);
		
		//异步产生首页消息信息.
		userMsgAsyncService.newOrderMsg(users.getId(), orderId, "recycle", "");
		
		return "redirect:/order/greenList";
	}
}
