package com.simi.service.order;

import com.simi.vo.AppResultData;

public interface OrdersCancelService {

	AppResultData<Object> orderCancel(String orderNo);

	AppResultData<Object> orderCancelBack(String orderNo);

}