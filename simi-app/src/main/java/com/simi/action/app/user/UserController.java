package com.simi.action.app.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.IPUtil;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.huanxin.EasemobIMUsers;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.UserImgs;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserSmsToken;
import com.simi.po.model.user.Users;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.order.OrderSeniorService;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserImgService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.TagNameListVo;
import com.simi.vo.user.UserImgVo;
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
	private OrderSeniorService orderSeniorService;

	@Autowired
	private UserCouponService userCouponService;

	@Autowired
	private UserPushBindService userPushBindService;

	@Autowired
	private UserRef3rdService userRef3rdService;

	@Autowired
	private UserRefSecService userRefSecService;

	@Autowired
	private UserImgService userImgService;
	
	@Autowired
	private UsersAsyncService usersAsyncService;

	// 5. 会员登陆接口
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public AppResultData<Object> login(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_token") String smsToken,
			@RequestParam("login_from") Short loginFrom,
			@RequestParam(value = "sms_type", required = false, defaultValue = "0") Short smsType,
			@RequestParam(value = "user_type", required = false, defaultValue = "1") int userType,
			@RequestParam(value = "device_type", required = false, defaultValue = "ios") String deviceType) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// 判断验证码正确与否，测试账号13810002890 000000 不需要验证
		AppResultData<Object> validateResult = null;
		if (mobile.equals("13810002890") && smsToken.equals("000000")) {
			validateResult = result;
		} else {
			validateResult = smsTokenService.validateSmsToken(mobile, smsToken,
					smsType);
		}

		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			return validateResult;
		}

		Users u = userService.selectByMobile(mobile);

		if (u == null) {// 验证手机号是否已经注册，如果未注册，则自动注册用户，
			u = userService.genUser(mobile, "", Constants.USER_APP);
		}
		
		// 根据mobile找到user_baidu_bind信息
		UserPushBind userPushBind = userPushBindService.selectByUserId(u.getId());

		UserPushBindVo vo = new UserPushBindVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(u, vo);

		vo.setAppId("");
		vo.setChannelId("");
		vo.setAppUserId("");
		vo.setClientId("");
		if (userPushBind != null) {
			vo.setClientId(userPushBind.getClientId());
		}

		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, vo);
		
		//异步操作
		// 如果第一次登陆未注册时未成功注册环信，则重新注册
		usersAsyncService.genImUser(u.getId());
		
		// 记录用户登陆信息
		long ip = IPUtil.getIpAddr(request);
		usersAsyncService.userLogined(u.getId(), loginFrom, ip);
		
		return result;
	}

	// 4. 获取验证码接口sms_type：0 = 用户登陆 1 = 秘书登录
	@RequestMapping(value = "get_sms_token", method = RequestMethod.GET)
	public AppResultData<String> getSmsToken(
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_type") int sms_type) {

		/*
		 * AppResultData<String> result = new AppResultData<String>(
		 * Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		 */
		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		// 2'调用函数生成六位验证码，调用短信平台，将发送的信息返回值更新到 user_sms_token
		String code = RandomUtil.randomNumber();

		if (mobile.equals("18610807136")) {
			code = "000000";
		}

		String[] content = new String[] { code, Constants.GET_CODE_MAX_VALID };
		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
				Constants.GET_CODE_TEMPLE_ID, content);
		UserSmsToken record = smsTokenService.initUserSmsToken(mobile,
				sms_type, code, sendSmsResult);

		smsTokenService.insert(record);

		return result;
	}

	// 4. 获取验证码接口sms_type：0 = 用户登陆 1 = 秘书登录
	@RequestMapping(value = "get_register_sms_token", method = RequestMethod.GET)
	public AppResultData<String> getRegisterSmsToken(
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_type") int sms_type) {

		/*
		 * AppResultData<String> result = new AppResultData<String>(
		 * Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		 */
		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		// 2'调用函数生成六位验证码，调用短信平台，将发送的信息返回值更新到 user_sms_token
		String code = RandomUtil.randomNumber();

		if (mobile.equals("17090397818")||mobile.equals("17090397828")||mobile.equals("17090397822")) {
			code = "000000";
		}
		String[] content = new String[] { code, Constants.GET_CODE_MAX_VALID };
		HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobile,
				Constants.GET_CODE_TEMPLE_ID, content);
		UserSmsToken record = smsTokenService.initUserSmsToken(mobile,
				sms_type, code, sendSmsResult);

		smsTokenService.insert(record);

		return result;
	}

	// 判断验证码是否正确，进入注册页面进行注册
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public AppResultData<Object> register(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_token") String smsToken,
			@RequestParam("name") String name,
			// @RequestParam("login_from") Short loginFrom,
			@RequestParam(value = "sms_type", required = false, defaultValue = "0") Short smsType,
			@RequestParam(value = "user_type", required = false, defaultValue = "1") int userType) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// 判断验证码正确与否，测试账号13810002890 000000 不需要验证
		AppResultData<Object> validateResult = null;
		if (mobile.equals("17090397818") && smsToken.equals("000000")||mobile.equals("17090397828") && smsToken.equals("000000")||mobile.equals("17090397822") && smsToken.equals("000000")) {
			validateResult = result;
		} else {
			validateResult = smsTokenService.validateSmsToken(mobile, smsToken,
					smsType);
		}
		
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			return validateResult;
		}
		if (!mobile.equals("17090397828")&&!mobile.equals("17090397818")&&mobile.equals("17090397822")) {

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
					Users u = userService.initUsers();
					u.setMobile(mobile);
					u.setName(name);
					result = new AppResultData<Object>(Constants.ERROR_998,
							ConstantMsg.USER_EXIST_SEC_MG, u);

					return result;
				}
			}
		}
		Users u = userService.initUsers();
		u.setMobile(mobile);
		u.setName(name);

		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, u);

		return result;
	}

	/**
	 * 获得标签和学历的集合
	 * 
	 * @return
	 */
	@RequestMapping(value = "get_tag_list", method = RequestMethod.GET)
	public AppResultData<Object> getTagsList(
			@RequestParam("mobile") String mobile,
			@RequestParam("name") String name) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		TagNameListVo vo = new TagNameListVo();

		List<Tags> tagList = tagsService.selectAll();
		vo.setList(tagList);

		List<Long> tagIdList = new ArrayList<Long>();
		/*
		 * for (Tags item : tagList) { tagIdList.add(item.getTagId()); }
		 */

		vo.setTagList(tagIdList);

		List<String> degreeTypeList = MeijiaUtil.getDegreeType();

		vo.setDegreeTypeList(degreeTypeList);
		// 如果已注册用户，确定要注册秘书，则去表中查到对应的用户数据但是不在注册列表里展示信息
		// 只为了存用户的昵称（name）;
		if (!mobile.equals("17090397828")&&!mobile.equals("17090397818")&&mobile.equals("17090397822")) {
			Users user = userService.selectByMobile(mobile);
			if (user != null && user.getUserType() == 0) {
				user.setName(name);
				BeanUtilsExp.copyPropertiesIgnoreNull(user, vo);
			}
		}

		result.setData(vo);

		return result;
	}

	// 秘书注册信息提交
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

		if (!mobile.equals("17090397828")&&!mobile.equals("17090397818")&&mobile.equals("17090397822")) {

			Users users = userService.selectByMobile(mobile);
			// 若users不为空，则为已有用户注册秘书 ,修改用户为秘书（userType=1）
			if (users != null) {
				users.setName(name);
				users.setRealName(realName);
				users.setSex(sex);
				users.setIdCard(idCard);
				users.setMajor(major);
				users.setDegreeId(degreeId);
				users.setUserType((short) 1);
				users.setAddTime(TimeStampUtil.getNow() / 1000);
				users.setUpdateTime(TimeStampUtil.getNow() / 1000);
				userService.updateByPrimaryKeySelective(users);
				// 像tagUsers表中插入用户的标签记录
				if (!StringUtil.isEmpty(tagIds)) {

					String[] tagIdsAry = StringUtil.convertStrToArray(tagIds);

					for (int i = 0; i < tagIdsAry.length; i++) {
						TagUsers tagUsers = new TagUsers();

						tagUsers.setUserId(users.getId());
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
				userService.userOrderAmPushSms(users);
			} else {
				// 记录用户注册信息
				// long ip = IPUtil.getIpAddr(request);
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
				record.setUserType((short) 1);
				record.setIsApproval((short) 0);
				record.setAddFrom((short) 1);
				record.setScore(0);

				record.setAddTime(TimeStampUtil.getNow() / 1000);
				record.setUpdateTime(TimeStampUtil.getNow() / 1000);

				userService.insert(record);
				userService.updateByPrimaryKeySelective(record);
				// 如果第一次登陆未注册时未成功注册环信，则重新注册
				UserRef3rd userRef3rd = userRef3rdService
						.selectByUserIdForIm(record.getId());
				if (userRef3rd == null) {
					userService.genImUser(record);
				}
				Users least = userService.selectUserByIdCard(idCard);
				// 想tagUsers表中插入用户的标签记录
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
				userService.userOrderAmPushSms(record);
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

		AppResultData<Object> result = smsTokenService.validateSmsToken(mobile,
				smsToken, smsType);

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

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		// 要判断mobile不为空，并且手机号在其他用户上没有使用过;
		if (!StringUtil.isEmpty(mobile)) {
			Users user = userService.selectByMobile(mobile);
			if (user != null && !user.getId().equals(userId)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("该手机号已经在由其他用户注册.");
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

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		Users u = userService.selectByPrimaryKey(userId);
		Users viewUser = userService.selectByPrimaryKey(viewUserId);
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

	/**
	 * 用户头像上传接口
	 * 
	 * @throws IOException
	 * 
	 *             参考 ：http://www.concretepage.com/spring-4/spring-4-mvc-single-
	 *             multiple-file-upload-example-with-tomcat
	 */
	@RequestMapping(value = "post_user_head_img", method = RequestMethod.POST)
	public AppResultData<Object> userHeadImg(HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam("file") MultipartFile file) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		HashMap<String, String> img = new HashMap<String, String>();
		if (file != null && !file.isEmpty()) {

			String url = Constants.IMG_SERVER_HOST + "/upload/";
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			
			System.out.println("fileName = " + fileName);
			System.out.println("fileType = " + fileType);
			
			fileType = fileType.toLowerCase();
			String sendResult = ImgServerUtil.sendPostBytes(url,
					file.getBytes(), fileType);

			ObjectMapper mapper = new ObjectMapper();

			HashMap<String, Object> o = mapper.readValue(sendResult,
					HashMap.class);
			System.out.println(o.toString());
			String ret = o.get("ret").toString();
			
			HashMap<String, String> info = (HashMap<String, String>) o
					.get("info");

			String imgUrl = Constants.IMG_SERVER_HOST + "/"
					+ info.get("md5").toString();

			u.setHeadImg(ImgServerUtil.getImgSize(imgUrl, "100", "100"));

			userService.updateByPrimaryKeySelective(u);

			img.put("user_id", userId.toString());
			img.put("head_img", ImgServerUtil.getImgSize(imgUrl, "100", "100"));
		}

		result.setData(img);
		return result;

	}

	/**
	 * 用户图片上传
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "post_user_img", method = RequestMethod.POST)
	public AppResultData<Object> userImg(HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam("file") MultipartFile[] files) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		List<HashMap<String, String>> imgs = new ArrayList<HashMap<String, String>>();

		if (files != null && files.length > 0) {

			for (int i = 0; i < files.length; i++) {
				String url = Constants.IMG_SERVER_HOST + "/upload/";
				String fileName = files[i].getOriginalFilename();
				String fileType = fileName
						.substring(fileName.lastIndexOf(".") + 1);
				fileType = fileType.toLowerCase();
				String sendResult = ImgServerUtil.sendPostBytes(url,
						files[i].getBytes(), fileType);

				ObjectMapper mapper = new ObjectMapper();

				HashMap<String, Object> o = mapper.readValue(sendResult,
						HashMap.class);

				String ret = o.get("ret").toString();

				HashMap<String, String> info = (HashMap<String, String>) o
						.get("info");

				String imgUrl = Constants.IMG_SERVER_HOST + "/"
						+ info.get("md5").toString();

				UserImgs userImg = new UserImgs();
				userImg.setImgId(0L);
				userImg.setImgUrl(imgUrl);
				userImg.setUserId(userId);
				userImg.setAddTime(TimeStampUtil.getNowSecond());
				userImgService.insert(userImg);

				HashMap<String, String> img = new HashMap<String, String>();
				img.put("user_id", userId.toString());
				img.put("img", imgUrl);
				img.put("img_trumb", ImgServerUtil.getImgSize(imgUrl, "400", "400"));
				
				imgs.add(img);
			}
		}

		result.setData(imgs);
		return result;
	}

	/**
	 * 获取用户图片
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "get_user_imgs", method = RequestMethod.GET)
	public AppResultData<Object> getUserImgs(HttpServletRequest request,
			@RequestParam("user_id") Long userId) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		List<UserImgs> userImgs = userImgService.selectByUserId(userId);

		List<UserImgVo> userImgVos = new ArrayList<UserImgVo>();

		for (UserImgs item : userImgs) {
			UserImgVo vo = new UserImgVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			vo.setImgTrumb(ImgServerUtil.getImgSize(item.getImgUrl(), "400", "400"));
			vo.setImg(item.getImgUrl());
			userImgVos.add(vo);
		}
		result.setData(userImgVos);

		return result;
	}

	/**
	 * 用户图片删除
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "del_user_img", method = RequestMethod.POST)
	public AppResultData<Object> delUserImg(HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam("img_id") Long imgId) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		UserImgs userImg = userImgService.selectByPrimaryKey(imgId);

		if (userImg != null) {
			userImgService.deleteByPrimaryKey(imgId);
		}
		return result;
	}

}
