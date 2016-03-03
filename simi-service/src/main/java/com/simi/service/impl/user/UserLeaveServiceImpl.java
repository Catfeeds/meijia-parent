package com.simi.service.impl.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.user.UserLeavePassService;
import com.simi.service.user.UserLeaveService;
import com.simi.service.user.UsersService;
import com.simi.vo.UserLeaveSearchVo;
import com.simi.vo.user.UserLeaveDetailVo;
import com.simi.vo.user.UserLeaveListVo;
import com.simi.vo.user.UserLeavePassVo;
import com.simi.po.dao.user.UserLeaveMapper;
import com.simi.po.model.user.UserLeave;
import com.simi.po.model.user.UserLeavePass;
import com.simi.po.model.user.Users;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserLeaveServiceImpl implements UserLeaveService {

	@Autowired
	private UsersService userService;

	@Autowired
	private UserLeaveMapper userLeaveMapper;
	
	@Autowired
	private UserLeavePassService userLeavePassService;

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

	@Override
	public List<UserLeaveListVo> changeToListVo(List<UserLeave> list) {
		List<UserLeaveListVo> result = new ArrayList<UserLeaveListVo>();
		
		for (int i = 0; i < list.size(); i++) {
			UserLeave item = list.get(i);
			UserLeaveListVo vo = new UserLeaveListVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			Long userId = item.getUserId();
			Users u = userService.selectByPrimaryKey(userId);
			
			// 处理日期
			vo.setStartDate(DateUtil.format(item.getStartDate(), "yyyy-MM-dd"));
			vo.setEndDate(DateUtil.format(item.getEndDate(), "yyyy-MM-dd"));

			vo.setAddTimeStr(TimeStampUtil.fromTodayStr(item.getAddTime() * 1000));

			// 处理状态中文名
			vo.setStatusName(getStatusName(item.getStatus()));
			
			vo.setName(u.getName());
			vo.setHeadImg(u.getHeadImg());
			
			result.add(vo);

		}

		return result;
	}

	@Override
	public UserLeaveDetailVo changeToDetailVo(UserLeave item) {
		UserLeaveDetailVo vo = new UserLeaveDetailVo();
		Long leaveId = item.getLeaveId();
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);

		Long userId = item.getUserId();
		Users u = userService.selectByPrimaryKey(userId);
		
		vo.setName(u.getName());
		vo.setHeadImg(u.getHeadImg());
		// 处理日期
		vo.setStartDate(DateUtil.format(item.getStartDate(), "yyyy-MM-dd"));
		vo.setEndDate(DateUtil.format(item.getEndDate(), "yyyy-MM-dd"));

		vo.setAddTimeStr(TimeStampUtil.fromTodayStr(item.getAddTime()));

		// 处理图片
		List<String> imgs = new ArrayList<String>();
		vo.setImgs(imgs);
		if (StringUtil.isEmpty(item.getImgs())) {

			String[] imgAry = StringUtil.convertStrToArray(item.getImgs());
			for (int j = 0; j < imgAry.length; j++) {
				imgs.add(imgAry[j]);
			}
			vo.setImgs(imgs);
		}

		// 处理状态中文名
		vo.setStatusName(getStatusName(item.getStatus()));
		
		// 处理审批进度
		List<UserLeavePass> userLeavePass = userLeavePassService.selectByLeaveId(leaveId);
		List<UserLeavePassVo> passUsers = new ArrayList<UserLeavePassVo>();
		
		//审批人申请的
		UserLeavePassVo passVo = new UserLeavePassVo();
		passVo.setUserId(u.getId());
		passVo.setName(u.getName());
		passVo.setHeadImg(u.getHeadImg());
		passVo.setStatus((short) 0);
		passVo.setStatusName("发起审批");
		passVo.setRemarks("");
		passVo.setAddTimeStr(TimeStampUtil.fromTodayStr(item.getAddTime() * 1000));
		passUsers.add(passVo);
		
		//如果审批已撤销，则输入撤销的
		if (item.getStatus().equals((short)3)) {
			passVo = new UserLeavePassVo();
			passVo.setUserId(u.getId());
			passVo.setName(u.getName());
			passVo.setHeadImg(u.getHeadImg());
			passVo.setStatus(item.getStatus());
			passVo.setStatusName(getStatusName(item.getStatus()));
			passVo.setRemarks("");
			passVo.setAddTimeStr(TimeStampUtil.fromTodayStr(item.getUpdateTime() * 1000));
			passUsers.add(passVo);
		}
		
		if (!item.getStatus().equals((short)3)) {
			for (int i = 0 ; i < userLeavePass.size(); i++) {
				UserLeavePass pass = userLeavePass.get(i);
				passVo = new UserLeavePassVo();
				
				Long passUserId = pass.getPassUserId();
				Short passStatus = pass.getPassStatus();
				Users passUser = userService.selectByPrimaryKey(passUserId);
				
				passVo.setUserId(pass.getPassUserId());
				passVo.setName(passUser.getName());
				passVo.setHeadImg(passUser.getHeadImg());
				passVo.setStatus(pass.getPassStatus());
				passVo.setStatusName(getStatusName(passStatus));
				passVo.setRemarks(pass.getRemarks());
				
				passVo.setAddTimeStr("");
				if (passStatus.equals((short)1) || passStatus.equals((short)2)) {
					passVo.setAddTimeStr(TimeStampUtil.fromTodayStr(pass.getUpdateTime() * 1000));
				}
				passUsers.add(passVo);
			}
		}
		vo.setPassUsers(passUsers);
		return vo;
	}

	private String getStatusName(Short status) {
		String statusName = "";

		switch (status) {
		case 0:
			statusName = "审批中";
			break;
		case 1:
			statusName = "审批通过";
			break;
		case 2:
			statusName = "审批不通过";
			break;
		case 3:
			statusName = "已撤销";
			break;
		default:
			statusName = "";
		}
		return statusName;
	}

}