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
				@RequestParam("service_type_id") Long serviceTypeId,
				@RequestParam(value = "user_id",required = false, defaultValue = "0") Long userId) {
			
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
					users.setUserType((short)2);
					users.setMobile(mobile);
					usersService.insert(users);
				}
				if (users != null && users.getUserType()==0) {
					
					users.setUserType((short)2);
					usersService.updateByPrimaryKeySelective(users);
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
		//进入店铺注册页面是，先查询是否是已有用户，若是，就将信息查出来，放到表单中
		@RequestMapping(value = "get_exist_by_user_id", method = RequestMethod.GET)
		public AppResultData<Object> getPartnerId(@RequestParam("user_id") Long userId) {
			
			AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
			
			Users users = usersService.selectByPrimaryKey(userId);
			
			if (users != null) {
			result.setData(users);
			}
			
			return result;
		}
}