package com.simi.service.user;

import java.util.List;
import com.github.pagehelper.PageInfo;
import com.simi.vo.user.UserBaseVo;
import com.simi.vo.user.UserIndexVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.user.UserViewVo;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;


public interface UsersService {
	
	Long insert(Users record);
	
	Long insertSelective(Users u);
	
	int updateByPrimaryKeySelective(Users user) ;
	
	Users initUsers();
	
	Users genUser(String mobile, String name, short addFrom, String introduction);
	
	Users selectByPrimaryKey(Long id);
	
	Users selectByMobile(String mobile);

	UserViewVo getUserInfo(Long userId);
	
	List<Users> selectByAll();

	PageInfo selectByListPage(UserSearchVo searchVo,int pageNo,int pageSize);
	
	List<Users> selectBySearchVo(UserSearchVo searchVo);
		
	List<Users> selectNotInMobiles(List<String> mobiles);
		
	List<UserViewVo> getUserInfos(List<Long> userIds, Users secUser, UserRef3rd userRef3rd);

	String getHeadImg(Users u);

	UserBaseVo getUserBaseVo(Users user);

	UserIndexVo getUserIndexVo(Users user, Users viewUser);

	List<UserBaseVo> getUserBaseVos(List<Users> list);
	
	//全部推送。的所有用户 =  普通用户（最近一个月）+ 所有秘书 + 所有服务商
	List<Long>	selectUsersForPushAll();
	
	//根据 推送类型，得到用户
	List<Users> selectUsersByPushUserType(Short pushUserType);
	
}