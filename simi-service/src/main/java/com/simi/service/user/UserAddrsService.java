package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.UserAddrs;

public interface UserAddrsService {
    int deleteByPrimaryKey(Long id);

    Long insert(UserAddrs record);

    int insertSelective(UserAddrs record);

    UserAddrs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAddrs record);

    public int updateByPrimaryKey(UserAddrs record, boolean updateOther);

    int updateByPrimaryKey(UserAddrs record);

	List<UserAddrs> selectByMobile(String mobile);
	
	List<UserAddrs> selectByUserId(Long userId);

	List<UserAddrs> getAll();

	UserAddrs initUserAddrs(Long user_id, String mobile, Long cell_id,
			String addr, String longitude, String latitude, int poi_type,
			String name, String address, String city, String uid, String phone,
			String post_code, Short is_default, UserAddrs userAddrs);

	int updataDefaultByMobile(String mobile);

}