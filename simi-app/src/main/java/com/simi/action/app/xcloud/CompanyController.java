package com.simi.action.app.xcloud;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.zxing.WriterException;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.po.model.xcloud.XcompanyStaffReq;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffReqService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.utils.OrderUtil;
import com.simi.utils.XcompanyUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Controller
@RequestMapping(value = "/app/company")
public class CompanyController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;

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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/reg", method = { RequestMethod.POST })
	public AppResultData<Object> companyReg(@RequestParam("user_name") String userName, @RequestParam("sms_token") String smsToken,
			@RequestParam(value = "company_id", required = false, defaultValue = "0") Long companyId, @RequestParam("company_name") String companyName,
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
			u = usersService.genUser(mobile, "", Constants.USER_XCOULD);
		}

		// 验证是否出现重名的情况.
		Xcompany xCompany = xCompanyService.selectByCompanyNameAndUserName(companyName, userName);
		if (xCompany != null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您已经注册过" + companyName);
			return result;
		}

		String passwordMd5 = StringUtil.md5(password.trim());

		xCompany = xCompanyService.initXcompany();
		if (companyId > 0L) {
			xCompany = xCompanyService.selectByPrimaryKey(companyId);
		}

		xCompany.setUserName(userName);
		xCompany.setPassword(passwordMd5);
		xCompany.setCompanyName(companyName);
		xCompany.setShortName(shortName);

		if (companyId > 0L) {
			xCompany.setUpdateTime(TimeStampUtil.getNowSecond());
			xCompanyService.updateByPrimaryKeySelective(xCompany);
		} else {

			// 生成团队唯一邀请码
			String invitationCode = StringUtil.generateShortUuid();
			xCompany.setInvitationCode(invitationCode);
			xCompanyService.insert(xCompany);
			companyId = xCompany.getCompanyId();

			// 生成团队邀请二维码
			String imgUrl = XcompanyUtil.genQrCode(invitationCode);

			xCompany.setQrCode(imgUrl);
			xCompanyService.updateByPrimaryKeySelective(xCompany);
		}

		// 团队部门预置信息.
		List<String> defaultDepts = MeijiaUtil.getDefaultDept();
		for (int i = 0; i < defaultDepts.size(); i++) {
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
		// 将用户加入团队员工中
		XcompanyStaff record = xCompanyStaffService.initXcompanyStaff();
		record.setUserId(u.getId());
		record.setCompanyId(companyId);
		record.setDeptId(deptId);
		record.setIsDefault((short) 1);
		record.setJobNumber(xCompanyStaffService.getNextJobNumber(companyId));
		xCompanyStaffService.insertSelective(record);

		return result;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/join", method = { RequestMethod.POST })
	public AppResultData<Object> companyReg(
			@RequestParam("user_name") String userName, 
			@RequestParam("sms_token") String smsToken,
			@RequestParam("invitation_code") String invitationCode,
			@RequestParam(value = "remarks", required = false, defaultValue = "") String remarks) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(smsToken) || StringUtil.isEmpty(invitationCode)) {
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

		Users u = usersService.selectByMobile(userName);
		Long userId = 0L;
		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = usersService.genUser(userName, "", Constants.USER_XCOULD);
		}
		userId = u.getId();

		Xcompany xCompany = xCompanyService.selectByInvitationCode(invitationCode);
		Long companyId = 0L;
		if (xCompany == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("邀请码不存在!");
			return result;
		}
		companyId = xCompany.getCompanyId();
		
		//是否已经加入团队
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> rsList = xCompanyStaffService.selectBySearchVo(searchVo);

		XcompanyStaff vo = null;
		if (!rsList.isEmpty()) {
			vo = rsList.get(0);
		}

		if (vo != null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您已经加入该团队.");
			return result;
		}
		
		//是否已经发出过请求.
		List<XcompanyStaffReq> xCompanyStaffReqs = xCompanyStaffReqService.selectByUserId(userId);
		if (!xCompanyStaffReqs.isEmpty()) {
			for (XcompanyStaffReq item : xCompanyStaffReqs) {
				if (item.getCompanyId().equals(companyId)) {
					result.setStatus(Constants.ERROR_999);
					result.setMsg("您已经申请过加入该团队.");
					return result;
				}
			}
		}
		
		XcompanyStaffReq record = xCompanyStaffReqService.initXcompanyStaffReq();
		record.setCompanyId(companyId);
		record.setUserId(userId);
		record.setRemarks("");
		xCompanyStaffReqService.insert(record);
		
		//异步产生首页消息信息.
		
		
		//1. 给申请者发送
		String title = "申请加入团队";
		
		String summary =  "你申请加入"+ xCompany.getCompanyName();
		userMsgAsyncService.newActionAppMsg(userId, 0L, "company_pass", title, summary, "http://img.51xingzheng.cn/2997737093caa7e25d98579512053b5c?p=0");
		
		//2.给审批者发出msg信息。
		String adminMobile = xCompany.getUserName();
		Users xCompanyAdmin = usersService.selectByMobile(adminMobile);
		if (xCompanyAdmin == null) return result;
		Long adminId = xCompanyAdmin.getId();
		
		title = "员工审批";
		String staffName = (StringUtil.isEmpty(u.getName())) ? u.getMobile() : u.getName();
		summary = "新员工" + staffName + "申请加入团队,请关注.";
		userMsgAsyncService.newActionAppMsg(adminId, 0L, "company_pass", title, summary, "http://img.51xingzheng.cn/2997737093caa7e25d98579512053b5c?p=0");
		
