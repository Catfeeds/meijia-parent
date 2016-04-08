package com.simi.service.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;
import com.simi.vo.user.UserViewVo;

public interface UserRefSecService {

	List<UserViewVo> selectVoByUserId(Long userId);

	List<UserRefSec> selectBySecId(Long secId);
	
	UserRefSec selectByUserId(Long userId);

	UserRefSec initUserRefSec();

	List<HashMap> statBySecId();

	int insert(UserRefSec record);

	int insertSelective(UserRefSec record);

	int updateByPrimaryKeySelective(UserRefSec record);

	Map<String, String> getSeniorImUsername(Users user);

}
