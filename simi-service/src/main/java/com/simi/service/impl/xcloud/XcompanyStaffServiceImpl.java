package com.simi.service.impl.xcloud;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.record.UseSelFSRecord;
import org.aspectj.weaver.patterns.IfPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.simi.common.Constants;
import com.simi.po.dao.xcloud.XcompanyStaffMapper;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.OrdersListVo;
import com.simi.vo.UserCompanySearchVo;
import com.simi.vo.UserFriendSearchVo;
import com.simi.vo.xcloud.UserCompanyFormVo;

@Service
public class XcompanyStaffServiceImpl implements XcompanyStaffService {

	@Autowired
	XcompanyStaffMapper xCompanyStaffMapper;

	@Autowired
	XcompanyDeptService xcompanyDeptService;

	@Autowired
	UsersService usersService;
	
	@Override
	public XcompanyStaff initXcompanyStaff() {
		
		XcompanyStaff record = new XcompanyStaff();
		
		record.setId(0L);
		record.setUserId(0L);
		record.setCompanyId(0L);
		record.setDeptId(0L);
		record.setCityId(0L);
		record.setTel("");
		record.setTelExt("");
		record.setCompanyEmail("");
		record.setCompanyFax("");
		record.setStaffType((short)0L);
		record.setJobName("");
		record.setJobNumber("");
		record.setJoinDate(new Date());
		record.setRegularDate(new Date());
		
		return record;
	}
	
	@Override
	public PageInfo selectByListPage(UserCompanySearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<XcompanyStaff> list = xCompanyStaffMapper.selectByListPage(searchVo);

		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				XcompanyStaff item = list.get(i);
				UserCompanyFormVo vo = getUserCompany(item);
				list.set(i, vo);
			}
	}	
		PageInfo result = new PageInfo(list);
		return result;
	}
	
	@Override
	public List<XcompanyStaff> selectBySearchVo(Long companyId, Long deptId) {

		return xCompanyStaffMapper.selectBySearchVo(companyId, deptId);

	}

	@Override
	public List<XcompanyStaff> selectByCompanyIdAndDeptId(Long companyId, Long deptId) {

		return xCompanyStaffMapper.selectByCompanyIdAndDeptId(companyId, deptId);
	}
	
	@Override
	public XcompanyStaff selectByCompanyIdAndUserId(Long companyId, Long userId) {

		return xCompanyStaffMapper.selectByCompanyIdAndUserId(companyId, userId);
	}	

	@Override
	public int deleteByPrimaryKey(Long id) {

		return xCompanyStaffMapper.deleteByPrimaryKey(id);
	}

	@Override
	public UserCompanyFormVo getUserCompany(XcompanyStaff item) {

		UserCompanyFormVo vo = new UserCompanyFormVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
		
		Users users = usersService.selectByPrimaryKey(item.getUserId());

		BeanUtilsExp.copyPropertiesIgnoreNull(users, vo);
		
		// 员工类型
		XcompanyStaff xcompanyStaff = xCompanyStaffMapper.selectByCompanyIdAndUserId(item.getCompanyId(), item.getUserId());

		vo.setStaffType(xcompanyStaff.getStaffType());
		if (xcompanyStaff.getStaffType() == 0) {
			vo.setStaffName("全职");
		}
		if (xcompanyStaff.getStaffType() == 1) {
			vo.setStaffName("兼职");
		}
		if (xcompanyStaff.getStaffType() == 2) {
			vo.setStaffName("实习");
		}
		// 部门名称
		XcompanyDept xcompanyDept = xcompanyDeptService
				.selectByPrimaryKey(xcompanyStaff.getDeptId());
		if (xcompanyDept != null) {
			vo.setDeptName(xcompanyDept.getName());
		} else {
			vo.setDeptName("");
		}
		return vo;
	}

	@Override
	public List<XcompanyStaff> selectByCompanyId(Long companyId) {
		
		return xCompanyStaffMapper.selectByCompanyId(companyId);
	}

	@Override
	public int insertSelective(XcompanyStaff xcompanyStaff) {

	    return xCompanyStaffMapper.insertSelective(xcompanyStaff);
	}

	@Override
	public XcompanyStaff selectByPrimarykey(Long id) {

		return xCompanyStaffMapper.selectByPrimaryKey(id);
		
	}

	@Override
	public List<XcompanyStaff> selectByUserId(Long userId) {
		
		return xCompanyStaffMapper.selectByUserId(userId);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyStaff xcompanyStaff) {
		
		return xCompanyStaffMapper.updateByPrimaryKeySelective(xcompanyStaff);
	}

	

}
