package com.simi.service.impl.user;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.user.UserFriendReqMapper;
import com.simi.po.dao.user.UserRef3rdMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.user.UserFriendReq;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserFriendReqService;
import com.simi.vo.user.UserFriendReqVo;
import com.simi.vo.user.UserFriendSearchVo;

@Service
public class UserFriendReqServiceImpl implements UserFriendReqService {

	@Autowired
	private UserFriendReqMapper userFriendReqMapper;
	
	@Autowired
	private UsersMapper userMapper;
	
	@Autowired
	private UserRef3rdMapper userRef3rdMapper;

	
	@Override
	public int updateByPrimaryKeySelective(UserFriendReq userFriendReq) {
		return userFriendReqMapper.updateByPrimaryKeySelective(userFriendReq);
	}
	
	@Override
	public UserFriendReq initUserFriendReq() {
		UserFriendReq u = new UserFriendReq();

		u.setId(0L);
		u.setUserId(0L);
		u.setFriendId(0L);
		u.setRemarks("");
	    u.setStatus((short) 0);
		u.setAddTime(TimeStampUtil.getNow() / 1000);
		u.setUpdateTime(TimeStampUtil.getNow() / 1000);
		return u;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		
		return userFriendReqMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserFriendReq record) {
		
		return userFriendReqMapper.insert(record);
	}

	@Override
	public int insertSelective(UserFriendReq record) {

		return userFriendReqMapper.insertSelective(record);
	}

	@Override
	public UserFriendReq selectByPrimaryKey(Long id) {
		
		return userFriendReqMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserFriendReq record) {
		
		return userFriendReqMapper.updateByPrimaryKey(record);
	}

	@Override
	public UserFriendReq selectByIsFirend(UserFriendSearchVo searchVo) {

		return userFriendReqMapper.selectByIsFirend(searchVo);
	}

	@Override
	public List<UserFriendReq> selectByUserId(Long userId) {
		
		return userFriendReqMapper.selectByUserId(userId);
	}

	@Override
	public UserFriendReqVo getFriendReqVo(UserFriendReq item, Long userId) {
		/*friend_idlong用户ID
		namestring用户名称
		sexstring性别   男 女
		head_imgstring头像
		mobilestring手机号
		statusint申请状态 0 = 申请  1 = 同意 2 = 拒绝
		add_time_strstring申请时间*/
		UserFriendReqVo vo = new UserFriendReqVo();
		
		//reqType  0 = 我申请的  1 = 我需要去审批的
		Short reqType = (short)0;
		Long viewUserId = item.getFriendId();
		if (!userId.equals(item.getUserId())) {
			reqType = (short)1;
			viewUserId = item.getUserId();
		}
		vo.setReqType(reqType);
		Users user = userMapper.selectByPrimaryKey(viewUserId);
		
		if (reqType.equals((short)0)) {
			vo.setUserId(item.getUserId());
			vo.setFriendId(item.getFriendId());
		} else {
			vo.setUserId(item.getFriendId());
			vo.setFriendId(item.getUserId());
		}
		
		vo.setName(user.getName());
		vo.setSex(user.getSex());
		vo.setHeadImg(user.getHeadImg());
		vo.setMobile(user.getMobile());
		vo.setStatus(item.getStatus());
		Long addTime = item.getAddTime()*1000;	
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime));
		
		return vo;
	}
	
	@Override
	public PageInfo selectByListPage(UserFriendSearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<UserFriendReq> list = userFriendReqMapper.selectByListPage(searchVo);
		PageInfo result = new PageInfo(list);
		return result;
	}

}
