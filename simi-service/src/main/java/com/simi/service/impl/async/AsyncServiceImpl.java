package com.simi.service.impl.async;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.simi.service.async.AsyncService;

@Service
public class AsyncServiceImpl implements AsyncService {

    @Async  
    @Override
    public Future<Boolean> async() {

        // Demonstrate that our beans are being injected
        System.out.println("async start");

        try {
            Thread.sleep(5000);
            System.out.println("async running....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("async end");

        return new AsyncResult<Boolean>(true);
    }

    @Async
	@Override
	public Future<Boolean> schoolAsync() {
		System.out.println("放学");
		
		try {
			Thread.sleep(6000);
			System.out.println("别");
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("别");
		return new AsyncResult<Boolean>(true);
	}  
	
}
