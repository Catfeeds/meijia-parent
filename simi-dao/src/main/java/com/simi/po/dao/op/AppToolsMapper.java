package com.simi.po.dao.op;

import java.util.List;

import com.simi.po.model.op.AppTools;
import com.simi.vo.ApptoolsSearchVo;

public interface AppToolsMapper {
    int deleteByPrimaryKey(Long tId);

    int insert(AppTools record);

    Long insertSelective(AppTools record);

    AppTools selectByPrimaryKey(Long tId);

    int updateByPrimaryKeySelective(AppTools record);

    int updateByPrimaryKey(AppTools record);

	List<AppTools> selectBySearchVo(ApptoolsSearchVo searchVo);

	List<AppTools> selectByListPage(ApptoolsSearchVo searchVo);
}