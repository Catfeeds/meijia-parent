package com.simi.po.dao.user;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.user.UserDetailScore;
import com.simi.vo.user.UserDetailScoreSearchVo;
import com.simi.vo.user.UserMsgSearchVo;
import com.simi.vo.user.UserSearchVo;

public interface UserDetailScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDetailScore record);

    int insertSelective(UserDetailScore record);

	int updateByPrimaryKeySelective(UserDetailScore record);
	
	UserDetailScore selectByPrimaryKey(Long id);

	List<UserDetailScore> selectByListPage(UserDetailScoreSearchVo searchVo);
	
	List<UserDetailScore> selectBySearchVo(UserDetailScoreSearchVo searchVo);
	
	List<HashMap> scoreRanking(UserDetailScoreSearchVo searchVo);
}