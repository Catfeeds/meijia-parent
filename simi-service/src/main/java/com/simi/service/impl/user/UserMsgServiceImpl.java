package com.simi.service.impl.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.user.UserMsgService;
import com.simi.vo.promotion.MsgSearchVo;
import com.simi.po.dao.user.UserMsgMapper;
import com.simi.po.model.dict.DictServiceTypes;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.promotion.Msg;
import com.simi.po.model.user.UserMsg;
@Service
public class UserMsgServiceImpl implements UserMsgService {

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
	public int updateByPrimaryKeySelective(UserMsg record) {
		return userMsgMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserMsg record) {
		return userMsgMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<UserMsg> selectByMsgId(Long msgId) {
		return userMsgMapper.selectByMsgId(msgId);
	}

	@Override
	public UserMsg getUserByMobile(String mobile, Long msgId) {
		HashMap<String, Object> conditions = new HashMap<String,Object>();
		conditions.put("mobile",mobile);
		conditions.put("msgId",msgId);
		return userMsgMapper.selectByMobile(conditions);
	}
	@Override
	public UserMsg selectUserByMobile(String mobile,int page) {
		HashMap<String, Object> conditions = new HashMap<String,Object>();
		conditions.put("mobile",mobile);
		conditions.put("page",page);


		return userMsgMapper.selectUserByMobile(conditions);
	}

	@Override
	public PageInfo searchVoListPage(int pageNo, int pageSize,String mobile) {

		 PageHelper.startPage(pageNo, pageSize);
         List<UserMsg> list = userMsgMapper.selectByListPage(mobile);
        PageInfo result = new PageInfo(list);
		return result;
	}


	@Override
	public Long countNewMsgByMobile(String mobile) {
		return userMsgMapper.countNewMsgByMobile(mobile);
	}

	@Override
	public int postMsgRead(String mobile, Long msgId) {
		UserMsg userMsg = new UserMsg();
		userMsg.setMobile(mobile);
		userMsg.setMsgId((msgId));
		return userMsgMapper.updateBySelect(userMsg);
	}

	@Override
	public PageInfo searchListPage(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
        List<UserMsg> list =  userMsgMapper.selectAll();
        PageInfo result = new PageInfo(list);
		return result;
	}

}
