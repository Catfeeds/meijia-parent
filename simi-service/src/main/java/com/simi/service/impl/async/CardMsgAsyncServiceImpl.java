package com.simi.service.impl.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.UserMsg;
import com.simi.service.async.CardMsgAsyncService;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.simi.utils.CardUtil;
import com.simi.vo.user.UserMsgSearchVo;

@Service
public class CardMsgAsyncServiceImpl implements CardMsgAsyncService {

	@Autowired
	public UsersService usersService;

	@Autowired
	private UserMsgService userMsgService;

	@Autowired
	private CardService cardService;

	@Autowired
	private CardAttendService cardAttendService;


	/**
	 * 新增卡片时发送消息
	 */
	@Async
	@Override
	public Future<Boolean> newCardMsg(Long cardId) {

		Cards card = cardService.selectByPrimaryKey(cardId);
		if (card == null)
			return new AsyncResult<Boolean>(true);

		Long createUserId = card.getCreateUserId();
		// 找出所有需要发送消息人员:
		// 获取到参会、参与人员列表
		List<CardAttend> attends = cardAttendService.selectByCardId(cardId);
		List<Long> userIds = new ArrayList<Long>();

		for (CardAttend item : attends) {
			if (!userIds.contains(item.getUserId())) {
				userIds.add(item.getUserId());
			}
		}

		if (!userIds.contains(createUserId))
			userIds.add(createUserId);

		String serviceContent = card.getServiceContent();
		if (serviceContent != null && serviceContent.length() > 20) {
			serviceContent = serviceContent.substring(0, 20);
		}

		if (serviceContent.length() == 0) {
			serviceContent = "你创建了新的日程安排";
		}

		for (Long uid : userIds) {
			
			//判断是否为已存在，存在则修改.
			UserMsgSearchVo searchVo = new UserMsgSearchVo();
			searchVo.setUserId(uid);
			searchVo.setAction("card");
			searchVo.setParams(cardId.toString());
			
			List<UserMsg> list = userMsgService.selectBySearchVo(searchVo);
			
			if (!list.isEmpty()) {
				for (UserMsg item : list) {
					item.setTitle(CardUtil.getCardTypeName(card.getCardType()));
					item.setServiceTime(card.getServiceTime());
					item.setSummary(serviceContent);
					userMsgService.updateByPrimaryKey(item);
				}
			} else {
				UserMsg record = userMsgService.initUserMsg();
	
				record.setUserId(uid);
				record.setFromUserId(createUserId);
				record.setToUserId(uid);
				record.setCategory("app");
				record.setAction("card");
				record.setParams(cardId.toString());
				record.setGotoUrl("");
				record.setTitle(CardUtil.getCardTypeName(card.getCardType()));
				record.setServiceTime(card.getServiceTime());
				record.setSummary(serviceContent);
				record.setIconUrl(CardUtil.getCardIcon(card.getCardType()));
				userMsgService.insert(record);
			
			}
		}

		return new AsyncResult<Boolean>(true);
	}
}
