package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.simi.po.dao.user.UserMsgMapper;
import com.simi.po.model.user.UserMsg;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserMsgServiceImpl implements UserMsgService {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserMsgMapper userMsgMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userMsgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserMsg record) {
		return userMsgMapper.insert(record);
	}

	@Override
	public int insertSelective(UserMsg record) {
		return userMsgMapper.insertSelective(record);
	}

	@Override
	public UserMsg selectByPrimaryKey(Long id) {
		return userMsgMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserMsg record) {
		return userMsgMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserMsg record) {
		return userMsgMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public UserMsg initUserMsg() {
		UserMsg record = new UserMsg();
		record.setMsgId(0L);
		record.setUserId(0L);
		record.setFromUserId(0L);
		record.setCategory("");
		record.setAction("");
		record.setParams("");
		record.setGotoUrl("");
		record.setTitle("");
		record.setSummary("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(Long userId, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<UserMsg> list = userMsgMapper.selectByListPage(userId);
		PageInfo result = new PageInfo(list);
		return result;
	}
	
	@Override
	public List<UserMsg> selectByUserId(Long userId) {
		List<UserMsg> result = userMsgMapper.selectByUserId(userId);
		return result;
	}
		
}