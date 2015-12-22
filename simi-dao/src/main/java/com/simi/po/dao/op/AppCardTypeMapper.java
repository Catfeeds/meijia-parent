package com.simi.po.dao.op;

import java.util.List;

import com.simi.po.model.op.AppCardType;
import com.simi.po.model.op.AppTools;

public interface AppCardTypeMapper {
    int deleteByPrimaryKey(Long cardTypeId);

    int insert(AppCardType record);

    int insertSelective(AppCardType record);

    AppCardType selectByPrimaryKey(Long cardTypeId);

    int updateByPrimaryKeySelective(AppCardType record);

    int updateByPrimaryKey(AppCardType record);

	List<AppCardType> selectByAppType(String appType);

	List<AppTools> selectByListPage();
}