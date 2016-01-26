package com.simi.service.impl.user;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.user.UserImHistoryService;
import com.simi.service.user.UserImLastService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UsersService;
import com.simi.vo.UserImLastSearchVo;
import com.simi.po.dao.user.UserImHistoryMapper;
import com.simi.po.model.user.UserImHistory;
import com.simi.po.model.user.UserImLast;
import com.simi.po.model.user.UserRef3rd;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserImHistoryServiceImpl implements UserImHistoryService {
	
	@Autowired 
	UserImHistoryMapper userImHistoryMapper;
	
	@Autowired
	private UserRefSecService userRefSecService;	
	
	@Autowired
	private UsersService userService;	
	
	@Autowired
	private UserRef3rdService userRef3rdService;

	@Autowired
	private UserImLastService userImLastService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;			
	
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
	
	/**
	 * 获取两个用户的对话历史列表
	 */
	@Override
	public PageInfo selectByImUserListPage(String fromImUser, String toImUser, int pageNo,
			int pageSize) {
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<UserImHistory> list = userImHistoryMapper.selectByImUserListPage(fromImUser, toImUser);
		PageInfo result = new PageInfo(list);
		return result;
	}
	
	@Override
	public List<UserImHistory> getAllImUserLastIm() {
		List<UserImHistory> result= userImHistoryMapper.selectMaxByFromAll();
		return result;
	}
		
	/**
	 * 插入聊天记录信息
	 * @param 聊天记录信息
	 * @param syncType  all = 所有  yesterday = 昨天  five = 5分钟内的
	 */
	@Override
	public boolean insertByObjectNo(ObjectNode messages, String syncType) {
		
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
	          
	          String content = "";
	          if (data.get(i).get("payload") != null) {
	        	  content = data.get(i).get("payload").toString();
	        	  content = convertToUtf8mb4(content);
	          }
	          String imContent = getImMsg(data.get(i).get("payload"));
	          
//	          System.out.println(content);
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
	          
	          if (!syncType.equals("five")) continue;
	          
	          //如果是5分钟的同步，则需要插入user_im_last表中，形成用户与好友最后一条的聊天记录
	          UserRef3rd fromUser = 	userRef3rdService.selectByUserNameAnd3rdType(fromImUser, "huanxin");
	          UserRef3rd toUser = 	userRef3rdService.selectByUserNameAnd3rdType(toImUser, "huanxin");
	          
	         Long fromUserId = 0L;
	         Long toUserId = 0L;
	         if (fromUser != null) fromUserId = fromUser.getUserId();
	         if (toUser != null) toUserId = toUser.getUserId();
	          
	         if (fromUserId.equals(0L) && toUserId.equals(0L)) continue;
	          //先插入fromUser -> toUser.
	         
	          UserImLastSearchVo searchVo = new UserImLastSearchVo();
	          searchVo.setFromUserId(fromUserId);
	          searchVo.setToUserId(toUserId);
	          UserImLast fromUserPo = userImLastService.selectBySearchVo(searchVo);
	          Boolean isNew = false;
	          if (fromUserPo == null) {
	        	  fromUserPo = userImLastService.initUserImLast();
	        	  isNew = true;
	          }
	          
	          fromUserPo.setFromUserId(fromUserId);
	          fromUserPo.setToUserId(toUserId);
	          fromUserPo.setChatType(chatType);
	          fromUserPo.setFromImUser(fromImUser);
	          fromUserPo.setToImUser(toImUser);
	          fromUserPo.setMsgId(msgId);
	          
	          
	          fromUserPo.setImContent(imContent);
	          
	          fromUserPo.setAddTime(addTime);
	          
	          if (isNew) {
	        	  userImLastService.insert(fromUserPo);
	          } else {
	        	  userImLastService.updateByPrimaryKeySelective(fromUserPo);
	          }
	          
	          //再插入 toUser -> fromuser;
	          searchVo.setFromUserId(toUserId);
	          searchVo.setToUserId(fromUserId);
	          UserImLast toUserPo = userImLastService.selectBySearchVo(searchVo);
	          isNew = false;
	          if (toUserPo == null) {
	        	  toUserPo = userImLastService.initUserImLast();
	        	  isNew = true;
	          }
	          
	          toUserPo.setFromUserId(toUserId);
	          toUserPo.setToUserId(fromUserId);
	          toUserPo.setChatType(chatType);
	          toUserPo.setFromImUser(fromImUser);
	          toUserPo.setToImUser(toImUser);
	          toUserPo.setMsgId(msgId);
	          toUserPo.setImContent(imContent);
	          
	          toUserPo.setAddTime(addTime);
	          
	          if (isNew) {
	        	  userImLastService.insert(toUserPo);
	          } else {
	        	  userImLastService.updateByPrimaryKeySelective(toUserPo);
	          }
	          
	          //生成消息记录
	          userMsgAsyncService.newImMsg(fromUserId, fromImUser, toUserId, toImUser, imContent);
	          
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	private String getDataItemValue(JsonNode data, String itemName) {
		
		String itemValue = "";
		if (data.get(itemName) != null) {
			itemValue = data.get(itemName).asText();
		}
		return itemValue;
	}
	
	private String convertToUtf8mb4(String content) throws UnsupportedEncodingException {
		
		byte[] bText = content.getBytes("utf-8");
	    for (int i = 0; i < bText.length; i++)  
	    {  
	        if((bText[i] & 0xF8)== 0xF0){  
	            for (int j = 0; j < 4; j++) {                          
	            	bText[i+j]=0x30;                     
	        }  
	        i+=3;  
	        }  
	    }  
	    
	    String result = new String(bText);
	    return result;
	}
	
	@Override
	public String getImMsg(JsonNode contentJsonNode) {
		String imContentType = "";
		String imContentMsg = "";
		
		if (contentJsonNode.get("bodies") != null) {
			 if (contentJsonNode.get("bodies").get(0) != null &&
			     contentJsonNode.get("bodies").get(0).get("type") != null) {
				 imContentType = contentJsonNode.get("bodies").get(0).get("type").asText();
				 
				 switch(imContentType) {
				 	case "txt": 
				 		imContentMsg =  contentJsonNode.get("bodies").get(0).get("msg").asText();
				 		break;
				 	case "img":
				 		imContentMsg = "图片";
				 		break;
				 	case "audio":
				 		imContentMsg = "语音";
				 		break;
				 	case "loc":
				 		imContentMsg = "位置";
				 		break;		
				 	case "video":
				 		imContentMsg = "视频";
				 		break;
				 	case "cmd":
				 		imContentMsg = "";
				 		break;	
				 }
			 }
		}		
		
		return imContentMsg;
	}
}