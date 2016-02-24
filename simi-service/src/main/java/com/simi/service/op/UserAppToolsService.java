package com.simi.service.op;
import com.simi.po.model.op.UserAppTools;

public interface UserAppToolsService {

	UserAppTools initUserAppTools();

	UserAppTools selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UserAppTools record);
	
	int insert(UserAppTools record);

	int insertSelective(UserAppTools record);
	
	int deleteByPrimaryKey(Long id);

	//PageInfo selectByListPage(int pageNo, int pageSize);

}
