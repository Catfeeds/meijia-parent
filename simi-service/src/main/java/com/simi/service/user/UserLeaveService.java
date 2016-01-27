package com.simi.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserLeave;
import com.simi.vo.UserLeaveSearchVo;
import com.simi.vo.user.UserLeaveDetailVo;
import com.simi.vo.user.UserLeaveListVo;


public interface UserLeaveService {

	int deleteByPrimaryKey(Long id);

	Long insert(UserLeave record);

	Long insertSelective(UserLeave record);

	UserLeave selectByPrimaryKey(Long id);

	int updateByPrimaryKey(UserLeave record);

	int updateByPrimaryKeySelective(UserLeave record);

	UserLeave initUserLeave();

	@SuppressWarnings("rawtypes")
	PageInfo selectByListPage(UserLeaveSearchVo searchVo, int pageNo, int pageSize);

	List<UserLeave> selectBySearchVo(UserLeaveSearchVo searchVo);

	List<UserLeaveListVo> changeToListVo(List<UserLeave> list);

	UserLeaveDetailVo changeToDetailVo(UserLeave item);



}