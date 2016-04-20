package com.simi.po.dao.user;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.user.UserRef;
import com.simi.vo.user.UserRefSearchVo;

public interface UserRefMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRef record);

    int insertSelective(UserRef record);

    UserRef selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRef record);

    int updateByPrimaryKey(UserRef record);
    
    List<UserRef> selectBySearchVo(UserRefSearchVo searchVo);
    
    List<UserRef> selectByListPage(UserRefSearchVo searchVo);
    
	List<HashMap> statByRefId(UserRefSearchVo searchVo);
}