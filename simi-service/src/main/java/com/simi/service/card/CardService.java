package com.simi.service.card;

import java.util.HashMap;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.card.Cards;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.CardViewVo;



public interface CardService {

	int deleteByPrimaryKey(Long id);

	Long insert(Cards record);

	Cards initCards();

	int insertSelective(Cards record);

	Cards selectByPrimaryKey(Long id);

	int updateByPrimaryKey(Cards record);

	int updateByPrimaryKeySelective(Cards record);

	CardViewVo changeToCardViewVo(Cards card);

	PageInfo selectByListPage(CardSearchVo vo, int pageNo, int pageSize);

	List<CardViewVo> changeToCardViewVoBat(List<Cards> cards);

	List<HashMap> totalByMonth(CardSearchVo vo);

	List<Cards> selectByReminds(CardSearchVo vo);

	boolean updateFinishByOvertime();

	List<Cards> selectListByAddtimeTwo();

	List<Cards> selectListByAddtimeThirty();

	List<Cards> selectByRemindAll(CardSearchVo vo);

	CardViewVo getWeatherCard(String serviceDate, String lat, String lng);

	CardViewVo initCardView();

	

}
