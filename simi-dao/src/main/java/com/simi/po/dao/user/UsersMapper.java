package com.simi.po.dao.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simi.po.model.user.UserDetailPay;
import com.simi.po.model.user.Users;
import com.simi.vo.UserSearchVo;
import com.simi.vo.UsersSearchVo;



public interface UsersMapper {

	int deleteByPrimaryKey(Long id);

    int insert(Users record);

    int insertSelective(Users record);

    List<Users> selectByAll();

    Users selectByPrimaryKey(Long id);

    Users selectByMobile(String mobile);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    List<Users> selectByListPage(Map<String,Object> conditions);

    List<Users> selectByUserIds(List<Long> userIds);

    List<Users> selectByMobiles(List<String> mobiles);

    List<Users> selectNotInMobiles(List<String> mobiles);

	List<UserDetailPay> selectByCount(int addTime);

	List<Users> searchVoByAll(UserSearchVo searchVo);

	List<HashMap> selectUserStat(HashMap map);
	
    Users selectByOpenidAnd3rdType(Map<String ,Object> conditions );

    List<Users> selectVoByUserId(List<Long> ids);

	Users selectByUserId(Long userId);

	List<Users> selectByUserType(Short userType);

	Users selectUserByIdCard(String idCard);

	List<Users> selectByIsAppRoval();

	List<Users> selectByIsAppRovalYes();

	List<Users> selectVoByListPage(UsersSearchVo userSearchVo);
	
	List<Users> selectVoByListPageNo(UsersSearchVo userSearchVo);
    
}