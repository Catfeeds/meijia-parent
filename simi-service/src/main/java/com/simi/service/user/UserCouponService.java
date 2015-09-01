package com.simi.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserCouponSearchVo;
import com.simi.po.model.dict.DictCoupons;
import com.simi.po.model.user.UserCoupons;
import com.simi.po.model.user.Users;

public interface UserCouponService {

	Long insert(UserCoupons record);

	int insertSelective(UserCoupons record);

	List<UserCoupons> selectByMobile(String mobile);

	UserCoupons selectByMobileCardPwd(String mobile, String card_passwd);

	UserCoupons selectByMobileOrderNo(String mobile, String orderNo);

	int updateByPrimaryKeySelective(UserCoupons userCoupons);

	AppResultData<Object> validateByOrderNo(String mobile, String cardPasswd,
			String orderNo);

	AppResultData<Object> validateCoupon(String mobile, String cardPasswd);

	AppResultData<Object> validateByCouponsSuitType(String cardPasswd,
			String serviceType, Short rangFrom);
	
	UserCoupons initUserCoupon();

	AppResultData<Object> validateCouponAll(String mobile, String cardPasswd,
			String orderNo, Short orderFrom, Short serviceType);

	PageInfo searchVoListPage(UserCouponSearchVo searchVo,int pageNo,int pageSize);

	UserCoupons initUserCoupons(Users users,DictCoupons dictCoupons);

	UserCoupons selectByUserIdOrderNo(Long userId, String orderNo);

	List<UserCoupons> selectByUserId(Long id);

}