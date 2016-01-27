package com.simi.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserLeave;
import com.simi.vo.UserLeaveSearchVo;


public interface UserLeaveService {

	int deleteByPrimaryKey(Long id);

	Long insert(UserLeave record);

	Long insertSelective(UserLeave record);

	UserLeave selectByPrimaryKey(Long id);

	int updateByPrimaryKey(UserLeave record);

	int updateByPrimaryKeySelective(UserLeave record);

	UserLeave initUserLeave();

	PageInfo selectByListPage(UserLeaveSearchVo searchVo, int pageNo, int pageSize);

	List<UserLeave> selectBySearchVo(UserLeaveSearchVo searchVo);



}