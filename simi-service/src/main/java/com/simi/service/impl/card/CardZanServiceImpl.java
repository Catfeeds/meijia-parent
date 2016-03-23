package com.simi.service.impl.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.card.CardZanService;
import com.simi.service.user.UsersService;
import com.simi.vo.card.CardZanViewVo;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.po.model.card.CardZan;
import com.simi.po.model.user.Users;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.card.CardZanMapper;

@Service
public class CardZanServiceImpl implements CardZanService {
	@Autowired
	CardZanMapper cardZanMapper;
	
	@Autowired
	UsersService usersService;
	
	@Override
	public CardZan initCardZan() {
		CardZan record = new CardZan();
		record.setCardId(0L);
		record.setCardId(0L);
		record.setUserId(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	@Override
	public CardZan selectByPrimaryKey(Long id) {
		return cardZanMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public CardZan selectBySearchCardVo(CardSearchVo vo) {
		return cardZanMapper.selectBySearchCardVo(vo);
	}
	
	@Override
	public int totalByCardId(Long cardId) {
		return cardZanMapper.totalByCardId(cardId);
	}
	
	@Override
	public List<HashMap> totalByCardIds(List<Long> cardIds) {
		return cardZanMapper.totalByCardIds(cardIds);
	}	
	
	@Override
	public List<CardZanViewVo> getByTop10(Long cardId) {
		
		List<CardZanViewVo> result = new ArrayList<CardZanViewVo>();
		
		List<CardZan> cardzans = cardZanMapper.getByTop10(cardId);
		List<Long> userIds = new ArrayList<Long>();
		if (!cardzans.isEmpty()) {
			CardZan item = null;
			for (int i = 0; i < cardzans.size(); i++) {
				item = cardzans.get(i);
				userIds.add(item.getUserId());
			}
			
			List<Users> userList = new ArrayList<Users>();
			if (userIds.size() > 0) {
				UserSearchVo searchVo = new UserSearchVo();
				searchVo.setUserIds(userIds);
				userList = usersService.selectBySearchVo(searchVo);
			}
			
			for (int i = 0; i < cardzans.size(); i++) {
				
				CardZanViewVo vo = new CardZanViewVo();
				item = cardzans.get(i);
				BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
				Users u = null;
				for (int j = 0; j < userList.size(); j++) {
					u = userList.get(j);
					if (vo.getUserId().equals(u.getId())) {
						vo.setName(u.getName());
						vo.setHeadImg(usersService.getHeadImg(u));
						break;
					}
				}
				result.add(vo);
			}
		}
		
		return result;
	}

	@Override
	public int insertSelective(CardZan record) {
		return cardZanMapper.insertSelective(record);
	}	
	
	@Override
	public Long insert(CardZan record) {
		return cardZanMapper.insert(record);
	}	
	
	@Override
	public int updateByPrimaryKey(CardZan record) {
		return cardZanMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(CardZan record) {
		return cardZanMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return cardZanMapper.deleteByPrimaryKey(id);
	}	

}