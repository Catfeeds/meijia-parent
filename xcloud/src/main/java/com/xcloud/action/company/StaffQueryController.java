package com.xcloud.action.company;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.StringUtil;
import com.simi.vo.AppResultData;
import com.github.pagehelper.PageInfo;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.xcloud.StaffListVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;


@Controller
@RequestMapping(value = "/staff")
public class StaffQueryController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyDeptService xcompanyDeptService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@AuthPassport
	@RequestMapping(value = "/get-by-dept", method = { RequestMethod.GET })
	public Map<String, Object> getByDpt(HttpServletRequest request,
			@RequestParam(value = "dept_id", required = false, defaultValue = "0") Long deptId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "length", required = false, defaultValue = "10") int length
			) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", 0);
		result.put("recordsFiltered", 0);		
		result.put("data", "");
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		
		if (deptId > 0L) {
			searchVo.setDeptId(deptId);
		}
		searchVo.setStatus((short) 1);
		
		PageInfo plist = xcompanyStaffService.selectByListPage(searchVo, page, length);

		List<StaffListVo> list = plist.getList();
		
		if (!list.isEmpty()) {
			result.put("recordsTotal", plist.getTotal());
			result.put("recordsFiltered", plist.getTotal());		
			result.put("data", list);
		}

		return result;
	}
	
	@AuthPassport
	@RequestMapping(value = "/get-by-mobile", method = { RequestMethod.GET })
	public AppResultData<Object> getByMobile(HttpServletRequest request,
			@RequestParam(value = "mobile", required = false, defaultValue = "") String mobile
			) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (StringUtil.isEmpty(mobile)) return result;
		
		Users u = usersService.selectByMobile(mobile);
		
		if (u == null) return result;
		
		Long userId = u.getId();
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();
		
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> rsList = xcompanyStaffService.selectBySearchVo(searchVo);
		XcompanyStaff xcompanyStaff = null;
		if (!rsList.isEmpty()) {
			xcompanyStaff = rsList.get(0);
		}
		
		if (xcompanyStaff == null) {
			xcompanyStaff = xcompanyStaffService.initXcompanyStaff();
		}
		
		StaffListVo vo = new StaffListVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(xcompanyStaff, vo);
		
		BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);
		//注意这里user表id 和 xcompanyStaff表的id同名，所以需要手动设置
		vo.setId(xcompanyStaff.getId());
		result.setData(vo);
		
		return result;
	}
	
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String staffTreeAndList(HttpServletRequest request, Model model,
			@RequestParam(value = "dept_id", required = false, defaultValue = "0") Long deptId,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();
		
		model.addAttribute("companyId", companyId);
				
		List<XcompanyDept> deptList = xcompanyDeptService.selectByXcompanyId(companyId);

		model.addAttribute("deptList", deptList);
		
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		
		if (deptId > 0L) {
			searchVo.setDeptId(deptId);
		}
		searchVo.setStatus((short) 1);
		
		PageInfo result = xcompanyStaffService.selectByListPage(searchVo, pageNo, pageSize);
		
		model.addAttribute("contentModel", result);

		return "/staffs/staff-list";
	}

}
