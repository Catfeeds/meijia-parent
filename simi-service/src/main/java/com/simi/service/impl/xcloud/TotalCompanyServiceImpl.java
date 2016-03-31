package com.simi.service.impl.xcloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.xcloud.TotalCompanyMapper;
import com.simi.po.model.xcloud.TotalCompany;
import com.simi.service.xcloud.TotalCompanyService;
import com.simi.vo.xcloud.CompanySearchVo;


@Service
public class TotalCompanyServiceImpl implements TotalCompanyService {

	@Autowired
	TotalCompanyMapper totalCompanyMapper;

	@Override
	public TotalCompany initTotalCompany() {

		TotalCompany record = new TotalCompany();

		record.setId(0L);
		record.setCompanyId(0L);
		record.setTotalBarcode(0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public List<TotalCompany> selectBySearchVo(CompanySearchVo searchVo) {
		return totalCompanyMapper.selectBySearchVo(searchVo);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return totalCompanyMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int insertSelective(TotalCompany TotalCompany) {
		return totalCompanyMapper.insertSelective(TotalCompany);
	}

	@Override
	public TotalCompany selectByPrimarykey(Long id) {
		return totalCompanyMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TotalCompany TotalCompany) {
		return totalCompanyMapper.updateByPrimaryKeySelective(TotalCompany);
	}
}
