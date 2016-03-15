package com.xcloud.action.xz;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.StringUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderExtClean;
import com.simi.po.model.order.OrderExtRecycle;
import com.simi.po.model.order.OrderExtWater;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.service.order.OrderExtCleanService;
import com.simi.service.order.OrderExtRecycleService;
import com.simi.service.order.OrderExtWaterService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.AppResultData;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.OrdersListVo;
import com.simi.vo.order.OrderExtWaterListVo;
import com.simi.vo.order.OrderWaterComVo;
import com.simi.vo.order.OrdersCleanAddOaVo;
import com.simi.vo.order.OrdersRecycleAddOaVo;
import com.simi.vo.order.OrdersWaterAddOaVo;
import com.simi.vo.order.OrdersWaterListVo;
import com.simi.vo.partners.PartnerUserSearchVo;
import com.simi.vo.user.UserAddrVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/xz/recycle/")
public class RecycleController extends BaseController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UserAddrsService userAddrsService;
	
	@Autowired
	private OrderExtRecycleService orderExtRecycleService;

	
	// 查询与登记
	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model,OrderSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();
		
		searchVo.setUserId(userId);
	
		PageInfo result = orderExtRecycleService.selectByPage(searchVo, pageNo, pageSize);
		
		model.addAttribute("contentModel", result);
		
		return "xz/recycle-list";
	}
	
	// 保洁
	@AuthPassport
	@RequestMapping(value = "recycle-form", method = RequestMethod.GET)
	public String waterForm(Model model, HttpServletRequest request) {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long userId = accountAuth.getUserId();
		model.addAttribute("userId", userId);
		Users users = usersService.selectByPrimaryKey(userId);
		
		OrdersRecycleAddOaVo vo = new OrdersRecycleAddOaVo();
		OrderExtRecycle recycle = orderExtRecycleService.initOrderExtRecycle();
		BeanUtilsExp.copyPropertiesIgnoreNull(recycle, vo);
		vo.setRemarks("");
		vo.setMobile("");
		vo.setLinkMan(users.getName());
		vo.setLinkTel(users.getMobile());

		// 用户地址列表
				List<UserAddrs> userAddrsList = userAddrsService.selectByUserId(userId);
				List<UserAddrVo> voList = new ArrayList<UserAddrVo>();
				for (int i = 0; i < userAddrsList.size(); i++) {
					UserAddrs addrs = userAddrsList.get(i);
					UserAddrVo vos = new UserAddrVo();
					vos.setAddrId(addrs.getId());
					vos.setAddrName(addrs.getAddress() + addrs.getAddr());
					voList.add(vos);
				}
				model.addAttribute("userAddrVo", voList);
		
		model.addAttribute("contentModel", vo);
		return "xz/recycle-form";
	}




}
