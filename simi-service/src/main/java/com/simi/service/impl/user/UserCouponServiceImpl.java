package com.simi.service.impl.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.service.dict.CouponService;
import com.simi.service.user.UserCouponService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserCouponSearchVo;
import com.simi.po.dao.user.UserCouponsMapper;
import com.simi.po.model.dict.DictCoupons;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.Users;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserCouponServiceImpl implements UserCouponService {
	@Autowired
	private UserCouponsMapper userCouponsMapper;

	@Autowired
	private CouponService couponService;

	@Override
	public UserCoupons initUserCoupon() {
		UserCoupons record = new UserCoupons();
		record.setId(0L);
		record.setUserId(0L);
		record.setMobile("00000000000");
		record.setCouponId(0L);
		record.setCardPasswd("0");
		record.setValue(new BigDecimal(0));
		record.setExpTime(0L);
		record.setIsUsed((short) 0);
		record.setUsedTime(0L);
		record.setOrderNo("0");
		record.setAddTime(TimeStampUtil.getNow()/1000);
		record.setUpdateTime(0L);
		return record;
	}

	@Override
	public List<UserCoupons> selectByMobile(String mobile) {
		return userCouponsMapper.selectByMobile(mobile);
	}

	@Override
	public Long insert(UserCoupons record) {
		return userCouponsMapper.insert(record);
	}

	@Override
	public UserCoupons selectByMobileCardPwd(String mobile, String card_passwd) {
		return userCouponsMapper.selectByMobileCardPwd(mobile, card_passwd);
	}

	/*
	 * 整体验证优惠券是否有效的方法
	 */
	@Override
	public AppResultData<Object> validateCouponAll(String mobile, 
												   String cardPasswd,
										  		   String orderNo, 
										  		   Short orderFrom,
												   Short serviceType) {
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		result = this.validateCoupon(mobile, cardPasswd);

		if (result.getStatus() != Constants.SUCCESS_0) {
			return result;
		}

		result = this.validateByCouponsSuitType(cardPasswd, String.valueOf(serviceType), orderFrom);
		if (result.getStatus() != Constants.SUCCESS_0) {
			return result;
		}

		result = this.validateByOrderNo(mobile, cardPasswd, orderNo);
		if (result.getStatus() != Constants.SUCCESS_0) {
			return result;
		}

		return result;
	}

	/*
	 * 验证优惠券是否有效方法
	 * @param
	 * 		mobile  手机号, require = true
	 * 		cardPasswd  优惠券  require = true
	 */
	@Override
	public AppResultData<Object> validateCoupon(String mobile,
			String cardPasswd) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG,
				"");
		//查找出此优惠券信息.
		DictCoupons coupon =  couponService.selectByCardPasswd(cardPasswd);

		if (coupon == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.COUPON_INVALID_MSG);
			result.setData("");
			return result;
		}
		
		//1. 是否已经过了有效期  exp_time，
		//如果已经过去，返回提示信息：“要兑换的优惠券已过期，换一个吧。”
		Long expTime = coupon.getExpTime();
		Long nowTime = TimeStampUtil.getNow() / 1000;
		if (expTime > 0 && expTime < nowTime) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.COUPON_EXP_TIME_MSG);
			result.setData("");
			return result;
		}		
		
		//3.如果是唯一性的优惠券，则需要判定是否被别人用过.
		if (coupon.getRangType().equals((short)1)) {
			List<UserCoupons> listUsed = userCouponsMapper.selectByCardPasswd(cardPasswd);
			UserCoupons temp = null;
			for(int i = 0; i < listUsed.size(); i++) {
				temp = listUsed.get(i);
				if (!temp.getMobile().equals(mobile)) {
					result.setStatus(Constants.ERROR_999);
					result.setMsg(ConstantMsg.COUPON_IS_USED_MSG);
					return result;
				}
			}
		}		

		//查找此用户和优惠券的使用情况
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("mobile", mobile);
		conditions.put("cardPasswd", cardPasswd);
		conditions.put("isUsed", 0);
		List<UserCoupons> list = userCouponsMapper.selectByConditions(conditions);
		
		if (list.isEmpty()) {
			return result;
		}
		
		
		UserCoupons item = null;
		if (!list.isEmpty()) {
			item = list.get(0);
		}

		//2.是否已经使用过
		if (item != null && item.getIsUsed().equals((short)1)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.COUPON_IS_USED_MSG);
			result.setData("");
			return result;
		}



		return result;
	}

	/*
	 * 验证优惠券是否有效方法,仅验证是否用于其他订单
	 * @param
	 * 		mobile  手机号, require = true
	 * 		cardPasswd  优惠券  require = true
	 * 		orderNo		订单号，默认为""  require = false;
	 */
	@Override
	public AppResultData<Object> validateByOrderNo(String mobile,
			String cardPasswd, String orderNo) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG,
				"");
		//查找出此优惠券信息.
		DictCoupons coupon =  couponService.selectByCardPasswd(cardPasswd);

		if (coupon == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.COUPON_INVALID_MSG);
			result.setData("");
		}

		//查找此用户和优惠券的使用情况
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("mobile", mobile);
		conditions.put("cardPasswd", cardPasswd);
		conditions.put("isUsed", 0);
		List<UserCoupons> list = userCouponsMapper.selectByConditions(conditions);
		
		if (list.isEmpty()) {
			return result;
		}
		
		UserCoupons item = null;
		if (!list.isEmpty()) {
			item = list.get(0);
		}

		//4.判断是否已经被其他orderNo使用过.
		if (item !=null && item.getOrderNo().equals("") && orderNo.equals("")) {
			if (item.getOrderNo() != orderNo) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.COUPON_INVALID_MSG);
				result.setData("");
				return result;
			}
		}

		return result;
	}

	/*
	 * 验证优惠券是否有效方法，仅验证服务类型和来源渠道是否正确
	 * @param
	 * 		mobile  手机号, require = true
	 * 		cardPasswd  优惠券  require = true
	 * 		serviceType  服务类型，默认为 0 ,
	 */
	@Override
	public AppResultData<Object> validateByCouponsSuitType(
			String cardPasswd, String serviceType, Short rangFrom ) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG,
				"");
		//查找出此优惠券信息.
		DictCoupons coupon =  couponService.selectByCardPasswd(cardPasswd);

		if (coupon == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.COUPON_INVALID_MSG);
			result.setData("");
		}

		//5.  该优惠券的服务类型 dict_coupons.service_type 跟当前的订单服务类型是否匹配
		if (coupon !=null && serviceType.isEmpty() == false) {
				if (!coupon.getServiceType().equals("0")) {
					if (coupon.getServiceType().indexOf(serviceType) < 0) {
						result.setStatus(Constants.ERROR_999);
						result.setMsg(ConstantMsg.COUPON_SERVICE_TYPE_INVALID_MSG);
						result.setData("");
						return result;
					}
				}
		}
		
		//6.该优惠卷的渠道类型 dict_coupons.range_from
		//跟当前的订单来源order_from 是否匹配   999 = 全部渠道适用
		if (coupon !=null && coupon.getRangFrom() < 999) {
			if (!coupon.getRangFrom().equals(rangFrom)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.COUPON_INVALID_MSG);
				result.setData("");
				return result;
			}
		}

		return result;
	}

	@Override
	public int updateByPrimaryKeySelective(UserCoupons userCoupons) {
		return userCouponsMapper.updateByPrimaryKeySelective(userCoupons);
	}

	@Override
	public PageInfo searchVoListPage(UserCouponSearchVo searchVo, int pageNo,
			int pageSize) {
		HashMap<String,Object> conditions = new HashMap<String,Object>();
		 String cardPasswd = searchVo.getCardPasswd();

		if(cardPasswd !=null && !cardPasswd.isEmpty() ){
			conditions.put("cardPasswd", cardPasswd.trim());
		}

		 PageHelper.startPage(pageNo, pageSize);
         List<UserCoupons> list = userCouponsMapper.selectByListPage(conditions);
        /*
         if(list!=null && list.size()!=0){
             List<OrderViewVo> orderViewList = this.getOrderViewList(list);

             for(int i = 0; i < list.size(); i++) {
            	 if (orderViewList.get(i) != null) {
            		 list.set(i, orderViewList.get(i));
            	 }
             }
         }*/
        PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public int insertSelective(UserCoupons record) {
		return userCouponsMapper.insertSelective(record);
	}

	@Override
	public UserCoupons initUserCoupons(Users users, DictCoupons dictCoupons) {
		UserCoupons userCoupons = new UserCoupons();
		userCoupons.setUserId(users.getId());
		userCoupons.setMobile(users.getMobile());
		userCoupons.setCouponId(dictCoupons.getId());
		userCoupons.setCardPasswd(dictCoupons.getCardPasswd());
		userCoupons.setValue(dictCoupons.getValue());
		userCoupons.setExpTime(dictCoupons.getExpTime());
		userCoupons.setIsUsed(Constants.IS_USER_PROMOTION_0);
		userCoupons.setAddTime(TimeStampUtil.getNow()/1000);
		userCoupons.setOrderNo("0");
		return userCoupons;
	}

	@Override
	public UserCoupons selectByMobileOrderNo(String mobile, String orderNo) {
		return userCouponsMapper.selectByMobileOrderNo(mobile, orderNo);
	}
	
	@Override
	public UserCoupons selectByUserIdOrderNo(Long userId, String orderNo) {
		return userCouponsMapper.selectByUserIdOrderNo(userId, orderNo);
	}

}