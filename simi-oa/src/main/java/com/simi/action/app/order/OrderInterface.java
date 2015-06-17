package com.simi.action.app.order;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.BaseController;
import com.simi.service.order.OrdersService;

@Controller
@RequestMapping(value = "/interface-order")
public class OrderInterface extends BaseController {

	@Autowired
	private OrdersService  ordersService;

	/**
	 * 根据月份显示该月派工个数
	 * @return
	 * @throws IOException
	 */
//	@SuppressWarnings("rawtypes")//传递参数时也要传递带泛型的参数--压制这种警告
//	@RequestMapping(value = "get-dispatch-by-month.json", method = RequestMethod.GET)
//    public List<Map<String,Object>>  getDispatch(HttpServletResponse response,
//    		@RequestParam(value = "start", required = true, defaultValue = "" ) String start,
//    		@RequestParam(value="end" ,required = true, defaultValue = "") String end) {
//
//		return ordersService.selectOrdersCountByYearAndMonth(start, end);
//    }
}
