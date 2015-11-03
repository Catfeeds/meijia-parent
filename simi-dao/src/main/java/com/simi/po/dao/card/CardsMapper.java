package com.simi.po.dao.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	List<HashMap> totalByMonth(CardSearchVo vo);

	List<Cards> selectByReminds(CardSearchVo vo);

	List<Cards> selectByOverTime(Object v);

	int updateFinishByOvertime();

	List<Cards> selectListByAddtimeTwo();

	List<Cards> selectListByAddtimeThirty();
}