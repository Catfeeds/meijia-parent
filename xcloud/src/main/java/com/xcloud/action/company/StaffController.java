package com.xcloud.action.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.StringUtil;
import com.simi.vo.AppResultData;
import com.github.pagehelper.PageInfo;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
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

	/*@RequestMapping(value = "/userForm", method = { RequestMethod.GET })
	public String staffUserForm(Model model, HttpServletRequest request,
			@RequestParam(value = "id", required = false) Long userId) {

		UserCompanyFormVo vo = new UserCompanyFormVo();
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();
		
		Xcompany xCompany = xCompanyService.selectByPrimaryKey(companyId);
				
		if (userId > 0L) {

			XcompanyStaff xcompanyStaff = xcompanyStaffService.selectByCompanyIdAndUserId(companyId, userId);

			Users users = usersService.selectByPrimaryKey(userId);

			BeanUtilsExp.copyPropertiesIgnoreNull(xcompanyStaff, vo);

			BeanUtilsExp.copyPropertiesIgnoreNull(users, vo);
			
			Xcompany xcompany = xCompanyService.selectByPrimaryKey(xcompanyStaff.getCompanyId());

			vo.setCompanyName(xcompany.getCompanyName());
			vo.setDeptName("");
			vo.setStaffName("");

		}

		model.addAttribute("contentModel", vo);

		return "/staffs/userForm";
	}*/

	/**
	 * 新增员工提交
	 * 
	 * @param request
	 * @param name
	 * @param idCard
	 * @param companyEmail
	 * @param companyName
	 * @param companyFax
	 * @param tel
	 * @param staffType
	 * @param jobNumber
	 * @param deptId
	 * @param jobName
	 * @return
	 */
	@RequestMapping(value = "/postUserForm", method = RequestMethod.POST)
	public String saveUserForm(HttpServletRequest request,
			@RequestParam("company_id") Long companyId,
			@RequestParam("name") String name,
			@RequestParam("idCard") String idCard,
			@RequestParam("companyEmail") String companyEmail,
			@RequestParam("companyName") String companyName,
			@RequestParam("companyFax") String companyFax,
			@RequestParam("tel") String tel,
			@RequestParam("telExt") String telExt,
			@RequestParam("cityId") Long cityId,
			@RequestParam("staffType") short staffType,
			@RequestParam("jobNumber") String jobNumber,
			@RequestParam("deptId") Long deptId,
			@RequestParam("jobName") String jobName,
			@RequestParam(value = "id", required = false,defaultValue = "0") Long userId) {

		// Long id = Long.valueOf(request.getParameter("id"));

		 Xcompany xcompany = xCompanyService.selectByCompanyName(companyName);
		 if (userId > 0L) {
			Users users = usersService.selectByPrimaryKey(userId);
			users.setName(name);
			users.setIdCard(idCard);
			usersService.updateByPrimaryKeySelective(users);
			
			XcompanyStaff xcompanyStaff = xcompanyStaffService.selectByCompanyIdAndUserId(companyId, userId);
			if (xcompany != null) {
			xcompanyStaff.setCompanyId(xcompany.getCompanyId());
			xcompanyStaff.setUserId(users.getId());
			xcompanyStaff.setDeptId(deptId);
			xcompanyStaff.setTel(tel);
			xcompanyStaff.setTelExt(telExt);
			xcompanyStaff.setCityId(cityId);
			xcompanyStaff.setCompanyEmail(companyEmail);
			xcompanyStaff.setCompanyFax(companyFax);
			xcompanyStaff.setStaffType(staffType);
			xcompanyStaff.setJobNumber(jobNumber);
			xcompanyStaff.setJobName(jobName);
			xcompanyStaffService.updateByPrimaryKeySelective(xcompanyStaff);
			
			}
		}else {
			
		Users users = usersService.initUsers();
		users.setName(name);
		users.setIdCard(idCard);
		usersService.insertSelective(users);

		if (xcompany != null) {
			XcompanyStaff xcompanyStaff = xcompanyStaffService
					.initXcompanyStaff();
			xcompanyStaff.setCompanyId(xcompany.getCompanyId());
			xcompanyStaff.setUserId(users.getId());
			xcompanyStaff.setDeptId(deptId);
			xcompanyStaff.setTel(tel);
			xcompanyStaff.setTelExt(telExt);
			xcompanyStaff.setCityId(cityId);
			xcompanyStaff.setCompanyEmail(companyEmail);
			xcompanyStaff.setCompanyFax(companyFax);
			xcompanyStaff.setStaffType(staffType);
			xcompanyStaff.setJobNumber(jobNumber);
			xcompanyStaff.setJobName(jobName);

			xcompanyStaffService.insertSelective(xcompanyStaff);
		}}
		return "redirect:list";
	}

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
