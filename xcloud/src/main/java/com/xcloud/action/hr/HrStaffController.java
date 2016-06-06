package com.xcloud.action.hr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.ExcelUtil;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.StringUtil;
import com.simi.vo.AppResultData;
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
import com.simi.vo.xcloud.StaffListVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;

@Controller
@RequestMapping(value = "/hr")
public class HrStaffController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyDeptService xcompanyDeptService;

	@AuthPassport
	@RequestMapping(value = "/staff-list", method = { RequestMethod.GET })
	public String staffList(Model model, HttpServletRequest request, 
			UserCompanySearchVo searchVo,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "staff_id", required = false) Long staffId) {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();
		
		model.addAttribute("companyId", companyId);
				
		List<XcompanyDept> deptList = xcompanyDeptService.selectByXcompanyId(companyId);

		model.addAttribute("deptList", deptList);
		
		if (searchVo == null) searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		
		searchVo.setStatus((short) 1);
		
		//判断姓名或者手机号
		String keyword = searchVo.getKeyword();
		if (!StringUtil.isEmpty(keyword)) {
			if (RegexUtil.isDigits(keyword)) {
				searchVo.setMobile(keyword);
			} else {
				searchVo.setName(keyword);
			}
		}
		
		PageInfo result = xcompanyStaffService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);


		
		model.addAttribute("contentModel", result);
		
		return "hr/staff-list";
	}

	@AuthPassport
	@RequestMapping(value = "/staff-form", method = { RequestMethod.GET })
	public String staffUserForm(Model model, HttpServletRequest request, @RequestParam(value = "staff_id", required = false) Long staffId) {

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

			String jobNumber = xcompanyStaffService.getNextJobNumber(companyId);
			vo.setJobNumber(jobNumber);

			if (staffId > 0L) {

				xcompanyStaff = xcompanyStaffService.selectByPrimarykey(staffId);

				userId = xcompanyStaff.getUserId();

				u = usersService.selectByPrimaryKey(userId);

				BeanUtilsExp.copyPropertiesIgnoreNull(xcompanyStaff, vo);

				BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);

			}
			// 注意这里user表id 和 xcompanyStaff表的id同名，所以需要手动设置
			vo.setId(xcompanyStaff.getId());
			vo.setCompanyId(companyId);

			model.addAttribute("contentModel", vo);
		}

		model.addAttribute("xCompany", xCompany);

		List<XcompanyDept> deptList = xcompanyDeptService.selectByXcompanyId(companyId);

		model.addAttribute("deptList", deptList);

		return "/hr/staff-form";
	}
}
