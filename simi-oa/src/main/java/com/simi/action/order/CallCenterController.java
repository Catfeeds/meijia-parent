package com.simi.action.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.simi.action.admin.AdminController;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.user.UsersService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.user.UserViewVo;

@Controller
@RequestMapping(value = "/")
public class CallCenterController extends AdminController {

	@Autowired
	private OrderSeniorService orderSeniorService;

	@Autowired
	private UsersService usersService;

	@Autowired
    private OrderQueryService orderQueryService;

	@RequestMapping(value="/callcenter", method = {RequestMethod.GET})
    public String callCenter(Model model,HttpServletRequest request,
    		@RequestParam(value="customeuserrNumber") String customerNumber,
    		//@RequestParam(value="customerAreaCode") String customerAreaCode
    		OrderSearchVo searchVo
    		){

		//来电信息
		model.addAttribute("customerNumber", customerNumber);
		model.addAttribute("callTime", DateUtil.getNow());

		//用户信息
		Users u = usersService.selectByMobile(customerNumber);
		UserViewVo users = usersService.getUserViewByUserId(u.getId());
		model.addAttribute("userModel", users);

		//用户订单信息

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = 10;

		if (searchVo == null)  {
			searchVo = new OrderSearchVo();
		}

		if (searchVo.getMobile() == null) {
			searchVo.setMobile(customerNumber);
		}
		
		model.addAttribute("searchModel", searchVo);
		PageInfo result = orderQueryService.selectByListPage(searchVo, pageNo, pageSize);

		model.addAttribute("contentModel", result);

		//将form提交和分页路径加上
		String url = "/callcenter?customerNumber="+customerNumber;
		model.addAttribute("actionUrl", url);

        return "callcenter/show";
    }
}
