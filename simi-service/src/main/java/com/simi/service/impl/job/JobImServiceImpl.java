package com.simi.service.impl.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meijia.utils.huanxin.EasemobChatMessage;

import com.simi.service.job.JobImService;
import com.simi.service.user.UserImHistoryService;

@Service
public class JobImServiceImpl implements JobImService {

	@Autowired 
	UserImHistoryService userImHistoryService;
	
	/**
	 *  同步环信历史数据方法
	 *  @param beginTime 历史数据的开始时间
	 *  @param syncType  all = 所有  yesterday = 昨天  five = 5分钟内的
	 */
	@Override
	public Boolean syncIm(Long beginTime, String syncType) {
		
		JsonNodeFactory factory = new JsonNodeFactory(false);
        // 聊天消息 分页获取
        ObjectNode queryStrNode2 = factory.objectNode();
        queryStrNode2.put("limit", "500");
        
        queryStrNode2.put("ql", "select * where  timestamp >= " + beginTime);
        // 第一页
        ObjectNode messages2 = EasemobChatMessage.getChatMessages(queryStrNode2);
//        System.out.println(messages2.toString());
        userImHistoryService.insertByObjectNo(messages2, syncType);
        
        // 第二页
        if (messages2.get("cursor") != null) {
	        String cursor = messages2.get("cursor").asText();
	        ObjectNode messages3 = null;
	        while (true) {
	        	try {
		            queryStrNode2.put("cursor", cursor);
		            messages3 = EasemobChatMessage.getChatMessages(queryStrNode2);  
		            if (messages3.get("cursor") == null ) break;
		            cursor = messages3.get("cursor").asText();
		            userImHistoryService.insertByObjectNo(messages3, syncType);
	        	} catch(Exception e) {
	        		e.printStackTrace();
	        		break;
	        	}
	        }
	        //最后一页
	        userImHistoryService.insertByObjectNo(messages3, syncType);
        }
        
		return true;
	}
	
	
}