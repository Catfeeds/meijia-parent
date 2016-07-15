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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.admin.AdminController;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.order.OrderExtClean;
import com.simi.po.model.order.OrderExtPartner;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.service.async.NoticeSmsAsyncService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.order.OrderExtCleanService;
import com.simi.service.order.OrderExtPartnerService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerRefServiceTypeService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.utils.OrderUtil;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.OrdersListVo;
import com.simi.vo.order.OrderCleanOaVo;
import com.simi.vo.order.OrdersCleanAddOaVo;
import com.simi.vo.partners.PartnersSearchVo;
import com.simi.vo.user.UserAddrVo;

@Controller
@RequestMapping(value = "/order")
public class OrderCleanController extends AdminController {

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
	private PartnersService partnersService;

	@Autowired
	private OrderExtPartnerService orderExtPartnerService;

	@Autowired
	private OrderExtCleanService orderExtCleanService;

	@Autowired
	private PartnerRefServiceTypeService partnerRefServiceTypeService;
	
	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	@Autowired
	private NoticeSmsAsyncService noticeSmsAsyncService;
	
	//serviceTypeId = 204L
	
	private Long serviceTypeId = 204L;
	
	/**
	 * 保洁订单列表
	 * 
	 * @param request
	 * @param model
	 * @param searchVo
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@AuthPassport
	@RequestMapping(value = "/cleanList", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, OrderSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		searchVo.setServiceTypeId(serviceTypeId);

		PageHelper.startPage(pageNo, pageSize);

		List<Orders> orderList = orderQueryService.selectByListPageList(searchVo, pageNo, pageSize);

		Orders orders = null;
		for (int i = 0; i < orderList.size(); i++) {
			orders = orderList.get(i);
			OrdersListVo ordersListVo = orderQueryService.completeVo(orders);

			OrderCleanOaVo vo = new OrderCleanOaVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(ordersListVo, vo);

			OrderExtClean clean = orderExtCleanService.selectByOrderId(vo.getOrderId());
			if (clean != null) {
				BeanUtilsExp.copyPropertiesIgnoreNull(clean, vo);
			}
			
			if (vo.getAddrId() > 0L) {
				Long addrId = vo.getAddrId();
				UserAddrs userAddr =	userAddrsService.selectByPrimaryKey(addrId);
				vo.setAddr(userAddr.getName() + " " + userAddr.getAddr());
			}

			orderList.set(i, vo);
		}
		PageInfo result = new PageInfo(orderList);

		model.addAttribute("contentModel", result);
		model.addAttribute("oaOrderSearchVoModel", searchVo);
		return "order/orderCleanList";
	}
	
	/**
	 * 订单详情
	 * 
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/orderCleanForm", method = RequestMethod.GET)
	public String orderDetail(Long orderId, Model model) {
				
		Orders orders = ordersService.selectByPrimaryKey(orderId);
		String orderNo = orders.getOrderNo();
		OrderCleanOaVo vo = new OrderCleanOaVo();
		// OrdersGreenPartnerVo vo = new OrdersGreenPartnerVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(orders, vo);
		
		vo.setOrderMoney(new BigDecimal(0));
		vo.setOrderPay(new BigDecimal(0));
		OrderPrices orderPrice = orderPricesService.selectByOrderId(orderId);
		if (orderPrice != null) {
			vo.setOrderMoney(orderPrice.getOrderMoney());
			vo.setOrderPay(orderPrice.getOrderPay());
		}
		
		
		OrderExtPartner orderExtPartner = orderExtPartnerService.selectByOrderId(orderId);
		if (orderExtPartner == null) {
			vo.setPartnerOrderNo("");
			vo.setPartnerOrderMoney(new BigDecimal(0));
			vo.setPartnerId(0L);
			vo.setRemarks("");
		} else {
			vo.setPartnerOrderNo(orderExtPartner.getPartnerOrderNo());
			vo.setPartnerOrderMoney(orderExtPartner.getPartnerOrderMoney());
			vo.setPartnerId(orderExtPartner.getPartnerId());
			vo.setRemarks(orderExtPartner.getRemarks());
		}

		OrderExtClean clean = orderExtCleanService.selectByOrderId(vo.getOrderId());

		vo.setServicePriceId(serviceTypeId);
		vo.setCompanyName(clean.getCompanyName());
		vo.setCleanArea(clean.getCleanArea());
		vo.setCleanType(clean.getCleanType());
		vo.setLinkMan(clean.getLinkMan());
		vo.setLinkTel(clean.getLinkTel());
		vo.setOrderExtStatus(clean.getOrderExtStatus());

		model.addAttribute("contentModel", vo);
		
		Long addTime = orders.getAddTime();
		String addTimeStr = TimeStampUtil.timeStampToDateStr(addTime * 1000, "yyyy-MM-dd HH:mm:ss");
		model.addAttribute("addTimeStr", addTimeStr);
		
		//用户信息
		Users user = usersService.selectByPrimaryKey(clean.getUserId());
		model.addAttribute("user", user);
				
		// 用户地址列表
		List<UserAddrs> userAddrsList = userAddrsService.selectByUserId(orders.getUserId());
		List<UserAddrVo> voList = new ArrayList<UserAddrVo>();
		for (int i = 0; i < userAddrsList.size(); i++) {
			UserAddrs addrs = userAddrsList.get(i);
			UserAddrVo vos = new UserAddrVo();
			vos.setAddrId(addrs.getId());
			vos.setAddrName(addrs.getAddress() + addrs.getAddr());
			voList.add(vos);
		}
		model.addAttribute("userAddrVo", voList);
		
		//服务商信息
		// 服务商列表
		PartnersSearchVo searchVo = new PartnersSearchVo();
		searchVo.setServiceTypeId(serviceTypeId);
		List<PartnerRefServiceType> partnerRefServiceType = partnerRefServiceTypeService.selectBySearchVo(searchVo);
		List<Partners> partnerList = new ArrayList<Partners>();
		for (int i = 0; i < partnerRefServiceType.size(); i++) {
			Long partnerId = partnerRefServiceType.get(i).getPartnerId();
			Partners partners = partnersService.selectByPrimaryKey(partnerId);
			partnerList.add(partners);
		}
		model.addAttribute("partnerList", partnerList);
		
		//订单服务商信息
		
		if (orderExtPartner == null) {
			orderExtPartner = orderExtPartnerService.initOrderExtPartner();
			orderExtPartner.setOrderId(orderId);
			orderExtPartner.setOrderNo(orderNo);
		}
		model.addAttribute("orderExtPartner", orderExtPartner);
		
		return "order/orderCleanForm";
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
	@AuthPassport
	@RequestMapping(value = "/saveOrderClean", method = { RequestMethod.POST })
	public String adForm(Model model,
	@ModelAttribute("contentModel") OrderCleanOaVo vo, BindingResult result, HttpServletRequest request) throws IOException {

		Long orderId = vo.getOrderId();
		Orders order = ordersService.selectByPrimaryKey(orderId);
		if (order == null) return "redirect:/order/cleanList";
		
		//更新订单基础信息
		order.setAddrId(vo.getAddrId());
		order.setOrderStatus(vo.getOrderStatus());
		order.setRemarks(vo.getRemarks());
		ordersService.updateByPrimaryKeySelective(order);
		
		//更新价格信息
		OrderPrices orderPrice = orderPricesService.selectByOrderId(orderId);
		orderPrice.setOrderMoney(vo.getOrderMoney());
		orderPrice.setOrderPay(vo.getOrderPay());
		orderPricesService.updateByPrimaryKeySelective(orderPrice);
			
		// 更新订单扩展保洁表
		OrderExtClean clean = orderExtCleanService.selectByOrderId(orderId);
		clean.setCompanyName(vo.getCompanyName());
		clean.setCleanArea(vo.getCleanArea());
		clean.setCleanType(vo.getCleanType());
		clean.setLinkMan(vo.getLinkMan());
		clean.setLinkTel(vo.getLinkTel());
		orderExtCleanService.updateByPrimaryKeySelective(clean);
		
		return "redirect:/order/cleanList";
	}	
	
	@AuthPassport
	@RequestMapping(value = "/saveOrderCleanPartner", method = { RequestMethod.POST })
	public String saveOrderCleanPartner(Model model,
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
			

			//异步产生首页消息信息.
			PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(order.getServiceTypeId());
			String title = serviceType.getName();
			String summary =  OrderUtil.getOrderStausMsg(order.getOrderStatus());
			userMsgAsyncService.newActionAppMsg(userId, orderId, "clean", title, summary, "");
			
		} else {
			vo.setUpdateTime(TimeStampUtil.getNowSecond());
			orderExtPartnerService.updateByPrimaryKeySelective(vo);
			
			String orderExtStatusStr = request.getParameter("orderExtStatus");
			orderExtStatus = Short.valueOf(orderExtStatusStr);
	
		}
		
		OrderExtClean clean = orderExtCleanService.selectByOrderId(orderId);
		clean.setOrderExtStatus(orderExtStatus);
		orderExtCleanService.updateByPrimaryKeySelective(clean);
		
		
		return "redirect:/order/cleanList";
	}	



	/**
	 * 后台保洁订单表单
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/orderCleanAddForm", method = RequestMethod.GET)
	public String orderTeamAdd(Long id, Model model) {
		Long serviceTypeId = 204L;
		OrdersCleanAddOaVo vo = new OrdersCleanAddOaVo();
		OrderExtClean clean = orderExtCleanService.initOrderExtClean();
		BeanUtilsExp.copyPropertiesIgnoreNull(clean, vo);
		model.addAttribute("contentModel", vo);

		return "order/orderCleanAddForm";
	}

	/**
	 * 后台保洁订单保存
	 * 
	 * @param model
	 * @param vo
	 * @param result
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@AuthPassport
	@RequestMapping(value = "/saveOrderCleanAdd", method = RequestMethod.POST)
	public String orderTeamSave(Model model,
			@ModelAttribute("contentModel") OrdersCleanAddOaVo vo,
			BindingResult result, HttpServletRequest request)
			throws UnsupportedEncodingException {

		Long serviceTypeId = 204L;
		Users u = usersService.selectByMobile(vo.getMobile());
		
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);

		BigDecimal orderMoney = new BigDecimal(0.0);//原价
		BigDecimal orderPay = new BigDecimal(0.0);//折扣价
						
		// 调用公共订单号类，生成唯一订单号
    	Orders order = null;
    	String orderNo = "";
    
    	orderNo = String.valueOf(OrderNoUtil.genOrderNo());
		order = ordersService.initOrders();
    	
		String remarks = vo.getRemarks();
		// 服务内容及备注信息需要进行urldecode;		
		if (!StringUtil.isEmpty(remarks)) {
			remarks = URLDecoder.decode(remarks,Constants.URL_ENCODE);
		}
		//保存订单信息
		order.setOrderNo(orderNo);
		order.setServiceTypeId(serviceTypeId);
		order.setUserId(u.getId());
		order.setMobile(u.getMobile());
		order.setServiceContent(serviceType.getName());
		order.setAddrId(vo.getAddrId());
		if (!StringUtil.isEmpty(remarks)) {
			order.setRemarks(remarks);	
		}
		order.setOrderStatus(Constants.ORDER_STATUS_3_PROCESSING);
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
		orderPrice.setUserId(u.getId());
		orderPrice.setMobile(u.getMobile());
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderPay);
		orderPricesService.insert(orderPrice);
		
		//保存保洁订单扩展表信息
		OrderExtClean clean = orderExtCleanService.initOrderExtClean();
		clean.setOrderId(orderId);
		clean.setOrderNo(orderNo);
		clean.setUserId(u.getId());
		if (vo.getCompanyName()!=null) {
		clean.setCompanyName(vo.getCompanyName());
		}
		if (vo.getCleanArea() != null) {
			clean.setCleanArea(vo.getCleanArea());
		}
		clean.setCleanType(vo.getCleanType());
		clean.setLinkMan(vo.getLinkMan());
		clean.setLinkTel(vo.getLinkTel());

		orderExtCleanService.insert(clean);
		
		//通知运营人员，订单支付成功
		noticeSmsAsyncService.noticeOrderOper(orderId);
		
		//异步产生首页消息信息.
		
		String title = serviceType.getName();
		String summary =  OrderUtil.getOrderStausMsg(order.getOrderStatus());
		userMsgAsyncService.newActionAppMsg(u.getId(), orderId, "clean", title, summary, "");

		return "redirect:/order/cleanList";
	}
}
