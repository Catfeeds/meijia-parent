package com.xcloud.action.xz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.utils.CardUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.AppResultData;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;


@Controller
@RequestMapping(value = "/xz/meeting/")
public class MeetingController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XCompanyService xCompanyService;
	
	@Autowired
	private XCompanySettingService xCompanySettingService;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	CardAttendService cardAttendService;	

	@AuthPassport
	@RequestMapping(value = "/get-meeting-list", method = { RequestMethod.GET })
	public List<Object> getCardList(HttpServletRequest request, Model model,
			@RequestParam(value = "start", required = false, defaultValue = "") String start,
			@RequestParam(value = "end", required = false, defaultValue = "") String end
			) {
		
		List<Object> result = new ArrayList<Object>();
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		if (accountAuth == null) return result;
		
		Long userId = accountAuth.getUserId();
		
		if (userId.equals(0L)) return result;
		
		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
//		if (u == null)  return result;

		//处理时间的问题
		Long startTime = 0L;
		Long endTime = 0L;
		
		if (StringUtil.isEmpty(start)) start = DateUtil.getToday();
		if (StringUtil.isEmpty(end)) end = DateUtil.getToday();
		
		Date startDate = DateUtil.parse(start);
		String startTimeStr = DateUtil.format(startDate, "yyyy-MM-dd 00:00:00");
		startTime = TimeStampUtil.getMillisOfDayFull(startTimeStr) / 1000;
		
		Date endDate = DateUtil.parse(end);
		String endTimeStr = DateUtil.format(endDate, "yyyy-MM-dd 23:59:59");
		endTime = TimeStampUtil.getMillisOfDayFull(endTimeStr) / 1000;
		
		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setCardFrom((short) 0);
		searchVo.setUserId(userId);
		searchVo.setUserType(u.getUserType());
		searchVo.setCardType((short) 1);
		
		if (startTime > 0L && endTime > 0L) {
			searchVo.setStartTime(startTime);
			searchVo.setEndTime(endTime);
		}
		
		List<Cards> cards = cardService.selectBySearchVo(searchVo);
		
		if (cards.isEmpty()) return result;
		
		for (Cards item : cards) {
			Map<String, String> vo = new HashMap<String, String>();
			vo.put("id", item.getCardId().toString());
			vo.put("title", CardUtil.getCardTypeName(item.getCardType()));
			vo.put("url", "/xcloud/schedule/card-view?card_id="+item.getCardId());
			
			Long serviceTime = item.getServiceTime() * 1000;
			
			vo.put("start", TimeStampUtil.timeStampToDateStr(serviceTime, "yyyy-MM-dd HH:mm:ss"));
			result.add(vo);
		}

		return result;
	}	
	
	//会议一览
	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {

		return "xz/meeting-list";

	}	
	//会议设置
	@AuthPassport
	@RequestMapping(value = "setting", method = RequestMethod.GET)
	public String setting(HttpServletRequest request, Model model) {
		
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();
		
		//会议室列表
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType("meeting-room");
		List<XcompanySetting> meettingRooms = xCompanySettingService.selectBySearchVo(searchVo);
		
		model.addAttribute("meetingRooms", "");
		if (!meettingRooms.isEmpty()) {
			model.addAttribute("meetingRooms", meettingRooms);
		}
		//会议类型列表
		searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType("meeting-type");
		List<XcompanySetting> meettingTypes = xCompanySettingService.selectBySearchVo(searchVo);
		
		model.addAttribute("meettingTypes", "");
		if (!meettingTypes.isEmpty()) {
			model.addAttribute("meettingTypes", meettingTypes);
		}
		
		return "xz/meeting-setting";

	}
	
	//会议设置
	@AuthPassport
	@RequestMapping(value = "setting", method = RequestMethod.POST)
	public String doSetting(HttpServletRequest request, Model model) {
		
		
		String requestUrl = request.getServletPath();
		String settingType = request.getParameter("settingType");
		
		requestUrl = requestUrl + "?setting_type=" + settingType;
		
		String name = request.getParameter("name");
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();
		
		if (StringUtil.isEmpty(settingType) || StringUtil.isEmpty("name")) {
			return "redirect:"+requestUrl;
		}
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType(settingType);
		searchVo.setName(name);
		
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		if (!list.isEmpty()) {
			return "redirect:"+requestUrl;
		}
		
		XcompanySetting record = xCompanySettingService.initXcompanySetting();
		record.setCompanyId(companyId);
		record.setSettingType(settingType);
		record.setName(name);
		xCompanySettingService.insert(record);
		
		return "redirect:"+requestUrl;

	}	
	
	
	@AuthPassport
	@RequestMapping(value = "/check-setting-name", method = { RequestMethod.GET })
	public AppResultData<Object> checkSettingName(HttpServletRequest request, Model model,
			@RequestParam("setting_type") String settingType,
			@RequestParam("name") String name
			) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long companyId = accountAuth.getCompanyId();
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType(settingType);
		searchVo.setName(name);
		
		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		if (!list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("");
			result.setData(list);
			return result;
		}

		return result;
	}		
	
	//会展服务商
	@RequestMapping(value = "service", method = RequestMethod.GET)
	public String serviceList(HttpServletRequest request) {

		return "xz/meeting-service";

	}		


}
