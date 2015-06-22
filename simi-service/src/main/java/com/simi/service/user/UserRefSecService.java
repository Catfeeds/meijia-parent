package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.UserRefSec;
import com.simi.vo.user.UserViewVo;

public interface UserRefSecService {

	List<UserViewVo> selectVoByUserId(Long userId);

	List<UserRefSec> selectBySecId(Long secId);

}
