package com.simi.service.card;

import java.util.HashMap;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.card.Cards;
import com.simi.vo.card.CardListVo;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.CardViewVo;
import com.simi.vo.card.CardVo;



public interface CardService {

	int deleteByPrimaryKey(Long id);

	Long insert(Cards record);

	Cards initCards();

	int insertSelective(Cards record);

	Cards selectByPrimaryKey(Long id);

	int updateByPrimaryKey(Cards record);

	int updateByPrimaryKeySelective(Cards record);

	CardViewVo changeToCardViewVo(Cards card);

	PageInfo selectByUserListPage(CardSearchVo vo, int pageNo, int pageSize);

	List<HashMap> totalByMonth(CardSearchVo vo);

	List<Cards> selectByReminds(CardSearchVo vo);

	boolean updateFinishByOvertime();

	List<Cards> selectListByAddtimeTwo();

	List<Cards> selectListByAddtimeThirty();

	List<Cards> selectByRemindAll(CardSearchVo vo);

	CardViewVo initCardView();

	CardListVo initCardListVo();

	List<CardListVo> changeToCardListVo(List<Cards> cards);

	List<Cards> selectByUserSearchVo(CardSearchVo vo);

	List<Cards> selectBySearchVo(CardSearchVo vo);

	PageInfo selectByListPage(CardSearchVo searchVo, int pageNo, int pageSize);

	CardVo changeToCardVo(Cards item);
}
