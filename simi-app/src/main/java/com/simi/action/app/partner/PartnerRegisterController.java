package com.simi.action.app.partner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderQueryService;
import com.simi.service.partners.PartnerRefServiceTypeService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.sec.SecService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.UserSearchVo;
import com.simi.vo.sec.SecViewVo;
import com.simi.vo.user.UserViewVo;

@Controller
@RequestMapping(value = "/app/partner")
public class PartnerRegisterController extends BaseController {

	@Autowired
	private PartnersService partnersService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private PartnerRefServiceTypeService partnerRefServiceTypeService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private PartnerUserService partnerUserService;
	/**
	 * 店铺注册
	 * 
	 * @param request
	 * @param mobile
	 * @param smsToken
	 * @param name
	 * @param smsType
	 * @param userType
	 * @return
	 */
	
	// 判断验证码是否正确，进入注册页面进行注册
/*	@RequestMapping(value = "partnerRegister", method = RequestMethod.POST)
	public AppResultData<Object> partnerRegister(
			HttpServletRequest request,
			@RequestParam("register_type") short registerType,
			@RequestParam("mobile") String mobile,
			@RequestParam("company_name") String companyName,
			@RequestParam("service_type_id") String serviceTypeId) 
			) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		
		if (!mobile.equals("17090397828") && !mobile.equals("17090397818")
				&& !mobile.equals("17090397822")
				&& !mobile.equals("13701187136")
				&& !mobile.equals("13810002890")
				&& !mobile.equals("18610807136")
				&& !mobile.equals("18612514665")
				&& !mobile.equals("13146012753")
				&& !mobile.equals("15727372986")) {

			Users user = userService.selectByMobile(mobile);
			// 如果秘书或用户存在
			if (user != null) {
				// 秘书已存在
				if (user.getUserType() == 1) {
					result.setStatus(Constants.ERROR_999);
					result.setMsg(ConstantMsg.SEC_EXIST_MG);
					return result;
				}
				// 已注册过，是否注册秘书
				if (user.getUserType() == 0) {
					result = new AppResultData<Object>(Constants.ERROR_999,
							ConstantMsg.USER_EXIST_SEC_MG, user);

					return result;
				}
			}
		}
		Users u = userService.initUsers();
		u.setMobile(mobile);
	//	u.setName(name);

		// 注册为美家公司的一员.

		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, u);
		return result;
	}*/
	/**
	 * 店铺注册信息提交
	 * @param request
	 * @param mobile
	 * @param name
	 * @param realName
	 * @param sex
	 * @param degreeId
	 * @param major
	 * @param idCard
	 * @param tagIds
	 * @return
	 */
		@RequestMapping(value = "post_partner_register", method = RequestMethod.POST)
		public AppResultData<Object> partnerRegister(
				HttpServletRequest request,
				@RequestParam("register_type") short registerType,
				@RequestParam("mobile") String mobile,
				@RequestParam("company_name") String companyName,
				@RequestParam("service_type_id") Long serviceTypeId) {
			
			AppResultData<Object> result = new AppResultData<Object>(
					Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

				Partners partners = partnersService.selectByCompanyNames(companyName);
				// 公司名称如果有重复，则不能创建流程
				if (partners != null) {
					result.setStatus(Constants.ERROR_999);
					result.setMsg(ConstantMsg.XCOMPANY_NAME_EXIST);
					return result;
				}
				//公司不存在，新增
				partners = partnersService.iniPartners();
				partners.setCompanyName(companyName);
				partners.setStatus((short)3L);
				partners.setRegisterType(registerType);
				partners.setAddTime(TimeStampUtil.getNowSecond());
				partnersService.insert(partners);
				//手机号对应的用户表 users 没有则创建
				Users users = usersService.selectByMobile(mobile);
				if (users == null) {
					
					users = usersService.initUsers();
					users.setMobile(mobile);
					usersService.insert(users);
				}
				//更新后的partnes表
				partners = partnersService.selectByCompanyNames(companyName);
				
				// 将此公司和用户建立关系
				PartnerUsers partnerUsers = partnerUserService.iniPartnerUsers();
				partnerUsers.setUserId(users.getId());
				partnerUsers.setPartnerId(partners.getPartnerId());
				partnerUsers.setServiceTypeId(serviceTypeId);
				partnerUserService.insert(partnerUsers);
		
			return result;
}
}