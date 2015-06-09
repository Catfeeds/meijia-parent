package com.simi.service.log;

import com.simi.po.model.log.LogRequestParams;

public interface LogRequestParamService {
	 int insert(LogRequestParams record);

	 int insertSelective(LogRequestParams record);

}
