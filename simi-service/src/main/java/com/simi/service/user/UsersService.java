package com.simi.service.user;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.simi.vo.UserSearchVo;
import com.simi.vo.UsersSearchVo;
import com.simi.vo.user.UserIndexVo;
import com.simi.vo.user.UserViewVo;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;


public interface UsersService {
	
	Users initUsers();
	
	Users initUsersByMobileAndName(String mobile, String name);
	
	Long insert(Users record);
	
	Long insertSelective(Users u);
	
	int updateByPrimaryKeySelective(Users user) ;
	
	Users initUserForm();
	
	Users genUser(String mobile, String name, Short addFrom);
	
	Users selectByPrimaryKey(Long id);
	
	Users selectByOpenidAndThirdType(String openid,String thirdType);
	
	Users selectByMobile(String mobile);
	
	Users selectUserByIdCard(String idCard);
	
	UserIndexVo getUserIndexVoByUserId(Users user, Users viewUser);
	
	UserViewVo getUserViewByUserId(Long userId);
	
	UserViewVo getUserInfo(Long userId);
	
	List<Users> selectByAll();
	
	List<Users> selectByUserIds(List<Long> ids);
	
	List<Users> selectUsersHaveOrdered(List<String> mobiles);
	
	PageInfo searchVoListPage(UserSearchVo searchVo,int pageNo,int pageSize);

	List<Users> selectByListPage(UsersSearchVo usersSearchVo, int pageNo, int pageSize);
	
	List<Users> selectUsersNoOrdered(List<String> mobiles);
	
	List<Users> selectByUserType(Short userType);
	
	List<UserViewVo> getUserInfos(List<Long> userIds, Users secUser, UserRef3rd userRef3rd);

	Map<String, String> getImRobot(Users user);

	Map<String, String> getSeniorImUsername(Users user);

	@SuppressWarnings("rawtypes")
	PageInfo selectByIsAppRoval(int pageNo, int pageSize);

	@SuppressWarnings("rawtypes")
	PageInfo selectByIsAppRovalYes(int pageNo, int pageSize);

	UserRef3rd genImUser(Users user);

	

}