package com.simi.service.op;
import java.util.List;

import com.simi.po.model.op.UserAppTools;

public interface UserAppToolsService {

	UserAppTools initUserAppTools();

	UserAppTools selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UserAppTools record);
	
	int insert(UserAppTools record);

	int insertSelective(UserAppTools record);
	
	int deleteByPrimaryKey(Long id);

	List<UserAppTools> selectByUserIdAndStatus(Long userId);

	List<UserAppTools> selectByUserIdAndStatusOne(Long userId);

	//PageInfo selectByListPage(int pageNo, int pageSize);

}
