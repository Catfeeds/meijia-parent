package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserImgService;
import com.simi.po.dao.user.UserImgsMapper;
import com.simi.po.model.user.UserImgs;

@Service
public class UserImgServiceImpl implements UserImgService{

	@Autowired
	private UserImgsMapper userImgMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userImgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserImgs record) {
		return userImgMapper.insert(record);
	}

	@Override
	public List<UserImgs> selectByUserId(Long userId) {
		return userImgMapper.selectByUserId(userId);
	}

	@Override
	public UserImgs selectByPrimaryKey(Long id) {
		return userImgMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserImgs record) {
		return userImgMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserImgs record) {
		return userImgMapper.updateByPrimaryKeySelective(record);
	}	
}