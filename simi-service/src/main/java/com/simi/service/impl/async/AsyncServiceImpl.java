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
	
}
