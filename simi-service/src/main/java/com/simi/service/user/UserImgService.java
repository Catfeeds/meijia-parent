package com.simi.service.user;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.user.UserImgs;

public interface UserImgService {

    int deleteByPrimaryKey(Long id);

    int insert(UserImgs record);

    UserImgs selectByPrimaryKey(Long id);

    List<UserImgs> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(UserImgs record);

    int updateByPrimaryKey(UserImgs record);
}
