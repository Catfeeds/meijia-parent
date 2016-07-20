package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.user.GroupsMapper;
import com.simi.po.model.user.Groups;
import com.simi.service.user.GroupService;
import com.simi.vo.user.GroupsSearchVo;

@Service
public class GroupServiceImpl implements GroupService{
  
	@Autowired
	private GroupsMapper groupsMapper;
		
	/*
	 * 初始化用户对象
	 */
	@Override
	public Groups initGroups() {
		Groups record = new Groups();
		record.setGroupId(0L);
		record.setName("");
		record.setRemarks("");
		record.setAddTime(TimeStampUtil.getNow() / 1000);
		return record;
	}	

	
	@Override
	public int insert(Groups record) {
		return groupsMapper.insert(record);
	}

	@Override
	public int insertSelective(Groups record) {
		return groupsMapper.insertSelective(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(Groups record) {
		return groupsMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public Groups selectByPrimaryKey(Long id) {
		return groupsMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<Groups> selectBySearchVo(GroupsSearchVo searchVo) {
		return groupsMapper.selectBySearchVo(searchVo);
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(GroupsSearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<Groups> list = groupsMapper.selectByListPage(searchVo);
		PageInfo result = new PageInfo(list);
		return result;
	}

}
