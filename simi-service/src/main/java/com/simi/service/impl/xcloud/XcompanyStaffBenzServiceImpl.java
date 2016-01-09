package com.simi.service.impl.xcloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyStaffBenzMapper;
import com.simi.po.model.xcloud.XcompanyStaffBenz;
import com.simi.service.xcloud.XcompanyStaffBenzService;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Service
public class XcompanyStaffBenzServiceImpl implements XcompanyStaffBenzService {

	@Autowired
	XcompanyStaffBenzMapper xCompanyStaffBenzMapper;

	@Override
	public XcompanyStaffBenz initXcompanyStaffBenz() {

		XcompanyStaffBenz record = new XcompanyStaffBenz();

		record.setId(0L);
		record.setCompanyId(0L);
		record.setStaffId(0L);
		record.setUserId(0L);
		record.setBenzId(0L);
		record.setDeptId(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public List<XcompanyStaffBenz> selectBySearchVo(UserCompanySearchVo searchVo) {
		return xCompanyStaffBenzMapper.selectBySearchVo(searchVo);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {

		return xCompanyStaffBenzMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(XcompanyStaffBenz record) {
		return xCompanyStaffBenzMapper.insertSelective(record);
	}

	@Override
	public XcompanyStaffBenz selectByPrimarykey(Long id) {
		return xCompanyStaffBenzMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyStaffBenz record) {
		return xCompanyStaffBenzMapper.updateByPrimaryKeySelective(record);
	}

}
