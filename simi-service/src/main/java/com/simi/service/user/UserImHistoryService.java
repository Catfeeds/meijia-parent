package com.simi.service.user;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserImHistory;
import com.simi.vo.user.UserImVo;

public interface UserImHistoryService {

	UserImHistory initUserImHistory();

	Long insert(UserImHistory record);

	int updateByPrimaryKeySelective(UserImHistory record);

	int insertSelective(UserImHistory record);

	boolean insertByObjectNo(ObjectNode messages);

	PageInfo selectByImUserListPage(String fromImUser, String toImUser, int pageNo, int pageSize);

	List<UserImVo> getAllImUserLastIm(Long secId, String imUserName);

}