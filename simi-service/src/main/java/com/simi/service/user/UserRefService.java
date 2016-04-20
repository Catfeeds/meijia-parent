package com.simi.service.user;

import java.util.HashMap;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserRef;
import com.simi.vo.user.UserRefSearchVo;

public interface UserRefService {
	
	UserRef initUserRef();

	int insert(UserRef record);

	int insertSelective(UserRef record);

	int updateByPrimaryKeySelective(UserRef record);

	List<UserRef> selectBySearchVo(UserRefSearchVo searchVo);

	PageInfo selectByListPage(UserRefSearchVo searchVo, int pageNo, int pageSize);

	List<HashMap> statByRefId(UserRefSearchVo searchVo);

}
