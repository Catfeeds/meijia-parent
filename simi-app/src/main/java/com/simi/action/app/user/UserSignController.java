package com.simi.action.app.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserActionRecord;
import com.simi.service.async.UserScoreAsyncService;
import com.simi.service.user.UserActionRecordService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserActionSearchVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserSignController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserActionRecordService userActionRecordService;
	
	@Autowired
	private UserScoreAsyncService userScoreAsyncService;


	@RequestMapping(value = "day_sign", method = RequestMethod.POST)
	public AppResultData<Object> daySing(
			@RequestParam("user_id") Long userId) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		
		Long startTime = TimeStampUtil.getBeginOfToday();
		Long endTime = TimeStampUtil.getEndOfToday();
		
		UserActionSearchVo searchVo = new UserActionSearchVo();
		searchVo.setUserId(userId);
		searchVo.setActionType("day_sign");
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		
		List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(searchVo);
		if (!rs.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("今天已经签过到.");
			return result;
		}
		
		UserActionRecord record = userActionRecordService.initUserActionRecord();
		record.setUserId(userId);
		record.setActionType("day_sign");
		
		userActionRecordService.insert(record);
		
		//积分赠送
		userScoreAsyncService.sendScore(userId, 5, "day_sign", record.getId().toString(), "签到");
		
		result.setMsg("签到成功");
		result.setData(5);
		
		return result;
	}
}
