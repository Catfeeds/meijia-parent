package com.simi.service.impl.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserImLastService;
import com.simi.service.user.UsersService;
import com.simi.vo.UserImLastSearchVo;
import com.simi.vo.user.UserImLastVo;
import com.simi.po.dao.user.UserImLastMapper;
import com.simi.po.model.user.UserImLast;
import com.simi.po.model.user.Users;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserImLastServiceImpl implements UserImLastService {
	
	@Autowired 
	UserImLastMapper userImLastMapper;
	
	@Autowired
	private UsersService userService;	

	@Override
	public UserImLast initUserImLast() {
		UserImLast record = new UserImLast();
		record.setId(0L);
		record.setFromUserId(0L);
		record.setToUserId(0L);
		record.setFromImUser("");
		record.setToImUser("");
		record.setChatType("");
		record.setImContent("");
		record.setMsgId("");
		record.setUuid("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public Long insert(UserImLast record) {
		return userImLastMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserImLast record) {
		return userImLastMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(UserImLast record) {
		return userImLastMapper.insertSelective(record);
	}
	
	@Override
	public UserImLast selectBySearchVo(UserImLastSearchVo vo) {
		return userImLastMapper.selectBySearchVo(vo);
	}	
	
	@Override
	public List<UserImLast> selectByUserId(Long userId) {
		return userImLastMapper.selectByUserId(userId);
	}		
	
	
	/**
	 * 获取当前秘书所有好友的最新一条聊天信息
	 * 流程为
	 * 1. 先获得当前用户发给所有好友的最新一条聊天信息，得到集合A.
	 * 2. 在获得所有好友发给当前用户的最新一条聊天信息，得到集合B.
	 * 3. 集合A和集合B 进行 当前用户 - 当前好友的 排查，得到集合C。
	 * 4. 获得当前所有好友的记录，得到集合D，
	 * 5. 遍历集合D，每一个好友去匹配集合C的最新一条聊天信息，得到最终的集合信息E。
	 */
	@Override
	public List<UserImLastVo> getLastIm(Long userId) {
		
		List<UserImLastVo> result= new ArrayList<UserImLastVo>();
		
		List<UserImLast> userImLists = this.selectByUserId(userId);
		
		if (userImLists.isEmpty()) return result;
		
		List<Long> userIds = new ArrayList<Long>();
		for (UserImLast um : userImLists) {
			if (!userIds.contains(um.getToUserId()));
				userIds.add(um.getToUserId());
		}
		
		List<Users> users = userService.selectByUserIds(userIds);
		
		UserImLast item = null;
		for (int i = 0 ; i < userImLists.size(); i++) {
			item = userImLists.get(i);
			UserImLastVo vo = new UserImLastVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			for (Users u : users) {
				if (u.getId().equals(vo.getToUserId())) {
					vo.setHeadImg(u.getHeadImg());
					break;
				}
			}
			result.add(vo);
		}

		return result;
		
	}	
}