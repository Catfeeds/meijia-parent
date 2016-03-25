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
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.admin.AdminController;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.order.OrderExtPartner;
import com.simi.po.model.order.OrderExtWater;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.order.OrderExtRecycleService;
import com.simi.service.order.OrderExtPartnerService;
import com.simi.service.order.OrderExtWaterService;
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
import com.simi.utils.OrderUtil;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.OrdersListVo;
import com.simi.vo.order.OrderWaterComVo;
import com.simi.vo.order.OrdersWaterAddOaVo;
import com.simi.vo.order.OrdersWaterListVo;
import com.simi.vo.partners.PartnerUserSearchVo;
import com.simi.vo.user.UserAddrVo;

@Controller
@RequestMapping(value = "/order")
public class OrderWaterController extends AdminController {

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
	private OrderExtWaterService orderExtWaterService;

	@Autowired
	private PartnerUserService partnerUserService;

	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private UserMsgAsyncService userMsgAsyncService;

	@Autowired
	private OrderExtRecycleService orderExtGreenService;

	/**
	 * 送水订单列表
	 * 
	 * @param request
	 * @param model
	 * @param searchVo
	 * @param userId
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/waterList", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, OrderSearchVo searchVo, @RequestParam(value = "user_id", required = false) Long userId) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		searchVo.setUserId(userId);
		// 绿植服务大类ID
		searchVo.setServiceTypeId(239L);

		// 分页
		PageHelper.startPage(pageNo, pageSize);

		List<Orders> orderList = orderQueryService.selectByListPageList(searchVo, pageNo, pageSize);

		Orders orders = null;
		for (int i = 0; i < orderList.size(); i++) {
			orders = orderList.get(i);
			OrdersListVo ordersListVo = orderQueryService.completeVo(orders);

			OrdersWaterListVo vo = new OrdersWaterListVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(ordersListVo, vo);

			OrderExtWater water = orderExtWaterService.selectByOrderId(vo.getOrderId());
			if (water != null) {
				Long servicePriceId = water.getServicePriceId();
				PartnerServiceType servicePrice = partnerServiceTypeService.selectByPrimaryKey(servicePriceId);
				PartnerServicePriceDetail servicePriceDetail = partnerServicePriceDetailService.selectByServicePriceId(servicePriceId);
				
				vo.setServicePriceId(servicePriceId);
				vo.setServicePriceName(servicePrice.getName());
				vo.setServicePriceImg(servicePriceDetail.getImgUrl());
				vo.setServiceNum(water.getServiceNum());
				vo.setLinkMan(water.getLinkMan());
				vo.setLinkTel(water.getLinkTel());
			}

			Long addrId = vo.getAddrId();
			UserAddrs userAddr = userAddrsService.selectByPrimaryKey(addrId);
			vo.setAddr(userAddr.getName() + " " + userAddr.getAddr());

			orderList.set(i, vo);
		}
		PageInfo result = new PageInfo(orderList);

		model.addAttribute("contentModel", result);
		model.addAttribute("oaOrderSearchVoModel", searchVo);
		return "order/orderWaterList";
	}

	/**
	 * 订单详情
	 * 
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/orderWaterForm", method = RequestMethod.GET)
	public String orderDetail(String orderNo, Model model) {

		Long serviceTypeId = 239L;

		Orders orders = ordersService.selectByOrderNo(orderNo);
		Long orderId = orders.getOrderId();

		OrdersListVo listvo = orderQueryService.completeVo(orders);
		OrdersWaterListVo vo = new OrdersWaterListVo();
		// OrdersGreenPartnerVo vo = new OrdersGreenPartnerVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(listvo, vo);
		OrderExtPartner orderExtPartner = orderExtPartnerService.selectByOrderId(orders.getOrderId());
		if (orderExtPartner == null) {
			vo.setPartnerOrderNo("");
			vo.setPartnerOrderMoney(new BigDecimal(0));
			vo.setPartnerId(0L);
		} else {
			vo.setPartnerOrderNo(orderExtPartner.getPartnerOrderNo());
			vo.setPartnerOrderMoney(orderExtPartner.getPartnerOrderMoney());
			vo.setPartnerId(orderExtPartner.getPartnerId());
		}

		OrderExtWater water = orderExtWaterService.selectByOrderId(vo.getOrderId());

		vo.setServicePriceId(water.getServicePriceId());
		vo.setServiceNum(water.getServiceNum());
		vo.setLinkMan(water.getLinkMan());
		vo.setLinkTel(water.getLinkTel());
		vo.setOrderExtStatus(water.getOrderExtStatus());

		model.addAttribute("contentModel", vo);

		Long addTime = orders.getAddTime();
		String addTimeStr = TimeStampUtil.timeStampToDateStr(addTime * 1000, "yyyy-MM-dd HH:mm:ss");
		model.addAttribute("addTimeStr", addTimeStr);

		// 用户信息
		Users user = usersService.selectByPrimaryKey(water.getUserId());
		model.addAttribute("user", user);

		// 商品名称
		Long servicePriceId = water.getServicePriceId();
		PartnerServiceType servicePrice = partnerServiceTypeService.selectByPrimaryKey(servicePriceId);
		PartnerServicePriceDetail servicePriceDetail = partnerServicePriceDetailService.selectByServicePriceId(servicePriceId);
		model.addAttribute("servicePrice", servicePrice);
		model.addAttribute("servicePriceDetail", servicePriceDetail);

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

		// 获得商品列表的选择下拉列表
		// 先根据服务大类找到相应的推荐人员.
		PartnerUserSearchVo searchVo = new PartnerUserSearchVo();
		searchVo.setServiceTypeId(serviceTypeId);
		searchVo.setWeightType((short) 1);

		List<PartnerUsers> list = partnerUserService.selectBySearchVo(searchVo);

		PartnerUsers partnerUser = list.get(0);

		Long parnterUserId = partnerUser.getUserId();

		List<PartnerServicePriceDetail> servicePriceDetails = partnerServicePriceDetailService.selectByUserId(parnterUserId);

		List<Long> servicePriceIds = new ArrayList<Long>();
		for (PartnerServicePriceDetail item : servicePriceDetails) {
			if (!servicePriceIds.contains(item.getServicePriceId())) {
				servicePriceIds.add(item.getServicePriceId());
			}
		}
		List<PartnerServiceType> serviceTypes = partnerServiceTypeService.selectByIds(servicePriceIds);
		List<OrderWaterComVo> waterComVos = new ArrayList<OrderWaterComVo>();
		PartnerServiceType serviceType = null;
		for (PartnerServiceType item : serviceTypes) {
			serviceType = item;
			PartnerServicePriceDetail detail = partnerServicePriceDetailService.selectByServicePriceId(serviceType.getId());
			OrderWaterComVo waterComVo = new OrderWaterComVo();
			waterComVo.setPrice(detail.getPrice());
			waterComVo.setDisprice(detail.getDisPrice());
			waterComVo.setImgUrl(detail.getImgUrl());
			waterComVo.setServicePriceId(serviceType.getId());
			waterComVo.setName(serviceType.getName());
			waterComVo.setNamePrice(serviceType.getName() + "(原价:" + detail.getPrice().toString() + "元,折扣价：" + detail.getDisPrice().toString() + "元)");
			waterComVos.add(waterComVo);

		}
		model.addAttribute("waterComVos", waterComVos);

		// 服务商信息
		// 服务商列表

		List<PartnerRefServiceType> partnerRefServiceType = partnerRefServiceTypeService.selectByServiceTypeId(serviceTypeId);
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
			orderExtPartner.setOrderId(orderId);
			orderExtPartner.setOrderNo(orderNo);
		}
		model.addAttribute("orderExtPartner", orderExtPartner);

		return "order/orderWaterForm";
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
	@RequestMapping(value = "/saveOrderWater", method = { RequestMethod.POST })
	public String adForm(Model model, @ModelAttribute("contentModel") OrdersWaterListVo vo, BindingResult result, HttpServletRequest request)
			throws IOException {

		Long orderId = vo.getOrderId();
		Orders order = ordersService.selectByPrimaryKey(orderId);
		if (order == null)
			return "redirect:/order/waterList";

		// 更新订单基础信息
		order.setAddrId(vo.getAddrId());
		order.setOrderStatus(vo.getOrderStatus());
		order.setRemarks(vo.getRemarks());
		ordersService.updateByPrimaryKeySelective(order);

		// 更新价格信息
		OrderPrices orderPrice = orderPricesService.selectByOrderId(orderId);
		orderPrice.setOrderMoney(vo.getOrderMoney());
		orderPrice.setOrderPay(vo.getOrderPay());
		orderPricesService.updateByPrimaryKeySelective(orderPrice);

		// 更新订单扩展送水表
		OrderExtWater water = orderExtWaterService.selectByOrderId(orderId);
		water.setLinkMan(vo.getLinkMan());
		water.setLinkTel(vo.getLinkTel());
		water.setServicePriceId(vo.getServicePriceId());
		water.setServiceNum(vo.getServiceNum());
		orderExtWaterService.updateByPrimaryKeySelective(water);

		return "redirect:/order/waterList";
	}

	@AuthPassport
	@RequestMapping(value = "/saveOrderWaterPartner", method = { RequestMethod.POST })
	public String saveOrderWaterPartner(Model model, @ModelAttribute("orderExtPartner") OrderExtPartner vo, BindingResult result, HttpServletRequest request)
			throws IOException {

		Long orderId = vo.getOrderId();
		Orders order = ordersService.selectByPrimaryKey(orderId);
		Long id = vo.getId();
		Long userId = order.getUserId();

		Short orderExtStatus = 1;
		if (id.equals(0L)) {
			vo.setAddTime(TimeStampUtil.getNowSecond());
			vo.setUpdateTime(TimeStampUtil.getNowSecond());

			orderExtPartnerService.insert(vo);
			// 订单状态改完处理中
			order.setOrderStatus(Constants.ORDER_STATUS_3_PROCESSING);
			order.setUpdateTime(TimeStampUtil.getNowSecond());
			ordersService.updateByPrimaryKey(order);

			PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(order.getServiceTypeId());
			String title = serviceType.getName();
			String summary = OrderUtil.getOrderStausMsg(order.getOrderStatus());
			userMsgAsyncService.newActionAppMsg(userId, orderId, "water", title, summary, "");

		} else {
			vo.setUpdateTime(TimeStampUtil.getNowSecond());
			orderExtPartnerService.updateByPrimaryKeySelective(vo);

			String orderExtStatusStr = request.getParameter("orderExtStatus");
			orderExtStatus = Short.valueOf(orderExtStatusStr);

		}

		OrderExtWater water = orderExtWaterService.selectByOrderId(orderId);
		water.setOrderExtStatus(orderExtStatus);
		orderExtWaterService.updateByPrimaryKeySelective(water);

		// 更新此商品对于的服务人员信息
		String servicePriceIdStr = request.getParameter("servicePriceList");
		Long servicePriceId = 0L;
		if (!StringUtil.isEmpty(servicePriceIdStr))
			servicePriceId = Long.valueOf(servicePriceIdStr);

		PartnerServicePriceDetail servicePriceDetail = partnerServicePriceDetailService.selectByServicePriceId(servicePriceId);
		if (servicePriceDetail != null) {
			order.setPartnerUserId(servicePriceDetail.getUserId());
			ordersService.updateByPrimaryKey(order);
		}

		return "redirect:/order/waterList";
	}

	/**
	 * 后台下送水订单表单
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/orderWaterAddForm", method = RequestMethod.GET)
	public String orderTeamAdd(Long id, Model model) {

		Long serviceTypeId = 239L;
		OrdersWaterAddOaVo vo = new OrdersWaterAddOaVo();
		OrderExtWater water = orderExtWaterService.initOrderExtWater();
		BeanUtilsExp.copyPropertiesIgnoreNull(water, vo);
		vo.setRemarks("");
		vo.setMobile("");
		// 获得商品列表的选择下拉列表
		// 先根据服务大类找到相应的推荐人员.
		PartnerUserSearchVo searchVo = new PartnerUserSearchVo();
		searchVo.setServiceTypeId(serviceTypeId);
		searchVo.setWeightType((short) 1);

		List<PartnerUsers> list = partnerUserService.selectBySearchVo(searchVo);

		PartnerUsers partnerUser = list.get(0);

		Long parnterUserId = partnerUser.getUserId();

		List<PartnerServicePriceDetail> servicePriceDetails = partnerServicePriceDetailService.selectByUserId(parnterUserId);

		List<Long> servicePriceIds = new ArrayList<Long>();
		for (PartnerServicePriceDetail item : servicePriceDetails) {
			if (!servicePriceIds.contains(item.getServicePriceId())) {
				servicePriceIds.add(item.getServicePriceId());
			}
		}
		List<PartnerServiceType> serviceTypes = partnerServiceTypeService.selectByIds(servicePriceIds);
		List<OrderWaterComVo> waterComVos = new ArrayList<OrderWaterComVo>();
		PartnerServiceType serviceType = null;
		for (PartnerServiceType item : serviceTypes) {
			serviceType = item;
			PartnerServicePriceDetail detail = partnerServicePriceDetailService.selectByServicePriceId(serviceType.getId());
			OrderWaterComVo waterComVo = new OrderWaterComVo();
			waterComVo.setPrice(detail.getPrice());
			waterComVo.setDisprice(detail.getDisPrice());
			waterComVo.setImgUrl(detail.getImgUrl());
			waterComVo.setServicePriceId(serviceType.getId());
			waterComVo.setName(serviceType.getName());
			waterComVo.setNamePrice(serviceType.getName() + "(原价:" + detail.getPrice().toString() + "元,折扣价：" + detail.getDisPrice().toString() + "元)");
			waterComVos.add(waterComVo);

		}
		model.addAttribute("waterComVos", waterComVos);

		// 商品名称
		Long servicePriceId = water.getServicePriceId();
		PartnerServiceType servicePrice = partnerServiceTypeService.selectByPrimaryKey(servicePriceId);
		PartnerServicePriceDetail servicePriceDetail = partnerServicePriceDetailService.selectByServicePriceId(servicePriceId);
		model.addAttribute("servicePrice", servicePrice);
		model.addAttribute("servicePriceDetail", servicePriceDetail);

		model.addAttribute("contentModel", vo);

		return "order/orderWaterAddForm";
	}

	/**
	 * 后台下送水订单保存
	 * 
	 * @param model
	 * @param vo
	 * @param result
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@AuthPassport
	@RequestMapping(value = "/saveOrderWaterAdd", method = RequestMethod.POST)
	public String orderTeamSave(Model model, @ModelAttribute("contentModel") OrdersWaterAddOaVo vo, BindingResult result, HttpServletRequest request)
			throws UnsupportedEncodingException {

		Long serviceTypeId = (long) 239;
		Users u = usersService.selectByMobile(vo.getMobile());

		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
		PartnerServicePriceDetail servicePriceDetail = partnerServicePriceDetailService.selectByServicePriceId(vo.getServicePriceId());

		BigDecimal orderMoney = new BigDecimal(0.0);// 原价
		BigDecimal orderPay = new BigDecimal(0.0);// 折扣价

		BigDecimal disPrice = servicePriceDetail.getDisPrice();
		BigDecimal serviceNumDe = BigDecimal.valueOf(vo.getServiceNum().doubleValue());
		orderMoney = MathBigDecimalUtil.mul(disPrice, serviceNumDe);
		orderPay = orderMoney;

		// 调用公共订单号类，生成唯一订单号
		Orders order = null;
		String orderNo = "";

		orderNo = String.valueOf(OrderNoUtil.genOrderNo());
		order = ordersService.initOrders();

		String remarks = vo.getRemarks();
		// 服务内容及备注信息需要进行urldecode;
		if (!StringUtil.isEmpty(remarks)) {
			remarks = URLDecoder.decode(remarks, Constants.URL_ENCODE);
		}
		// 保存订单信息
		order.setOrderNo(orderNo);
		order.setServiceTypeId(serviceTypeId);
		order.setUserId(u.getId());
		order.setMobile(vo.getMobile());
		order.setServiceContent(serviceType.getName());

		order.setAddrId(vo.getAddrId());
		if (!StringUtil.isEmpty(remarks)) {
			order.setRemarks(remarks);
		}
		order.setOrderStatus(Constants.ORDER_STATUS_1_PAY_WAIT);
		ordersService.insert(order);
		Long orderId = order.getOrderId();

		// 记录订单日志.
		OrderLog orderLog = orderLogService.initOrderLog(order);
		orderLogService.insert(orderLog);

		// 保存订单价格信息
		OrderPrices orderPrice = orderPricesService.initOrderPrices();

		orderPrice.setOrderId(orderId);
		orderPrice.setOrderNo(orderNo);
		orderPrice.setServicePriceId(vo.getServicePriceId());
		orderPrice.setUserId(u.getId());
		orderPrice.setMobile(u.getMobile());
		orderPrice.setOrderMoney(orderMoney);
		orderPrice.setOrderPay(orderPay);
		orderPricesService.insert(orderPrice);

		// 保存送水订单扩展表信息
		OrderExtWater water = orderExtWaterService.initOrderExtWater();
		water.setOrderId(orderId);
		water.setOrderNo(orderNo);
		water.setUserId(u.getId());
		water.setServicePriceId(vo.getServicePriceId());
		water.setServiceNum(vo.getServiceNum());
		water.setLinkMan(vo.getLinkMan());
		water.setLinkTel(vo.getLinkTel());

		orderExtWaterService.insert(water);

		// 异步产生首页消息信息.
		String title = serviceType.getName();
		String summary = OrderUtil.getOrderStausMsg(order.getOrderStatus());
		userMsgAsyncService.newActionAppMsg(vo.getUserId(), orderId, "water", title, summary, "");

		return "redirect:/order/waterList";
	}

}