//		XcompanyDept defaultDept = xCompanyDeptService.selectByXcompanyIdAndDeptName(companyId, "未分配");
//		Long deptId = 0L;
//		if (defaultDept != null) {
//			deptId = defaultDept.getDeptId();
//		}
//		// 将用户加入团队员工中
//		XcompanyStaff record = xCompanyStaffService.initXcompanyStaff();
//		record.setUserId(u.getId());
//		record.setCompanyId(companyId);
//		record.setDeptId(deptId);
//		record.setJobNumber(xCompanyStaffService.getNextJobNumber(companyId));
//		xCompanyStaffService.insertSelective(record);

		return result;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/get_by_user", method = { RequestMethod.GET })
	public AppResultData<Object> getByUserId(@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> companyList = xCompanyStaffService.selectBySearchVo(searchVo);

		if (companyList.isEmpty()) {
			return result;
		}

		List<Map> resultMap = new ArrayList<Map>();

		for (XcompanyStaff item : companyList) {
			Map<String, Object> vo = new HashMap<String, Object>();

			vo.put("company_id", item.getCompanyId());

			Xcompany xCompany = xCompanyService.selectByPrimaryKey(item.getCompanyId());

			vo.put("company_name", xCompany.getCompanyName());
			
			vo.put("is_default", item.getIsDefault().toString());
			resultMap.add(vo);
		}
		result.setData(resultMap);

		return result;
	}
	
	@RequestMapping(value = "/set_default", method = { RequestMethod.GET })
	public AppResultData<Object> setDefault(
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> rsList = xCompanyStaffService.selectBySearchVo(searchVo);
		
		if (rsList.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("你不是该企业员工");
			return result;
		}
		
		XcompanyStaff record = rsList.get(0);
		
		if (!record.getCompanyId().equals(companyId)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("你不是该企业员工");
			return result;
		}
		
		//把用户所属的其他的企业设为不默认
		searchVo = new UserCompanySearchVo();
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> list = xCompanyStaffService.selectBySearchVo(searchVo);
		for (XcompanyStaff item : list) {
			item.setIsDefault((short) 0);
			xCompanyStaffService.updateByPrimaryKeySelective(item);
		}
		
		
		record.setIsDefault((short) 1);
		xCompanyStaffService.updateByPrimaryKeySelective(record);
		return result;
	}
	
	@RequestMapping(value = "/get_detail", method = { RequestMethod.GET })
	public AppResultData<Object> getDetail(
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId) throws WriterException, IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> rsList = xCompanyStaffService.selectBySearchVo(searchVo);
		
		if (rsList.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("你不是该企业员工");
			return result;
		}
		
		XcompanyStaff record = rsList.get(0);
		
		if (!record.getCompanyId().equals(companyId)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("你不是该企业员工");
			return result;
		}
		
		Xcompany xCompany = xCompanyService.selectByPrimaryKey(companyId);
		
		if (xCompany == null) return result;
		
		Map<String, Object> vo = new HashMap<String, Object>();

		vo.put("companyId", companyId);

		vo.put("companyName", xCompany.getCompanyName());
		
		vo.put("companyType", xCompany.getCompanyType());
		
		vo.put("shortName", xCompany.getShortName());
		
		
		String invitationCode = xCompany.getInvitationCode();
		if (StringUtil.isEmpty(invitationCode)) {
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
