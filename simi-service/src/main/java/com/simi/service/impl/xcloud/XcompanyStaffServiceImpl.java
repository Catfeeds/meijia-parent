package com.simi.service.impl.xcloud;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.simi.po.dao.xcloud.XcompanyStaffMapper;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.utils.XcompanyUtil;
import com.simi.vo.UserCompanySearchVo;
import com.simi.vo.xcloud.StaffListVo;

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
				StaffListVo vo = changeToStaffLisVo(item);
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
	public StaffListVo changeToStaffLisVo(XcompanyStaff item) {

		StaffListVo vo = new StaffListVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
		
		Users users = usersService.selectByPrimaryKey(item.getUserId());
		
		Long companyId = item.getCompanyId();
		Long deptId = item.getDeptId();
		BeanUtilsExp.copyPropertiesIgnoreNull(users, vo);
		
		vo.setStaffType(item.getStaffType());
		vo.setStaffTypeName(XcompanyUtil.getStaffTypeName(item.getStaffType()));
		// 部门名称
		XcompanyDept xcompanyDept = xcompanyDeptService.selectByXcompanyIdAndDeptId(companyId, deptId);
		if (xcompanyDept != null) {
			vo.setDeptName(xcompanyDept.getName());
		} else {
			vo.setDeptName("未分配");
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
