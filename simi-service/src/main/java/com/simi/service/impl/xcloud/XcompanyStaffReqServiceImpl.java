package com.simi.service.impl.xcloud;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyStaffReqMapper;
import com.simi.po.model.xcloud.XcompanyStaffReq;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyStaffReqService;


@Service
public class XcompanyStaffReqServiceImpl implements XcompanyStaffReqService {

	@Autowired
	private XcompanyStaffReqMapper xCompanyStaffReqMapper;
	
	@Autowired
	private UsersService userService;

	
	@Override
	public int updateByPrimaryKeySelective(XcompanyStaffReq userFriendReq) {
		return xCompanyStaffReqMapper.updateByPrimaryKeySelective(userFriendReq);
	}
	
	@Override
	public XcompanyStaffReq initXcompanyStaffReq() {
		XcompanyStaffReq u = new XcompanyStaffReq();

		u.setId(0L);
		u.setUserId(0L);
		u.setCompanyId(0L);
		u.setRemarks("");
	    u.setStatus((short) 0);
		u.setAddTime(TimeStampUtil.getNow() / 1000);
		u.setUpdateTime(TimeStampUtil.getNow() / 1000);
		return u;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		
		return xCompanyStaffReqMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(XcompanyStaffReq record) {
		
		return xCompanyStaffReqMapper.insert(record);
	}

	@Override
	public int insertSelective(XcompanyStaffReq record) {

		return xCompanyStaffReqMapper.insertSelective(record);
	}

	@Override
	public XcompanyStaffReq selectByPrimaryKey(Long id) {
		
		return xCompanyStaffReqMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(XcompanyStaffReq record) {
		
		return xCompanyStaffReqMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<XcompanyStaffReq> selectByUserId(Long userId) {
		
		return xCompanyStaffReqMapper.selectByUserId(userId);
	}
	
	@Override
	public List<XcompanyStaffReq> selectByCompanyId(Long companyId) {
		
		return xCompanyStaffReqMapper.selectByCompanyId(companyId);
	}

}
