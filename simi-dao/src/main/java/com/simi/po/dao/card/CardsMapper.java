package com.simi.po.dao.card;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.card.Cards;
import com.simi.vo.card.CardSearchVo;

public interface CardsMapper {
    int deleteByPrimaryKey(Long cardId);

    Long insert(Cards record);

    int insertSelective(Cards record);

    Cards selectByPrimaryKey(Long cardId);

    int updateByPrimaryKeySelective(Cards record);

    int updateByPrimaryKey(Cards record);

	List<Cards> selectByListPage(CardSearchVo vo);
	
	List<Cards> selectMineByListPage(CardSearchVo vo);
	
	List<Cards> selectAttendByListPage(CardSearchVo vo);
}