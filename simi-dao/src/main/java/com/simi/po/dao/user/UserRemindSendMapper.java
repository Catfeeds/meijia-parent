package com.simi.po.dao.user;

import java.util.List;
import java.util.Map;

import com.simi.po.model.user.UserRemindSend;

public interface UserRemindSendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRemindSend record);

    int insertSelective(UserRemindSend record);

    UserRemindSend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRemindSend record);

    int updateByPrimaryKey(UserRemindSend record);

    int deleteByMap(Map<String ,Object> map);

    List<UserRemindSend> selectByMobile(String mobile);

   List<UserRemindSend> queryByRemindId(Long remind_id);
}