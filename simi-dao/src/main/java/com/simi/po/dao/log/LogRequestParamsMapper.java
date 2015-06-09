package com.simi.po.dao.log;

import com.simi.po.model.log.LogRequestParams;

public interface LogRequestParamsMapper {
    int insert(LogRequestParams record);

    int insertSelective(LogRequestParams record);
}