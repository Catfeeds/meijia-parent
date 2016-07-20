package com.simi.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.Groups;
import com.simi.vo.user.GroupsSearchVo;

public interface GroupService {
	
	Groups initGroups();

	int insert(Groups record);

	int insertSelective(Groups record);

	int updateByPrimaryKeySelective(Groups record);
	
	Groups selectByPrimaryKey(Long id);

	List<Groups> selectBySearchVo(GroupsSearchVo searchVo);

	PageInfo selectByListPage(GroupsSearchVo searchVo, int pageNo, int pageSize);

}
