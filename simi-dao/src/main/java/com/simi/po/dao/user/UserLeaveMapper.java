package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserLeave;
import com.simi.vo.user.UserLeaveSearchVo;

public interface UserLeaveMapper {
    int deleteByPrimaryKey(Long leaveId);

    Long insert(UserLeave record);

    Long insertSelective(UserLeave record);

    UserLeave selectByPrimaryKey(Long leaveId);

    int updateByPrimaryKeySelective(UserLeave record);

    int updateByPrimaryKey(UserLeave record);

	List<UserLeave> selectByListPage(UserLeaveSearchVo searchVo);

	List<UserLeave> selectBySearchVo(UserLeaveSearchVo searchVo);
}