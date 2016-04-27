package com.simi.service.impl;

import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.simi.service.NettySocketIOService;
import com.simi.vo.AppResultData;

@Service
public class NettySocketIOServiceImpl implements NettySocketIOService {

	@Override
	public void startUp() throws InterruptedException {
		Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(19092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("rt-simi-oa-card", AppResultData.class, new DataListener<AppResultData>() {
            @Override
            public void onData(SocketIOClient client, AppResultData data, AckRequest ackRequest) {
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
	}
}
