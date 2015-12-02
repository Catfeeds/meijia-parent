package com.simi.service.impl.xcloud;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.record.UseSelFSRecord;
import org.aspectj.weaver.patterns.IfPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.meijia.utils.BeanUtilsExp;
import com.simi.common.Constants;
import com.simi.po.dao.xcloud.XcompanyStaffMapper;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.UserCompanySearchVo;
import com.simi.vo.xcloud.UserCompanyFormVo;

@Service
public class XcompanyStaffServiceImpl implements XcompanyStaffService {

	@Autowired
	XcompanyStaffMapper xCompanyStaffMapper;

	@Autowired
	XcompanyDeptService xcompanyDeptService;

	
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
	public List<XcompanyStaff> selectBySearchVo(Long companyId, Long deptId) {

		return xCompanyStaffMapper.selectBySearchVo(companyId, deptId);

	}

	@Override
	public List<XcompanyStaff> selectByCompanyIdAndDeptId(Long companyId,
			Long deptId) {

		return xCompanyStaffMapper
				.selectByCompanyIdAndDeptId(companyId, deptId);
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
	public UserCompanyFormVo getUserCompany(Users users) {

		UserCompanyFormVo vo = new UserCompanyFormVo();

		BeanUtilsExp.copyPropertiesIgnoreNull(users, vo);

		// 员工类型
		XcompanyStaff xcompanyStaff = xCompanyStaffMapper.selectByUserId(users
				.getId());

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

		// 工号
		vo.setJobNumber(xcompanyStaff.getJobNumber());

		// 部门Id
		vo.setDeptId(xcompanyStaff.getDeptId());
		// 部门名称
		XcompanyDept xcompanyDept = xcompanyDeptService
				.selectByPrimaryKey(xcompanyStaff.getDeptId());
		if (xcompanyDept != null) {
			vo.setDeptName(xcompanyDept.getName());
		} else {
			vo.setDeptName("");
		}

		// 职位
		vo.setJobName(xcompanyStaff.getJobName());

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
	public XcompanyStaff selectByUserId(Long userId) {
		
		return xCompanyStaffMapper.selectByUserId(userId);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyStaff xcompanyStaff) {
		
		return xCompanyStaffMapper.updateByPrimaryKeySelective(xcompanyStaff);
	}

	

}
