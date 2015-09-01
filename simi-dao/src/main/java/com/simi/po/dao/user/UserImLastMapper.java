package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserImLast;
import com.simi.vo.UserImLastSearchVo;

public interface UserImLastMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(UserImLast record);

    int insertSelective(UserImLast record);

    UserImLast selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserImLast record);

    int updateByPrimaryKeyWithBLOBs(UserImLast record);

    int updateByPrimaryKey(UserImLast record);

	UserImLast selectBySearchVo(UserImLastSearchVo vo);

	List<UserImLast> selectByUserId(Long userId);
}