package com.xcloud.action.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.simi.vo.AppResultData;
import com.github.pagehelper.PageInfo;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictAd;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.UserCompanySearchVo;
import com.simi.vo.xcloud.StaffListVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/staff")
public class StaffController extends BaseController {

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
		
		XcompanyStaff xcompanyStaff = xcompanyStaffService.selectByCompanyIdAndUserId(companyId, userId);
		
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
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();
		
		Xcompany xCompany = xCompanyService.selectByPrimaryKey(companyId);
		model.addAttribute("companyId", accountAuth.getCompanyId());
		model.addAttribute("companyName", accountAuth.getCompanyName());
		model.addAttribute("shortName", accountAuth.getShortName());
		
		List<XcompanyDept> deptList = xcompanyDeptService.selectByXcompanyId(companyId);

		model.addAttribute("deptList", deptList);
	//	result.setData(plist);
		return "/staffs/staff-list";
		//return result;
	}
	
	@AuthPassport
	@RequestMapping(value = "/staff-form", method = { RequestMethod.GET })
	public String staffUserForm(Model model, HttpServletRequest request,
			@RequestParam(value = "staff_id", required = false) Long staffId) {
		
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();
		
		Xcompany xCompany = xCompanyService.selectByPrimaryKey(companyId);
		
		if (!model.containsAttribute("contentModel")) {
		  
			XcompanyStaff xcompanyStaff = xcompanyStaffService.initXcompanyStaff();
			StaffListVo vo = new StaffListVo();
			
			BeanUtilsExp.copyPropertiesIgnoreNull(xcompanyStaff, vo);
			
			Users u = usersService.initUsers();

			Long userId = 0L;

			String jobNumber = xcompanyStaffService.getMaxJobNumber(companyId);
			vo.setJobNumber(jobNumber);
			
			if (staffId > 0L) {
	
				xcompanyStaff = xcompanyStaffService.selectByPrimarykey(staffId);
				
				userId = xcompanyStaff.getUserId();
				
				u = usersService.selectByPrimaryKey(userId);
	
				BeanUtilsExp.copyPropertiesIgnoreNull(xcompanyStaff, vo);
	
				BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);
				
				
	
			}
			//注意这里user表id 和 xcompanyStaff表的id同名，所以需要手动设置
			vo.setId(xcompanyStaff.getId());
			vo.setCompanyId(companyId);
			
			model.addAttribute("contentModel", vo);
		}
		
		model.addAttribute("xCompany", xCompany);
		
		List<XcompanyDept> deptList = xcompanyDeptService.selectByXcompanyId(companyId);

		model.addAttribute("deptList", deptList);
		
		return "/staffs/staff-form";
	}

	/**
	 * 员工修改
	 */
	@AuthPassport
	@RequestMapping(value = "/staff-form", method = RequestMethod.POST)
	public String saveUserForm(HttpServletRequest request,
			 Model model, @ModelAttribute("contentModel") StaffListVo vo, BindingResult result) {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = vo.getCompanyId();
		
		Long staffId = 0L;
		if (vo.getId() != null)
			staffId = vo.getId();
		
		String mobile = vo.getMobile();
		String name = vo.getName();
		
		if (StringUtil.isEmpty(mobile) || StringUtil.isEmpty(name)) {
        	result.addError(new FieldError("contentModel","mobile","手机号或姓名不能为空"));
        	return staffUserForm(model, request, staffId);
		}
		
		Users u = usersService.selectByMobile(mobile);
		Long userId = 0L;
		// 验证手机号是否已经注册，如果未注册，则自动注册用户，
		if (u == null) {
			u = usersService.genUser(mobile, vo.getName(), Constants.User_XCOULD);
			
		}
		userId = u.getId();
		if (!u.getName().equals(vo.getName())) {
			u.setName(name);
			usersService.updateByPrimaryKeySelective(u);
		}
		
		XcompanyStaff xcompanyStaff = xcompanyStaffService.initXcompanyStaff();
		
		//新增要判断员工是否重复
		if (staffId.equals(0L)) {
			XcompanyStaff xcompanyStaffExist = xcompanyStaffService.selectByCompanyIdAndUserId(companyId, userId);
			if (xcompanyStaffExist != null) {
				result.addError(new FieldError("contentModel","mobile","该用户已经为贵司员工,不需要重复添加."));
	        	return staffUserForm(model, request, staffId);
			}
		}
		
		if (staffId > 0L) {
			xcompanyStaff = xcompanyStaffService.selectByPrimarykey(staffId);
		}
		xcompanyStaff.setId(staffId);
		xcompanyStaff.setCompanyId(companyId);
		xcompanyStaff.setUserId(userId);
		xcompanyStaff.setCompanyEmail(vo.getCompanyEmail());
		
		String joinDate = request.getParameter("joinDate");
		xcompanyStaff.setJoinDate(DateUtil.parse(joinDate));
		
		xcompanyStaff.setJobName(vo.getJobName());
		xcompanyStaff.setDeptId(vo.getDeptId());
		xcompanyStaff.setStaffType(vo.getStaffType());
		
		if (staffId > 0L) {
			xcompanyStaffService.updateByPrimaryKeySelective(xcompanyStaff);
		} else {
			xcompanyStaff.setJobNumber(xcompanyStaffService.getMaxJobNumber(companyId));
			xcompanyStaffService.insertSelective(xcompanyStaff);
		}
		
		//todo 给相应的用户进行通知
		
		return "redirect:list";
	}
	
	@AuthPassport
	@RequestMapping(value = "/deleteById", method = { RequestMethod.GET })
	public String deleteByRechargeCouponId(@RequestParam(value = "id") Long id) {

		String path = "redirect:list";
		if (id != null) {
			int result = xcompanyStaffService.deleteByPrimaryKey(id);
			if (result > 0) {
				path = "redirect:list";
			} else {
				path = "error";
			}
		}
		return path;
	}
	
	@RequestMapping(value = "/change-dept", method = RequestMethod.POST)
	public AppResultData<Object> changeDept(HttpServletRequest request,
			@RequestParam("company_id") Long companyId,
			@RequestParam("select_staff_ids") String selectStaffIds,
			@RequestParam("select_dept_id") Long deptId
			) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, null);
		
		if (StringUtil.isEmpty(selectStaffIds)) return result;
		
		String[] staffAry = StringUtil.convertStrToArray(selectStaffIds);
		
		for (int i = 0; i < staffAry.length; i++) {
			String staffId = staffAry[i];
			if (StringUtil.isEmpty(staffId)) continue;

			XcompanyStaff xcompanyStaff = xcompanyStaffService.selectByPrimarykey(Long.valueOf(staffId));
			if (xcompanyStaff == null) continue;
			
			xcompanyStaff.setDeptId(deptId);
			xcompanyStaffService.updateByPrimaryKeySelective(xcompanyStaff);
		}
		
		return result;
	}

}
