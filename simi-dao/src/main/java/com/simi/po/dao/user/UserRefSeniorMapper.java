package com.simi.po.dao.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simi.po.model.user.UserRefSenior;

public interface UserRefSeniorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRefSenior record);

    int insertSelective(UserRefSenior record);

    UserRefSenior selectByPrimaryKey(Long id);

    UserRefSenior selectByUserIdAndSenior(HashMap conditions);

    int updateByPrimaryKeySelective(UserRefSenior record);

    int updateByPrimaryKey(UserRefSenior record);

    UserRefSenior selectByUserId(Long id);

    List<UserRefSenior> selectBySeniorId(Long secId);

    <HashMap> List<HashMap> statBySeniorId();

    List<UserRefSenior> searchListByConditions(Map<String,Object> conditions);
    
    
}