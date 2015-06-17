package com.simi.service.user;

import java.util.List;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.UserDetailShare;
import com.simi.po.model.user.Users;

public interface UserDetailShareService {
    int insert(UserDetailShare record);

    int insert(Users users, UserDetailShare userDetailShare,UserDetailScore userDetailScore);

    int insertSelective(UserDetailShare record);

    UserDetailShare selectByMobile(String mobile);

    int updateByPrimaryKeySelective(UserDetailShare record);

    int updateByPrimaryKey(UserDetailShare record);

    List<UserDetailShare> selectByMobileAndShareType(Long userId,String shareType);
}