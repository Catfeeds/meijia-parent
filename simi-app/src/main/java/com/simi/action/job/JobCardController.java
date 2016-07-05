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

import com.meijia.utils.DateUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.Users;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.async.CardAsyncService;
import com.simi.service.card.CardService;
import com.simi.service.user.UsersService;
import com.simi.utils.CardUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.user.UserSearchVo;

@Controller
@RequestMapping(value = "/app/job/card")
public class JobCardController extends BaseController {

	@Autowired
	private UsersService userService;

	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardAsyncService cardAsyncService;	

	@Autowired
	private AdminAccountService adminAccountService;

	/**
	 * 已超过服务时间，则设置为已完成.
	 */
	@RequestMapping(value = "set_card_finish", method = RequestMethod.GET)
	public AppResultData<Object> genUserProvince(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			CardSearchVo searchVo = new CardSearchVo();
			searchVo.setPeriod((short) 0);
			searchVo.setStartTime(TimeStampUtil.getNowSecond());
			cardService.updateFinishByOvertime(searchVo);
		}
		return result;
	}

	/**
	 * 秘书2分钟未接受卡片给秘书发短信
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "check_sec_do_2min", method = RequestMethod.GET)
	public AppResultData<Object> SecDo2min(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// 1.获得秘书2分钟内未接受的列表
		List<Cards> list = cardService.selectListByAddtimeTwo();

		// 2.获得卡片类型
		List<Short> cardTypeList = new ArrayList<Short>();
		for (Cards item : list) {
			cardTypeList.add(item.getCardType());
		}
		List<Long> secIdList = new ArrayList<Long>();
		for (Cards item : list) {
			secIdList.add(item.getUserId());
		}
		
		UserSearchVo searchVo = new UserSearchVo();
		searchVo.setUserIds(secIdList);
		List<Users> secList = userService.selectBySearchVo(searchVo);
		// 3.获得秘书手机号列表
		List<String> secMobileList = new ArrayList<String>();
		// 4.获得秘书昵称列表
		List<String> secNameList = new ArrayList<String>();
		for (Users item : secList) {
			secNameList.add(item.getName());
			secMobileList.add(item.getMobile());
		}
		String minute = "2";
		String url = "";

		for (int i = 0; i < secList.size(); i++) {

			String[] content = new String[] { minute, secNameList.get(i), cardTypeList.get(i).toString(), url };
			HashMap<String, String> sendSmsResult = SmsUtil.SendSms(secMobileList.get(i), Constants.SEC_TWO_MINUTE, content);
//			System.out.println(sendSmsResult + "00000000000000");

		}
		return result;
	}

	/**
	 * 秘书超过30分钟未接受卡片--给运营人员发短信
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "check_sec_do_30min", method = RequestMethod.GET)
	public AppResultData<Object> SecDo30min(HttpServletRequest request) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// 1.获得秘书2分钟内未接受的列表
		List<Cards> list = cardService.selectListByAddtimeThirty();

		// 2.获得卡片类型
		List<Short> cardTypeList = new ArrayList<Short>();
		for (Cards item : list) {
			cardTypeList.add(item.getCardType());
		}
		List<Long> secIdList = new ArrayList<Long>();
		for (Cards item : list) {
			secIdList.add(item.getUserId());
		}
		UserSearchVo searchVo = new UserSearchVo();
		searchVo.setUserIds(secIdList);
		List<Users> secList = userService.selectBySearchVo(searchVo);
		// 3.获得秘书手机号列表
		List<String> secMobileList = new ArrayList<String>();
		// 4.获得秘书昵称列表
		List<String> secNameList = new ArrayList<String>();
		for (Users item : secList) {
			secNameList.add(item.getName());
			secMobileList.add(item.getMobile());
		}
		String minute = "30";

		for (int i = 0; i < secList.size(); i++) {

			String[] content = new String[] { secMobileList.get(i), minute, secNameList.get(i), cardTypeList.get(i).toString() };

			List<AdminAccount> adminAccounts = adminAccountService.selectByAll();
			List<String> mobileList = new ArrayList<String>();
			for (AdminAccount item : adminAccounts) {
				mobileList.add(item.getMobile());
			}
			for (int j = 0; j < mobileList.size(); j++) {
				HashMap<String, String> sendSmsResult = SmsUtil.SendSms(mobileList.get(j), Constants.SEC_THIRTY_MINUTE, content);

//				System.out.println(sendSmsResult + "00000000000000");
			}
		}
		return result;
	}
	
	/**
	 *  卡片定时提醒，发送短信, 仅针对一次性提醒
	 */
	@RequestMapping(value = "card_notify", method = RequestMethod.GET)
	public AppResultData<Object> cardNotify() {
		
//		System.out.println("======================card_notify"+ DateUtil.getNow() +"====================================");
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		//找出今天，明天，后天的卡片数据
		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setPeriod((short) 0);
		Long startTime = TimeStampUtil.getBeginOfToday();
		Date today = DateUtil.getNowOfDate();
		
		String toDateStr = DateUtil.addDay(today, 2, Calendar.DATE, DateUtil.DEFAULT_PATTERN);
		toDateStr = toDateStr + " 23:59:59";
		Long endTime = TimeStampUtil.getMillisOfDayFull(toDateStr) / 1000;
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		List<Cards> cardList = cardService.selectByRemindAll(searchVo);
		
		Long nowSecond = TimeStampUtil.getNowSecond();
		Long nowMin = TimeStampUtil.timeStampToDateHour(nowSecond * 1000);
		for (Cards vo : cardList) {
			Short setRemind = vo.getSetRemind();
			Long serviceTime = vo.getServiceTime();
			
			if (setRemind.equals((short)0)) {
				
				if (vo.getPeriod().equals((short)0) && serviceTime < TimeStampUtil.getNowSecond()) {
					vo.setStatus((short) 3);
					cardService.updateByPrimaryKey(vo);
				}
				continue;
			}

			int remindMin = CardUtil.getRemindMin(setRemind);
			
//			serviceTime = TimeStampUtil.timeStampToDateHour(serviceTime * 1000);
			Long remindTime = serviceTime - remindMin * 60;
			remindTime = TimeStampUtil.timeStampToDateHour(remindTime * 1000) / 1000;
			
//			System.out.println("card content = " + vo.getServiceContent());
//			System.out.println("setRemind = " + setRemind.toString() + "----remindMin =" + remindMin);
//			System.out.println("nowMin = " + nowMin.toString() + " ---- remindTime = " + remindTime.toString());
//			System.out.println(TimeStampUtil.timeStampToDateStr(nowMin,DateUtil.DEFAULT_FULL_PATTERN) + "----" + TimeStampUtil.timeStampToDateStr(remindTime * 1000,DateUtil.DEFAULT_FULL_PATTERN));
			if (nowMin.equals(remindTime * 1000)) {
//				System.out.println("========================cardAlertClock begin===================================");
				
				cardAsyncService.cardAlertClock(vo);
//				System.out.println("========================cardAlertClock end===================================");
			}
			
//			System.out.println("serviceTime = " + serviceTime + " ===========now = " + TimeStampUtil.getNowSecond());
			if (vo.getPeriod().equals((short)0) && serviceTime < TimeStampUtil.getNowSecond()) {
				vo.setStatus((short) 3);
				cardService.updateByPrimaryKey(vo);
			}
		}		
		return result;
	}
}
