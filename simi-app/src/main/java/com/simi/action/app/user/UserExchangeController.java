package com.simi.action.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictCoupons;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;
import com.simi.service.dict.CardTypeService;
import com.simi.service.dict.CouponService;
import com.simi.service.order.OrderCardsService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserExchangeController extends BaseController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private OrderCardsService orderCardsService;
	@Autowired
	private CardTypeService cardTypeService;
	@Autowired
	private UserCouponService userCouponService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private UserDetailScoreService userDetailScoreService;

	/**
	 * 积分兑换接口
	 * mobile true  手机号
	 * exchange_id 兑换物品ID
	 */
	@RequestMapping(value = "/post_score_exchange", method = RequestMethod.POST)
	public AppResultData<Object> scoreExchange(
			@RequestParam("mobile") 	String mobile,
			@RequestParam(value = "exchangeId", defaultValue = "0") Long exchangeId ) {

		AppResultData<Object> result_success = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG,
				"");

		AppResultData<Object> result_fail = new AppResultData<Object>(
				Constants.ERROR_999, ConstantMsg.USER_SOCRE_NOT_ENOUGH_MSG,
				"");
		Users users = usersService.getUserByMobile(mobile);
		DictCoupons dictCoupons = couponService.selectByCardPasswd(Constants.SCORE_CONVERT_COUPON_CARD_PASSWORD);
		if(users!=null && dictCoupons !=null){
			int score = users.getScore();
			//1、判断用户积分是否足够兑换
			if (score < 100) {
				return result_fail;
			}
			//2、满足兑换条件，将用户同20元优惠券(券码：CSNINL8B)进行绑定
			UserCoupons userCoupons = userCouponService.initUserCoupons(users, dictCoupons);
			userCouponService.insertSelective(userCoupons);

			//绑定优惠券后，将用户的积分减去100
			users.setScore((score-100));
			usersService.updateByPrimaryKeySelective(users);
			//增加一条用户明细记录，
			UserDetailScore userDetailScore = userDetailScoreService.initUserDetailScore(users);
			userDetailScoreService.insertSelective(userDetailScore);

			return result_success;
		}else{
			return result_fail;
		}

	}
}
