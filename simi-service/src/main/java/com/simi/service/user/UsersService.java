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

	Users getUserById(Long id);

	Users getUserByMobile(String mobile);

	int saveUser(Users user);

	int updateByPrimaryKeySelective(Users user) ;

	Users initUsers(String mobile, Short addFrom);
	
	Users initUser(String openid,Short addFrom);

	UserViewVo getUserViewByUserId(Long userId);

	PageInfo searchVoListPage(UserSearchVo searchVo,int pageNo,int pageSize);

	UserRef3rd genImUser(Users user);

	Map<String, String> getImRobot(Users user);

	Map<String, String> getSeniorImUsername(Users user);

	UserViewVo getUserInfo(Long userId);

	List<Users> selectByAll();

	List<Users> searchVoByAll(UserSearchVo searchVo);

	List<Users> selectUsersHaveOrdered(List<String> mobiles);

	List<Users> selectUsersNoOrdered(List<String> mobiles);
	
	Users selectByOpenidAndThirdType(String openid,String thirdType);

	List<Users> selectVoByUserId(List<Long> ids);

	List<Users> selectByUserIds(List<Long> ids);

	Users selectVoByUserId(Long userId);

	Users genUser(String mobile, String name, Short addFrom);

	UserIndexVo getUserIndexVoByUserId(Users user, Users viewUser);

	List<Users> selectByUserType(Short userType);

	List<UserViewVo> getUserInfos(List<Long> userIds, Users secUser, UserRef3rd userRef3rd);

	int insert(Users record);

	Users initUsers(String mobile, String name);

	Users selectUserByIdCard(String idCard);

	PageInfo selectByIsAppRoval(int pageNo, int pageSize);

	Users selectByPrimaryKey(Long id);

	int insertSelective(Users u);

	PageInfo selectByIsAppRovalYes(int pageNo, int pageSize);

	Users selectUserByMobile(String mobile);

	Users initUserForm();

	List<Users> selectByListPage(UsersSearchVo usersSearchVo, int pageNo,
			int pageSize);

	List<Users> selectByListPageNo(UsersSearchVo usersSearchVo, int pageNo,
			int pageSize);

}