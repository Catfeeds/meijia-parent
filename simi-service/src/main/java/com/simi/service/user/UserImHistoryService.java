package com.simi.service.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simi.po.model.user.UserImHistory;

public interface UserImHistoryService {

	UserImHistory initUserImHistory();

	Long insert(UserImHistory record);

	int updateByPrimaryKeySelective(UserImHistory record);

	int insertSelective(UserImHistory record);

	boolean insertByObjectNo(ObjectNode messages);

}