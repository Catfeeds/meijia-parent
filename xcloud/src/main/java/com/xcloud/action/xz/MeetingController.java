package com.xcloud.action.xz;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.pagehelper.PageInfo;
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
import com.simi.vo.xcloud.CompanySettingVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

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
		// if (u == null) return result;

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

		if (cards.isEmpty())
			return result;

		for (Cards item : cards) {
			Map<String, String> vo = new HashMap<String, String>();
			vo.put("id", item.getCardId().toString());
			vo.put("title", CardUtil.getCardTypeName(item.getCardType()));
			vo.put("url", "/xcloud/schedule/card-view?card_id=" + item.getCardId());

			Long serviceTime = item.getServiceTime() * 1000;

			vo.put("start", TimeStampUtil.timeStampToDateStr(serviceTime, "yyyy-MM-dd HH:mm:ss"));
			result.add(vo);
		}

		return result;
	}

	// 会议一览
	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {

		return "xz/meeting-list";

	}

	// 会议设置
	@AuthPassport
	@RequestMapping(value = "room-list", method = RequestMethod.GET)
	public String setting(HttpServletRequest request, Model model) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();

		Long companyId = accountAuth.getCompanyId();

		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType("meeting-room");

		PageInfo result = xCompanySettingService.selectByListPage(searchVo, pageNo, pageSize);

		List<XcompanySetting> list = result.getList();
		for (int i = 0; i < list.size(); i++) {
			CompanySettingVo vo = xCompanySettingService.getCompantSettingVo(list.get(i));
			list.set(i, vo);
		}
		PageInfo info = new PageInfo(list);
		model.addAttribute("contentModel", info);

		return "xz/meeting-room-list";

	}

	@AuthPassport
	@RequestMapping(value = "room-form", method = RequestMethod.GET)
	public String roomForm(HttpServletRequest request, Model model,  @RequestParam(value = "id") Long id) {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long companyId = accountAuth.getCompanyId();

		if (id == null) id = 0L;
		
		XcompanySetting record = xCompanySettingService.initXcompanySetting();
		if (id > 0L) {
			record = xCompanySettingService.selectByPrimaryKey(id);
			
		}
		
		model.addAttribute("contentModel", record);

		return "xz/meeting-room-form";
	}
	
	@AuthPassport
	@RequestMapping(value = "room-form", method = { RequestMethod.POST })
	public String doRoomForm(HttpServletRequest request, Model model, 
			@ModelAttribute("contentModel") XcompanySetting record,
			BindingResult result
			) throws IOException {

		Long id = record.getId();
		String name = record.getName();
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long userId = accountAuth.getUserId();
		Long companyId = accountAuth.getCompanyId();

		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType("meeting-room");
		searchVo.setName(name);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		if (!list.isEmpty()) {
			for (XcompanySetting item : list) {
				if (!item.getId().equals(id)) {
					result.addError(new FieldError("contentModel","username","用户名或密码错误。"));
		        	return roomForm(request, model, id);
				}
			}
		}
		
		record.setCompanyId(companyId);
		record.setUserId(userId);
		record.setSettingType("meeting-room");
		record.setIsEnable((short) 1);
		
		
		if (id > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			xCompanySettingService.updateByPrimaryKeySelective(record);
		} else {
			record.setAddTime(TimeStampUtil.getNowSecond());
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			xCompanySettingService.insert(record);
		}

		return "redirect:room-list";
	}	

	// 检测配置是否重名
	@AuthPassport
	@RequestMapping(value = "/check-room-name", method = { RequestMethod.GET })
	public AppResultData<Object> checkSettingName(HttpServletRequest request, Model model, 
			@RequestParam("id") Long id,
			@RequestParam("name") String name) throws UnsupportedEncodingException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		if (StringUtil.isEmpty(name)) return result;
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setSettingType("meeting-room");
		searchVo.setName(name);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);

		if (!list.isEmpty()) {
			for (XcompanySetting item : list) {
				if (!id.equals(item.getId())) {
					result.setStatus(Constants.ERROR_999);
					result.setMsg("");
					return result;
				}
			}
		}

		return result;
	}

	// 删除会议
	@AuthPassport
	@RequestMapping(value = "room-del", method = RequestMethod.POST)
	public AppResultData<Object> settingDel(HttpServletRequest request, Model model, @RequestParam("id") Long id) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (id.equals(0L)) return result;
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		XcompanySetting vo = xCompanySettingService.selectByPrimaryKey(id);
		if (vo != null) {
			if (vo.getCompanyId().equals(companyId)) {
				xCompanySettingService.deleteByPrimaryKey(id);
			}
		}

		return result;

	}

	// 会展服务商
	@RequestMapping(value = "service", method = RequestMethod.GET)
	public String serviceList(HttpServletRequest request) {

		return "xz/meeting-service";

	}

}
