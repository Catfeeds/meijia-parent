package com.simi.po.dao.card;

import java.util.HashMap;
import java.util.List;
import com.simi.po.model.card.Cards;
import com.simi.vo.card.CardSearchVo;

public interface CardsMapper {
    int deleteByPrimaryKey(Long cardId);

    Long insert(Cards record);

    int insertSelective(Cards record);

    Cards selectByPrimaryKey(Long cardId);

    int updateByPrimaryKeySelective(Cards record);

    int updateByPrimaryKey(Cards record);
    
    List<Cards> selectByListPage(CardSearchVo searchVo);

	List<Cards> selectByUserListPage(CardSearchVo vo);
	
	List<Cards> selectByMineListPage(CardSearchVo vo);
	
	List<Cards> selectByAttendListPage(CardSearchVo vo);
	
	List<Cards> selectBySearchVo(CardSearchVo vo);
	
	List<Cards> selectByUserSearchVo(CardSearchVo vo);
	
	List<Cards> selectByMineSearchVo(CardSearchVo vo);
	
	List<Cards> selectByAttendSearchVo(CardSearchVo vo);	

	List<HashMap> totalByMonth(CardSearchVo vo);

	List<Cards> selectByReminds(CardSearchVo vo);

	List<Cards> selectByRemindAll(CardSearchVo vo);	
	
	int updateFinishByOvertime();

	List<Cards> selectListByAddtimeTwo();

	List<Cards> selectListByAddtimeThirty();

	
}