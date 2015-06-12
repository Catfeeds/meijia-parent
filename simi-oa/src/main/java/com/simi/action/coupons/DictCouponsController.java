package com.simi.action.coupons;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;
import com.simi.action.BaseController;
import com.simi.oa.common.ConstantOa;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictCoupons;
import com.simi.service.dict.CouponService;
import com.simi.service.user.UserCouponService;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.dict.CouponSearchVo;
import com.simi.vo.user.UserCouponSearchVo;

@Controller
@RequestMapping(value = "/coupon")
public class DictCouponsController extends BaseController {

	@Autowired
	private CouponService couponService;

	@Autowired
	private UserCouponService userCouponService;

	// @AuthPassport

	/**列表显示发不的优惠券
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model,
			CouponSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result = couponService.searchVoListPage(searchVo, pageNo,
				pageSize);

		model.addAttribute("contentModel", result);

		return "coupons/couponList";
	}

	@RequestMapping(value = "/toAddCoupons", method = { RequestMethod.GET })
	public String toAddCoupons(
			@ModelAttribute("dictCoupons") DictCoupons dictCoupons) {

		return "coupons/couponForm";
	}

	/**
	 * 新增优惠券
	 * @param request
	 * @param model
	 * @param dictCoupons
	 * @param result
	 * @return
	 */
	// @AuthPassport
	@RequestMapping(value = "/addCoupons", method = { RequestMethod.POST })
	public String addCoupons(HttpServletRequest request, Model model,
			@ModelAttribute("dictCoupons") DictCoupons dictCoupons,
			BindingResult result) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		Long id = dictCoupons.getId();
		List<DictCoupons> list = couponService.selectAll();
		Long cardNo = 0L;
		if (id != null && id >= 0) {
			for (int i = 1; i <= id; i++) {
				dictCoupons.setId(null);
				if (list != null && list.size() > 0) {
					cardNo = Long.valueOf(couponService.selectAllByCardNo()
							.get(0).getCardNo());
					dictCoupons.setCardNo("" + (cardNo + 1));
				} else {
					cardNo = (long) 300000000;
					dictCoupons.setCardNo("130" + (cardNo + i));
				}
				dictCoupons.setAddTime(TimeStampUtil.getNow() / 1000);
				dictCoupons.setCardPasswd(RandomUtil.randomCode(8));
				String str = request.getParameter("expTime");
				if (str != null && !str.isEmpty()) {
					dictCoupons.setExpTime(TimeStampUtil.getMillisOfDay(request
							.getParameter("expTime")) / 1000);
				} else {
					dictCoupons.setExpTime(Constants.COUPON_VALID_FOREVER);
				}
				dictCoupons.setUpdateTime(TimeStampUtil.getNow() / 1000);
				couponService.insertSelective(dictCoupons);
			}
		}
		return "redirect:list";
	}

	// @AuthPassport

	/**
	 * 列表显示用户优惠券
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	@RequestMapping(value = "/used", method = { RequestMethod.GET })
	public String userCouponsList(HttpServletRequest request, Model model,
			UserCouponSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		model.addAttribute("searchModel", searchVo);

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result = userCouponService.searchVoListPage(searchVo, pageNo,
				pageSize);
		model.addAttribute("contentModel", result);

		return "coupons/userCouponList";
	}

}
