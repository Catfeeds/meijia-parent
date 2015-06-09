package com.simi.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserMsg;

public interface UserMsgService {

	int deleteByPrimaryKey(Long id);

    int insert(UserMsg record);

    int insertSelective(UserMsg record);

    UserMsg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserMsg record);

    int updateByPrimaryKey(UserMsg record);

    List<UserMsg> selectByMsgId(Long msgId);

	UserMsg getUserByMobile(String mobile, Long msgId);

	UserMsg selectUserByMobile(String mobile,int page);

	PageInfo searchVoListPage(int pageNo, int pageSize, String mobile);

    Long countNewMsgByMobile(String mobile);

    int postMsgRead(String mobile,Long msgId);

    PageInfo searchListPage(int pageNo, int pageSize);


}
