package com.simi.service.xcloud;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.vo.xcloud.CompanySettingVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;



public interface XCompanySettingService {

	int deleteByPrimaryKey(Long id);

	int insert(XcompanySetting record);

	int insertSelective(XcompanySetting record);

	XcompanySetting selectByPrimaryKey(Long id);

	int updateByPrimaryKey(XcompanySetting record);

	int updateByPrimaryKeySelective(XcompanySetting record);

	XcompanySetting initXcompanySetting();

	List<XcompanySetting> selectBySearchVo(CompanySettingSearchVo searchVo);

	CompanySettingVo getCompantSettingVo(XcompanySetting item);

	PageInfo selectByListPage(CompanySettingSearchVo searchVo, int pageNo, int pageSize);

}
