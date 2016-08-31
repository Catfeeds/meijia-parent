package com.xcloud.action.hr;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.order.OrderExtTeam;
import com.simi.po.model.user.Users;
import com.simi.service.dict.CityService;
import com.simi.service.order.OrderExtTeamService;
import com.simi.service.order.OrderExtWaterService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.dict.DictCityVo;
import com.simi.vo.order.OrdersTeamAddOaVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/hr/teamwork/")
public class TeamController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private OrderExtWaterService orderExtWaterService;

	@Autowired
	private PartnerUserService partnerUserService;

	@Autowired
	private PartnerServicePriceService partnerServicePriceService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	private OrderQueryService orderQueryService;

	@Autowired
	private OrderExtTeamService orderExtTeamService;

	@Autowired
	private CityService cityService;

	// 一键送水
	@AuthPassport
	@RequestMapping(value = "teamwork-form", method = RequestMethod.GET)
	public String waterForm(Model model, HttpServletRequest request) {

		Long serviceTypeId = (long) 79;
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long userId = accountAuth.getUserId();
		model.addAttribute("userId", userId);

		Users users = usersService.selectByPrimaryKey(userId);

		OrdersTeamAddOaVo vo = new OrdersTeamAddOaVo();
		OrderExtTeam team = orderExtTeamService.initOrderExtTeam();
		BeanUtilsExp.copyPropertiesIgnoreNull(team, vo);
		vo.setRemarks("");
		vo.setMobile("");
		vo.setLinkMan(users.getName());
		vo.setLinkTel(users.getMobile());

		List<DictCity> dictCityList = cityService.selectAll();
		List<DictCityVo> voList = new ArrayList<DictCityVo>();
		for (int i = 0; i < dictCityList.size(); i++) {
			DictCity city = dictCityList.get(i);
			DictCityVo vos = new DictCityVo();
			vos.setCityId(city.getCityId());
			vos.setCityName(city.getName());
			voList.add(vos);
		}
		model.addAttribute("dictCityVo", voList);
		model.addAttribute("contentModel", vo);
		return "hr/team-form";
	}

	// 查询与登记
	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model,
			OrderSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();

		searchVo.setUserId(userId);

		PageInfo result = orderExtTeamService.selectByPage(searchVo, pageNo,
				pageSize);

		model.addAttribute("contentModel", result);

		return "hr/team-list";
	}

}
