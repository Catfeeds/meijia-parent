package com.simi.action.coupons;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.action.BaseController;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.dict.DictCoupons;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.dict.DictCouponsService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserCouponService;
import com.meijia.utils.DateUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.dict.CouponSearchVo;
import com.simi.vo.dict.CouponVo;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;

@Controller
@RequestMapping(value = "/coupon")
public class DictCouponsController extends BaseController {

	@Autowired
	private DictCouponsService couponService;

	@Autowired
	private UserCouponService userCouponService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	

	/**优惠券列表
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
    //@AuthPassport
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

		PageInfo result = couponService.searchVoListPage(searchVo,(short)1, pageNo,
				pageSize);
		
		model.addAttribute("contentModel", result);
		model.addAttribute("searchModel", searchVo);

		return "coupons/couponList";
	}
	 /**
	  * 新增或修改表单
	  * @param dictCoupons
	  * @param model
	  * @param id
	  * @return
	  */
//	@AuthPassport
	@RequestMapping(value = "/toRechargeCouponForm", method = { RequestMethod.GET })
	public String toAddRechargeCoupons(
			@ModelAttribute("dictCoupons") DictCoupons dictCoupons,
			 Model model,
			@RequestParam(value="id" ,required = false)Long id) {
		
		if (id == null) {
			id = 0L;
			dictCoupons = couponService.initRechargeCoupon();
		}else {
			dictCoupons = couponService.selectByPrimaryKey(id);
			}
		
		CouponVo couponVo = new CouponVo();
		try {
			BeanUtils.copyProperties(couponVo,dictCoupons);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(0L);
		searchVo.setViewType((short) 0);

		
		
		List<PartnerServiceType> partnerServiceType =   partnerServiceTypeService.selectBySearchVo(searchVo);
		model.addAttribute("partnerServiceType", partnerServiceType);
		   
		model.addAttribute("dictCoupons", couponVo);
		model.addAttribute("selectDataSource",couponService.getSelectRangMonthSource());
		//model.addAttribute("serviceTypeMap",couponService.getSelectServiceTypeSource());
		return "coupons/couponForm";
	}
	/**
	 *新增或修改保存
	 */
//	@AuthPassport
	@RequestMapping(value = "/rechargeCouponForm", method = { RequestMethod.POST })
	public String addRechargeCoupons(HttpServletRequest request, Model model,
			@ModelAttribute("dictCoupons") DictCoupons dictCoupons,
			BindingResult result) {
		
		
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
		String fromDate = request.getParameter("fromDate");			//有效开始时间
		String toDate = request.getParameter("toDate");	      //有效结束时间
	
		Long flag = Long.valueOf(request.getParameter("id"));


		//更新或者新增
		if (flag != null && flag > 0) {
			//更新充值后赠送优惠券
			DictCoupons dictCoupon = couponService.selectByPrimaryKey(flag);
			dictCoupon.setServiceTypeId(dictCoupons.getServiceTypeId());
			dictCoupon.setServicePriceId(dictCoupons.getServicePriceId());
			dictCoupon.setValue(dictCoupons.getValue());
			dictCoupon.setMaxValue(dictCoupons.getMaxValue());
			dictCoupon.setDescription(dictCoupons.getDescription());
			dictCoupon.setIntroduction(dictCoupons.getIntroduction());
			dictCoupon.setRangMonth(dictCoupons.getRangMonth());
			dictCoupon.setRangType(dictCoupons.getRangType());
			couponService.updateByPrimaryKeySelective(dictCoupon);
		} else {
			String  passwordNum = request.getParameter("password_num"); //优惠券兑换码位数
			Long count = Long.valueOf(request.getParameter("count"));	//生成卡数量
			List<DictCoupons> list = couponService.selectAll();
			Long cardNo = 0L;
			//count >0 执行插入操作
			DictCoupons dictCoupon = couponService.initRechargeCoupon();
			if (count != null && count >= 0) {
				for (int i = 1; i <= count; i++) {
					dictCoupon.setId(null);
					if (list != null && list.size() > 0) {
						cardNo = Long.valueOf(couponService.selectAllByCardNo()
							.get(0).getCardNo());
						dictCoupon.setCardNo("" + (cardNo + 1));
					} else {
						cardNo = (long) 300000000;
						dictCoupon.setCardNo("130" + (cardNo + i));
					}
					dictCoupon.setAddTime(TimeStampUtil.getNow() / 1000);
					if(passwordNum !=null){
						dictCoupon.setCardPasswd(RandomUtil.randomCode(Integer.valueOf(passwordNum)));
					}else {
						dictCoupon.setCardPasswd(RandomUtil.randomCode(4));
					}
					
					dictCoupon.setUpdateTime(TimeStampUtil.getNow() / 1000);
					//如果兑换码相同，重新生成
					DictCoupons temp = couponService.selectByCardPasswd(dictCoupons.getCardPasswd());
					if(temp !=null){
						i--;
						continue;
					}else {
						//新增充值后赠送优惠券
						//dictCoupon.setCardNo(RandomUtil.randomNumber(6));
						//dictCoupon.setCardPasswd(RandomUtil.randomCode(8));
					//	dictCoupon.setServiceType(dictCoupons.getServiceType());
						dictCoupon.setValue(dictCoupons.getValue());
						dictCoupon.setMaxValue(dictCoupons.getMaxValue());
						dictCoupon.setDescription(dictCoupons.getDescription());
						dictCoupon.setServiceTypeId(dictCoupons.getServiceTypeId());
						dictCoupon.setServicePriceId(dictCoupons.getServicePriceId());
						dictCoupon.setRangType(dictCoupons.getRangType());
						dictCoupon.setIntroduction(dictCoupons.getIntroduction());
						dictCoupon.setRangMonth(dictCoupons.getRangMonth());
						if (!StringUtil.isEmpty(fromDate) && !StringUtil.isEmpty(toDate)) {
							dictCoupon.setFromDate(DateUtil.parse(fromDate));
							dictCoupon.setToDate(DateUtil.parse(toDate));
						}
						couponService.insertSelective(dictCoupon);
						
					}
				}
			}
			
		}
		return "redirect:list";
	}
	/**
	 * 根据id 删除优惠券
	 * @param dictCoupons
	 * @param id
	 * @return
	 */
//	@AuthPassport
	@RequestMapping(value = "/deleteByRechargeCouponId", method = { RequestMethod.GET })
	public String deleteByRechargeCouponId(
			@ModelAttribute("dictCoupons") DictCoupons dictCoupons,
			@RequestParam(value="id")Long id) {
		String path = "redirect:list";
		if(id!=null){
			int result = couponService.deleteByPrimaryKey(id);
			if(result > 0){
				path = "redirect:list";
			}else{
				path = "error";
			}
		}
		return path;
	}
}
