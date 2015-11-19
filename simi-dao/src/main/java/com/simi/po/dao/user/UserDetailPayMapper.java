package com.simi.po.dao.user;

import java.util.List;
import java.util.Map;

import com.simi.po.model.user.UserDetailPay;

public interface UserDetailPayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDetailPay record);

    int insertSelective(UserDetailPay record);

    UserDetailPay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDetailPay record);

    int updateByPrimaryKey(UserDetailPay record);

	UserDetailPay selectByTradeNo(String tradeNo);

	 List<UserDetailPay> selectByListPage(Map<String,Object> conditions);

	List<UserDetailPay> selectByUserIdPage(Long userId, int start, int end);

}