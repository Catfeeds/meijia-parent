package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.UserActionRecord;
import com.simi.vo.UserActionSearchVo;

public interface UserActionRecordService {
    int deleteByPrimaryKey(Long id);

    int insert(UserActionRecord record);

    int insertSelective(UserActionRecord record);

    UserActionRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserActionRecord record);

    int updateByPrimaryKey(UserActionRecord record);

	UserActionRecord initUserActionRecord();

	List<UserActionRecord> selectBySearchVo(UserActionSearchVo searchVo);

}