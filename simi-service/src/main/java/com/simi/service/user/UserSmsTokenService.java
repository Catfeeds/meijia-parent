package com.simi.service.user;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.user.UserSmsToken;
import com.simi.vo.AppResultData;
import com.simi.vo.UsersSmsTokenVo;

public interface UserSmsTokenService {

    int deleteByPrimaryKey(Long id);

    int insert(UserSmsToken record);

    UserSmsToken selectByPrimaryKey(Long id);

    UserSmsToken selectByMobile(String mobile);

    int updateByPrimaryKeySelective(UserSmsToken record);

    int updateByPrimaryKey(UserSmsToken record);

	UserSmsToken initUserSmsToken(String mobile, int sms_type, String code,
			HashMap<String, String> sendSmsResult);

	UserSmsToken selectByMobileAndType(String mobile, Short smsType);

	AppResultData<Object> validateSmsToken(String mobile, String token, Short smsType);

	List<UserSmsToken> selectByListPage(UsersSmsTokenVo usersSmsTokenVo,
			int pageNo, int pageSize);
	

}
