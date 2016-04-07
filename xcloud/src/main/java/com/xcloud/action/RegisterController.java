package com.xcloud.action;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.zxing.WriterException;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.utils.XcompanyUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CompanySearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Controller
@RequestMapping(value = "/")
public class RegisterController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private UsersService usersService;	
	
	@Autowired
	private UserSmsTokenService smsTokenService;	
	
	@Autowired
	private XcompanyDeptService xCompanyDeptService;
	
	@Autowired
	private XcompanyStaffService xCompanyStaffService;
	
	@RequestMapping(value="/register", method = {RequestMethod.GET})
    public String register(Model model) {
		
		if (!model.containsAttribute("contentModel")) {
			Xcompany xCompany = xCompanyService.initXcompany();
			model.addAttribute("contentModel", xCompany);
		}
		
        return "/home/register";
    }	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/register", method = {RequestMethod.POST})
    public String doRegister(HttpServletRequest request, 
    						 HttpServletResponse response,  
    						 Model model,
    						 @ModelAttribute("contentModel") Xcompany xCompanyVo, 
    						 BindingResult result) throws NoSuchAlgorithmException, WriterException, IOException {
        
		String mobile = xCompanyVo.getUserName();
		String smsToken = request.getParameter("sms_token");
		
		AppResultData<Object> validateResult = smsTokenService.validateSmsToken(mobile, smsToken, (short) 3);
		
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			result.addError(new FieldError("contentModel","userName","验证码错误"));
			model.addAttribute("contentModel", xCompanyVo);
			return register(model);
		}
		
		//然后判断用户是否已经存在，不存在则添加新用户
		Users u = usersService.selectByMobile(mobile);
		
		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = usersService.genUser(mobile, "", Constants.USER_XCOULD, "");
		}
		Long userId = u.getId();
		
		String companyName = xCompanyVo.getCompanyName().trim();
		//验证团队与用户是否已经存在
		CompanySearchVo searchVo1 = new CompanySearchVo();
		searchVo1.setUserName(mobile);
		searchVo1.setCompanyName(companyName);
		
		
		List<Xcompany> rs = xCompanyService.selectBySearchVo(searchVo1);
		
		if (!rs.isEmpty()) {
			result.addError(new FieldError("contentModel","userName","您已经注册过此团队."));
			model.addAttribute("contentModel", xCompanyVo);
			return register(model);
		}
		
		//获得注册的form信息
		Long companyId = xCompanyVo.getCompanyId();
		
		String password = xCompanyVo.getPassword();
		
		String passwordMd5 = StringUtil.md5(password.trim());
		
		Xcompany xCompany = xCompanyService.initXcompany();
		if (companyId > 0L) {
			xCompany = xCompanyService.selectByPrimaryKey(companyId);
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(xCompanyVo, xCompany);
		
		xCompany.setPassword(passwordMd5);
		
		if (companyId > 0L) {
			xCompany.setUpdateTime(TimeStampUtil.getNowSecond());
			xCompanyService.updateByPrimaryKeySelective(xCompany);
		} else {
			
			//生成团队唯一邀请码
			String invitationCode = StringUtil.generateShortUuid();
			xCompany.setInvitationCode(invitationCode);
			xCompanyService.insert(xCompany);
			companyId = xCompany.getCompanyId();
			
			//生成团队邀请二维码
			String imgUrl = XcompanyUtil.genQrCode(invitationCode);

			xCompany.setQrCode(imgUrl);
			xCompanyService.updateByPrimaryKeySelective(xCompany);
			
		}
		
		
		
		//团队部门预置信息.
		List<String> defaultDepts = MeijiaUtil.getDefaultDept();
		for (int i = 0 ; i < defaultDepts.size(); i++) {
			XcompanyDept dept = xCompanyDeptService.initXcompanyDept();
			dept.setName(defaultDepts.get(i));
			dept.setCompanyId(companyId);
			dept.setParentId(0L);
			xCompanyDeptService.insert(dept);
		}
		
		XcompanyDept defaultDept = xCompanyDeptService.selectByXcompanyIdAndDeptName(companyId, "未分配");
		Long deptId = 0L;
		if (defaultDept != null) {
			deptId = defaultDept.getDeptId();
		}
		
		
		//将用户加入团队员工中
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);

		List<XcompanyStaff> rsList = xCompanyStaffService.selectBySearchVo(searchVo);
		XcompanyStaff record = null;
		if (!rsList.isEmpty()) {
			record = rsList.get(0);
		}
		if (record == null) {
			record = xCompanyStaffService.initXcompanyStaff();
		}
		record.setUserId(userId);
		record.setCompanyId(companyId);
		record.setDeptId(deptId);
		record.setJobNumber(xCompanyStaffService.getNextJobNumber(companyId));
		xCompanyStaffService.insertSelective(record);
		
		return "redirect:/register-ok";
    }	
	
	@RequestMapping(value="/register-ok", method = {RequestMethod.GET})
    public String registerOk(Model model) {
        return "/home/register-ok";
    }	
	
}
