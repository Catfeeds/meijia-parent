package com.simi.po.dao.user;

import java.util.List;
import java.util.Map;

import com.simi.po.model.user.UserDetailShare;

public interface UserDetailShareMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDetailShare record);

    int insertSelective(UserDetailShare record);

    UserDetailShare selectByMobile(String mobile);

    int updateByPrimaryKeySelective(UserDetailShare record);

    int updateByPrimaryKey(UserDetailShare record);

    List<UserDetailShare> selectByMobileAndShareType(Map<String,Object> conditions);
}