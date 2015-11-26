package com.simi.service.impl.xcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyDeptMapper;
import com.simi.po.dao.xcloud.XcompanyMapper;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.service.xcloud.XcompanyDeptService;

@Service
public class XcompanyDeptServiceImpl implements XcompanyDeptService {
	
	@Autowired
	XcompanyDeptMapper xCompanyDeptMapper;		
	
	
	@Override
	public XcompanyDept initXcompanyDept() {
		XcompanyDept record = new XcompanyDept();
		record.setDeptId(0L);
		record.setCompanyId(0L);
		record.setName("");
		record.setParentId(0L);

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

}