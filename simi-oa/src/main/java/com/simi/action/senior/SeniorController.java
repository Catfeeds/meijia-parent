package com.simi.action.senior;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;
import com.simi.action.BaseController;
import com.simi.oa.common.ConstantOa;
import com.simi.service.user.UserRefSeniorService;
import com.simi.vo.admin.SeniorSearchVo;

@Controller
@RequestMapping(value = "/senior")
public class SeniorController extends BaseController {

	@Autowired
	private UserRefSeniorService userRefSeniorService;

	/**
	 * 列表显示管家人员列表
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	//@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String staffList(HttpServletRequest request, Model model,
			SeniorSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		model.addAttribute("searchModel", searchVo);

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result = userRefSeniorService.searchVoListPage(searchVo, pageNo,
				pageSize);

		model.addAttribute("contentModel", result);

		return "senior/list";
	}
}
