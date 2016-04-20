package com.simi.action.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;
import com.simi.action.admin.AdminController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.card.Cards;
import com.simi.service.card.CardService;
import com.simi.service.user.UsersService;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.CardViewVo;

@Controller
@RequestMapping(value = "/card")
public class CardController extends AdminController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private CardService cardService;

	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String userList(HttpServletRequest request, Model model, CardSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		if (searchVo == null) searchVo = new CardSearchVo();
		
		PageInfo result = null;
		if (searchVo.getUserId() != null && searchVo.getUserId() > 0L) {
			result = cardService.selectByUserListPage(searchVo, pageNo, pageSize);
		} else {
			result = cardService.selectByListPage(searchVo, pageNo, pageSize);
		}
		
		List<Cards> list = result.getList();
		for (int i = 0; i < list.size(); i++) {
			Cards item = list.get(i);
			CardViewVo vo = cardService.changeToCardViewVo(item);
			list.set(i, vo);
		}
		result = new PageInfo(list);
		
		model.addAttribute("contentModel", result);

		return "card/list";
	}
	

}
