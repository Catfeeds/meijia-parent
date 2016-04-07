package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.UserActionRecord;
import com.simi.vo.user.UserActionSearchVo;

public interface UserActionRecordService {
    int deleteByPrimaryKey(Long id);

    Long insert(UserActionRecord record);

    Long insertSelective(UserActionRecord record);

    UserActionRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserActionRecord record);

    int updateByPrimaryKey(UserActionRecord record);

	UserActionRecord initUserActionRecord();

	List<UserActionRecord> selectBySearchVo(UserActionSearchVo searchVo);

}