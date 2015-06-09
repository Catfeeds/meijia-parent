package com.simi.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.vo.admin.SeniorSearchVo;
import com.simi.vo.user.UserRefSeniorVo;
import com.simi.po.model.user.UserRefSenior;
import com.simi.po.model.user.Users;

public interface UserRefSeniorService {

	UserRefSenior selectByPrimaryKey(Long id);

	int insert(UserRefSenior record);

	int updateByPrimaryKeySelective(UserRefSenior record);

	UserRefSenior initUserRefSenior();

	Boolean allotSenior(Users user);

	PageInfo searchVoListPage(SeniorSearchVo seniorSearchVo,int pageNo,int pageSize);

	List<UserRefSeniorVo> getSeniorViewList(List<UserRefSenior> list);
	
	List<UserRefSenior> selectBySeniorId(Long seniorId);


}