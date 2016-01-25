package com.xcloud.action.xz;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.ExcelUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.utils.CardUtil;
import com.simi.vo.AppResultData;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.xcloud.StaffListVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
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
	private CardService cardService;
	
	@Autowired
	CardAttendService cardAttendService;	

//	@AuthPassport
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
		if (u == null)  return result;

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
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {

		return "xz/meeting-list";

	}	
	//会议设置
	@RequestMapping(value = "setting", method = RequestMethod.GET)
	public String setting(HttpServletRequest request) {

		return "xz/meeting-setting";

	}
	//会展服务商
	@RequestMapping(value = "service", method = RequestMethod.GET)
	public String serviceList(HttpServletRequest request) {

		return "xz/meeting-service";

	}		


}
