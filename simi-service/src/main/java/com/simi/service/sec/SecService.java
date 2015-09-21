package com.simi.service.sec;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.Users;
import com.simi.vo.sec.SecInfoVo;
import com.simi.vo.sec.SecViewVo;

public interface SecService {

	List<SecViewVo> changeToSecViewVos(List<Users> userList);

}
