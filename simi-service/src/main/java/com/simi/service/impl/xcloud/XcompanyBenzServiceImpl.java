package com.simi.service.impl.xcloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyBenzMapper;
import com.simi.po.model.xcloud.XcompanyBenz;
import com.simi.po.model.xcloud.XcompanyBenzTime;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.po.model.xcloud.XcompanyStaffBenz;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyBenzService;
import com.simi.service.xcloud.XcompanyBenzTimeService;
import com.simi.service.xcloud.XcompanyStaffBenzService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.xcloud.UserCompanySearchVo;

@Service
public class XcompanyBenzServiceImpl implements XcompanyBenzService {
	
	@Autowired
	XcompanyBenzMapper xCompanyBenzMapper;		
	
	@Autowired
	private XcompanyBenzTimeService xCompanyBenzTimeService;	
	
	@Autowired
	private XCompanyService xCompanyService;
	
	@Autowired
	private XcompanyStaffService xCompanyStaffService;	
	
	@Autowired
	private XcompanyStaffBenzService xCompanyStaffBenzService;	
	
	@Override
	public XcompanyBenz initXcompanyBenz() {
		XcompanyBenz record = new XcompanyBenz();
		record.setBenzId(0L);
		record.setCompanyId(0L);
		record.setName("");
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return xCompanyBenzMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insert(XcompanyBenz record) {
		return xCompanyBenzMapper.insert(record);
	}
	
	@Override
	public Long insertSelective(XcompanyBenz record) {
		return xCompanyBenzMapper.insertSelective(record);
	}

	@Override
	public XcompanyBenz selectByPrimaryKey(Long id) {
		return xCompanyBenzMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(XcompanyBenz record) {
		return xCompanyBenzMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyBenz record) {
		return xCompanyBenzMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<XcompanyBenz> selectByXcompanyId(Long xcompanyId) {
		return xCompanyBenzMapper.selectByXcompanyId(xcompanyId);
	}

	
	@Override
	public Long setDefaultBenz(Long companyId) {
		XcompanyBenz benz = this.initXcompanyBenz();
		benz.setCompanyId(companyId);
		benz.setName("默认班次");
		
		this.insert(benz);
		
		Long benzId = benz.getBenzId();
		
		//设置默认班次时间段
		XcompanyBenzTime benzTime = xCompanyBenzTimeService.initXcompanyBenzTime();

		benzTime.setBenzId(benzId);
		benzTime.setCompanyId(0L);
		benzTime.setCheckIn("09:00");
		benzTime.setCheckOut("18:00");
		benzTime.setFlexibleMin(30);
		
		xCompanyBenzTimeService.insert(benzTime);
		
		//设置全部员工加入默认班次.
		
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xCompanyStaffService.selectBySearchVo(searchVo);
		
		for (XcompanyStaff item : staffList) {
			XcompanyStaffBenz vo = xCompanyStaffBenzService.initXcompanyStaffBenz();
			
			vo.setCompanyId(companyId);
			vo.setStaffId(item.getId());
			vo.setUserId(item.getUserId());
			vo.setBenzId(benzId);
			vo.setDeptId(item.getDeptId());
		    xCompanyStaffBenzService.insertSelective(vo);
		}
		return benzId;
	}
}

