package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserAddrs;

public interface UserAddrsMapper {

	 int deleteByPrimaryKey(Long id);

	    Long insert(UserAddrs record);

	    int insertSelective(UserAddrs record);

	    UserAddrs selectByPrimaryKey(Long id);

	    int updateDefaultByMobile(String mobile);

	    int updateByMobile(String mobile);

	    int updateByPrimaryKeySelective(UserAddrs record);

	    int updateByPrimaryKey(UserAddrs record);

		List<UserAddrs> selectByMobile(String mobile);
		
		List<UserAddrs> selectByUserId(Long userId);

		List<UserAddrs> selectAll();

		List<UserAddrs> selectByIds(List<Long> ids);
		
		
}