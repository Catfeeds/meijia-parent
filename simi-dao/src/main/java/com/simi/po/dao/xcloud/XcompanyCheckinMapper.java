package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;

public interface XcompanyCheckinMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XcompanyCheckin record);

    int insertSelective(XcompanyCheckin record);

    XcompanyCheckin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XcompanyCheckin record);

    int updateByPrimaryKey(XcompanyCheckin record);

	List<XcompanyCheckin> selectBySearchVo(CompanyCheckinSearchVo searchVo);
}