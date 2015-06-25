package com.simi.po.dao.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserRefSec;

public interface UserRefSecMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRefSec record);

    int insertSelective(UserRefSec record);

    UserRefSec selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRefSec record);

    int updateByPrimaryKey(UserRefSec record);

	<UserViewVo> List<UserViewVo> selectVoByUserId(Long userId);
	
	List<UserRefSec> selectBySecId(Long secId);
	
	UserRefSec selectByUserId(Long userId);
	    
	<HashMap> List<HashMap> statBySecId();
}