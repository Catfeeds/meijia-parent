package com.simi.service.async;

import java.util.concurrent.Future;

public interface AsyncService {

	Future<Boolean> async();

	Future<Boolean> schoolAsync();
	
}