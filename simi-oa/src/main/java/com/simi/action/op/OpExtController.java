package com.simi.action.op;

import java.io.IOException;

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

import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.xcloud.CompanySettingSearchVo;

@Controller
@RequestMapping(value = "/op")
public class OpExtController extends BaseController {

	@Autowired
	private XCompanySettingService settingService;

	@AuthPassport
	@RequestMapping(value = "/ext_list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, CompanySettingSearchVo searchVo) {
		
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel");
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		if (searchVo == null) {
			searchVo = new CompanySettingSearchVo();
		}
		searchVo.setSettingType("op_ext");
		
		PageInfo result = settingService.selectByListPage(searchVo, pageNo, pageSize);

		model.addAttribute("contentModel", result);

		return "op/extList";
	}

	@AuthPassport
	@RequestMapping(value = "/extForm", method = { RequestMethod.GET })
	public String adForm(Model model, @RequestParam(value = "id") Long id, HttpServletRequest request) {

		if (id == null) id = 0L;

		XcompanySetting ext = settingService.initXcompanySetting();
		if (id != null && id > 0) {
			ext = settingService.selectByPrimaryKey(id);
		}

		model.addAttribute("extModel", ext);

		return "op/extForm";
	}
	
	@AuthPassport
	@RequestMapping(value = "/extForm", method = { RequestMethod.POST })
	public String doAdForm(HttpServletRequest request, Model model, @ModelAttribute("opChannel") XcompanySetting opExt, BindingResult result) throws IOException {

		Long id = Long.valueOf(request.getParameter("id"));

		// 更新或者新增
		if (id != null && id > 0) {
			opExt.setUpdateTime(TimeStampUtil.getNowSecond());
			settingService.updateByPrimaryKeySelective(opExt);
		} else {
			opExt.setAddTime(TimeStampUtil.getNow() / 1000);
			settingService.insertSelective(opExt);
		}

		return "redirect:op/extList";
	}

}
