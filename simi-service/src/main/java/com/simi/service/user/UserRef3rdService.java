package com.simi.service.user;


import java.util.List;

import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;

public interface UserRef3rdService {

		int deleteByPrimaryKey(Long id);

	    int insert(UserRef3rd record);

	    int insertSelective(UserRef3rd record);

	    UserRef3rd selectByPrimaryKey(Long id);

	    int updateByPrimaryKeySelective(UserRef3rd record);

	    int updateByPrimaryKey(UserRef3rd record);
	    
	    UserRef3rd selectByMobile(String mobile);
	    
	    UserRef3rd selectByPidAnd3rdType(String pid,String thirdType);
	    
	    UserRef3rd initUserRef3rd(String mobile);
	    
		Boolean allotSec(Users user);

		UserRefSec initUserRefSec();

		UserRef3rd selectByUserIdForIm(Long userId);

		UserRef3rd selectByUserNameAnd3rdType(String userName, String thirdType);

		List<UserRef3rd> selectByUserIds(List<Long> userIds);

}