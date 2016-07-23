package com.simi.action.app.xcloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyAdmin;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.po.model.xcloud.XcompanyStaffReq;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyAdminService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffReqService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CompanyAdminSearchVo;
import com.simi.vo.xcloud.CompanySearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.simi.vo.xcloud.XcompanyStaffReqVo;

@Controller
@RequestMapping(value = "/app/company")
public class CompanyStaffController extends BaseController {

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
	private UsersAsyncService userAsyncService;

	
	
	@RequestMapping(value = "/join", method = { RequestMethod.POST })
	public AppResultData<Object> companyReg(
			@RequestParam("user_name") String userName, 
			@RequestParam("sms_token") String smsToken,
			@RequestParam("invitation_code") String invitationCode,
			@RequestParam(value = "name", required = false, defaultValue = "") String name, 
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
			u = usersService.genUser(userName, name, Constants.USER_APP, "");
		}
		userId = u.getId();
		
		CompanySearchVo searchVo1 = new CompanySearchVo();
		searchVo1.setInvitationCode(invitationCode);
		
		Xcompany xCompany = null;
		List<Xcompany> rs = xCompanyService.selectBySearchVo(searchVo1);
		Long companyId = 0L;
		if (rs.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("邀请码不存在!");
			return result;
		} else {
			xCompany = rs.get(0);
		}
		
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
		String title = "团队申请";
		
		String summary =  "你申请加入"+ xCompany.getCompanyName();
		userMsgAsyncService.newActionAppMsg(userId, 0L, "company_pass", title, summary, "http://img.51xingzheng.cn/2997737093caa7e25d98579512053b5c?p=0");
		
		//2.给审批者发出msg信息。
		CompanyAdminSearchVo adminSearchVo = new CompanyAdminSearchVo();
		adminSearchVo.setCompanyId(companyId);
		List<XcompanyAdmin> adminList = xCompanyAdminService.selectBySearchVo(adminSearchVo);
		
		for (XcompanyAdmin item : adminList) {
			Long adminId = item.getUserId();
			String staffName = (StringUtil.isEmpty(u.getName())) ? u.getMobile() : u.getName();
			summary = staffName + "申请加入" + xCompany.getCompanyName() + ".";
			userMsgAsyncService.newActionAppMsg(adminId, 0L, "company_pass", title, summary, "http://img.51xingzheng.cn/2997737093caa7e25d98579512053b5c?p=0");
		}
		
		//统计总公司数
		userAsyncService.statUser(userId, "totalCompanys");
		
