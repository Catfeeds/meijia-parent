package com.simi.service.user;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.user.UserViewVo;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;


public interface UsersService {

	Users getUserById(Long id);

	Users getUserByMobile(String mobile);

	int saveUser(Users user);

	int updateByPrimaryKeySelective(Users user) ;

	Users initUsers(String mobile, Short addFrom);

	UserViewVo getUserViewByMobile(String mobile);

	PageInfo searchVoListPage(UserSearchVo searchVo,int pageNo,int pageSize);

	UserRef3rd genImUser(Users user);

	Map<String, String> getImRobot(Users user);

	Map<String, String> getSeniorImUsername(Users user);

	UserViewVo getUserInfo(String mobile);

	Users genUser(String mobile, Short addFrom);

	List<Users> selectByAll();

	List<Users> searchVoByAll(UserSearchVo searchVo);

	List<Users> selectUsersHaveOrdered(List<String> mobiles);

	List<Users> selectUsersNoOrdered(List<String> mobiles);




}