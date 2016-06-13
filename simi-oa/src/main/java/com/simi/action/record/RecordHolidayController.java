package com.simi.action.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.service.record.RecordHolidayService;
import com.simi.vo.record.RecordHolidaySearchVo;

@Controller
@RequestMapping(value = "/record/holiday")
public class RecordHolidayController extends BaseController {

	@Autowired
	private RecordHolidayService recordHolidayService;
	
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, RecordHolidaySearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		
		
		if (searchVo == null) {
			searchVo = new RecordHolidaySearchVo();
			
		}
		
		if (searchVo.getYear() == 0)
		searchVo.setYear(DateUtil.getYear());
		
		model.addAttribute("searchModel", searchVo);
		
		int year = DateUtil.getYear();
		//年份列表，前两年 + 今年
		List<Map> years = new ArrayList<Map>();
		Map<String, String> item1 = new HashMap<String, String>();
		item1.put("name", String.valueOf((year-2)));
		item1.put("value", String.valueOf((year-2)));
		
		Map<String, String> item2 = new HashMap<String, String>();
		item2.put("name", String.valueOf((year-1)));
		item2.put("value", String.valueOf((year-1)));
		
		Map<String, String> item3 = new HashMap<String, String>();
		item3.put("name", String.valueOf((year)));
		item3.put("value", String.valueOf((year)));
		
		years.add(item3);
		years.add(item2);
		years.add(item1);

		model.addAttribute("years", years);

		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result = recordHolidayService.selectByListPage(searchVo, pageNo, pageSize);
		
		model.addAttribute("contentModel", result);

		return "record/holidayList";
	}
}
