package com.simi.action.app.sec;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.IPUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.sec.Sec;
import com.simi.po.model.user.UserLogined;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.UserSmsToken;
import com.simi.po.model.user.Users;
import com.simi.service.dict.CityService;
import com.simi.service.dict.DictService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.sec.SecService;
import com.simi.service.user.UserBaiduBindService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.UserSearchVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.vo.sec.SecInfoVo;
import com.simi.vo.sec.SecViewVo;
import com.simi.vo.user.LoginVo;
import com.simi.vo.user.UserViewVo;


@Controller
@RequestMapping(value = "/app/sec")
public class SecController extends BaseController {

	@Autowired
	private UserLoginedService userLoginedService;

	@Autowired
	private OrderQueryService orderQueryService;
	
	@Autowired
	private UsersService userService;
	
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private DictService dictService;

	@Autowired
	private SecService secService;

	@Autowired
	private UserRefSecService userRefSecService;

	@Autowired
	private UserBaiduBindService userBaiduBindService;

	@Autowired
	private UserSmsTokenService smsTokenService;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public AppResultData<Object> login(
			HttpServletRequest request,
			@RequestParam("mobile") String mobile,
			@RequestParam("sms_token") String smsToken,
			@RequestParam("login_from") Short loginFrom,
			@RequestParam(value = "sms_type", required = false, defaultValue = "0") Short smsType,
			@RequestParam(value = "user_type", required = false, defaultValue = "1") int userType) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		
		Sec sec = secService.selectByMobile(mobile);
		
		if (sec == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		//判断验证码正确与否，测试账号18610807136 13810002890 000000 不需要验证
		AppResultData<Object> validateResult = null;
		if ((mobile.trim().equals("18610807136") || mobile.trim().equals("13810002890")) && 
	        	 smsToken.trim().equals("000000") ) {
			validateResult = result;
		} else {
			validateResult = smsTokenService.validateSmsToken(mobile, smsToken, smsType);
		}
		
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			return validateResult;
		}
		
		SecInfoVo secInfoVo = secService.changeSecToVo(sec);
		
