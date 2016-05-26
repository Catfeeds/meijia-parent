package com.simi.service.impl.xcloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyAdminMapper;
import com.simi.po.model.xcloud.XcompanyAdmin;
import com.simi.service.xcloud.XcompanyAdminService;
import com.simi.vo.xcloud.CompanyAdminSearchVo;


@Service
public class XcompanyAdminServiceImpl implements XcompanyAdminService {

	@Autowired
	XcompanyAdminMapper xCompanyAdminMapper;

	@Override
	public XcompanyAdmin initXcompanyAdmin() {

		XcompanyAdmin record = new XcompanyAdmin();

		record.setId(0L);
		record.setCompanyId(0L);
		record.setCompanyName("");
		record.setUserId(0L);
		record.setUserName("");
		record.setPassword("");
		record.setIsCreate(0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		
		return record;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(CompanyAdminSearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<XcompanyAdmin> list = xCompanyAdminMapper.selectByListPage(searchVo);
		
		PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public List<XcompanyAdmin> selectBySearchVo(CompanyAdminSearchVo searchVo) {
		return xCompanyAdminMapper.selectBySearchVo(searchVo);
	}
	
	@Override
	public int insert(XcompanyAdmin record) {
		return xCompanyAdminMapper.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return xCompanyAdminMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public XcompanyAdmin selectByPrimarykey(Long id) {
		return xCompanyAdminMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyAdmin XcompanyAdmin) {
		return xCompanyAdminMapper.updateByPrimaryKeySelective(XcompanyAdmin);
	}
}
