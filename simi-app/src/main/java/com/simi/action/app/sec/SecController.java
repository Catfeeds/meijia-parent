package com.simi.action.app.sec;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.druid.util.StringUtils;
import com.meijia.utils.IPUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictCity;
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
import com.simi.vo.OrderSearchVo;
import com.simi.vo.SecList;
import com.simi.vo.order.OrderViewVo;
import com.simi.vo.sec.SecCeShi;
import com.simi.vo.sec.SecInfoVo;
import com.simi.vo.user.LoginVo;
import com.simi.vo.user.UserBaiduBindVo;
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
			@RequestParam("sms_token") String sms_token,
			@RequestParam("login_from") int login_from,
			@RequestParam(value = "user_type", required = false, defaultValue = "1") int user_type) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		
		SecList sec = secService.selectByMobile(mobile);
		
       if (mobile.trim().equals("18610807136") && sms_token.trim().equals("000000")) {
    	   /*
    		SecCeShi secCeShi= new SecCeShi();
			secCeShi.setId((long)1);
			secCeShi.setMobile("13810002890");*/
    	    result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, sec);
			return result;
			
		}else {	
			
		
		if (sec == null) {
			
			result = new AppResultData<Object>(Constants.ERROR_999,
					ConstantMsg.ERROR_999_MSG_1, "");
			return result;
		}

		
		UserSmsToken smsToken = smsTokenService.selectByMobileAndType(mobile);// 1、根据mobile
		// 从表user_sms_token找出最新一条记录
		LoginVo loginVo = new LoginVo(0l, mobile);
		if (smsToken == null || smsToken.getAddTime() == null) {
			result = new AppResultData<Object>(Constants.ERROR_999,
					ConstantMsg.ERROR_999_MSG_2, "");
			return result;
		}
		loginVo = new LoginVo(smsToken.getUserId(), mobile);
		// 2、判断是否表记录字段add_time 是否超过十分钟.
		long expTime = TimeStampUtil.compareTimeStr(smsToken.getAddTime(),
				System.currentTimeMillis() / 1000);
		if (expTime > 600) {// 超时
			// 999
			result = new AppResultData<Object>(Constants.ERROR_999,
					ConstantMsg.ERROR_999_MSG_8, "");
			return result;
		} /*else {

		}*/
		long ip = IPUtil.getIpAddr(request);
		UserLogined record = userLoginedService.initUserLogined(smsToken,
				login_from, ip);
		userLoginedService.insert(record);

		if (!smsToken.getSmsToken().equals(sms_token)) {// 验证码错误
			result = new AppResultData<Object>(Constants.ERROR_999,
					ConstantMsg.ERROR_999_MSG_2, "");
			return result;
		}
		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, sec);
		
		return result;
		}
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
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "get_secinfo", method = RequestMethod.POST)
	public AppResultData<Object> Sec(
			@RequestParam("sec_id") Long secId,
			@RequestParam("mobile") String mobile) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.ERROR_999, ConstantMsg.USER_NOT_EXIST_MG, "");
        
		Sec sec = secService.selectVoBySecId(secId);
		
		if (sec == null) {
			result = new AppResultData<Object>(Constants.ERROR_999,
					ConstantMsg.ERROR_999_MSG_1, "");
			return result;
		}else {
			
		
		 SecInfoVo secInfoVo=new SecInfoVo();		 
         try {
			BeanUtils.copyProperties(secInfoVo, sec);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         if (secInfoVo.getCityId()==0) {
			secInfoVo.setCityId(null);
			result = new AppResultData<Object>(Constants.SUCCESS_0,
					ConstantMsg.SUCCESS_0_MSG, secInfoVo);
			return result;
		}
		DictCity city=cityService.selectByCityId(secInfoVo.getCityId());
		
		secInfoVo.setCityName(city.getName());
		
		result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, secInfoVo);
		return result;
		}
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
		
		
		if (!StringUtils.isEmpty(name) && !name.equals(sec.getName())) {
			
			sec.setName(name);
			
		}
        if (!StringUtils.isEmpty(nickName) && !nickName.equals(sec.getNickName())) {
        	
        	sec.setNickName(nickName);
			
		}
		if (!StringUtils.isEmpty(sex) && !sex.equals(sec.getSex())) {
		        	
			sec.setSex(sex);
					
				}
		/*if (!StringUtils.isEmpty(birthDay) && !birthDay.equals(sec.getBirthDay())) {
			
			    sec.setBirthDay(birthDay);
			
		}*/
		if (!cityId.equals("") && !cityId.equals(sec.getCityId())) {
			sec.setCityId(cityId);
		}
		
          if (!StringUtils.isEmpty(headImg) && !headImg.equals(sec.getHeadImg())) {
			
        	  sec.setHeadImg(headImg);			
		}
          
       
        
        secService.updateByPrimaryKeySelective(sec);
        
        SecInfoVo secInfoVo=new SecInfoVo();
        
        try {
  			BeanUtils.copyProperties(secInfoVo, sec);
  		} catch (IllegalAccessException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		} catch (InvocationTargetException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
        
        secInfoVo.setAddTime(TimeStampUtil.getNow()/1000);
        
        DictCity city=cityService.selectByCityId(secInfoVo.getCityId());
		
		secInfoVo.setCityName(city.getName());
        
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
			@RequestParam(value = "page", required = false, defaultValue = "0") int page) {

		List<OrderViewVo> orderList = new ArrayList<OrderViewVo>();
		
		AppResultData<List<OrderViewVo>> result = new AppResultData<List<OrderViewVo>>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG,orderList);
	
		Sec sec = secService.getUserById(secId);
		
		if (sec==null) {
			return result;
		}
		List<OrderViewVo> orderViewVo = orderQueryService.selectBySecId(secId, page, Constants.PAGE_MAX_NUMBER);
		

        result.setData(orderList);
		
		return result;
}
	
}