		long ip = IPUtil.getIpAddr(request);
		UserLogined record = userLoginedService.initUserLogined(mobile, secInfoVo.getId(), loginFrom, ip);
		userLoginedService.insert(record);

		
		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, secInfoVo);
		
		return result;
		
	}

	/**
	 * 秘书获取用户
	 * 
	 * @param secId
	 * @param mobile
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */

	@RequestMapping(value = "get_users", method = RequestMethod.POST)
	public AppResultData<Object> getUsers(@RequestParam("sec_id") Long secId,
			@RequestParam("mobile") String mobile

	) throws IllegalAccessException, InvocationTargetException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.ERROR_999, ConstantMsg.USER_NOT_EXIST_MG, "");
		List<UserRefSec> list = userRefSecService.selectBySecId(secId);

		if (list == null) {
			result = new AppResultData<Object>(Constants.ERROR_999,
					ConstantMsg.ERROR_999_MSG_1, "");
			return result;
		}
		List<Long> ids = new ArrayList<Long>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {

			UserRefSec userRefSec = (UserRefSec) iterator.next();
			ids.add(userRefSec.getUserId());
		}

		List<UserViewVo> listVo= new ArrayList<UserViewVo>();
		List<Users> u = userService.selectVoByUserId(ids);
		
		for (Iterator iterator = u.iterator(); iterator.hasNext();) {

			Users user = (Users) iterator.next();
			UserViewVo vo = userService.getUserInfo(user.getId());	
			
			listVo.add(vo);
		}
		
		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, listVo);
		return result;
	}

	/**
	 * 秘书信息获取
	 * 
	 * @param secId
	 * @param mobile
	 * @return
	 * @throws ParseException 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "get_secinfo", method = RequestMethod.POST)
	public AppResultData<Object> Sec(HttpServletRequest request,
			@RequestParam("sec_id") Long secId,
			@RequestParam("mobile") String mobile) throws ParseException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
        
		Sec sec = secService.selectVoBySecId(secId);
		
		if (sec == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.ERROR_999_MSG_1);
			return result;
		}
		
		SecInfoVo secInfoVo = secService.changeSecToVo(sec);

		result.setData(secInfoVo);
		return result;
		
	}
	/**
	 * 秘书信息修改
	 * 
	 * @param secId
	 * @param mobile
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "post_secinfo", method = RequestMethod.POST)
	public AppResultData<Object> secInfo(
			HttpServletRequest request,
			@RequestParam("sec_id") Long secId,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "nick_name", required = false, defaultValue = "") String nickName,
			@RequestParam(value = "sex", required = false, defaultValue = "") String sex,
			@RequestParam(value = "birth_day", required = false, defaultValue = "") String birthDay,
			@RequestParam(value = "city_id", required = false, defaultValue = "") Long cityId,
			@RequestParam(value = "head_img", required = false, defaultValue = "") String headImg) {
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.ERROR_999, ConstantMsg.USER_NOT_EXIST_MG, "");

		Sec sec = secService.selectVoBySecId(secId);
		
		if (sec==null) {
			
			result = new AppResultData<Object>(Constants.ERROR_999,
					ConstantMsg.ERROR_999_MSG_1, "");
			return result;
		}
      
        if (!StringUtils.isEmpty(request.getParameter("mobile")) && !name.equals(sec.getMobile())) {
			
        	sec.setMobile(request.getParameter("mobile"));
 			
 		}
       if (!StringUtils.isEmpty(name) ) {
			
    	   sec.setName(name);
			
		}
        if (!StringUtils.isEmpty(nickName) ) {
        	
        	sec.setNickName(nickName);
			
		}
		if (!StringUtils.isEmpty(sex) ) {
			sec.setSex(sex);	
		}
		
		if (!StringUtils.isEmpty(birthDay) ) {
			
			sec.setBirthDay(DateUtil.parse(birthDay));
			
		}
		if (!cityId.equals(cityId) ) {
			sec.setCityId(cityId);
		}
		
          if (!StringUtils.isEmpty(headImg)) {
        	  sec.setHeadImg(headImg);			
		}
     
        if (cityId > 0L) {
	        sec.setCityId(cityId);
        }
		
		
		// 保存上传的图像
		
		
		//新建一个Smartupload对象
		/*SmartUpload su=new SmartUpload();
		//上传初始化
		su.initialize(headImg);
		//上传文件
		su.upload();
		//将上传文件全部保存到指定目录
		int count=su.save("simi-oa/src/webapp/upload/sec/");
		System.out.println(count+"个上传文件成功！<br>");*/
		
		secService.updateByPrimaryKeySelective(sec);
		
		SecInfoVo secInfoVo = secService.changeSecToVo(sec);
		
		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, secInfoVo);
		return result;

	}
	
	/**
	 * 秘书所有订单列表接口
	 * @param secId
	 * @param mobile
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "get_orderlist", method = RequestMethod.GET)
	public AppResultData<List<OrderViewVo>> orderList(
			@RequestParam("sec_id") Long secId,
			@RequestParam("mobile") String mobile,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		List<OrderViewVo> orderList = new ArrayList<OrderViewVo>();
		
		AppResultData<List<OrderViewVo>> result = new AppResultData<List<OrderViewVo>>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG,orderList);

		List<OrderViewVo> orderViewVo = orderQueryService.selectBySecId(secId, page, Constants.PAGE_MAX_NUMBER);
	    
        result.setData(orderViewVo);
		
		return result;
	}
	
	@RequestMapping(value = "get_list", method = RequestMethod.GET)
	public AppResultData<Object> secList(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
			
			AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");

			Users u = userService.getUserById(userId);

			// 判断是否为注册用户，非注册用户返回 999
			if (u == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
				return result;
			}
			
			//获取秘书列表
			UserSearchVo searchVo = new UserSearchVo();
			searchVo.setUserType((short) 1);
			PageInfo userList = userService.searchVoListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
			List<Users> users = userList.getList();
			List<SecViewVo> secList = new ArrayList<SecViewVo>();
			if (!users.isEmpty()) {
				secList = secService.changeToSecViewVos(users);
				result.setData(secList);
			}
			return result;
	}
}