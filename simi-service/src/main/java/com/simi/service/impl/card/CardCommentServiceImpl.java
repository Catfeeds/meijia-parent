package com.simi.service.impl.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.card.CardService;
import com.simi.service.card.CardCommentService;
import com.simi.service.user.UsersService;
import com.simi.vo.card.CardCommentViewVo;
import com.simi.vo.card.CardSearchVo;
import com.simi.po.model.card.CardComment;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.Users;
import com.github.pagehelper.PageHelper;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.card.CardCommentMapper;
import com.simi.po.dao.card.CardsMapper;

@Service
public class CardCommentServiceImpl implements CardCommentService {
	@Autowired
	CardCommentMapper cardCommentMapper;
	
	@Autowired
	UsersService usersService;	
	
	@Override
	public CardComment initCardComment() {
		CardComment record = new CardComment();
		record.setCardId(0L);
		record.setCardId(0L);
		record.setUserId(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	@Override
	public CardComment selectByPrimaryKey(Long id) {
		return cardCommentMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public CardComment selectBySearchCardVo(CardSearchVo vo) {
		return cardCommentMapper.selectBySearchCardVo(vo);
	}
	
	@Override
	public List<CardComment> selectByListPage(CardSearchVo vo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		
		List<CardComment> list = cardCommentMapper.selectByListPage(vo);
		
		return list;
	}	
	
	@Override
	public List<CardCommentViewVo> changeToCardComments(List<CardComment> cardComments) {
		
		List<CardCommentViewVo> result = new ArrayList<CardCommentViewVo>();
		
		if (cardComments.isEmpty()) return result;
		
		List<Long> userIds = new ArrayList<Long>();
		for(CardComment item : cardComments) {
			userIds.add(item.getUserId());
		}
		
		List<Users> users = new ArrayList<Users>();
		if (!userIds.isEmpty()) {
			users = usersService.selectByUserIds(userIds);
		}
		
		CardComment item = null;
		for (int i =0; i < cardComments.size(); i++) {
			item = cardComments.get(i);
			CardCommentViewVo vo = new CardCommentViewVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			Users u = null;
			for (int j = 0; j < users.size(); j++) {
				u = users.get(i);
				if (u.getId().equals(vo.getUserId())) {
					vo.setName(u.getName());
					vo.setHeadImg(u.getHeadImg());
					break;
				}
			}
			result.add(vo);
		}
		
		return result;
	}		
	
	@Override
	public int totalByCardId(Long cardId) {
		return cardCommentMapper.totalByCardId(cardId);
	}
	
	@Override
	public List<HashMap> totalByCardIds(List<Long> cardIds) {
		return cardCommentMapper.totalByCardIds(cardIds);
	}	

	@Override
	public int insertSelective(CardComment record) {
		return cardCommentMapper.insertSelective(record);
	}	
	
	@Override
	public Long insert(CardComment record) {
		return cardCommentMapper.insert(record);
	}	
	
	@Override
	public int updateByPrimaryKey(CardComment record) {
		return cardCommentMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(CardComment record) {
		return cardCommentMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return cardCommentMapper.deleteByPrimaryKey(id);
	}	

}