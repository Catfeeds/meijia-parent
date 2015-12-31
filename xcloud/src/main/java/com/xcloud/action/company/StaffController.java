package com.xcloud.action.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.ExcelUtil;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.StringUtil;
import com.simi.vo.AppResultData;
import com.fasterxml.jackson.databind.ObjectMapper;
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

			String jobNumber = xcompanyStaffService.getNextJobNumber(companyId);
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
		
		Long companyId = accountAuth.getCompanyId();
		
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
			u = usersService.genUser(mobile, vo.getName(), Constants.USER_XCOULD);
			
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
			xcompanyStaff.setJobNumber(xcompanyStaffService.getNextJobNumber(companyId));
			xcompanyStaffService.insertSelective(xcompanyStaff);
		}
		
		//todo 给相应的用户进行通知
		
		return "redirect:list";
	}
	
	@AuthPassport
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public AppResultData<Object> deleteByRechargeCouponId(HttpServletRequest request,
			@RequestParam(value = "staff_id") Long staffId) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		if (staffId != null) {
			
			// 获取登录的用户
			AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
			XcompanyStaff vo = xcompanyStaffService.selectByPrimarykey(staffId);
			Long companyId = accountAuth.getCompanyId();			
			if (vo.getCompanyId().equals(companyId)) {
				xcompanyStaffService.deleteByPrimaryKey(staffId);
			}
		}
		return result;
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
	
	@AuthPassport
	@RequestMapping(value = "/staff-import", method = { RequestMethod.GET })
	public String staffImportPre(Model model, HttpServletRequest request) {
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();
		
		Xcompany xCompany = xCompanyService.selectByPrimaryKey(companyId);		
		
		model.addAttribute("xCompany", xCompany);
		
		return "/staffs/staff-import";
	}	
	
	@AuthPassport
	@RequestMapping(value = "/staff-import", method = { RequestMethod.POST })
	public String doStaffImportPre(Model model, HttpServletRequest request) throws Exception {
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();
				
		// 创建一个通用的多部分解析器.
		String path = "/data/attach/staff/";
		String fileToken = RandomUtil.randomNumber(6);
		String newFileName = "";
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		if (multipartResolver.isMultipart(request)) {
			// 判断 request 是否有文件上传,即多部分请求...
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
			
			MultipartFile file = multiRequest.getFile("staff-file");
			if (file != null && !file.isEmpty()) {
				 // 存储的临时文件名 = 获取时间戳+随机六位数+"."图片扩展名
				String fileName = file.getOriginalFilename();
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				
				newFileName = companyId.toString() + "-" + fileToken + "." + ext;
				
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path,newFileName));
				
			}
			
		}
		
		if (StringUtil.isEmpty(newFileName)) {
			model.addAttribute("errors", "表格数据为空，请下载模板后填写.");
			return "/staffs/staff-import-error";
		}
		
		List<Object> excelDatas = new ArrayList<Object>();
		
		InputStream in = new FileInputStream(path + newFileName);
		excelDatas = ExcelUtil.readToList(path + newFileName, in, 0, 0);
		
		//校验表格是否正确.
		AppResultData<Object> validateResult = xcompanyStaffService.validateStaffImport(companyId, excelDatas);
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			model.addAttribute("errors", validateResult.getMsg());
			model.addAttribute("tableDatas", validateResult.getData());
			return "/staffs/staff-import-error";
		}
		
		//检测都有哪些重复数据
		List<Object> dupList = xcompanyStaffService.checkDuplication(companyId, excelDatas);
		int totalNewCount = excelDatas.size() - 1;
		model.addAttribute("totalNewCount", totalNewCount);
		
		int totalUpdateCount = 0;
		model.addAttribute("tableDatas", "");
		if (!dupList.isEmpty()) {
			totalUpdateCount = dupList.size();
			model.addAttribute("tableDatas", dupList);
		}
		
		model.addAttribute("totalUpdateCount", totalUpdateCount);
		model.addAttribute("newFileName", newFileName);
		
		return "/staffs/staff-import-confirm";
	}		
	
	@AuthPassport
	@RequestMapping(value = "/staff-import-do", method = { RequestMethod.POST })
	public String doStaffImport(Model model, HttpServletRequest request) throws Exception {
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();

		// 创建一个通用的多部分解析器.
		String path = "/data/attach/staff/";
		String newFileName = request.getParameter("newFileName").toString();
		
		if (StringUtil.isEmpty(newFileName)) {
			model.addAttribute("errors", "表格数据为空，请下载模板后填写.");
			return "/staffs/staff-import-error";
		}
		
		List<Object> excelDatas = new ArrayList<Object>();
		
		InputStream in = new FileInputStream(path + newFileName);
		excelDatas = ExcelUtil.readToList(path + newFileName, in, 0, 0);
		
		AppResultData<Object> validateResult = xcompanyStaffService.staffImport(companyId, excelDatas);
		
		return "/staffs/staff-import-ok";
	}		
			

}
