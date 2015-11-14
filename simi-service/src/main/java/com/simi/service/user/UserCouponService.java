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
	
	UserCoupons initUserCoupon();

	PageInfo searchVoListPage(UserCouponSearchVo searchVo,int pageNo,int pageSize);

	UserCoupons initUserCoupons(Users users,DictCoupons dictCoupons);

	UserCoupons selectByUserIdOrderNo(Long userId, String orderNo);

	List<UserCoupons> selectByUserId(Long id);

	AppResultData<Object> validateCoupon(Long userId, String cardPasswd);

	AppResultData<Object> validateByOrderNo(Long userId, String cardPasswd, String orderNo);

	UserCoupons selectByUserIdCardPwd(Long userId, String card_passwd);

	UserCoupons selectByPrimaryKey(Long userCouponId);

	AppResultData<Object> validateCouponAll(Long userId, Long userCouponId, String orderNo, Long serviceTypeId, Long servicePriceId, Short orderFrom);

}