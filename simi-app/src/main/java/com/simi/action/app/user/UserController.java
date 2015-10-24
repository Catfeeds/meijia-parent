package com.simi.action.app.user;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.IPUtil;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.huanxin.EasemobIMUsers;
import com.meijia.utils.huanxin.EasemobMessages;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.UserLogined;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserSmsToken;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.TagNameListVo;
import com.simi.vo.user.UserPushBindVo;
import com.simi.vo.user.UserIndexVo;
import com.simi.vo.user.UserViewVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private TagsUsersService tagsUsersService;
	
	@Autowired
	private TagsService tagsService;

	@Autowired
	private UserSmsTokenService smsTokenService;

	@Autowired
	private UserLoginedService userLoginedService;

	@Autowired
	private OrderSeniorService orderSeniorService;

	@Autowired
	private UserCouponService userCouponService;

	@Autowired
	private UserPushBindService userPushBindService;

	@Autowired
	private UserRef3rdService userRef3rdService;
	
	
	@Autowired
	private UserRefSecService userRefSecService;

	// 5. 会员登陆接口
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public AppResultData<Object> login(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_token") String smsToken,
			@RequestParam("login_from") Short loginFrom,
			@RequestParam(value = "sms_type", required = false, defaultValue = "0") Short smsType,
			@RequestParam(value = "user_type", required = false, defaultValue = "1") int userType,
			@RequestParam(value = "device_type", required = false, defaultValue = "ios") String deviceType
			) {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		//判断验证码正确与否，测试账号13810002890 000000 不需要验证
		AppResultData<Object> validateResult = null;
		if (mobile.equals("13810002890") && smsToken.equals("000000")) {
			validateResult = result;
		} else {
			validateResult = smsTokenService.validateSmsToken(mobile, smsToken, smsType);
		}
		
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			return validateResult;
		}
		
		Users u = userService.getUserByMobile(mobile);
		
		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = userService.genUser(mobile, "", Constants.USER_APP);
		}
		
		//如果第一次登陆未注册时未成功注册环信，则重新注册
		UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(u.getId());
		if(userRef3rd == null){
			userService.genImUser(u);
		}
		
		//如果第一次登录未注册时未成功分配秘书，则重新分配
