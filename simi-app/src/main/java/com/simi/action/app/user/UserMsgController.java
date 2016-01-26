package com.simi.action.app.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.UserMsgSearchVo;
import com.simi.vo.user.UserMsgVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserMsgController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserMsgService userMsgService;	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "get_msg", method = RequestMethod.GET)
	public AppResultData<Object> getMsg(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "service_date", required = false, defaultValue = "") String serviceDate,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page
			) {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserMsgSearchVo searchVo = new UserMsgSearchVo();
		searchVo.setUserId(userId);
		
		Long startTime = TimeStampUtil.getBeginOfToday();
		Long endTime = TimeStampUtil.getEndOfToday();
		if (!StringUtil.isEmpty(serviceDate) ) {
			Date serviceDateObj = DateUtil.parse(serviceDate);
			
			String startTimeStr = DateUtil.format(serviceDateObj, "yyyy-MM-dd 00:00:00");
			String endTimeStr = DateUtil.format(serviceDateObj, "yyyy-MM-dd 23:59:59");
			startTime = TimeStampUtil.getMillisOfDayFull(startTimeStr) / 1000;
			endTime = TimeStampUtil.getMillisOfDayFull(endTimeStr) / 1000;
		}
		
		
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		
		PageInfo list = userMsgService.selectByListPage(searchVo, page, 10);
		List<UserMsg> msgList = list.getList();
		
		List<UserMsgVo> resultList = new ArrayList<UserMsgVo>();
		
		for (int i = 0; i < msgList.size(); i++) {
			UserMsg item = msgList.get(i);
			UserMsgVo vo = new UserMsgVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			Long updateTime = item.getUpdateTime();
			String msgTime = TimeStampUtil.timeStampToDateStr(updateTime * 1000, "HH:mm");
			vo.setMsgTime(msgTime);
			resultList.add(vo);
		}
		
		result.setData(resultList);
		return result;
	}
}
