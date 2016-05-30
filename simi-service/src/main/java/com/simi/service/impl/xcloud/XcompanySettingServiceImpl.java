package com.simi.service.impl.xcloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.xcloud.CompanySettingVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.po.model.op.OpChannel;
import com.simi.po.model.xcloud.XcompanySetting;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanySettingMapper;

@Service
public class XcompanySettingServiceImpl implements XCompanySettingService {
	
	@Autowired
	XcompanySettingMapper xcompanySettingMapper;
	

	@Override
	public XcompanySetting initXcompanySetting() {
		XcompanySetting record = new XcompanySetting();
		
		record.setId(0L);
		record.setCompanyId(0L);
		record.setUserId(0L);
		record.setName("");
		record.setSettingJson("");
		record.setSettingType("");
		record.setIsEnable((short)1);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return xcompanySettingMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int insert(XcompanySetting record) {
		return xcompanySettingMapper.insert(record);
	}
	
	@Override
	public int insertSelective(XcompanySetting record) {
		return xcompanySettingMapper.insertSelective(record);
	}

	@Override
	public XcompanySetting selectByPrimaryKey(Long id) {
		return xcompanySettingMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(XcompanySetting record) {
		return xcompanySettingMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanySetting record) {
		return xcompanySettingMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<XcompanySetting> selectBySearchVo(CompanySettingSearchVo searchVo) {
		return xcompanySettingMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public PageInfo selectByListPage(CompanySettingSearchVo searchVo, int pageNo, int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<XcompanySetting> list = xcompanySettingMapper.selectByListPage(searchVo);
		PageInfo result = new PageInfo(list);
		
		return result;
	}

	@Override
	public CompanySettingVo getCompantSettingVo(XcompanySetting item) {
		
		CompanySettingVo vo = new CompanySettingVo();
		//添加时间返回‘yyyy-mm-dd’
		Long addTime = item.getAddTime()*1000;
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime, "yyyy-MM-dd"));

		vo.setName(item.getName());
		vo.setSettingJson(item.getSettingJson());
		vo.setSettingId(item.getId());
		
		return vo;
	}
	
	
}