package com.simi.action.app.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/order")
public class OrderAddController extends BaseController {
	@Autowired
	private UsersService userService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	OrderPricesService orderPricesService;
	@Autowired
	UserAddrsService addrsService;

	// 1. 订单提交接口
	/**
	 * mobile: 手机号 city_id 城市 service_type
	 * 服务类型0=做饭1=洗衣2=家电清洗3=保洁4=擦玻璃5=管道疏通6=新居开荒 send_datas
	 * json_data:{[type:101,value:2],[type:12,value:3]} service_date 服务开始日期
	 * start_time 服务开始时间 service_hour 服务时长 addr_id服务地址ID remarks备注,urlencode
	 * service_model 服务模式0=零工(默认)1=包月（保留字段） order_from 0=APP1=微网站2=管理后台
	 */
	@RequestMapping(value = "post_add", method = RequestMethod.POST)
	public AppResultData<Object> saveOrder(
			@RequestParam("mobile") String mobile, 
			@RequestParam("service_type") int service_type, 
			@RequestParam("send_datas") String send_datas, 
			@RequestParam("service_date") Long service_date, 
			@RequestParam("start_time") Long start_time, 
			@RequestParam("service_hour") int service_hour, 
			@RequestParam("addr_id") int addr_id, 
			@RequestParam("remarks") String remarks,
			@RequestParam("order_from") int order_from,
			@RequestParam(value = "agent_mobile", required = false, defaultValue="") String agent_mobile,
			@RequestParam(value = "custom_name", required = false, defaultValue="") String customName,
			@RequestParam(value = "clean_tools", required = false, defaultValue="0") int clean_tools, 
			@RequestParam(value = "city_id", required = false, defaultValue="0") int city_id
			) {
		

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		return result;
	}
}
