package com.simi.service.user;

import java.util.HashMap;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.simi.vo.user.UserMsgSearchVo;
import com.simi.vo.user.UserMsgVo;

public interface UserMsgService {

	int deleteByPrimaryKey(Long id);

	int insert(UserMsg record);

	int insertSelective(UserMsg record);

	UserMsg selectByPrimaryKey(Long id);

	int updateByPrimaryKey(UserMsg record);

	int updateByPrimaryKeySelective(UserMsg record);

	UserMsg initUserMsg();

	List<UserMsg> selectByUserId(Long userId);

	List<UserMsg> selectBySearchVo(UserMsgSearchVo searchVo);

	PageInfo selectByListPage(UserMsgSearchVo searchVo, int pageNo, int pageSize);

	UserMsgVo getWeather(String serviceDate, String lat, String lng);

	List<HashMap> totalByMonth(UserMsgSearchVo vo);

	boolean checkPeriod(Users u, String serviceDate);

}