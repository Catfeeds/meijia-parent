package com.simi.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserImHistoryService;
import com.simi.po.dao.user.UserImHistoryMapper;
import com.simi.po.model.user.UserImHistory;
import com.fasterxml.jackson.databind.JsonNode;
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
		record.setImContent("");
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
	          String uuid = getDataItemValue(data.get(i), "uuid");
	          
	          if (uuid == null || uuid.length() <=0) continue;
	          
	          UserImHistory record = userImHistoryMapper.selectByUuid(uuid);
	          
	          if (record != null) continue;
	          
	          String chatType = getDataItemValue(data.get(i), "chat_type");

	          String addTimeStr =  getDataItemValue(data.get(i), "timestamp");

	          String fromImUser = getDataItemValue(data.get(i), "from");

	          String toImUser = getDataItemValue(data.get(i), "to");
	          String msgId = getDataItemValue(data.get(i), "msg_id");
	          String content = getDataItemValue(data.get(i), "payload");

	          record = this.initUserImHistory();
	          record.setUuid(uuid);
	          record.setChatType(chatType);
	          record.setFromImUser(fromImUser);
	          record.setToImUser(toImUser);
	          record.setMsgId(msgId);
	          record.setImContent(content);
	          
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
	
	
	private String getDataItemValue(JsonNode jsonNode, String itemName) {
		
		String itemValue = "";
		if (jsonNode.get(itemName) != null) {
			itemValue = jsonNode.get(itemName).asText();
		}
		return itemValue;
	}

}