package com.xcloud.action.company;

import java.util.ArrayList;
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
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictCoupons;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserApplyVo;
import com.simi.vo.xcloud.UserCompanyFormVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/staff")
public class StaffController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private XCompanyService xCompanyService;

	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String staffTreeAndList(HttpServletRequest request, Model model,
	// UserCompanySearchVo searchVo,
			@RequestParam(value = "dept_id", required = false) Long deptId) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		// model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		// searchVo.setSecId(secId);
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();
		// deptId = 2L;
		if (deptId == null || deptId == 0L) {
			List<XcompanyStaff> list = xcompanyStaffService
					.selectByCompanyId(companyId);

			List<Long> userIds = new ArrayList<Long>();
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					userIds.add(list.get(i).getUserId());
				}
				PageInfo result = usersService.searchVoListPage(userIds,
						pageNo, pageSize);
				model.addAttribute("companyId", accountAuth.getCompanyId());
				model.addAttribute("companyName", accountAuth.getCompanyName());
				model.addAttribute("shortName", accountAuth.getShortName());
				model.addAttribute("contentModel", result);
			}
		} else {
			List<XcompanyStaff> list = xcompanyStaffService
					.selectByCompanyIdAndDeptId(companyId, deptId);

			List<Long> userIds = new ArrayList<Long>();
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					userIds.add(list.get(i).getUserId());
				}
				PageInfo result = usersService.searchVoListPage(userIds,
						pageNo, pageSize);
				model.addAttribute("companyId", accountAuth.getCompanyId());
				model.addAttribute("companyName", accountAuth.getCompanyName());
				model.addAttribute("shortName", accountAuth.getShortName());
				model.addAttribute("contentModel", result);
			}
		}

		return "/staffs/staff-list";
	}

	@RequestMapping(value = "/userForm", method = { RequestMethod.GET })
	public String staffUserForm(Model model, HttpServletRequest request,
			@RequestParam(value = "id", required = false) Long userId) {

		UserCompanyFormVo vo = new UserCompanyFormVo();

		if (userId > 0L) {

			XcompanyStaff xcompanyStaff = xcompanyStaffService.selectByUserId(userId);

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
	}

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
			
			XcompanyStaff xcompanyStaff = xcompanyStaffService.selectByUserId(users.getId());
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

}
