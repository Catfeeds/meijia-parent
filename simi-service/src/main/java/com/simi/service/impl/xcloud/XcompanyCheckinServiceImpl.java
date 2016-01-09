package com.simi.service.impl.xcloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.XcompanyCheckinMapper;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;

@Service
public class XcompanyCheckinServiceImpl implements XcompanyCheckinService {

	@Autowired
	XcompanyCheckinMapper xcompanyCheckinMapper;

	@Override
	public XcompanyCheckin initXcompanyCheckin() {

		XcompanyCheckin record = new XcompanyCheckin();

		record.setId(0L);
		record.setCompanyId(0L);
		record.setStaffId(0L);
		record.setUserId(0L);
		record.setCheckinFrom((short) 0);
		record.setCheckinType((short) 0);
		record.setCheckinDevice("");
		record.setCheckinSn("");
		record.setPoiName("");
		record.setPoiLat("");
		record.setPoiLng("");
		record.setPoiDistance(0);
		record.setRemarks("");
		record.setCheckinStatus((short) 0);
		record.setCheckIn("");
		record.setCheckOut("");
		record.setFlexibleMin(0);
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public List<XcompanyCheckin> selectBySearchVo(CompanyCheckinSearchVo searchVo) {
		return xcompanyCheckinMapper.selectBySearchVo(searchVo);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {

		return xcompanyCheckinMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(XcompanyCheckin record) {
		return xcompanyCheckinMapper.insertSelective(record);
	}

	@Override
	public XcompanyCheckin selectByPrimarykey(Long id) {
		return xcompanyCheckinMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(XcompanyCheckin record) {
		return xcompanyCheckinMapper.updateByPrimaryKeySelective(record);
	}

}
