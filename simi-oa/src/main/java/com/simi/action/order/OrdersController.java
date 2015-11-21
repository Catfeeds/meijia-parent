package com.simi.action.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.action.admin.AdminController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.OrdersListVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.vo.user.UserViewVo;

@Controller
@RequestMapping(value = "/order")
public class OrdersController extends AdminController {

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

	/**
	 * 订单列表
	 * 
	 * @param request
	 * @param model
	 * @param searchVo
	 * @param userId
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
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
		// 分页
		PageHelper.startPage(pageNo, pageSize);

		List<Orders> orderList = orderQueryService.selectByListPageList(
				searchVo, pageNo, pageSize);

		Orders orders = null;
		for (int i = 0; i < orderList.size(); i++) {
			orders = orderList.get(i);
			OrdersListVo ordersListVo = orderQueryService.completeVo(orders);
			orderList.set(i, ordersListVo);
		}
		PageInfo result = new PageInfo(orderList);

		model.addAttribute("contentModel", result);
		model.addAttribute("oaOrderSearchVoModel", searchVo);
		return "order/orderList";
	}

	/**
	 * 订单详情
	 * 
	 * @param orderNo
	 * @param model
	 * @return
	 */
	// @AuthPassport

	@RequestMapping(value = "/orderView", method = RequestMethod.GET)
	public String orderDetail(String orderNo, Model model) {

		Orders orders = ordersService.selectByOrderNo(orderNo);
		OrdersListVo vo = orderQueryService.completeVo(orders);

		model.addAttribute("contentModel", vo);
		return "order/orderViewForm";
	}

	@AuthPassport
	@RequestMapping(value = "/listdetail", method = { RequestMethod.GET })
	public String detail(HttpServletRequest request, Model model,
			@RequestParam("orderNo") String orderNo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		if (orderNo == null) {
			return "order/list";
		}

		// Long id = Long.valueOf(request.getParameter("id"));
		// if(id!=null && id!=0){
		// Staffs staffs = staffsService.selectByPrimaryKey(id);
		// model.addAttribute("staffs",staffs);
		// }

		Orders order = orderQueryService.selectByOrderNo(orderNo);
		Long orderId = order.getOrderId();
		Long userId = order.getUserId();

		List<Orders> orderList = new ArrayList<Orders>();
		orderList.add(order);
		List<OrderViewVo> orderViewList = orderQueryService
				.getOrderViewList(orderList);
		OrderViewVo orderViewVo = new OrderViewVo();
		if (!orderViewList.isEmpty() && orderViewList.size() > 0) {
			orderViewVo = orderViewList.get(0);
		}
		model.addAttribute("orderModel", orderViewVo);

		UserViewVo user = usersService.getUserViewByUserId(userId);
		model.addAttribute("userModel", user);

		OrderPrices orderPrice = orderPricesService.selectByOrderId(orderId);
		model.addAttribute("orderPrice", orderPrice);

		// 订单日志信息
		List<OrderLog> orderLogs = orderLogService.selectByOrderNo(orderNo);
		model.addAttribute("orderLogs", orderLogs);

		return "order/orderDetail";
	}

	@RequestMapping(value = "/placeOrders", method = { RequestMethod.GET })
	public String placeOrders(HttpServletRequest request, Model model) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		return "order/placeOrders";
	}

	@RequestMapping(value = "/saveDisp", method = { RequestMethod.GET })
	public String saveDispatch(HttpServletRequest request, Model model) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		return "Success";
	}

	@RequestMapping(value = "/calendar", method = { RequestMethod.GET })
	public String orderCalender(HttpServletRequest request, Model model) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		return "order/calendar";
	}
}
