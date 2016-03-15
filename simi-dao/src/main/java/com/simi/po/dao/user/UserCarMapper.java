package com.simi.po.dao.user;

import com.simi.po.model.user.UserCar;

public interface UserCarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCar record);

    int insertSelective(UserCar record);

    UserCar selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCar record);

    int updateByPrimaryKey(UserCar record);
    
    UserCar selectByUserId(Long userId);

	UserCar selectByCarNo(String carNo);
}