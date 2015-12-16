package com.simi.action.app.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderDetailVo;
import com.simi.vo.order.OrderListVo;

@Controller
@RequestMapping(value = "/app/order")
public class OrderPartnerController extends BaseController {
	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;
	
	@Autowired
	private UsersService userService;

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
			
			//OrderDetailVo detailVo = orderQueryService.getOrderDetailVo(item, listVo);
			orderListVo.add(listVo);
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
	
}
