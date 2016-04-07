package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserDetailScore;
import com.simi.vo.user.UserMsgSearchVo;

public interface UserDetailScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDetailScore record);

    int insertSelective(UserDetailScore record);

	int updateByPrimaryKeySelective(UserDetailScore record);
	
	UserDetailScore selectByPrimaryKey(Long id);

	List<UserDetailScore> selectByListPage(UserMsgSearchVo searchVo);
	
	List<UserDetailScore> selectBySearchVo(UserMsgSearchVo searchVo);
}