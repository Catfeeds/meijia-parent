package com.simi.service.impl.user;



import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.RdbmsOperation;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.user.UserFriendReqMapper;
import com.simi.po.dao.user.UserRef3rdMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.user.UserFriendReq;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserFriendReqService;
import com.simi.service.user.UserRef3rdService;
import com.simi.vo.UserFriendSearchVo;
import com.simi.vo.user.UserFriendReqVo;

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
	public UserFriendReqVo getFriendReqVo(UserFriendReq item) {
		/*friend_idlong用户ID
		namestring用户名称
		sexstring性别   男 女
		head_imgstring头像
		mobilestring手机号
		statusint申请状态 0 = 申请  1 = 同意 2 = 拒绝
		add_time_strstring申请时间*/
		UserFriendReqVo vo = new UserFriendReqVo();

		vo.setFriendId(item.getFriendId());
		Users user = userMapper.selectByPrimaryKey(item.getFriendId());
		vo.setName(user.getName());
		if (user.getSex().equals((short)0)) {
			vo.setSex("先生");
		}else {
			vo.setSex("女士");
		}
		vo.setHeadImg(user.getHeadImg());
		vo.setMobile(user.getMobile());
		vo.setStatus(item.getStatus());
		Long addTime = item.getAddTime()*1000;	
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime));
		
		return vo;
	}

}
