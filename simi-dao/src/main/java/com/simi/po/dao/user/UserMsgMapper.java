package com.simi.po.dao.user;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.user.UserMsg;
import com.simi.vo.user.UserMsgSearchVo;

public interface UserMsgMapper {
    int deleteByPrimaryKey(Long msgId);

    int insert(UserMsg record);

    int insertSelective(UserMsg record);

    UserMsg selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(UserMsg record);

    int updateByPrimaryKey(UserMsg record);

	List<UserMsg> selectByUserId(Long userId);

	List<UserMsg> selectBySearchVo(UserMsgSearchVo searchVo);

	List<UserMsg> selectByListPage(UserMsgSearchVo searchVo);

	List<HashMap> totalByMonth(UserMsgSearchVo vo);
}