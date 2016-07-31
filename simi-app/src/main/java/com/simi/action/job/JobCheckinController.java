package com.simi.action.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meijia.utils.DateUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.card.Cards;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.async.CardAsyncService;
import com.simi.service.async.XcompanyAsyncService;
import com.simi.service.card.CardService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyCheckinStatService;
import com.simi.utils.CardUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.xcloud.CheckinNetVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;

@Controller
@RequestMapping(value = "/app/job/company")
public class JobCheckinController extends BaseController {

	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private XcompanyCheckinStatService xcompanyCheckinStatService;

	@Autowired
	private XcompanyAsyncService xcompanyAsyncService;

	/**
	 * 定时判断员工考勤状态，是否迟到，每分钟执行
	 */
	@RequestMapping(value = "checkin-late", method = RequestMethod.GET)
	public AppResultData<Object> checkinLate(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// String reqHost = request.getRemoteHost();
		// if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
		//
		// }

		// 1. 找出所有有配置出勤配置的公司，并计算是否达到早上考勤时间点
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setSettingType(Constants.SETTING_CHICKIN_NET);
		searchVo.setIsEnable((short) 1);
		List<XcompanySetting> checkinSettings = xCompanySettingService.selectBySearchVo(searchVo);

		List<Long> companyIds = new ArrayList<Long>();

		for (XcompanySetting item : checkinSettings) {
			if (!companyIds.contains(item.getCompanyId()))
				companyIds.add(item.getCompanyId());
		}

		for (Long companyId : companyIds) {
			xcompanyAsyncService.checkinStatLate(companyId);
		}

		return result;
	}

	/**
	 * 定时判断员工考勤状态，是否早退，每天一点执行即可，查找昨天的记录
	 */
	@RequestMapping(value = "checkin-early", method = RequestMethod.GET)
	public AppResultData<Object> checkinEarly(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// String reqHost = request.getRemoteHost();
		// if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
		//
		// }

		// 1. 找出所有有配置出勤配置的公司
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setSettingType(Constants.SETTING_CHICKIN_NET);
		searchVo.setIsEnable((short) 1);
		List<XcompanySetting> checkinSettings = xCompanySettingService.selectBySearchVo(searchVo);

		List<Long> companyIds = new ArrayList<Long>();

		for (XcompanySetting item : checkinSettings) {
			if (!companyIds.contains(item.getCompanyId()))
				companyIds.add(item.getCompanyId());
		}

		for (Long companyId : companyIds) {
			xcompanyAsyncService.checkinStatEarly(companyId);
		}
		return result;
	}

}
