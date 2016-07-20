package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.Groups;
import com.simi.vo.user.GroupsSearchVo;

public interface GroupsMapper {
    int deleteByPrimaryKey(Long groupId);

    int insert(Groups record);

    int insertSelective(Groups record);

    Groups selectByPrimaryKey(Long groupId);

    int updateByPrimaryKeySelective(Groups record);

    int updateByPrimaryKey(Groups record);

	List<Groups> selectByListPage(GroupsSearchVo searchVo);

	List<Groups> selectBySearchVo(GroupsSearchVo searchVo);
}