package com.simi.service.impl.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardCommentService;
import com.simi.service.card.CardService;
import com.simi.service.card.CardZanService;
import com.simi.service.user.UsersService;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.CardViewVo;
import com.simi.vo.card.CardZanViewVo;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.Users;
import com.github.pagehelper.PageHelper;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.card.CardsMapper;

@Service
public class CardsServiceImpl implements CardService {
	@Autowired
	CardsMapper cardsMapper;
	
	@Autowired
	UsersService usersService;

	@Autowired
	CardAttendService cardAttendService;

	@Autowired
	CardZanService cardZanService;
	
	@Autowired
	CardCommentService cardCommentService;	
	
	@Override
	public Cards initCards() {
		Cards record = new Cards();
		record.setCardId(0L);
		record.setCreateUserId(0L);
		record.setUserId(0L);
		record.setCardType((short) 0);
		record.setServiceTime(0L);
		record.setServiceContent("");
		record.setSetRemind((short) 0);
		record.setSetNowSend((short) 0);
		record.setSetSecDo((short) 0);
		record.setSetSecRemarks("");
		record.setTicketType((short) 0);
		record.setTicketFromCityId(0L);
		record.setTicketToCityId(0L);
		record.setStatus((short) 1);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}
	
	/**
	 * 转换card 对象为 cardViewVo对象
	 * @param card
	 * @return
	 */
	@Override
	public CardViewVo changeToCardViewVo(Cards card) {
		CardViewVo vo = new CardViewVo();
		if (card == null) return vo;
		Long cardId = vo.getCardId();
		
		//进行对象复制.
		BeanUtilsExp.copyPropertiesIgnoreNull(card, vo);
		
		//获取到参会、参与人员列表
		List<CardAttend> attends = cardAttendService.selectByCardId(cardId);
		vo.setAttends(attends);
		
		//获取用户名称
		Users createUser = usersService.getUserInfo(vo.getCreateUserId());
		if (createUser != null) vo.setCreateUserName(createUser.getName());
		
		Users u = usersService.getUserInfo(vo.getUserId());
		if (u != null) vo.setCreateUserName(u.getName());
		
		//统计赞的数量
		int totalZan = cardZanService.totalByCardId(cardId);
		vo.setTotalZan(totalZan);
		
		//统计评论的数量
		int totalComment = cardCommentService.totalByCardId(cardId);
		vo.setTotalComment(totalComment);
		
		//获得点赞前十个用户及头像.
		List<CardZanViewVo> zanTop10 = cardZanService.getByTop10(cardId);
		vo.setZanTop10(zanTop10);
		return vo;
	}
	
	/**
	 * 批量转换card 对象为 cardViewVo对象
	 * @param List<card>
	 * @return
	 */
	@Override
	public List<CardViewVo> changeToCardViewVoBat(List<Cards> cards) {
		List<CardViewVo> result = new ArrayList<CardViewVo>();
		if (cards.isEmpty()) return result;
		
		List<Long> cardIds = new ArrayList<Long>();
		List<Long> createUserIds = new ArrayList<Long>();
		List<Long> userIds = new ArrayList<Long>();
		
		for (Cards item : cards) {
			cardIds.add(item.getCardId());
			createUserIds.add(item.getCreateUserId());
			userIds.add(item.getUserId());
		}
		
		List<Users> createUsers = new ArrayList<Users>();
		if (!createUserIds.isEmpty()) {
			createUsers = usersService.selectByUserIds(createUserIds);
		}
		
		List<Users> users = new ArrayList<Users>();
		if (!userIds.isEmpty()) {
			users = usersService.selectByUserIds(userIds);
		}
		
		
		List<CardAttend> cardAttends = new ArrayList<CardAttend>();
		List<HashMap> totalCardZans = new ArrayList<HashMap>();
		List<HashMap> totalCardComments = new ArrayList<HashMap>();
		
		if (!cardIds.isEmpty()) {
			cardAttends = cardAttendService.selectByCardIds(cardIds);
			totalCardZans = cardZanService.totalByCardIds(cardIds);
			totalCardComments = cardCommentService.totalByCardIds(cardIds);
		}
		
		Cards item = null;
		for (int i = 0; i < cards.size(); i++) {
			item = cards.get(i);
			CardViewVo vo = new CardViewVo();
			//进行对象复制.
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			//获取到参会、参与人员列表
			List<CardAttend> cardAttendItems = new ArrayList<CardAttend>();
			for (CardAttend cardAttend : cardAttends) {
				if (cardAttend.getCardId().equals(vo.getCardId())) {
					cardAttendItems.add(cardAttend);
				}
			}
			vo.setAttends(cardAttendItems);
			
			//获取创建用户名称
			for (Users u : users) {
				if (u.getId().equals(vo.getUserId())) {
					vo.setCreateUserName(u.getName());
					break;
				}
			}
			
			//获取创建用户名称
			for (Users createUser : createUsers) {
				if (createUser.getId().equals(vo.getCreateUserId())) {
					vo.setCreateUserName(createUser.getName());
					break;
				}
			}			
			
			//统计评论的数量
			vo.setTotalZan(0);
			for (HashMap totalCardComment : totalCardComments) {
				Long tmpCardId = Long.valueOf(totalCardComment.get("card_id").toString());
				if (tmpCardId.equals(vo.getCardId())) {
					vo.setTotalZan(Integer.parseInt(totalCardComment.get("total").toString()));
				}
			}
			
			//统计赞的数量
			vo.setTotalComment(0);
			for (HashMap totalCardZan : totalCardZans) {
				Long tmpCardId = Long.valueOf(totalCardZan.get("card_id").toString());
				if (tmpCardId.equals(vo.getCardId())) {
					vo.setTotalZan(Integer.parseInt(totalCardZan.get("total").toString()));
				}
			}		
			
			//获得点赞前十个用户及头像.
			vo.setZanTop10(new ArrayList<CardZanViewVo>());
			
			result.add(vo);
		}
		
		return result;
	}	
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return cardsMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insert(Cards record) {
		return cardsMapper.insert(record);
	}
	
	@Override
	public int insertSelective(Cards record) {
		return cardsMapper.insertSelective(record);
	}

	@Override
	public Cards selectByPrimaryKey(Long id) {
		return cardsMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<Cards> selectByListPage(CardSearchVo vo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		
		List<Cards> list = cardsMapper.selectByListPage(vo);
		
		return list;
	}	

	@Override
	public int updateByPrimaryKey(Cards record) {
		return cardsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Cards record) {
		return cardsMapper.updateByPrimaryKeySelective(record);
	}

}