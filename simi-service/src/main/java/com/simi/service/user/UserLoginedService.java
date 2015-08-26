package com.simi.service.user;

import com.simi.po.model.user.UserLogined;
import com.simi.po.model.user.UserSmsToken;
import com.simi.po.model.user.Users;

public interface UserLoginedService {
    int deleteByPrimaryKey(Long id);

    int insert(UserLogined record);

    int insertSelective(UserLogined record);

    UserLogined selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLogined record);

    int updateByPrimaryKey(UserLogined record);
    
	UserLogined initUserLogined(String mobile, Long id, Short login_from, long ip);
}