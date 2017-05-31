package com.simi.action.job;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meijia.utils.MobileUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserActionRecord;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.user.UserActionRecordService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserActionSearchVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;

@Controller
@RequestMapping(value = "/app/job")
public class JobUserSignController extends BaseController {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private XCompanySettingService xCompanySettingService;	
	
	@Autowired
	private UserPushBindService userPushBindService;
	
	@Autowired
	private NoticeAppAsyncService noticeAppAsyncService;
	
	@Autowired
	private UserActionRecordService userActionRecordService;
	
	/**
	 * 用户的手机号所在地批量更新,仅提供某个特定参数下使用
	 */
	@RequestMapping(value = "alarm_day_sign", method = RequestMethod.GET)
	public AppResultData<Object> genUserProvince(HttpServletRequest request) {


		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {

			//获取需要签到提醒的用户列表
			CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
			searchVo.setSettingType(Constants.SETTING_ALARM_DAY_SIGN);
			List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
			
			//找出已经签到的用户
			Long startTime = TimeStampUtil.getBeginOfToday();
			Long endTime = TimeStampUtil.getEndOfToday();
			
			UserActionSearchVo userActionSearchVo = new UserActionSearchVo();
			userActionSearchVo.setActionType("day_sign");
			userActionSearchVo.setStartTime(startTime);
			userActionSearchVo.setEndTime(endTime);
			
			List<UserActionRecord> rs = userActionRecordService.selectBySearchVo(userActionSearchVo);
			List<Long> userIds = new ArrayList<Long>();
			for (UserActionRecord item: rs) {
				if (!userIds.contains(item.getUserId())) userIds.add(item.getUserId());
			}

			String title = " 亲，记得签到哦，坚持签到有奖励呢~~";
			String msgContent = "";
			String category = "h5";
			String action = "";
			String params = "";
			String gotoUrl = "http://app.bolohr.com/simi-h5/show/sign-today.html?user_id=";
			for (XcompanySetting item : list) {
				Long userId = item.getUserId();
				gotoUrl+=userId;
				
				boolean isNeedNotice = true;
				//检测是否已经签到.
				for (Long signedUserId : userIds) {
					if (userId.equals(signedUserId)) {
						isNeedNotice = false;
						break;
					}
				}
				
				if (isNeedNotice) {
					noticeAppAsyncService.pushMsgToDevice(userId, title, msgContent, category, action, params, gotoUrl);
				}
			}
		}	
		return result;
	}
}
