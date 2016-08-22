package com.simi.service.impl.xcloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.meijia.utils.BeanUtilsExp;
import com.simi.po.dao.xcloud.XcompanyDeptMapper;
import com.simi.po.dao.xcloud.XcompanyMapper;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.simi.vo.xcloud.XcompanyDeptVo;
import com.simi.vo.xcloud.company.DeptSearchVo;

@Service
public class XcompanyDeptServiceImpl implements XcompanyDeptService {
	
	@Autowired
	XcompanyDeptMapper xCompanyDeptMapper;		
	
	@Autowired
	XcompanyMapper xCompanyMapper;
	
	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private UsersService userService;
	
	@Override
	public XcompanyDept initXcompanyDept() {
		XcompanyDept record = new XcompanyDept();
		record.setDeptId(0L);
		record.setCompanyId(0L);
		record.setName("");
		record.setParentId(0L);
		record.setLeaderUserId(0L);
		
		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return xCompanyDeptMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int insert(XcompanyDept record) {
		return xCompanyDeptMapper.insert(record);
	}
	 
	@Override
	public int insertSelective(XcompanyDept record) {
		return xCompanyDeptMapper.insertSelective(record);
	}

	@Override
	public XcompanyDept selectByPrimaryKey(Long id) {
		return xCompanyDeptMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(XcompanyDept record) {
		return xCompanyDeptMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyDept record) {
		return xCompanyDeptMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<XcompanyDept> selectByXcompanyId(Long xcompanyId) {
		
		return xCompanyDeptMapper.selectByXcompanyId(xcompanyId);
	}

	@Override
	public List<XcompanyDept> selectByParentId(Long parentId) {

		return xCompanyDeptMapper.selectByParentId(parentId);
	}

	@Override
	public List<XcompanyDept> selectByParentIdAndXcompanyId(Long parentId,Long xcompanyId) {

		return xCompanyDeptMapper.selectByParentIdAndXcompanyId(parentId,xcompanyId);
	}
	
	@Override
	public XcompanyDept selectByXcompanyIdAndDeptName(Long companyId,String name) {

		return xCompanyDeptMapper.selectByXcompanyIdAndDeptName(companyId,name);
	}
	
	@Override
	public XcompanyDept selectByXcompanyIdAndDeptId(Long companyId, Long deptId) {

		return xCompanyDeptMapper.selectByXcompanyIdAndDeptId(companyId,deptId);
	}

	@Override
	public List<XcompanyDept> selectBySearchVo(DeptSearchVo searchVo) {
		return xCompanyDeptMapper.selectBySearchVo(searchVo);
	}

	@Override
	public XcompanyDeptVo initVo() {
		
		XcompanyDeptVo record = new  XcompanyDeptVo();
		
		XcompanyDept dept = initXcompanyDept();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(dept, record);
		
		record.setCompanyName("");
		record.setParentName("");
		
		return record;
	}

	@Override
	public XcompanyDeptVo transToVo(XcompanyDept dept) {
		
		XcompanyDeptVo initVo = initVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(dept, initVo);
		
		Long companyId = dept.getCompanyId();
		
		Xcompany xcompany = xCompanyMapper.selectByPrimaryKey(companyId);
		
		//公司名称
		initVo.setCompanyName(xcompany.getCompanyName());
		
//		XcompanyDept dept2 = xCompanyDeptMapper.selectByPrimaryKey(dept.getParentId());
//		
//		//上级部门名称
//		initVo.setParentName(dept2.getName());
		
		//负责人

		initVo.setLeadUserName("");
		if (dept.getLeaderUserId() > 0L) {
			Users users = userService.selectByPrimaryKey(dept.getLeaderUserId());
			initVo.setLeadUserName(users.getName());
		}
		
		initVo.setTotal(0);
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setDeptId(dept.getDeptId());
		List<XcompanyStaff> staffs = xcompanyStaffService.selectBySearchVo(searchVo);
		if (!staffs.isEmpty()) {
			initVo.setTotal(staffs.size());
		}
		return initVo;
	}

	@Override
	public List<XcompanyDept> selectByListPage(DeptSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<XcompanyDept> list = xCompanyDeptMapper.selectBySearchVo(searchVo);
		
		return list;
	}
	
}

