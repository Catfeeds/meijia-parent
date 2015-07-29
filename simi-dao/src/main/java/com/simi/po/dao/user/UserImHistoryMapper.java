package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserImHistory;

public interface UserImHistoryMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(UserImHistory record);

    int insertSelective(UserImHistory record);

    int updateByPrimaryKeySelective(UserImHistory record);

    int updateByPrimaryKey(UserImHistory record);

    UserImHistory selectByPrimaryKey(Long id);
    
    UserImHistory selectByUuid(String uuid);
    
    //获得跟某人的聊天记录，分页展现
    List<UserImHistory> selectByImUserListPage(String imUserName);
    
    //获得所有发给好友的最新一条聊天记录
    List<UserImHistory> selectMaxByFromImUser(String imUserName);
    
    //获得好友发给我的最新一条聊天记录
    List<UserImHistory> selectMaxByToImUser(String imUserName);

	List<UserImHistory> selectByImUserListPage(String fromImUser, String toImUser);
}