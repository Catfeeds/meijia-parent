package com.simi.service.impl.xcloud;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyJobMapper;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyJob;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyJobService;
import com.simi.vo.xcloud.CompanyJobSearchVo;
import com.simi.vo.xcloud.XcompanyJobVo;

@Service 
public class XcompanyJobServiceImpl implements XcompanyJobService {
	
	@Autowired
	private XcompanyJobMapper jobMapper;
	@Autowired
	private XcompanyDeptService deptService;
	
	@Override
	public int deleteByPrimaryKey(Long kjobId) {
		return jobMapper.deleteByPrimaryKey(kjobId);
	}

	@Override
	public int insert(XcompanyJob record) {
		return jobMapper.insert(record);
	}

	@Override
	public XcompanyJob selectByPrimaryKey(Long kjobId) {
		return jobMapper.selectByPrimaryKey(kjobId);
	}

	@Override
	public int updateByPrimaryKey(XcompanyJob record) {
		return jobMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<XcompanyJob> selectByListPage(CompanyJobSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<XcompanyJob> list = jobMapper.selectByListPage(searchVo);
		
		return list; 
	}

	@Override
	public List<XcompanyJob> selectBySearchVo(CompanyJobSearchVo searchVo) {
		return jobMapper.selectBySearchVo(searchVo);
	}

	@Override
	public XcompanyJob initJob() {
		
		XcompanyJob job = new XcompanyJob();
		
		job.setJobId(0L);
		job.setCompanyId(0L);
		job.setUserId(0L);
		job.setJobName("");
		job.setTotalNum((short)0);
		job.setRemarks("");
		job.setAddTime(TimeStampUtil.getNowSecond());
		job.setUpdateTime(0L);
		
		job.setDeptId(0L);
		
		return job;
	}

	@Override
	public XcompanyJobVo initVo() {
		
		XcompanyJobVo jobVo = new XcompanyJobVo();
		
		XcompanyJob job = initJob();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(job, jobVo);
		
		jobVo.setDeptName("");
		jobVo.setUserList(new ArrayList<Users>());
		
		return jobVo;
	}

	@Override
	public XcompanyJobVo transToVO(XcompanyJob job) {
		
		XcompanyJobVo jobVo = initVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(job, jobVo);
		
		//所属部门
		Long deptId = job.getDeptId();
		XcompanyDept dept = deptService.selectByPrimaryKey(deptId);
		
		jobVo.setDeptName(dept.getName());
		
		return jobVo;
	}

}
