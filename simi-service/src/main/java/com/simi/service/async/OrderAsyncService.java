package com.simi.service.async;

import java.util.concurrent.Future;

import com.simi.po.model.order.Orders;

public interface OrderAsyncService {

	Future<Boolean> orderScore(Orders order);

	
}