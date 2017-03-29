package com.simi.action.app.xcloud;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.zxing.WriterException;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyAdmin;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.async.UserScoreAsyncService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyAdminService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffReqService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.utils.XcompanyUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CompanyAdminSearchVo;
import com.simi.vo.xcloud.CompanySearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Controller
@RequestMapping(value = "/app/company")
public class CompanyController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;
	
	@Autowired
	private XcompanyAdminService xCompanyAdminService;

	@Autowired
	private UserSmsTokenService smsTokenService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private XcompanyDeptService xCompanyDeptService;

	@Autowired
	private XcompanyStaffService xCompanyStaffService;
	
	@Autowired
	private XcompanyStaffReqService xCompanyStaffReqService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	@Autowired
	private UserScoreAsyncService userScoreAsyncService;

	@RequestMapping(value = "/reg", method = { RequestMethod.POST })
	public AppResultData<Object> companyReg(
			@RequestParam("user_name") String userName, 
			@RequestParam("sms_token") String smsToken,
			@RequestParam(value = "company_id", required = false, defaultValue = "0") Long companyId, 
			@RequestParam("company_name") String companyName,
			@RequestParam("short_name") String shortName, @RequestParam("password") String password) throws NoSuchAlgorithmException, WriterException,
			IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(smsToken) || StringUtil.isEmpty(companyName) || StringUtil.isEmpty(shortName)
				|| StringUtil.isEmpty(password)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("数据异常");
			return result;
		}

		String mobile = userName;
		AppResultData<Object> validateResult = smsTokenService.validateSmsToken(mobile, smsToken, (short) 3);

		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("验证码错误");
			return result;
		}

		// 然后判断用户是否已经存在，不存在则添加新用户
		Users u = usersService.selectByMobile(mobile);

		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = usersService.genUser(mobile, "", "", Constants.USER_XCOULD, Constants.USER_TYPE_0, "");
		}

		Long userId = u.getId();
		// 验证是否出现重名的情况.
		CompanyAdminSearchVo searchVo = new CompanyAdminSearchVo();
		searchVo.setCompanyName(companyName);
		searchVo.setUserName(userName);
		List<XcompanyAdmin> rs = xCompanyAdminService.selectBySearchVo(searchVo);
		if (!rs.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您已经注册过" + companyName);
			return result;
		}

		String passwordMd5 = StringUtil.md5(password.trim());

		Xcompany xCompany = xCompanyService.initXcompany();
		if (companyId > 0L) {
			xCompany = xCompanyService.selectByPrimaryKey(companyId);
		}

		xCompany.setCompanyName(companyName);
		xCompany.setShortName(shortName);

		if (companyId > 0L) {
			xCompany.setUpdateTime(TimeStampUtil.getNowSecond());
			xCompanyService.updateByPrimaryKeySelective(xCompany);
			return result;
		}
		
		String invitationCode = StringUtil.generateShortUuid();
		xCompany.setInvitationCode(invitationCode);
		
		xCompanyService.insert(xCompany);
	
		//创建管理员关联表
		XcompanyAdmin xcompanyAdmin = xCompanyAdminService.initXcompanyAdmin();
		xcompanyAdmin.setCompanyId(companyId);
		xcompanyAdmin.setCompanyName(companyName);
		xcompanyAdmin.setUserId(userId);
		xcompanyAdmin.setUserName(userName);
		xcompanyAdmin.setPassword(passwordMd5);
		xcompanyAdmin.setIsCreate(1);
		
		xCompanyAdminService.insert(xcompanyAdmin);
		
		xCompanyService.regExtend(userId, companyId);
		
		return result;

	}
	
	@RequestMapping(value = "/reg_app", method = { RequestMethod.POST })
	public AppResultData<Object> companyRegApp(
			@RequestParam("user_id") Long userId,
			@RequestParam("city_id") Long cityId, 
			@RequestParam("company_name") String companyName,
			@RequestParam("short_name") String shortName, 
			@RequestParam("company_size") Short companySize) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);
		
		if (StringUtil.isEmpty(u.getMobile())) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您的信息不完善，请完善手机号后再来企业注册.");
			return result;
		}
		
		// 验证是否出现重名的情况.
		CompanyAdminSearchVo searchVo = new CompanyAdminSearchVo();
		searchVo.setCompanyName(companyName);
		searchVo.setUserId(userId);
		List<XcompanyAdmin> rs = xCompanyAdminService.selectBySearchVo(searchVo);
		if (!rs.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您已经注册过" + companyName);
			return result;
		}

		String passwordMd5 = "";
		try {
			passwordMd5 = StringUtil.md5("000000");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Xcompany xCompany = xCompanyService.initXcompany();
		xCompany.setCompanyName(companyName);
		xCompany.setShortName(shortName);
		xCompany.setCityId(cityId);
		xCompany.setCompanySize(companySize);
		
		String invitationCode = StringUtil.generateShortUuid();
		xCompany.setInvitationCode(invitationCode);

		xCompanyService.insert(xCompany);
		Long companyId = xCompany.getCompanyId();

		//创建管理员关联表
		XcompanyAdmin xcompanyAdmin = xCompanyAdminService.initXcompanyAdmin();
		xcompanyAdmin.setCompanyId(companyId);
		xcompanyAdmin.setCompanyName(companyName);
		xcompanyAdmin.setUserId(userId);
		xcompanyAdmin.setUserName(u.getMobile());
		xcompanyAdmin.setPassword(passwordMd5);
		xcompanyAdmin.setIsCreate(1);
		
		xCompanyAdminService.insert(xcompanyAdmin);
		
		xCompanyService.regExtend(userId, companyId);
		
		return result;

	}	
	
	@RequestMapping(value = "/get_detail", method = { RequestMethod.GET })
	public AppResultData<Object> getDetail(
			@RequestParam("user_id") Long userId, 
			@RequestParam(value = "company_id", required = false, defaultValue = "0") Long companyId, 
			@RequestParam(value = "invitation_code", required = false, defaultValue = "") String invitationCode
			) throws WriterException, IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		if (companyId.equals(0L) && StringUtil.isEmpty(invitationCode)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("没有企业信息.");
			return result;
		}
		
		if (companyId.equals(0L) && !StringUtil.isEmpty(invitationCode)) {
			
			CompanySearchVo searchVo1 = new CompanySearchVo();
			searchVo1.setInvitationCode(invitationCode);
			
			Xcompany xCompany = null;
			List<Xcompany> rs = xCompanyService.selectBySearchVo(searchVo1);
			if (rs.isEmpty()) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("邀请码不存在!");
				return result;
			} else {
				xCompany = rs.get(0);
			}
			
			if (xCompany != null) {
				companyId = xCompany.getCompanyId();
			}
		}
		
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> rsList = xCompanyStaffService.selectBySearchVo(searchVo);
		
		if (rsList.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("你不是该团队员工");
			return result;
		}
		
		XcompanyStaff record = rsList.get(0);
		
		if (!record.getCompanyId().equals(companyId)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("你不是该团队员工");
			return result;
		}
		
		Xcompany xCompany = xCompanyService.selectByPrimaryKey(companyId);
		
		if (xCompany == null) return result;
		
		Map<String, Object> vo = new HashMap<String, Object>();

		vo.put("companyId", companyId);

		vo.put("companyName", xCompany.getCompanyName());
		
		vo.put("companyType", xCompany.getCompanyType());
		
		vo.put("shortName", xCompany.getShortName());
		
		
		if (StringUtil.isEmpty(xCompany.getInvitationCode())) {
			invitationCode = StringUtil.generateShortUuid();
			xCompany.setInvitationCode(invitationCode);
			xCompanyService.updateByPrimaryKey(xCompany);
		}

		String qrCode = xCompany.getQrCode();
		if (StringUtil.isEmpty(qrCode)) {
			qrCode = XcompanyUtil.genQrCode(xCompany.getInvitationCode());
			xCompany.setQrCode(qrCode);
			xCompanyService.updateByPrimaryKey(xCompany);
		}

		vo.put("invitationCode", xCompany.getInvitationCode());
		vo.put("qrCode", xCompany.getQrCode());
		
		vo.put("addTimeStr", TimeStampUtil.timeStampToDateStr(xCompany.getAddTime() * 1000));
		
		result.setData(vo);
		return result;
	}
}
