package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.vo.xcloud.CompanySettingSearchVo;

public interface XcompanySettingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XcompanySetting record);

    int insertSelective(XcompanySetting record);

    XcompanySetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XcompanySetting record);

    int updateByPrimaryKey(XcompanySetting record);

	List<XcompanySetting> selectBySearchVo(CompanySettingSearchVo searchVo);

	List<XcompanySetting> selectByListPage(CompanySettingSearchVo searchVo);
}