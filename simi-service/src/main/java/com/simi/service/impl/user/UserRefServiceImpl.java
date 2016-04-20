package com.simi.service.impl.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.user.UserRefMapper;
import com.simi.po.model.user.UserRef;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.user.UserRefService;
import com.simi.vo.user.UserRefSearchVo;

@Service
public class UserRefServiceImpl implements UserRefService{
  
	@Autowired
	private UserRefMapper userRefMapper;
	
	@Autowired
	private AdminAccountService adminAccountService;
	
	/*
	 * 初始化用户对象
	 */
	@Override
	public UserRef initUserRef() {
		UserRef record = new UserRef();
		record.setId(0L);
		record.setUserId(0L);
		record.setRefId(0L);
		record.setRefType("");
		record.setAddTime(TimeStampUtil.getNow() / 1000);
		return record;
	}	

	
	@Override
	public int insert(UserRef record) {
		return userRefMapper.insert(record);
	}

	@Override
	public int insertSelective(UserRef record) {
		return userRefMapper.insertSelective(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(UserRef record) {
		return userRefMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<UserRef> selectBySearchVo(UserRefSearchVo searchVo) {
		return userRefMapper.selectBySearchVo(searchVo);
	}
	
	@Override
	public List<HashMap> statByRefId(UserRefSearchVo searchVo) {
		return userRefMapper.statByRefId(searchVo);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(UserRefSearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<UserRef> list = userRefMapper.selectByListPage(searchVo);
		PageInfo result = new PageInfo(list);
		return result;
	}

}
