package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserImgs;

public interface UserImgsMapper {
    int deleteByPrimaryKey(Long imgId);

    int insert(UserImgs record);

    int insertSelective(UserImgs record);

    UserImgs selectByPrimaryKey(Long imgId);
    
    List<UserImgs> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(UserImgs record);

    int updateByPrimaryKey(UserImgs record);
}