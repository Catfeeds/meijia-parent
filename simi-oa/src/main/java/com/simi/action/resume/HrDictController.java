package com.simi.action.resume;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.resume.po.model.dict.HrDictType;
import com.resume.po.model.dict.HrDicts;
import com.resume.po.model.dict.HrFrom;
import com.simi.action.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.service.CacheDictService;
import com.simi.service.dict.DictService;
import com.simi.service.resume.HrDictTypeService;
import com.simi.service.resume.HrDictsService;
import com.simi.service.resume.HrFromService;
import com.simi.vo.AppResultData;
import com.simi.vo.resume.HrDictSearchVo;
import com.simi.vo.resume.ResumeRuleSearchVo;

@Controller
@RequestMapping(value = "/resume")
public class HrDictController extends BaseController {
	
	@Autowired
	private HrFromService hrFromService;

	@Autowired
	private HrDictsService hrDictService;
	
	@Autowired
	private HrDictTypeService hrDictTypeService;
	
	@Autowired
	private CacheDictService dictCacheService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@AuthPassport
	@RequestMapping(value = "/hrDictList", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, HrDictSearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		if (searchVo == null) {
			searchVo = new HrDictSearchVo();
		}
		model.addAttribute("searchModel", searchVo);
		PageInfo result = hrDictService.selectByListPage(searchVo, pageNo, pageSize);
		List<HrDicts> list = result.getList();

		result = new PageInfo(list);
		
		model.addAttribute("contentModel", result);
		
		//字典类型
		List<HrDictType> hrDictTypes = hrDictTypeService.selectByAll();
		
		model.addAttribute("hrDictTypes", hrDictTypes);
		//来源定义
		
		List<HrFrom> hrFroms = hrFromService.selectByAll();
		
		model.addAttribute("hrFroms", hrFroms);
		return "resume/hrDictList";
	}

	@AuthPassport
	@RequestMapping(value = "/hrDictForm", method = { RequestMethod.GET })
	public String fromForm(Model model, @RequestParam(value = "id") Long id, HttpServletRequest request) {

		if (id == null) id = 0L;

		HrDicts record = hrDictService.initHrDicts();
		if (id != null && id > 0) {
			record = hrDictService.selectByPrimaryKey(id);
		}

		model.addAttribute("contentModel", record);
		
		//字典类型
		List<HrDictType> hrDictTypes = hrDictTypeService.selectByAll();
		
		model.addAttribute("hrDictTypes", hrDictTypes);
		//来源定义
		
		List<HrFrom> hrFroms = hrFromService.selectByAll();
		
		model.addAttribute("hrFroms", hrFroms);
		
		return "resume/hrDictForm";
	}

	@AuthPassport
	@RequestMapping(value = "/hrDictForm", method = { RequestMethod.POST })
	public String doFromForm(HttpServletRequest request, Model model, @ModelAttribute("contentModel") HrDicts item, BindingResult result) throws IOException {

		Long id = item.getId();
		HrDicts record = hrDictService.initHrDicts();
		if (id != null && id > 0) {
			record = hrDictService.selectByPrimaryKey(id);
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(item, record);
		
		
		if (!StringUtil.isEmpty(record.getPid()) || !record.getPid().equals("0")) {
			record.setLevel((short) 1);
		}
		
		// 更新或者新增
		if (id != null && id > 0) {
			hrDictService.updateByPrimaryKeySelective(record);
		} else {
			record.setAddTime(TimeStampUtil.getNowSecond());
			hrDictService.insert(record);
		}
		
		if (record.getType().equals("parse_rule")) {
			dictCacheService.loadHrDictRules(true);
		}

		return "redirect:hrDictList";
	}
}
