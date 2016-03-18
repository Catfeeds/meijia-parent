package com.simi.action.xcloud;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.action.admin.AdminController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.service.xcloud.XCompanyService;
import com.simi.vo.xcloud.CompanySearchVo;
@Controller
@RequestMapping(value = "/company")
public class XcompanyController extends AdminController {

	@Autowired
	private XCompanyService xCompanyService;
	 /**
     * 用户创建的企业列表
     * @param request
     * @param model
     * @param usersSmsTokenVo
     * @return
     */
    @AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String userTokenList(HttpServletRequest request, Model model,
			CompanySearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		// 分页
		PageHelper.startPage(pageNo, pageSize);

		List<Xcompany> lists = xCompanyService.selectByListPage(
				searchVo, pageNo, pageSize);

		PageInfo result = new PageInfo(lists);

		model.addAttribute("searchModel", searchVo);
		model.addAttribute("contentModel", result);

		return "xcloud/companyList";
	}

 
}