//		UserRefSec userRefSec  = userRefSecService.selectByUserId(u.getId());
//		if(userRefSec == null){
//			userRef3rdService.allotSec(u);
//		}
		
		//记录用户登陆信息
		long ip = IPUtil.getIpAddr(request);
		UserLogined record = userLoginedService.initUserLogined(mobile, u.getId(), loginFrom, ip);
		userLoginedService.insert(record);		
		
		// 根据mobile找到user_baidu_bind信息
		UserPushBind userPushBind = userPushBindService.selectByUserIdAndDeviceType(u.getId(), deviceType);
		
		UserPushBindVo vo = new UserPushBindVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);
		
		vo.setAppId("");
		vo.setChannelId("");
		vo.setAppUserId("");
		vo.setClientId("");
		if (userPushBind != null) {
			vo.setClientId("");
		}		
		
		result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, vo);

		return result;	
	}

	// 4. 获取验证码接口sms_type：0 = 用户登陆 1 = 秘书登录
	@RequestMapping(value = "get_sms_token", method = RequestMethod.GET)
	public AppResultData<String> getSmsToken(
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_type") int sms_type) {

		
		/*AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");*/
		AppResultData<String> result = new AppResultData<String>( 
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		// 2'调用函数生成六位验证码，调用短信平台，将发送的信息返回值更新到 user_sms_token
		String code = RandomUtil.randomNumber();

		if (mobile.equals("18610807136")) {
			code = "000000";
		}

		String[] content = new String[] { code, Constants.GET_CODE_MAX_VALID };
		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile, Constants.GET_CODE_TEMPLE_ID, content);
		UserSmsToken record = smsTokenService.initUserSmsToken(mobile, sms_type, code, sendSmsResult);

		smsTokenService.insert(record);

		
		return result;
	}

	// 4. 获取验证码接口sms_type：0 = 用户登陆 1 = 秘书登录
	@RequestMapping(value = "get_register_sms_token", method = RequestMethod.GET)
	public AppResultData<String> getRegisterSmsToken(
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_type") int sms_type) {

		
		/*AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");*/
		AppResultData<String> result = new AppResultData<String>( 
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		// 2'调用函数生成六位验证码，调用短信平台，将发送的信息返回值更新到 user_sms_token
		String code = RandomUtil.randomNumber();

		if (mobile.equals("15712917308")) {
			code = "000000";
		}
		
		Users user = userService.selectUserByMobile(mobile);
		if (user != null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.MOBILE_EXIST_MG);
			return result;
		}

		String[] content = new String[] { code, Constants.GET_CODE_MAX_VALID };
		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile, Constants.GET_CODE_TEMPLE_ID, content);
		UserSmsToken record = smsTokenService.initUserSmsToken(mobile, sms_type, code, sendSmsResult);

		smsTokenService.insert(record);

		
		return result;
	}
	
	
	//判断验证码是否正确，进入注册页面践行注册
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public AppResultData<Object> register(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_token") String smsToken,
			@RequestParam("name") String name,
			//@RequestParam("login_from") Short loginFrom,
			@RequestParam(value = "sms_type", required = false, defaultValue = "0") Short smsType,
			@RequestParam(value = "user_type", required = false, defaultValue = "1") int userType) {
		
			AppResultData<Object> result = new AppResultData<Object>( 
					Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
	
	    
			
		//判断验证码正确与否，测试账号13810002890 000000 不需要验证
		AppResultData<Object> validateResult = null;
		if (mobile.equals("15712917308") && smsToken.equals("000000")) {
			validateResult = result;
		} else {
			
			validateResult = smsTokenService.validateSmsToken(mobile, smsToken, smsType);
		}
		
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			return validateResult;
		}

		Users u =userService.initUsers(mobile, name);
		
		result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, u);
		
		return result;
	}
	/**
	 * 获得标签和学历的集合
	 * @return
	 */
	@RequestMapping(value = "get_tag_list", method = RequestMethod.GET)
	public AppResultData<Object> getTagsList() {
		
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		TagNameListVo vo = new TagNameListVo();
		
		List<Tags> tagList = tagsService.selectAll();
		vo.setList(tagList);
		
		List<Long> tagIdList = new ArrayList<Long>();
		for (Tags item : tagList) {
			tagIdList.add(item.getTagId());
		}
	
		
		vo.setTagList(tagIdList);
		
		List<String> degreeTypeList = MeijiaUtil.getDegreeType();
		
		vo.setDegreeTypeList(degreeTypeList);
		
		result.setData(vo);
		
		return result;
	}
	//用户信息提交
	@RequestMapping(value = "post_user_register", method = RequestMethod.POST)
	public AppResultData<Object> register2(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile,
			@RequestParam("name") String name,
			@RequestParam("realName") String realName,
			@RequestParam("sex") String sex,
			@RequestParam("degreeId") Short degreeId,
			@RequestParam("major") String major,
			//@RequestParam("birthDay") Date birthDay,
			//@RequestParam("tagId") Long tagId,
			@RequestParam("idCard") String idCard,
			//@RequestParam("login_from") Short loginFrom,
			@RequestParam(value = "sms_type", required = false, defaultValue = "0") Short smsType,
			@RequestParam(value = "user_type", required = false, defaultValue = "0") int userType,
			@RequestParam(value = "tag_ids", required = false, defaultValue = "") String tagIds) {
		
			AppResultData<Object> result = new AppResultData<Object>( 
					Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
			
			Users users = userService.selectUserByIdCard(idCard);
				if (users != null) {
					result.setStatus(Constants.ERROR_999);
					result.setMsg(ConstantMsg.USER_EXIST_MG);
					return result;
				}
		
			//记录用户注册信息
			//long ip = IPUtil.getIpAddr(request);
			Users record = new Users();
			record.setId(0L);
			record.setMobile(mobile);
			record.setProvinceName("");
			record.setThirdType(" ");
			record.setOpenid("");
			record.setName(name);
			record.setRealName(realName);
			record.setIdCard(idCard);
			record.setSex(sex);
			record.setBirthDay(new Date());
			record.setDegreeId(degreeId);
			record.setMajor(major);
			record.setHeadImg(" ");
			record.setRestMoney(new BigDecimal(0));
			record.setUserType((short) 0);
			record.setIsApproval((short)0);
			record.setAddFrom((short) 1);
			record.setScore(0);
			
			record.setAddTime(TimeStampUtil.getNow() / 1000);
			record.setUpdateTime(TimeStampUtil.getNow() / 1000);
			//UserLogined record = new UserLogined();
			userService.insert(record);	
			//userService.updateByPrimaryKeySelective(record);
			
			Users least = userService.selectUserByIdCard(idCard);
			//想Users表中插入用户的标签记录
			if (!StringUtil.isEmpty(tagIds)) {

				String[] tagIdsAry = StringUtil.convertStrToArray(tagIds);
				
				for (int i = 0; i < tagIdsAry.length; i++) {
					TagUsers tagUsers = new TagUsers();

					tagUsers.setUserId(least.getId());
					tagUsers.setAddTime(TimeStampUtil.getNow() / 1000);
					
					if (StringUtil.isEmpty(tagIdsAry[i])) {
						continue;
					} else {
						tagUsers.setTagId(Long.valueOf(tagIdsAry[i]));
						
						tagsUsersService.insertByTagUsers(tagUsers);

					}
				}
			} 
			
			return result;
	}
	// 4. 获取验证码接口sms_type：0 = 用户登陆 1 = 秘书登录
	@RequestMapping(value = "val_sms_token", method = RequestMethod.GET)
	public AppResultData<Object> valSmsToken(
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_token") String smsToken,
			@RequestParam("sms_type") Short smsType) {

		
		AppResultData<Object> result = smsTokenService.validateSmsToken(mobile, smsToken, smsType);
		
		return result;
	}	

	// 6. 账号信息
	/**
	 * mobile:手机号 rest_money 余额 score会员积分
	 */
	@RequestMapping(value = "get_userinfo", method = RequestMethod.GET)
	public AppResultData<Object> getUserInfo(
			@RequestParam("user_id") Long userId) {

		UserViewVo vo = userService.getUserInfo(userId);

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, vo);
		return result;
	}

	
	/**
	 * 发送IM消息
	 */
	@RequestMapping(value = "send_im", method = RequestMethod.GET)
	public AppResultData<Object> sendImToRobot(
			@RequestParam("user_id") Long userId,
			@RequestParam("im_username_from") String imUsernameFrom,
			@RequestParam("im_username_to") String imUsernameTo,
			@RequestParam("msg") String msg) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.getUserById(userId);
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		if (StringUtil.isEmpty(imUsernameFrom)
				|| StringUtil.isEmpty(imUsernameTo)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.IM_USER_NOT_EXIST_MG);
			return result;
		}

		try {
			msg = URLDecoder.decode(msg, Constants.URL_ENCODE);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 给用户发一条文本消息
		EasemobMessages.sendMessageTxt(imUsernameFrom, imUsernameTo, msg);
		return result;
	}

	/**
	 * 用户信息修改接口
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "post_userinfo", method = RequestMethod.POST)
	public AppResultData<Object> updateUserInfo(
			HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "mobile", required = false, defaultValue = "") String mobile,
			@RequestParam(value = "sex", required = false, defaultValue = "") String sex,
			@RequestParam(value = "head_img", required = false, defaultValue = "") String headImg)
			throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.getUserById(userId);
		
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		//要判断mobile不为空，并且手机号在其他用户上没有使用过;
		if (!StringUtil.isEmpty(mobile)) {
			Users user = userService.getUserByMobile(mobile);
			if (user != null && !user.getId().equals(userId)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("该手机号已经在其他地方用过");
				return result;				
			} else {
				 u.setMobile(mobile);
			}
		}
		
		// 如果昵称name不为空，则对环信中昵称进行修改
		if (!StringUtil.isEmpty(name)) {
			u.setName(name);
		}		

		if (!StringUtil.isEmpty(headImg)) {
			u.setHeadImg(headImg);
		}		
		
		if (!StringUtil.isEmpty(sex)) {
			u.setSex(sex);
		}
		
		userService.updateByPrimaryKeySelective(u);
		
		if (!StringUtil.isEmpty(name)) {
			String username = "";
			UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(userId);
			// 如果该账号未绑定环信账号
			if (userRef3rd != null) {
				username = userRef3rd.getUsername();
				ObjectNode json2 = JsonNodeFactory.instance.objectNode();
				json2.put("nickname", name);
				EasemobIMUsers.modifyIMUserNickName(username, json2);
			}
		}
		
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			String paths = request.getSession().getServletContext()
					.getRealPath("/");
			System.out.println("paths---" + paths);
			String p = paths.substring(0, paths.lastIndexOf("\\"));
			String path = p + File.separator + "upload" + File.separator
					+ "users";
			// 判断 request 是否有文件上传,即多部分请求...
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null && !file.isEmpty()) {
					
					String fileName = file.getOriginalFilename();
					String extensionName = fileName.substring(fileName
							.lastIndexOf(".") + 1);
					// 新的图片文件名 = 获取时间戳+随机六位数+"."图片扩展名
					String before = TimeStampUtil.getNow()
							+ String.valueOf(RandomUtil.randomNumber());
					String newFileName = String.valueOf(before + "."
							+ extensionName);
					// 获取系统发布后upload路径
					FileUtils.copyInputStreamToFile(file.getInputStream(),
							new File(path, newFileName));
					String headImgs = "/simi-app/upload/users/" + newFileName;
					u.setHeadImg(headImgs);
					userService.updateByPrimaryKeySelective(u);
				}
			}
		}


		result.setData(u);
		return result;

	}
	
	/**
	 * 个人主页接口
	 */
	@RequestMapping(value = "get_user_index", method = RequestMethod.GET)
	public AppResultData<Object> getUserIndex(
			@RequestParam("user_id") Long userId,
			@RequestParam("view_user_id") Long viewUserId) {
		
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		Users u = userService.getUserById(userId);
		Users viewUser = userService.getUserById(viewUserId);
		// 判断是否为注册用户，非注册用户返回 999
		if (u == null || viewUser == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserIndexVo vo = userService.getUserIndexVoByUserId(u, viewUser);
		result.setData(vo);
		
		return result;
	}	

}
