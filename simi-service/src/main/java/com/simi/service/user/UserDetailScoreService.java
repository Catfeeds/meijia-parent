package com.simi.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserDetailScore;
import com.simi.vo.user.UserDetailScoreVo;
import com.simi.vo.user.UserMsgSearchVo;

public interface UserDetailScoreService {


    int deleteByPrimaryKey(Long id);

    int insert(UserDetailScore record);

    int insertSelective(UserDetailScore record);

    UserDetailScore selectByPrimaryKey(Long id);

	UserDetailScore initUserDetailScore();

	int updateByPrimaryKeySelective(UserDetailScore record);

	PageInfo selectByListPage(UserMsgSearchVo searchVo, int pageNo, int pageSize);

	List<UserDetailScore> selectBySearchVo(UserMsgSearchVo searchVo);

	UserDetailScoreVo getVo(UserDetailScore item);

}