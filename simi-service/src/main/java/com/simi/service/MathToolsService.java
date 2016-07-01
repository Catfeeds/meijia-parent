package com.simi.service;

import java.util.Date;
import java.util.Map;

import com.simi.po.model.common.Weathers;
import com.simi.vo.AppResultData;


public interface MathToolsService {

	AppResultData<Object> mathInsurance(Long cityId, int shebao, int gjj);


}
