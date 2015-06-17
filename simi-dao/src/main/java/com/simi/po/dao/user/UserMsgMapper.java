package com.simi.po.dao.user;

import java.util.List;
import java.util.Map;

import com.simi.po.model.user.UserMsg;

public interface UserMsgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserMsg record);

    int insertSelective(UserMsg record);

    UserMsg selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserMsg record);

    int updateByPrimaryKeySelective(UserMsg record);

    UserMsg selectByMobile(Map<String, Object> conditions);
    
    UserMsg selectByUserId(Map<String, Object> conditions);

    List<UserMsg> selectByMsgId(Long msgId);

	UserMsg selectUserByMobile(Map<String, Object> conditions);

	List<UserMsg> selectByListPage(String mobile);
	
	List<UserMsg> selectListByUserId(Long userId);

    Long countNewMsgByMobile(String mobile);
    
    Long countNewMsgByUserId(Long userId);

    int updateBySelect(UserMsg userMsg);

    List<UserMsg> selectAll();

}