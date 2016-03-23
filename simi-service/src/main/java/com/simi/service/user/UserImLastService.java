package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.UserImLast;
import com.simi.vo.user.UserImLastSearchVo;
import com.simi.vo.user.UserImLastVo;


public interface UserImLastService {

	UserImLast initUserImLast();

	Long insert(UserImLast record);

	int updateByPrimaryKeySelective(UserImLast record);

	int insertSelective(UserImLast record);

	UserImLast selectBySearchVo(UserImLastSearchVo vo);

	List<UserImLast> selectByUserId(Long userId);

	List<UserImLastVo> getLastIm(Long userId);

}