package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.UserRefSec;
import com.simi.vo.user.UserViewVo;

public interface UserRefSecService {

	List<UserViewVo> selectVoByUserId(Long userId);

	UserRefSec selectBySecId(Long secId);

}
