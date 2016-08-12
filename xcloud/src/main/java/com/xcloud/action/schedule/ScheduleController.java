package com.xcloud.action.schedule;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.Week;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.utils.CardUtil;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.user.UserMsgSearchVo;
import com.simi.vo.xcloud.StaffListVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;

@Controller
@RequestMapping(value = "/schedule")
public class ScheduleController extends BaseController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UserMsgService userMsgService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private CardService cardService;

	@Autowired
	CardAttendService cardAttendService;

	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, @RequestParam(value = "year", required = false, defaultValue = "0") int year,
			@RequestParam(value = "month", required = false, defaultValue = "0") int month) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		return "/schedule/list";
	}

	// @AuthPassport
	@RequestMapping(value = "/get-card-list", method = { RequestMethod.GET })
	public List<Object> getCardList(HttpServletRequest request, Model model, @RequestParam(value = "start", required = false, defaultValue = "") String start,
			@RequestParam(value = "end", required = false, defaultValue = "") String end) {

		List<Object> result = new ArrayList<Object>();

		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		if (accountAuth == null)
			return result;

		Long userId = accountAuth.getUserId();

		if (userId.equals(0L))
			return result;

		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null)
			return result;

		// 处理时间的问题
		Long startTime = 0L;
		Long endTime = 0L;

		if (StringUtil.isEmpty(start))
			start = DateUtil.getToday();
		if (StringUtil.isEmpty(end))
			end = DateUtil.getToday();

		Date startDate = DateUtil.parse(start);
		String startTimeStr = DateUtil.format(startDate, "yyyy-MM-dd 00:00:00");
		startTime = TimeStampUtil.getMillisOfDayFull(startTimeStr) / 1000;

		Date endDate = DateUtil.parse(end);
		String endTimeStr = DateUtil.format(endDate, "yyyy-MM-dd 23:59:59");
		endTime = TimeStampUtil.getMillisOfDayFull(endTimeStr) / 1000;

		// 先检测是否有重复类卡片，如果有则加入日程.
		
		// 先计算出时间范围
		List<String> dataRange = new ArrayList<String>();
		if (start.equals(end)) {
			dataRange.add(start);
		} else {
			int i = 0;
			while (true) {
				String tmpDateStr = DateUtil.addDay(startDate, i, Calendar.DATE, "yyyy-MM-dd");
				if (DateUtil.compare(end, tmpDateStr))
					break;
				dataRange.add(tmpDateStr);
				i++;
			}
		}
		
		for (int i = 0; i < dataRange.size(); i++) {
			String strDate = dataRange.get(i);
			userMsgService.checkPeriod(u, strDate);
		}


		UserMsgSearchVo searchVo = new UserMsgSearchVo();
		searchVo.setUserId(userId);

		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);

		
		List<UserMsg> msgList = userMsgService.selectBySearchVo(searchVo);
		
		for (UserMsg item : msgList) {
			Map<String, String> vo = new HashMap<String, String>();
			vo.put("id", item.getMsgId().toString());
			String t = item.getSummary();
			if (t.length() > 10) t = t.substring(0, 10) + "...";
			vo.put("title",  t);
			vo.put("description", item.getTitle() + ":" + item.getSummary());
			vo.put("url", "");
			vo.put("color", "#7BB95D");
			
			Long serviceTime = item.getServiceTime() * 1000;
			String timeStr = TimeStampUtil.timeStampToDateStr(serviceTime, DateUtil.DEFAULT_FULL_PATTERN);
			vo.put("start", timeStr);
			result.add(vo);
		}
		
		
		return result;
	}

	@AuthPassport
	@RequestMapping(value = "/card-form", method = { RequestMethod.GET })
	public String cardForm(HttpServletRequest request, Model model, @RequestParam("card_type") Short cardType,
			@RequestParam(value = "card_id", required = false, defaultValue = "0") Long cardId) {

		String cardTypeName = CardUtil.getCardTypeName(cardType);
		String cardTips = CardUtil.getCardTips(cardType);
		String labelAttendStr = CardUtil.getLabelAttendStr(cardType);
		String labelTimeStr = CardUtil.getLabelTimeStr(cardType);
		String labelContentStr = CardUtil.getLabelContentStr(cardType);

		model.addAttribute("cardType", cardType);
		model.addAttribute("cardTypeName", cardTypeName);
		model.addAttribute("cardTips", cardTips);

		model.addAttribute("labelAttendStr", labelAttendStr);
		model.addAttribute("labelTimeStr", labelTimeStr);
		model.addAttribute("labelContentStr", labelContentStr);

		Cards record = cardService.initCards();
		if (cardId > 0L) {
			record = cardService.selectByPrimaryKey(cardId);
		}

		record.setCardType(cardType);

		model.addAttribute("contentModel", record);

		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		Long userId = accountAuth.getUserId();

		model.addAttribute("userId", userId);

		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xcompanyStaffService.selectBySearchVo(searchVo);
		List<StaffListVo> staffListVos = xcompanyStaffService.changeToStaffLisVos(companyId, staffList);

		model.addAttribute("staffList", staffListVos);

		return "/schedule/card-form";
	}

	@AuthPassport
	@RequestMapping(value = "/card-view", method = { RequestMethod.GET })
	public String cardView(HttpServletRequest request, Model model, @RequestParam("card_id") Long cardId) {

		Cards record = cardService.selectByPrimaryKey(cardId);

		if (record == null) {
			return "";
		}

		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();

		Users u = usersService.selectByPrimaryKey(userId);

		model.addAttribute("userId", userId);
		model.addAttribute("headImg", u.getHeadImg());
		model.addAttribute("name", u.getName());

		Short cardType = record.getCardType();
		String cardTypeName = CardUtil.getCardTypeName(cardType);
		String cardTips = CardUtil.getCardTips(cardType);
		String labelAttendStr = CardUtil.getLabelAttendStr(cardType);
		String labelTimeStr = CardUtil.getLabelTimeStr(cardType);
		String labelContentStr = CardUtil.getLabelContentStr(cardType);

		model.addAttribute("cardId", cardId);
		model.addAttribute("cardType", cardType);
		model.addAttribute("cardTypeName", cardTypeName);
		model.addAttribute("cardTips", cardTips);

		model.addAttribute("labelAttendStr", labelAttendStr);
		model.addAttribute("labelTimeStr", labelTimeStr);
		model.addAttribute("labelContentStr", labelContentStr);

		List<CardAttend> attends = cardAttendService.selectByCardId(cardId);
		model.addAttribute("attends", attends);

		Long serviceTime = record.getServiceTime();
		String serviceTimeStr = TimeStampUtil.timeStampToDateStr(serviceTime * 1000, "yyyy-MM-dd HH:mm");
		model.addAttribute("serviceTimeStr", serviceTimeStr);
		model.addAttribute("serviceAddr", record.getServiceAddr());
		model.addAttribute("serviceContent", record.getServiceContent());

		return "/schedule/card-view";
	}

	private Map<String, String> getCalendarItem(Long cardId, Short cardType, String timeStr) {
		Map<String, String> vo = new HashMap<String, String>();
		vo.put("id", cardId.toString());
		vo.put("title", CardUtil.getCardTypeName(cardType));
		vo.put("url", "/xcloud/schedule/card-view?card_id=" + cardId);

		vo.put("start", timeStr);
		return vo;
	}
}
