package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserSmsToken;
import com.simi.vo.user.UsersSmsTokenVo;

public interface UserSmsTokenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserSmsToken record);

    int insertSelective(UserSmsToken record);

    UserSmsToken selectByPrimaryKey(Long id);
    
    UserSmsToken selectByMobile(String mobile);

    int updateByPrimaryKeySelective(UserSmsToken record);

    int updateByPrimaryKey(UserSmsToken record);

	UserSmsToken selectByMobileAndType(String mobile, Short smsType);

	List<UserSmsToken> selectUserSmsTokenByMobile(
			UsersSmsTokenVo usersSmsTokenVo);
}