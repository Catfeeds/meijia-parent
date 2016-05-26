package com.xcloud.action.xz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.model.dict.DictExpress;
import com.simi.po.model.record.RecordExpress;
import com.simi.po.model.user.Users;
import com.simi.service.dict.ExpressService;
import com.simi.service.record.RecordExpressService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.ExpressSearchVo;
import com.simi.vo.order.RecordExpressXcloudVo;
import com.simi.vo.record.RecordExpressSearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/xz/express/")
public class ExpressController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private RecordExpressService recordExpressService;
	
	@Autowired
	private ExpressService expressService;

	// 查询与登记
	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model,
			RecordExpressSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		
		if (searchVo == null) searchVo = new RecordExpressSearchVo();

		if (searchVo.getStartDate() == null) {
			//开始时间和结束时间，默认为上月25号到本月25号			
			String startDateStr = DateUtil.getSomeDayOfMonth(DateUtil.getYear(), DateUtil.getMonth()-2 , 25);
			searchVo.setStartDate(startDateStr);
		}
		
		if (searchVo.getEndDate() == null) {
			String endDateStr = DateUtil.getSomeDayOfMonth(DateUtil.getYear(), DateUtil.getMonth()-1, 25);
			searchVo.setEndDate(endDateStr);
		}
		
		Long startTime = TimeStampUtil.getMillisOfDayFull(searchVo.getStartDate() + " 00:00:00") / 1000;
		searchVo.setStartTime(startTime);
		Long endTime = TimeStampUtil.getMillisOfDayFull(searchVo.getEndDate() + " 23:59:59") / 1000;
		searchVo.setEndTime(endTime);
		

		model.addAttribute("searchModel", searchVo);
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();
		Long companyId = accountAuth.getCompanyId();
		model.addAttribute("userId", userId);
		model.addAttribute("companyId", companyId);
		
		searchVo.setCompanyId(companyId);

		PageInfo result = recordExpressService.selectByListPageVos(searchVo, pageNo, pageSize);
		
		model.addAttribute("contentModel", result);
		
		//快递公司列表
		ExpressSearchVo searchVo1 = new ExpressSearchVo();
		searchVo1.setIsHot((short) 1);
		List<DictExpress> expressList = expressService.selectBySearchVo(searchVo1);
		model.addAttribute("expressList", expressList);
		
		return "xz/express-list";
	}


	@AuthPassport
	@RequestMapping(value = "/express-form", method = { RequestMethod.GET })
	public String expressForm(Model model, HttpServletRequest request, @RequestParam(value = "id") Long id) {
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long userId = accountAuth.getUserId();
		Long companyId = accountAuth.getCompanyId();
		model.addAttribute("userId", userId);
		model.addAttribute("companyId", companyId);
		
		Users u = usersService.selectByPrimaryKey(userId);
		
		if (id == null) id = 0L;
		
		RecordExpressXcloudVo vo = new RecordExpressXcloudVo();
		RecordExpress express = recordExpressService.initRecordExpress();
		
		if (id > 0L) {
			express = recordExpressService.selectByPrimaryKey(id);
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(express, vo);
		
		if (id.equals(0L)) {
			vo.setFromName(u.getName());
			vo.setFromTel(u.getMobile());
		}
		

		model.addAttribute("contentModel", vo);
		
		//快递公司列表
		ExpressSearchVo searchVo = new ExpressSearchVo();
		searchVo.setIsHot((short) 1);
		List<DictExpress> expressList = expressService.selectBySearchVo(searchVo);
		
		model.addAttribute("expressList", expressList);
		
		return "xz/express-form";
	}

	// 快递服务商
	@AuthPassport
	@RequestMapping(value = "service", method = RequestMethod.GET)
	public String doSetting(HttpServletRequest request, Model model) {

		return "xz/express-service";
	}
}
