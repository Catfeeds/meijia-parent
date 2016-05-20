package com.simi.po.dao.user;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.user.Users;
import com.simi.vo.chart.ChartSearchVo;
import com.simi.vo.user.UserSearchVo;



public interface UsersMapper {

	int deleteByPrimaryKey(Long id);

    Long insert(Users record);

    Long insertSelective(Users record);
    
    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);    

    Users selectByPrimaryKey(Long id);

    Users selectByMobile(String mobile);

    List<Users> selectByAll();

    List<Users> selectByListPage(UserSearchVo searchVo);
    
    List<Users> selectBySearchVo(UserSearchVo searchVo);

    List<Users> selectNotInMobiles(List<String> mobiles);

	List<HashMap> statByMonth(HashMap map);
	
	int statByTotalUser(ChartSearchVo chartSearchVo);

	//全部推送。所有用户 =  普通用户（最近一个月）+ 所有秘书 + 所有服务商
	List<Long> selectUserIdsForPushAll();
	
	//根据 推送 类型，得到 需要 推送的用户。。
	List<Users> selectUsersByPushUserType(Short pushUserType);
	
}