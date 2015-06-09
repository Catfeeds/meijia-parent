package com.simi.po.dao.user;

import com.simi.po.model.user.UserTrail;

public interface UserTrailMapper {
    int insert(UserTrail record);

    int insertSelective(UserTrail record);
}