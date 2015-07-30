package com.simi.service.impl.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserImHistoryService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UsersService;
import com.simi.vo.UserSearchVo;
import com.simi.vo.user.UserImVo;
import com.simi.vo.user.UserViewVo;
import com.simi.po.dao.user.UserImHistoryMapper;
import com.simi.po.model.user.UserDetailPay;
import com.simi.po.model.user.UserImHistory;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.SortList;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserImHistoryServiceImpl implements UserImHistoryService {
	
	@Autowired 
	UserImHistoryMapper userImHistoryMapper;
	
	@Autowired
	private UserRefSecService userRefSecService;	
	
	@Autowired
	private UsersService userService;	
	
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
	
	
	
	/**
	 * 获取当前秘书所有好友的最新一条聊天信息
	 * 流程为
	 * 1. 先获得当前用户发给所有好友的最新一条聊天信息，得到集合A.
	 * 2. 在获得所有好友发给当前用户的最新一条聊天信息，得到集合B.
	 * 3. 集合A和集合B 进行 当前用户 - 当前好友的 排查，得到集合C。
	 * 4. 获得当前所有好友的记录，得到集合D，
	 * 5. 遍历集合D，每一个好友去匹配集合C的最新一条聊天信息，得到最终的集合信息E。
	 */
	@Override
	public List<UserImVo> getAllImUserLastIm(Long secId, String imUserName) {
		
		List<UserImVo> result= new ArrayList<UserImVo>();
		
		List<UserRefSec> userImList = userRefSecService.selectBySecId(secId);
		
		if (userImList.isEmpty()) {
			return result;
		}
		
		
		List<UserImHistory> fromImList = userImHistoryMapper.selectMaxByFromImUser(imUserName);
		
		List<UserImHistory> toImList = userImHistoryMapper.selectMaxByToImUser(imUserName);
		
		//判断哪个数组数据大，作为循环的条件， 另外一个作为对比的数组。
		List<UserImHistory> eachList = fromImList;
		List<UserImHistory> compList = toImList;
		if (fromImList.size() < toImList.size()) {
			eachList = toImList;
			compList = fromImList;
		}
		
		UserImHistory item = null;
		List<UserImHistory> hasImHistory = new ArrayList<UserImHistory>();
		
		//两重循环的意思是， 找出 fromImList toImList 中最新的一条数据
		// 比如  fromImList 有一条为  u1  ->  u2    时间为 18:00
		//       toImlist  有一条为  u2 -> u2  时间为 18：01 ，则取toImList的值
		for (int i = 0; i < eachList.size(); i++) {
			item = eachList.get(i);
			UserImHistory compItem = null;
			
			for (int j = 0; j < compList.size(); j++) {
				compItem = compList.get(j);
				
//				System.out.println(item.getFromImUser() +" == " + compItem.getFromImUser() + "-----" + item.getToImUser() +" == " + compItem.getToImUser());
				if (
						(item.getFromImUser().equals(compItem.getFromImUser()) && 
						 item.getToImUser().equals(compItem.getToImUser()) 
						)
					     ||
					    (item.getFromImUser().equals(compItem.getToImUser()) &&
					     item.getToImUser().equals(compItem.getFromImUser())
					    )
					) {
						if (item.getAddTime() < compItem.getAddTime()) {
							item = compItem;
							break;
						}
					}
			}
			System.out.println(item.getFromImUser() + "---" + item.getToImUser());
			hasImHistory.add(item);
			
		}
		
		
		//最后循环所有该用户的列表，展现出每个用户最后跟当前用户的最后一条聊天记录
		List<Long> ids = new ArrayList<Long>();
		for (Iterator<UserRefSec> iterator = userImList.iterator(); iterator.hasNext();) {

			UserRefSec userRefSec = (UserRefSec) iterator.next();
			ids.add(userRefSec.getUserId());
		}

		ObjectMapper mapper = new ObjectMapper();
		
		List<Users> u = userService.selectVoByUserId(ids);
		
		for (Iterator<Users> iterator = u.iterator(); iterator.hasNext();) {

			Users user = (Users) iterator.next();
			UserViewVo voItem = userService.getUserInfo(user.getId());	
			
			UserImVo vo = new UserImVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(voItem, vo);
			
			String imContentStr = "";
			String lastIm = "";
			String lastImTimeStr = "";
			Long lastImTime = 0L;
			for (Iterator<UserImHistory> itImList = hasImHistory.iterator(); itImList.hasNext();) {
				UserImHistory im = (UserImHistory)itImList.next();
				
				System.out.println(vo.getImSecUsername() +" == " + im.getFromImUser() + "-----" + vo.getImUsername() +" == " + im.getToImUser());

				if ( 
						(vo.getImSecUsername().equals(im.getFromImUser()) && 
						 vo.getImUsername().equals(im.getToImUser())
						)
						||
						(vo.getImSecUsername().equals(im.getToImUser()) && 
						 vo.getImUsername().equals(im.getFromImUser())
						)						
				   ) {
					
					imContentStr = im.getImContent();
					
					if (imContentStr == null) continue;
					
					 try {
						JsonNode contentJsonNode = mapper.readValue(imContentStr, JsonNode.class);
						lastIm = getImMsg(contentJsonNode);
						if (im.getAddTime() > 0L) {
							lastImTime = im.getAddTime();
							Date lastImTimeDate = TimeStampUtil.timeStampToDateFull(im.getAddTime() * 1000, DateUtil.DEFAULT_FULL_PATTERN);
							
							lastImTimeStr = DateUtil.fromToday(lastImTimeDate);
						}
						break;
					 } catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
			vo.setLastIm(lastIm);
			vo.setLastImTime(lastImTime);
			vo.setLastImTimeStr(lastImTimeStr);
			
			
			result.add(vo);
		}
		
		//最后按照时间先后顺序进行排序.
		SortList<UserImVo> sortList = new SortList<UserImVo>();  
		
		sortList.Sort(result, "getLastImTime", "desc");  
		return result;
		
	}
	
	
	
	/**
	 * 插入聊天记录信息
	 */
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
	          
	          String content = "";
	          if (data.get(i).get("payload") != null) {
	        	  content = data.get(i).get("payload").toString();
	        	  content = convertToUtf8mb4(content);
	          }
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
	
	private String getImMsg(JsonNode contentJsonNode) {
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