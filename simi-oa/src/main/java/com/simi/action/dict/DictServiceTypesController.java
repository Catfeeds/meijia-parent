package com.simi.action.dict;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.common.Constants;
import com.simi.po.model.admin.AdminRole;
import com.simi.po.model.dict.DictCoupons;
import com.simi.po.model.dict.DictServiceTypes;
import com.simi.service.dict.CouponService;
import com.simi.service.dict.ServiceTypeService;
import com.simi.service.user.UserCouponService;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.dict.CouponSearchVo;
import com.simi.vo.dict.ServiceTypeVo;
import com.simi.vo.user.UserCouponSearchVo;

@Controller
@RequestMapping(value = "/dict")
public class DictServiceTypesController extends BaseController {

	@Autowired
    private ServiceTypeService serviceTypeService;

	/**列表显示发不的优惠券
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model
		) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel");

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result=serviceTypeService.searchVoListPage( pageNo, pageSize);

		model.addAttribute("contentModel",result);

		return "dict/list";
	}

	/**
	 * 服务类型表单方法，显示
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/serviceTypeForm", method = { RequestMethod.GET })
	public String serviceTypeForm(Model model,
			@RequestParam(value = "id") Long id,
			HttpServletRequest request) {

		if (id == null) {
			id = 0L;
		}

		DictServiceTypes serviceType = serviceTypeService.initServiceType();
		if (id != null && id > 0) {
			serviceType = serviceTypeService.selectByPrimaryKey(id);
		}
//		model.addAttribute("requestUrl", request.getServletPath());
//		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("serviceTypeModel", serviceType);

		return "dict/serviceTypeForm";
	}

	/**
	 * 服务类型保存数据方法.
	 *
	 * @param request
	 * @param model
	 * @param serviceType
	 * @param result
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/serviceTypeForm", method = { RequestMethod.POST })
	public String doServiceTypeForm(HttpServletRequest request, Model model,
			@ModelAttribute("serviceType") DictServiceTypes serviceType,
			BindingResult result) {

		Long id = Long.valueOf(request.getParameter("id"));

		//更新或者新增
		if (id != null && id > 0) {
			serviceType.setUpdateTime(TimeStampUtil.getNow() / 1000);
			serviceTypeService.updateByPrimaryKeySelective(serviceType);
		} else {
			serviceType.setId(Long.valueOf(request.getParameter("id")));
			serviceType.setAddTime(TimeStampUtil.getNow()/1000);
			serviceType.setUpdateTime(0L);
	        serviceTypeService.insertSelective(serviceType);
		}



		return "redirect:list";
	}

}
