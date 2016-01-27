package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.user.UserLeaveService;
import com.simi.service.user.UsersService;
import com.simi.vo.UserLeaveSearchVo;
import com.simi.vo.UserMsgSearchVo;
import com.simi.po.dao.user.UserLeaveMapper;
import com.simi.po.model.user.UserLeave;
import com.simi.po.model.user.UserMsg;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserLeaveServiceImpl implements UserLeaveService {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserLeaveMapper userLeaveMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userLeaveMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(UserLeave record) {
		return userLeaveMapper.insert(record);
	}

	@Override
	public Long insertSelective(UserLeave record) {
		return userLeaveMapper.insertSelective(record);
	}

	@Override
	public UserLeave selectByPrimaryKey(Long id) {
		return userLeaveMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserLeave record) {
		return userLeaveMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserLeave record) {
		return userLeaveMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public UserLeave initUserLeave() {
		UserLeave record = new UserLeave();
		record.setLeaveId(0L);
		record.setCompanyId(0L);
		record.setUserId(0L);
		record.setLeaveType((short) 0);
		record.setStartDate(DateUtil.getNowOfDate());
		record.setEndDate(DateUtil.getNowOfDate());
		record.setTotalDays("0");
		record.setRemarks("");
		record.setImgs("");
		record.setStatus((short) 0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		return record;
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(UserLeaveSearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<UserLeave> list = userLeaveMapper.selectByListPage(searchVo);
		PageInfo result = new PageInfo(list);
		return result;
	}
	
	@Override
	public List<UserLeave> selectBySearchVo(UserLeaveSearchVo searchVo) {
		List<UserLeave> result = userLeaveMapper.selectBySearchVo(searchVo);
		return result;
	}	
			
}