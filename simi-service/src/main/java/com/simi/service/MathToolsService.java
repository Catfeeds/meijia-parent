package com.simi.service;

import java.util.Date;
import java.util.Map;

import com.simi.po.model.common.Weathers;
import com.simi.vo.AppResultData;


public interface MathToolsService {

	AppResultData<Object> mathTaxPersion(Double salary, Double insurance, Double beginTax);

	AppResultData<Object> mathInsurance(Long cityId, Double shebao, Double gjj);

	AppResultData<Object> mathTaxYear(Double money);

	AppResultData<Object> mathTaxPay(Double money);


}
