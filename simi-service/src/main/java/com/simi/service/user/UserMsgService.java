package com.simi.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserMsg;

public interface UserMsgService {

	int deleteByPrimaryKey(Long id);

	int insert(UserMsg record);

	int insertSelective(UserMsg record);

	UserMsg selectByPrimaryKey(Long id);

	int updateByPrimaryKey(UserMsg record);

	int updateByPrimaryKeySelective(UserMsg record);

	UserMsg initUserMsg();

	PageInfo selectByListPage(Long UserId, int pageNo, int pageSize);

	List<UserMsg> selectByUserId(Long userId);

}