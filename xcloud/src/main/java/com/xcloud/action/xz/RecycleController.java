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

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.StringUtil;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.order.OrderExtRecycle;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.service.op.AppToolsService;
import com.simi.service.order.OrderExtRecycleService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.simi.vo.ApptoolsSearchVo;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrdersRecycleAddOaVo;
import com.simi.vo.user.UserAddrVo;
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

	@Autowired
	private AppToolsService appToolsService;

	// 查询与登记
	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model, OrderSearchVo searchVo) {
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

		// 二维码
		String qrCode = "";
		ApptoolsSearchVo searchVo1 = new ApptoolsSearchVo();
		searchVo1.setAction("recycle");
		List<AppTools> apptools = appToolsService.selectBySearchVo(searchVo1);
		if (!apptools.isEmpty()) {
			AppTools a = apptools.get(0);
			qrCode = a.getQrCode();
		}

		if (StringUtil.isEmpty(qrCode))
			qrCode = "/assets/img/erweima.png";
		model.addAttribute("qrCode", qrCode);

		return "xz/recycle-form";
	}

}
