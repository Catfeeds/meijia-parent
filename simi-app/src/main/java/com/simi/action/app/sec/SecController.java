package com.simi.action.app.sec;

import java.util.ArrayList;
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
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.UserRef;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.simi.service.async.NoticeSmsAsyncService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.sec.SecService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.sec.SecViewVo;
import com.simi.vo.user.UserRefSearchVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.user.UserViewVo;

@Controller
@RequestMapping(value = "/app/sec")
public class SecController extends BaseController {

	@Autowired
	private UserLoginedService userLoginedService;

	@Autowired
	private UserSmsTokenService smsTokenService;

	@Autowired
	private OrderQueryService orderQueryService;

	@Autowired
	private TagsUsersService tagsUsersService;
	
	@Autowired
	private UsersService userService;

	@Autowired
	private SecService secService;

	@Autowired
	private UserRefService userRefService;

	@Autowired
	private UserRef3rdService userRef3rdService;
	
	@Autowired
	private NoticeSmsAsyncService noticeSmsAsyncService;

	/**
	 * 获取可用的秘书列表
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "get_list", method = RequestMethod.GET)
	public AppResultData<Object> secList(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, "", "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		// 获取秘书列表
		UserSearchVo searchVo = new UserSearchVo();
		searchVo.setUserType((short) 1);
		searchVo.setIsApproval((short) 2);
		PageInfo userList = userService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<Users> users = userList.getList();
		List<SecViewVo> secList = new ArrayList<SecViewVo>();
		if (!users.isEmpty()) {
			secList = secService.changeToSecViewVos(users);

			// 排除本身
			SecViewVo item = null;
			for (int i = 0; i < secList.size(); i++) {
				item = secList.get(i);
				if (userId.equals(item.getSecId())) {
					secList.remove(i);
				}
			}

			result.setData(secList);
		}

		return result;
	}

	/**
	 * 秘书获取绑定客户信息
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "get_users", method = RequestMethod.GET)
	public AppResultData<Object> getSecUsers(@RequestParam("sec_id") Long secId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, "", "");

		Users u = userService.selectByPrimaryKey(secId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		// 获取用户列表
		UserRefSearchVo searchVo = new UserRefSearchVo();
		searchVo.setRefId(secId);
		searchVo.setRefType("sec");
		List<UserRef> rs  = userRefService.selectBySearchVo(searchVo);
		
		List<UserViewVo> users = new ArrayList<UserViewVo>();
		if (rs.isEmpty()) {
			result.setData(users);
			return result;
		}
		
		
		List<Long> userIds = new ArrayList<Long>();

		for (UserRef item : rs) {
			if (!userIds.contains(item.getUserId())) {
				userIds.add(item.getUserId());
			}
		}
		
		UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(secId);
		
		users = userService.getUserInfos(userIds, u, userRef3rd);

		result.setData(users);
		return result;
	}

	/**
	 * 检验验证码是否正确
	 */
	@RequestMapping(value = "check_sms_token", method = RequestMethod.GET)
	public AppResultData<Object> checkSmstoken(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_token") String smsToken,
			@RequestParam(value = "sms_type", required = false, defaultValue = "0") Short smsType) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// 判断验证码正确与否，测试账号13810002890 000000 不需要验证
		AppResultData<Object> validateResult = null;
		if (mobile.equals("17090397818") && smsToken.equals("000000")
				|| mobile.equals("17090397828") && smsToken.equals("000000")
				|| mobile.equals("17090397822") && smsToken.equals("000000")
				|| mobile.equals("13701187136") && smsToken.equals("000000")
				|| mobile.equals("13810002890") && smsToken.equals("000000")
				|| mobile.equals("18610807136") && smsToken.equals("000000")
				|| mobile.equals("18612514665") && smsToken.equals("000000")
				|| mobile.equals("13146012753") && smsToken.equals("000000")
				|| mobile.equals("15727372986") && smsToken.equals("000000")) {
			validateResult = result;

		} else {
			validateResult = smsTokenService.validateSmsToken(mobile, smsToken,
					smsType);
		}

		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			return validateResult;
		}
		return result;
		
	}
	/**
	 * 秘书注册
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
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public AppResultData<Object> register(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile
			//@RequestParam("sms_token") String smsToken,
		//	@RequestParam("name") String name,
			// @RequestParam("login_from") Short loginFrom,
		//	@RequestParam(value = "sms_type", required = false, defaultValue = "0") Short smsType,
		//	@RequestParam(value = "user_type", required = false, defaultValue = "1") int userType
			) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// 判断验证码正确与否，测试账号13810002890 000000 不需要验证
/*		AppResultData<Object> validateResult = null;
		if (mobile.equals("17090397818") && smsToken.equals("000000")
				|| mobile.equals("17090397828") && smsToken.equals("000000")
				|| mobile.equals("17090397822") && smsToken.equals("000000")
				|| mobile.equals("13701187136") && smsToken.equals("000000")
				|| mobile.equals("13810002890") && smsToken.equals("000000")
				|| mobile.equals("18610807136") && smsToken.equals("000000")
				|| mobile.equals("18612514665") && smsToken.equals("000000")
				|| mobile.equals("13146012753") && smsToken.equals("000000")
				|| mobile.equals("15727372986") && smsToken.equals("000000")) {
			validateResult = result;

		} else {
			validateResult = smsTokenService.validateSmsToken(mobile, smsToken,
					smsType);
		}

		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			return validateResult;
		}*/
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

					// result.setStatus(Constants.ERROR_998);
					// result.setMsg(ConstantMsg.USER_EXIST_SEC_MG);
					//Users u = userService.initUsers();
					//u.setMobile(mobile);
					//u.setName(name);
					result = new AppResultData<Object>(Constants.ERROR_999,
							ConstantMsg.USER_EXIST_SEC_MG, user);

					return result;
				}
			}
		}
		Users u = userService.initUsers();
		u.setMobile(mobile);
	//	u.setName(name);

		// 注册为美家团队的一员.

		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, u);

		return result;
	}
	/**
	 * 秘书信息提交保存
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
		@RequestMapping(value = "post_user_register", method = RequestMethod.POST)
		public AppResultData<Object> register2(
				HttpServletRequest request,
				@RequestParam("mobile") String mobile,
				@RequestParam("name") String name,
				@RequestParam("realName") String realName,
				@RequestParam("sex") String sex,
				@RequestParam("degreeId") Long degreeId,
				@RequestParam("major") String major,
				@RequestParam("idCard") String idCard,
				@RequestParam(value = "tag_ids", required = false, defaultValue = "") String tagIds) {
			AppResultData<Object> result = new AppResultData<Object>(
					Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

			if (!mobile.equals("17090397828")&&!mobile.equals("17090397818")&&!mobile.equals("17090397822")
					&&!mobile.equals("13701187136")&&!mobile.equals("13810002890")&&!mobile.equals("18610807136")
					&&!mobile.equals("18612514665")&&!mobile.equals("13146012753")&&!mobile.equals("15727372986")) {
				

				Users record = userService.initUsers();
				
				//1.若用户已注册已经是用户user_type=0,则提醒用户”您已是用户，是否注册秘书“
				//2.若用户已注册过秘书user_type = 1,则提醒，”秘书已存在“。
				Users users = userService.selectByMobile(mobile);
				if (users != null ) {
					/*if (users.getUserType() == 0) {
						result = new AppResultData<Object>(Constants.ERROR_999,
								ConstantMsg.USER_EXIST_SEC_MG, users);
						return result;
					}*/
					if (users.getUserType() == 1) {
						
						result.setStatus(Constants.ERROR_999);
						result.setMsg(ConstantMsg.SEC_EXIST_MG);
						return result;	
					}
					
				}
				if (users != null) {
					record = users;
				}
				record.setName(name);
				record.setRealName(realName);
				record.setMobile(mobile);
				record.setSex(sex);
				record.setIdCard(idCard);
				record.setMajor(major);
				record.setDegreeId(degreeId);
				record.setUserType((short) 1);

				
				if (users != null) {
					userService.updateByPrimaryKeySelective(record);
				} else {
					userService.insert(record);
				}
				Long userId = record.getId();
				// 如果第一次登陆未注册时未成功注册环信，则重新注册
				UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(record.getId());
				if (userRef3rd == null) {
					userRef3rdService.genImUser(record);
				}
				
				UserSearchVo searchVo = new UserSearchVo();
				searchVo.setIdCard(idCard);
				List<Users> rs = userService.selectBySearchVo(searchVo);
				Users least = null;
				if (!rs.isEmpty()) {
					least = rs.get(0);
				}
				// 像tagUsers表中插入用户的标签记录
				if (!StringUtil.isEmpty(tagIds)) {
					tagsUsersService.deleteByUserId(userId);
					String[] tagIdsAry = StringUtil.convertStrToArray(tagIds);

					for (int i = 0; i < tagIdsAry.length; i++) {
						TagUsers tagUsers = new TagUsers();

						if (users == null) {
							tagUsers.setUserId(least.getId());
						}else {
							tagUsers.setUserId(users.getId());
						}
						
						tagUsers.setAddTime(TimeStampUtil.getNow() / 1000);

						if (StringUtil.isEmpty(tagIdsAry[i])) {
							continue;
						} else {
							tagUsers.setTagId(Long.valueOf(tagIdsAry[i]));

							tagsUsersService.insertByTagUsers(tagUsers);

						}
					}
				}
				// 用户注册秘书成功给运营人员推送短信通知
				noticeSmsAsyncService.userOrderAmPushSms(record);
			}
			return result;
		}
}