package com.simi.action.resume;

import java.io.IOException;
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
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.resume.po.model.dict.HrFrom;
import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.service.resume.HrFromService;
import com.simi.vo.resume.ResumeRuleSearchVo;

@Controller
@RequestMapping(value = "/resume")
public class HrFromController extends BaseController {

	@Autowired
	private HrFromService hrFromService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@AuthPassport
	@RequestMapping(value = "/hrFromList", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, ResumeRuleSearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel");
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		if (searchVo == null) {
			searchVo = new ResumeRuleSearchVo();
		}
		
		PageInfo result = hrFromService.selectByListPage(searchVo, pageNo, pageSize);
		List<HrFrom> list = result.getList();

		result = new PageInfo(list);
		
		model.addAttribute("contentModel", result);

		return "resume/hrFromList";
	}

	@AuthPassport
	@RequestMapping(value = "/hrFromForm", method = { RequestMethod.GET })
	public String fromForm(Model model, @RequestParam(value = "id") Long fromId, HttpServletRequest request) {

		if (fromId == null) {
			fromId = 0L;
		}

		HrFrom record = hrFromService.initHrDictFrom();
		if (fromId != null && fromId > 0) {
			record = hrFromService.selectByPrimaryKey(fromId);
		}

		model.addAttribute("contentModel", record);
		
		return "resume/hrFromForm";
	}

	@AuthPassport
	@RequestMapping(value = "/hrFromForm", method = { RequestMethod.POST })
	public String doFromForm(HttpServletRequest request, Model model, @ModelAttribute("contentModel") HrFrom record, BindingResult result) throws IOException {

		Long fromId = record.getFromId();

		// 更新或者新增
		if (fromId != null && fromId > 0) {
			hrFromService.updateByPrimaryKeySelective(record);
		} else {
			record.setFromId(fromId);
			record.setAddTime(TimeStampUtil.getNowSecond());
			hrFromService.insert(record);
		}

		return "redirect:hrFromList";
	}
}
