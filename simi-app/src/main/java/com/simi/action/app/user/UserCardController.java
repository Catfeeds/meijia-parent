package com.simi.action.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictCardType;
import com.simi.po.model.order.OrderCards;
import com.simi.po.model.user.Users;
import com.simi.service.dict.CardTypeService;
import com.simi.service.order.OrderCardsService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserCardController extends BaseController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private OrderCardsService orderCardsService;
	@Autowired
	private CardTypeService cardTypeService;


	// 8. 会员充值接口
	/**
	 * mobile true string 手机号
	 * card_type true int 充值卡类型
	 * pay_type true int 支付类型 0 =
	 * 余额支付 1 = 支付宝 2 = 微信支付 3 = 智慧支付 4 = 上门刷卡（保留，站位）
	 */
	@RequestMapping(value = "card_buy", method = RequestMethod.POST)
	public AppResultData<Object> cardBuy(@RequestParam("mobile")
	String mobile, @RequestParam("card_type")
	int card_type, @RequestParam("pay_type")
	int pay_type) {
//	    操作表 order_cards
//	    根据card_type 传递参数从表 dict_card_type 获取相应的金额

		Users users = usersService.getUserByMobile(mobile);
		DictCardType dictCardType = cardTypeService.selectByPrimaryKey(Long.valueOf(card_type));

		OrderCards record = orderCardsService.initOrderCards(mobile, card_type, users, dictCardType, pay_type);
		orderCardsService.insert(record);

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG,
				record);
		return result;
	}
}
