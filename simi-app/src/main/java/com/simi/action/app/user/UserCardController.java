package com.simi.action.app.user;

import java.math.BigDecimal;

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
	 * userId true long 用户Id
	 * card_type true int 充值卡类型
	 * pay_type true int 支付类型 0 =
	 * 余额支付 1 = 支付宝 2 = 微信支付 3 = 智慧支付 4 = 上门刷卡（保留，站位）
	 */
	@RequestMapping(value = "card_buy", method = RequestMethod.POST)
	public AppResultData<Object> cardBuy(
			@RequestParam("user_id")	Long userId,
			@RequestParam("card_type") Long cardType,
			@RequestParam("pay_type")  Short payType,
			@RequestParam(value = "card_money", required = false, defaultValue="0") BigDecimal cardMoney) {
//	    操作表 order_cards
//	    根据card_type 传递参数从表 dict_card_type 获取相应的金额

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");		
		
		//暂时不能进行充值功能：
//		result.setStatus(Constants.ERROR_999);
//		result.setMsg("系统维护,暂时无法充值.");
//		return result;
		
		
		Users users = usersService.selectByPrimaryKey(userId);
		
		// 判断是否为注册用户，非注册用户返回 999
		if (users == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}		
		
		if (cardType.equals(0L)) {
			if (cardMoney == null || cardMoney.equals(0)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("充值金额不正确!");
				return result;
			}
			
			OrderCards record = orderCardsService.initCardMoney(users, cardType, cardMoney, payType);
			orderCardsService.insert(record);
			result.setData(record);
			return result;
		}
		
		
		DictCardType dictCardType = cardTypeService.selectByPrimaryKey(cardType);

		OrderCards record = orderCardsService.initOrderCards(users, cardType, dictCardType, payType);
		orderCardsService.insert(record);

		result.setData(record);
		return result;
	}
}
