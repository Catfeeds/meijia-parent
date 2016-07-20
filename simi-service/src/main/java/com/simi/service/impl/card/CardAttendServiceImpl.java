package com.simi.service.impl.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.card.CardAttendService;
import com.simi.service.user.UserFriendReqService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UsersService;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.LinkManVo;
import com.simi.vo.user.UserFriendSearchVo;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.user.UserFriendReq;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.Users;
import com.simi.po.dao.card.CardAttendMapper;

@Service
public class CardAttendServiceImpl implements CardAttendService {
	
	@Autowired
	CardAttendMapper cardAttendMapper;
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private UsersAsyncService usersAsyncService;	
	
	@Autowired
	private UserFriendService userFriendService;
	
	@Autowired
	private UserFriendReqService userFriendReqService;

	@Override
	public CardAttend initCardAttend() {
		CardAttend record = new CardAttend();

		record.setId(0L);
		record.setCardId(0L);
		record.setMobile("");
		record.setName("");
		record.setLocalAlarm((short) 0);
		record.setLastAlarmTime(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}	
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return cardAttendMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(CardAttend record) {
		return cardAttendMapper.insert(record);
	}
	
	@Override
	public int insertSelective(CardAttend record) {
		return cardAttendMapper.insertSelective(record);
	}
	
	@Override
	public List<CardAttend> selectByCardId(Long cardId) {
		return  cardAttendMapper.selectByCardId(cardId);
	}
	
	@Override
	public List<CardAttend> selectByCardIds(List<Long> cardIds) {
		return  cardAttendMapper.selectByCardIds(cardIds);
	}	
	
	@Override
	public List<CardAttend> selectBySearchVo(CardSearchVo searchVo) {
		return  cardAttendMapper.selectBySearchVo(searchVo);
	}	

	@Override
	public int deleteByCardId(Long cardId) {
		return cardAttendMapper.deleteByCardId(cardId);
	}	
	
	@Override
	public int updateByPrimaryKey(CardAttend record) {
		return cardAttendMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(CardAttend record) {
		return cardAttendMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public boolean doCardAttend(Long cardId, Long userId, String attends) {
		
		Gson gson = new Gson();
		List<LinkManVo> linkManList = gson.fromJson(attends, new TypeToken<List<LinkManVo>>(){}.getType() ); 
		System.out.println(linkManList.toString());
		if (linkManList != null) {
			
			//先删除掉后再新增
			this.deleteByCardId(cardId);
			LinkManVo item = null;
			for (int i = 0; i < linkManList.size(); i++) {
//				System.out.println(linkManList.get(i).toString());
				item = linkManList.get(i);
				String mobile = item.getMobile();
				mobile = mobile.replaceAll(" ", "");  
				
				Long newUserId = 0L;
				newUserId = item.getUser_id();
				
				if (newUserId == null || newUserId.equals(0L)) {
					if (StringUtil.isEmpty(mobile)) continue;
					if (!RegexUtil.isMobile(mobile)) continue;
				}
				
				if (!StringUtil.isEmpty(mobile)) {
					if (!RegexUtil.isMobile(mobile)) continue;
				}
				
//				mobile = RegexUtil.checkMobile(mobile);
				
				//根据手机号找出对应的userID, 如果没有则直接新增用户.
				
				Users newUser = null;
				
				if (newUserId != null && newUserId > 0L) {
					newUser = userService.selectByPrimaryKey(newUserId);
				} else {
					newUser = userService.selectByMobile(mobile);
				}
				
				if (newUser == null) {
					newUser = userService.genUser(mobile, item.getName(), (short) 3, "");					
					usersAsyncService.genImUser(newUser.getId());
				}
				
				newUserId = newUser.getId();
				
				newUserId = newUser.getId();
				CardAttend cardAttend = this.initCardAttend();
				cardAttend.setCardId(cardId);
				cardAttend.setUserId(newUserId);
				cardAttend.setMobile(mobile);
				cardAttend.setName(item.getName());
				this.insert(cardAttend);

				// 如果不是好友，则自动发出好友请求.
				
				if (userId.equals(newUserId)) continue;
				
				UserFriendSearchVo searchVo = new UserFriendSearchVo();
				searchVo.setUserId(userId);
				searchVo.setFriendId(newUserId);
				UserFriends userFriend = userFriendService.selectByIsFirend(searchVo);	
				UserFriendReq userFriendReq = userFriendReqService.selectByIsFirend(searchVo);
				
				if (userFriend == null && userFriendReq == null) {
					userFriendReq = userFriendReqService.initUserFriendReq();
					userFriendReq.setUserId(newUserId);
					userFriendReq.setFriendId(userId);
					userFriendReq.setAddTime(TimeStampUtil.getNowSecond());
					userFriendReq.setUpdateTime(TimeStampUtil.getNowSecond());
					userFriendReqService.insert(userFriendReq);
				}
				
				//统计新用户卡片数
				usersAsyncService.statUser(newUserId, "totalCards");
			}
		}
		
		
		return true;
	}

}