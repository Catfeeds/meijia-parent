package com.simi.service.user;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserImHistory;

public interface UserImHistoryService {

	UserImHistory initUserImHistory();

	Long insert(UserImHistory record);

	int updateByPrimaryKeySelective(UserImHistory record);

	int insertSelective(UserImHistory record);

	PageInfo selectByImUserListPage(String fromImUser, String toImUser, int pageNo, int pageSize);

	List<UserImHistory> getAllImUserLastIm();

	boolean insertByObjectNo(ObjectNode messages, String syncType);

	String getImMsg(JsonNode contentJsonNode);

}