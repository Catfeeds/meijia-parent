package com.simi.action.op;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.meijia.utils.GsonUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.op.OpExtVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;

@Controller
@RequestMapping(value = "/op")
public class OpExtController extends BaseController {

	@Autowired
	private XCompanySettingService settingService;

	@AuthPassport
	@RequestMapping(value = "/extList", method = { RequestMethod.GET })
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
		List<XcompanySetting> list = result.getList();
		
		List<OpExtVo> resultList = new ArrayList<OpExtVo>();
		for (int i = 0; i < list.size(); i++) {
			XcompanySetting item = list.get(i);
			OpExtVo vo = new OpExtVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			vo.setExpIn("");
			vo.setRemarks("");
			if (!StringUtil.isEmpty(item.getSettingJson())) {
				Map<String, Object> json = GsonUtil.GsonToMaps(item.getSettingJson());
				if (json.get("exp_in") != null) vo.setExpIn(json.get("exp_in").toString());
				if (json.get("remarks") != null) vo.setRemarks(json.get("remarks").toString());
			}
			list.set(i, vo);
		}
		result = new PageInfo(list);
		
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
		ext.setSettingType("op_ext");
		OpExtVo vo = new OpExtVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(ext, vo);
		
		if (!StringUtil.isEmpty(ext.getSettingJson())) {
			Map<String, Object> json = GsonUtil.GsonToMaps(ext.getSettingJson());
			if (json.get("exp_in") != null) vo.setExpIn(json.get("exp_in").toString());
			if (json.get("remarks") != null) vo.setRemarks(json.get("remarks").toString());
		}
		
		
		model.addAttribute("contentModel", vo);

		return "op/extForm";
	}
	
	@AuthPassport
	@RequestMapping(value = "/extForm", method = { RequestMethod.POST })
	public String doAdForm(HttpServletRequest request, Model model, @ModelAttribute("contentModel") OpExtVo opExt, BindingResult result) throws IOException {

		Long id = Long.valueOf(request.getParameter("id"));
		
		XcompanySetting setting = settingService.initXcompanySetting();
		if (id != null && id > 0) {
			setting = settingService.selectByPrimaryKey(id);
		}
		
		setting.setName(opExt.getName());
		setting.setSettingType(opExt.getSettingType());
		
		Map<String, String> settingMap = new HashMap<String, String>();
		settingMap.put("exp_in", opExt.getExpIn());
		settingMap.put("remarks", opExt.getRemarks());
		String settingJson = GsonUtil.GsonString(settingMap);
		
		setting.setSettingJson(settingJson);
		// 更新或者新增
		if (id != null && id > 0) {
			opExt.setUpdateTime(TimeStampUtil.getNowSecond());
			settingService.updateByPrimaryKeySelective(setting);
		} else {
			opExt.setAddTime(TimeStampUtil.getNow() / 1000);
			settingService.insertSelective(setting);
		}

		return "redirect:extList";
	}

}
