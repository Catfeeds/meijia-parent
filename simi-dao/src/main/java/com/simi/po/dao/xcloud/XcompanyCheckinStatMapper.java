package com.simi.po.dao.xcloud;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.xcloud.XcompanyCheckinStat;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;

public interface XcompanyCheckinStatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XcompanyCheckinStat record);

    int insertSelective(XcompanyCheckinStat record);

    XcompanyCheckinStat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XcompanyCheckinStat record);

    int updateByPrimaryKey(XcompanyCheckinStat record);
    
    List<XcompanyCheckinStat> selectBySearchVo(CompanyCheckinSearchVo searchVo);
    
    List<HashMap> totalBySearchVo(CompanyCheckinSearchVo searchVo);
}