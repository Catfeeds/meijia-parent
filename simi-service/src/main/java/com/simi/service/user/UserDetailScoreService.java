package com.simi.service.user;

import java.util.HashMap;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserDetailScore;
import com.simi.vo.user.UserDetailScoreSearchVo;
import com.simi.vo.user.UserDetailScoreVo;
import com.simi.vo.user.UserMsgSearchVo;

public interface UserDetailScoreService {


    int deleteByPrimaryKey(Long id);

    int insert(UserDetailScore record);

    int insertSelective(UserDetailScore record);

    UserDetailScore selectByPrimaryKey(Long id);

	UserDetailScore initUserDetailScore();

	int updateByPrimaryKeySelective(UserDetailScore record);

	PageInfo selectByListPage(UserDetailScoreSearchVo searchVo, int pageNo, int pageSize);

	List<UserDetailScore> selectBySearchVo(UserDetailScoreSearchVo searchVo);

	UserDetailScoreVo getVo(UserDetailScore item);

	List<HashMap> scoreRanking(UserDetailScoreSearchVo searchVo);


}