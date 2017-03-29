package com.simi.action.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.simi.po.model.order.OrderScore;
import com.simi.po.model.order.Orders;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrderScoreService;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.OrdersListVo;
import com.simi.vo.order.OrderScoreVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.vo.user.UserViewVo;

@Controller
@RequestMapping(value = "/order")
public class OrderScoreController extends AdminController {

	@Autowired
	private OrderScoreService orderScoreService;

	@Autowired
	private UsersService usersService;

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
	@RequestMapping(value = "/scoreList", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, OrderSearchVo searchVo, @RequestParam(value = "user_id", required = false) Long userId) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		searchVo.setUserId(userId);

		
		PageInfo info = orderScoreService.selectByListPage(searchVo, pageNo, pageSize);

		List<OrderScore> list = info.getList();
		for (int i = 0; i < list.size(); i++) {
			OrderScore item = list.get(i);
			OrderScoreVo vo = orderScoreService.getVos(item);
			list.set(i, vo);
		}
		PageInfo result = new PageInfo(list);

		model.addAttribute("contentModel", result);
		model.addAttribute("searchModel", searchVo);
		return "order/scoreList";
	}
}
