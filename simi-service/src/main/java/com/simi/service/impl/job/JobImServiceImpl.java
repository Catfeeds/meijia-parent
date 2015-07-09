package com.simi.service.impl.job;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meijia.utils.huanxin.EasemobChatMessage;
import com.simi.service.job.JobImService;

@Service
public class JobImServiceImpl implements JobImService {

	
	@Override
	public Boolean syncImAll() {
		
		JsonNodeFactory factory = new JsonNodeFactory(false);
        // 聊天消息 分页获取
        ObjectNode queryStrNode2 = factory.objectNode();
        queryStrNode2.put("limit", "100");
        // 第一页
        ObjectNode messages2 = EasemobChatMessage.getChatMessages(queryStrNode2);
        // 第二页
        String cursor = messages2.get("cursor").asText();
        ObjectNode messages3 = null;
        while (true) {
        	try {
	            queryStrNode2.put("cursor", cursor);
	            messages3 = EasemobChatMessage.getChatMessages(queryStrNode2);  
	            if (messages3.get("cursor") == null ) break;
	            cursor = messages3.get("cursor").asText();
        	} catch(Exception e) {
        		e.printStackTrace();
        		break;
        	}
        }		
		
		
		return true;
	}
	
	
}