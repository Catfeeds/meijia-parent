package com.simi.action.app.user;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.Week;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.UserTrailReal;
import com.simi.po.model.user.Users;
import com.simi.service.card.CardService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UserTrailRealService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.user.UserMsgSearchVo;
import com.simi.vo.user.UserMsgVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserMsgController extends BaseController {

	@Autowired
	private UsersService userService;

	@Autowired
	private UserMsgService userMsgService;

	@Autowired
	private UserTrailRealService userTrailRealService;

	@Autowired
	private CardService cardService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "get_msg", method = RequestMethod.GET)
	public AppResultData<Object> getMsg(@RequestParam("user_id") Long userId,
			@RequestParam(value = "service_date", required = false, defaultValue = "") String serviceDate,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "lat", required = false, defaultValue = "") String lat,
			@RequestParam(value = "lng", required = false, defaultValue = "") String lng) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		// 先检测是否有重复类卡片，如果有则加入日程.
		userMsgService.checkPeriod(u, serviceDate);

		UserMsgSearchVo searchVo = new UserMsgSearchVo();
		searchVo.setUserId(userId);

		Long startTime = TimeStampUtil.getBeginOfToday();
		Long endTime = TimeStampUtil.getEndOfToday();
		if (!StringUtil.isEmpty(serviceDate)) {
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

			vo.setMsgId(item.getMsgId());
			vo.setUserId(userId);
			vo.setCategory(item.getCategory());
			vo.setAction(item.getAction());
			vo.setParams(item.getParams());
			vo.setGotoUrl(item.getGotoUrl());
			vo.setTitle(item.getTitle());
			vo.setSummary(item.getSummary());
			vo.setIconUrl(item.getIconUrl());

			Long serviceTime = item.getServiceTime();
			String msgTime = TimeStampUtil.timeStampToDateStr(serviceTime * 1000, "HH:mm");
			vo.setMsgTime(msgTime);
			resultList.add(vo);
		}

		result.setData(resultList);

		if (page != 1)
			return result;

		// 处理天气
		// 2. 如果不是当天，则不需要天气类卡片

		// 获得用户最后一次的地理位置信息.
		UserTrailReal userTrailReal = userTrailRealService.selectByUserId(userId);
		if (userTrailReal != null) {
			lat = userTrailReal.getLatitude();
			lng = userTrailReal.getLongitude();
		}

		UserMsgVo weatherVo = userMsgService.getWeather(serviceDate, lat, lng);
		if (weatherVo != null && !StringUtil.isEmpty(weatherVo.getTitle())) {
			resultList.add(0, weatherVo);
			result.setData(resultList);
		}

		return result;
	}

	// 按照年月查看卡片个数
	/**
	 * @param card_id
	 *            卡片ID, 0 表示新增
	 * @param user_id
	 *            用户ID
	 *
	 * @return CardViewVo
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/msg/total_by_month", method = RequestMethod.GET)
	public AppResultData<Object> totalByMonth(@RequestParam("user_id") Long userId, @RequestParam("year") int year, @RequestParam("month") int month) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		String startTimeStr = DateUtil.getFirstDayOfMonth(year, month) + " 00:00:00";
		String endTimeStr = DateUtil.getLastDayOfMonth(year, month) + " 23:59:59";

		Long startTime = TimeStampUtil.getMillisOfDayFull(startTimeStr) / 1000;
		Long endTime = TimeStampUtil.getMillisOfDayFull(endTimeStr) / 1000;

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		List<HashMap> monthDatas = new ArrayList<HashMap>();

		UserMsgSearchVo searchVo = new UserMsgSearchVo();
		searchVo.setUserId(userId);
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		
		List<HashMap> smonthDatas = userMsgService.totalByMonth(searchVo);
		List<String> months = DateUtil.getAllDaysOfMonth(year, month);

		List<HashMap> pmonthDatas = userMsgService.totalByMonth(searchVo);

		// 查找所有重复性的卡片
		CardSearchVo searchVo1 = new CardSearchVo();
		searchVo1.setUserId(userId);
		searchVo1.setUserType(u.getUserType());

		List<Short> periods = new ArrayList<Short>();
		periods.add((short) 1);
		periods.add((short) 2);
		periods.add((short) 3);
		periods.add((short) 4);
		periods.add((short) 5);
		searchVo1.setPeriods(periods);

		List<Short> statusIn = new ArrayList<Short>();
		statusIn.add((short) 1);
		statusIn.add((short) 2);
		statusIn.add((short) 3);
		searchVo1.setStatusIn(statusIn);

		List<Cards> periodCards = cardService.selectBySearchVo(searchVo1);

		if (!periodCards.isEmpty()) {
			String today = DateUtil.getToday();
			for (int i = 0; i < months.size(); i++) {
				int total = 0;
				String strDate = months.get(i);
				Date date = DateUtil.parse(strDate);

				if (!DateUtil.compare(today, strDate))
					continue;

				for (Cards item : periodCards) {
					Date serviceDate = null;
					try {
						serviceDate = DateUtil.timeStampToDate(item.getServiceTime() * 1000);

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// 判断重复提醒的开始时间的天数.

					// 1.如果是今天，并且时间已经过了，则不显示红点.
					if (strDate.equals(today) && item.getServiceTime() < TimeStampUtil.getNowSecond()) {
						continue;
					} else {
						// 其他的是未到第一次时间点则不显示.
						Long nextTime = TimeStampUtil.getMillisOfDay(strDate) / 1000;
						if (nextTime < item.getServiceTime())
							continue;
					}

					if (item.getPeriod().equals((short) 1))
						total = total + 1;

					if (item.getPeriod().equals((short) 2)) {
						Week w = DateUtil.getWeek(date);
						int weekday = w.getNumber();
						if (weekday >= 1 && weekday <= 5)
							total = total + 1;
					}

					if (item.getPeriod().equals((short) 3)) {
						Week sw = DateUtil.getWeek(serviceDate);
						int sweekday = sw.getNumber();

						Week w = DateUtil.getWeek(date);
						int weekday = w.getNumber();

						if (sweekday == weekday)
							total = total + 1;
					}

					if (item.getPeriod().equals((short) 4)) {
						String cd = TimeStampUtil.timeStampToDateStr(item.getServiceTime() * 1000, "dd");

						String d = DateUtil.format(date, "dd");

						if (cd.equals(d))
							total = total + 1;

					}

					if (item.getPeriod().equals((short) 5)) {
						String cd = TimeStampUtil.timeStampToDateStr(item.getServiceTime() * 1000, "MM-dd");

						String d = DateUtil.format(date, "MM-dd");

						if (cd.equals(d))
							total = total + 1;

					}
				}

				if (total == 0)
					continue;

				HashMap<String, String> monthData = new HashMap<String, String>();
				monthData.put("service_date", months.get(i));
				monthData.put("total", String.valueOf(total));
				pmonthDatas.add(monthData);
			}
		}

		for (int i = 0; i < months.size(); i++) {
			String strDate = months.get(i);
			HashMap item = null;
			for (int j = 0; j < smonthDatas.size(); j++) {
				HashMap monthData = smonthDatas.get(j);
				if (monthData == null || monthData.get("service_date") == null) continue;
				if (monthData.get("service_date").toString().equals(strDate)) {
					item = monthData;
					break;
				}
			}

			for (int k = 0; k < pmonthDatas.size(); k++) {
				HashMap monthData = pmonthDatas.get(k);
				
				if (monthData == null || monthData.get("service_date") == null) continue;
				
				if (monthData.get("service_date").toString().equals(strDate)) {
					if (item != null) {
						int total = Integer.valueOf(monthData.get("total").toString());
						int itotal = Integer.valueOf(item.get("total").toString());
						item.put("total", String.valueOf(itotal + total));
					} else {
						item = monthData;
					}
					break;
				}

			}

			if (item != null) {
				monthDatas.add(item);
			}
		}

		result.setData(monthDatas);
		return result;
	}

}
