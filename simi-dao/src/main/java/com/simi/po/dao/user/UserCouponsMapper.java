package com.simi.po.dao.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simi.po.model.user.UserCoupons;

public interface UserCouponsMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(UserCoupons record);

    int insertSelective(UserCoupons record);

    UserCoupons selectByPrimaryKey(Long id);

    int updateUserCouponsByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(UserCoupons record);

    int updateByPrimaryKey(UserCoupons record);

	List<UserCoupons> selectByMobile(String mobile);

	List<UserCoupons> selectByCardPasswd(String cardPasswd);

	List<UserCoupons> selectByConditions(HashMap conditions);

	UserCoupons selectByMobileCardPwd(String mobile, String card_passwd);

	UserCoupons selectByMobileOrderNo(String mobile, String orderNo);
	
	UserCoupons selectByUserIdOrderNo(Long userId, String orderNo);

	List<UserCoupons> selectByListPage(Map<String,Object> conditions);

	List<UserCoupons> selectByUserId(Long userId);
}