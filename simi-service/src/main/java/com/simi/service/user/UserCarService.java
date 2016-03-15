package com.simi.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserCar;



public interface UserCarService {

	int deleteByPrimaryKey(Long id);

	int insert(UserCar record);

	int insertSelective(UserCar record);

	UserCar selectByPrimaryKey(Long id);

	int updateByPrimaryKey(UserCar record);

	int updateByPrimaryKeySelective(UserCar record);

	UserCar initUserCar();

	UserCar selectByUserId(Long userId);

	UserCar selectByCarNo(String carNo);
	
}