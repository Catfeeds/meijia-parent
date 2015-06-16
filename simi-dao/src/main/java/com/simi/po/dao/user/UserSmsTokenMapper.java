package com.simi.po.dao.user;

import com.simi.po.model.user.UserSmsToken;

public interface UserSmsTokenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserSmsToken record);

    int insertSelective(UserSmsToken record);

    UserSmsToken selectByPrimaryKey(Long id);

    UserSmsToken selectByMobile(String mobile);

    int updateByPrimaryKeySelective(UserSmsToken record);

    int updateByPrimaryKey(UserSmsToken record);

	UserSmsToken selectByMobileAndType(String mobile);
}