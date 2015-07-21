package com.simi.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserImHistoryService;
import com.simi.po.dao.user.UserImHistoryMapper;
import com.simi.po.model.user.UserImHistory;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserImHistoryServiceImpl implements UserImHistoryService {
	
	@Autowired 
	UserImHistoryMapper userImHistoryMapper;
	
	@Override
	public UserImHistory initUserImHistory() {
		UserImHistory record = new UserImHistory();
		record.setId(0L);
		record.setFromImUser("");
		record.setToImUser("");
		record.setChatType("");
		record.setContent("");
		record.setMsgId("");
		record.setUuid("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public Long insert(UserImHistory record) {
		return userImHistoryMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserImHistory record) {
		return userImHistoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(UserImHistory record) {
		return userImHistoryMapper.insertSelective(record);
	}
	
	@Override
	public boolean insertByObjectNo(ObjectNode messages) {
		
		if (messages == null) return false;
		
		try {
		
	        ArrayNode data =(ArrayNode)messages.get("entities");
	
	        for (int i=0; i < data.size(); i++) {
	          String uuid = data.get(i).get("uuid").asText();
	          
	          if (uuid == null || uuid.length() <=0) continue;
	          
	          UserImHistory record = userImHistoryMapper.selectByUuid(uuid);
	          
	          if (record != null) continue;
	          
	          String chatType = data.get(i).get("chat_type").asText();
	          String addTimeStr = data.get(i).get("timestamp").asText();
	          String fromImUser = data.get(i).get("from").asText();
	          String toImUser = data.get(i).get("to").asText();
	          String msgId = data.get(i).get("msg_id").asText();
	          String content = data.get(i).get("payload").toString();
	          
	          record = this.initUserImHistory();
	          record.setUuid(uuid);
	          record.setChatType(chatType);
	          record.setFromImUser(fromImUser);
	          record.setToImUser(toImUser);
	          record.setMsgId(msgId);
	          record.setContent(content);
	          
	          Long addTime = Long.valueOf(addTimeStr) / 1000;
	          
	          record.setAddTime(addTime);
	          
	          userImHistoryMapper.insert(record);

	          System.out.println(uuid);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}