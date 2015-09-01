package com.simi.po.dao.user;

import java.util.List;
import java.util.Map;

import com.simi.po.model.user.UserRef3rd;

public interface UserRef3rdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRef3rd record);

    int insertSelective(UserRef3rd record);

    UserRef3rd selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRef3rd record);

    int updateByPrimaryKey(UserRef3rd record);
    
    UserRef3rd selectByMobile(String mobile);
    
    UserRef3rd selectByUserIdForIm(Long userId);
    
    UserRef3rd selectByPidAnd3rdType(Map<String ,Object> conditions );

	int selectBySecId(Long id);

	UserRef3rd selectByUserNameAnd3rdType(Map<String, Object> conditions);

	List<UserRef3rd> selectByUserIds(List<Long> userIds);
    
}