		return result;

	}
	
	@RequestMapping(value = "/pass", method = { RequestMethod.POST })
	public AppResultData<Object> companyPass(
			@RequestParam("company_id") Long companyId,
			@RequestParam("user_id") Long userId,
			@RequestParam("req_user_id") Long reqUserId,
			@RequestParam(value = "status", required = false, defaultValue = "1") Short status,
			@RequestParam(value = "remarks", required = false, defaultValue = "") String remarks) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users adminUser = usersService.selectByPrimaryKey(userId);
		Users reqUser = usersService.selectByPrimaryKey(reqUserId);
		if (adminUser == null || reqUser == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("用户不存在.");
			return result;
		}

		Xcompany company = xCompanyService.selectByPrimaryKey(companyId);
		if (company == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("公司信息不存在.");
			return result;
		}
		
		String companyName = company.getCompanyName();

		CompanyAdminSearchVo searchVo1 = new CompanyAdminSearchVo();
		searchVo1.setCompanyId(companyId);
		searchVo1.setUserId(userId);
		
		List<XcompanyAdmin> rs = xCompanyAdminService.selectBySearchVo(searchVo1);
		if (rs.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("你不是团队管理员.");
			return result;
		} 
		
		//是否已经加入团队
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(reqUserId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> rsList = xCompanyStaffService.selectBySearchVo(searchVo);

		XcompanyStaff vo = null;
		if (!rsList.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您已经加入该团队.");
			return result;
		}
		
		//是否已经发出过请求.
		XcompanyStaffReq req = null;
		List<XcompanyStaffReq> xCompanyStaffReqs = xCompanyStaffReqService.selectByUserId(reqUserId);
		if (!xCompanyStaffReqs.isEmpty()) {
			for (XcompanyStaffReq item : xCompanyStaffReqs) {
				if (item.getCompanyId().equals(companyId)) {
					req = item;
					break;
				}
			}
		}
		
		if (req != null) {
			req.setStatus(status);
			req.setRemarks(remarks);
			req.setUpdateTime(TimeStampUtil.getNowSecond());
			xCompanyStaffReqService.updateByPrimaryKey(req);
		}
		
		if (status.equals((short)1)) {
			XcompanyDept defaultDept = xCompanyDeptService.selectByXcompanyIdAndDeptName(companyId, "未分配");
			Long deptId = 0L;
			if (defaultDept != null) {
				deptId = defaultDept.getDeptId();
			}
			// 将用户加入团队员工中
			XcompanyStaff record = xCompanyStaffService.initXcompanyStaff();
			record.setUserId(reqUserId);
			record.setCompanyId(companyId);
			record.setDeptId(deptId);
			record.setJobNumber(xCompanyStaffService.getNextJobNumber(companyId));
			xCompanyStaffService.insertSelective(record);	
		}
		
		//异步产生首页消息信息.
		//1. 给申请者发送
		String title = "团队申请";
		
		String summary =  "你已经通过"+ companyName + "申请.";
		if (status.equals((short)2)) {
			summary =  "你申请加入"+ companyName + "已被拒绝.";
		}
		userMsgAsyncService.newActionAppMsg(userId, 0L, "company_pass", title, summary, "http://img.51xingzheng.cn/2997737093caa7e25d98579512053b5c?p=0");
		
		//2.给审批者发出msg信息。
		String staffName = (StringUtil.isEmpty(reqUser.getName())) ? reqUser.getMobile() : reqUser.getName();
		summary = "你已经通过" + staffName + "申请加入" +companyName + "的请求.";
		if (status.equals((short)2)) {
			summary = "你已经拒绝" + staffName + "申请加入" + companyName + "的请求.";
		}
		userMsgAsyncService.newActionAppMsg(userId, 0L, "company_pass", title, summary, "http://img.51xingzheng.cn/2997737093caa7e25d98579512053b5c?p=0");

		return result;

	}	

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
			result.setMsg("你不是该团队员工");
			return result;
		}
		
		XcompanyStaff record = rsList.get(0);
		
		if (!record.getCompanyId().equals(companyId)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("你不是该团队员工");
			return result;
		}
		
		//把用户所属的其他的团队设为不默认
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/get_pass", method = { RequestMethod.GET })
	public AppResultData<Object> getPass(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "company_id", required = false, defaultValue = "0") Long companyId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		if (companyId.equals(0L)) {
			CompanyAdminSearchVo searchVo1 = new CompanyAdminSearchVo();
			searchVo1.setUserId(userId);
			searchVo1.setIsCreate((short) 1);
			XcompanyAdmin xCompanyAdmin = null;
			List<XcompanyAdmin> rs = xCompanyAdminService.selectBySearchVo(searchVo1);
			
			if (rs != null && rs.size() > 0) {
				xCompanyAdmin = rs.get(0);
				companyId = xCompanyAdmin.getCompanyId();
			}
		}
		
		if (companyId.equals(0L)) return result;
		
		List<XcompanyStaffReq> xCompanyStaffReqs = new ArrayList<XcompanyStaffReq>();
		
		
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		

		searchVo.setCompanyId(companyId);

		
		PageInfo plist = xCompanyStaffReqService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		
		xCompanyStaffReqs = plist.getList();
		
		List<XcompanyStaffReqVo> datas = new ArrayList<XcompanyStaffReqVo>();
		datas = xCompanyStaffReqService.getVos(xCompanyStaffReqs, userId);
		
		result.setData(datas);
		
		
		return result;
	}
}
