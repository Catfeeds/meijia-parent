package com.simi.service.impl.async;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyAdmin;
import com.simi.service.async.UserScoreAsyncService;
import com.simi.service.card.CardService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyAdminService;
import com.simi.utils.CardUtil;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.xcloud.CompanyAdminSearchVo;
import com.simi.vo.xcloud.CompanySearchVo;

@Service
public class UserScoreAsyncServiceImpl implements UserScoreAsyncService {

	@Autowired
	public UsersService usersService;
	
	@Autowired
	public UserDetailScoreService userDetailScoreService;
	
	@Autowired
	private XCompanyService xCompanyService;
	
	@Autowired
	private XcompanyAdminService xCompanyAdminService;
	
	@Autowired
	private CardService cardService;
	
	/**
	 * 异步积分明细记录
	 */
	@Async
	@Override
	public Future<Boolean> sendScore(Long userId, Integer score, String action, String params, String remarks) {
		
		Users u = usersService.selectByPrimaryKey(userId);
		
		if (u == null) return new AsyncResult<Boolean>(true);
		
		//记录积分明细
		UserDetailScore record = userDetailScoreService.initUserDetailScore();
		record.setUserId(userId);
		record.setMobile(u.getMobile());
		record.setScore(score);
		record.setAction(action);
		record.setParams(params);
		record.setRemarks(remarks);
		
		userDetailScoreService.insert(record);
		
		//更新总积分
		u.setScore(u.getScore() + score);
		
		
		if (!action.equals("order")) {
			u.setExp(u.getExp() + score);
		}
		usersService.updateByPrimaryKeySelective(u);
		
		return new AsyncResult<Boolean>(true);
	}
	
	/**
	 * 异步积分明细记录，判断创建公司每日上限
	 */
	@Async
	@Override
	public Future<Boolean> sendScoreCompany(Long userId, Long companyId) {
		
		Users u = usersService.selectByPrimaryKey(userId);
		
		if (u == null) return new AsyncResult<Boolean>(true);
		
		String mobile = u.getMobile();
		Long startTime = TimeStampUtil.getBeginOfToday();
		Long endTime = TimeStampUtil.getEndOfToday();
		
		CompanyAdminSearchVo searchVo = new CompanyAdminSearchVo();
		searchVo.setUserId(userId);
		searchVo.setIsCreate((short) 1);
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		
		List<XcompanyAdmin> rs = xCompanyAdminService.selectBySearchVo(searchVo);
		if (rs.size() > 300) return new AsyncResult<Boolean>(true);
		
		Integer score = 300;
		
		return sendScore(userId, score, "company_reg", companyId.toString(), "创建企业/团队");
		
	}

	/**
	 * 异步积分明细记录，判断创建卡片每日上限
	 */
	@Async
	@Override
	public Future<Boolean> sendScoreCard(Long userId, Long cardId, Short cardType) {
		
		Users u = usersService.selectByPrimaryKey(userId);
		
		if (u == null) return new AsyncResult<Boolean>(true);
		
		Long startTime = TimeStampUtil.getBeginOfToday();
		Long endTime = TimeStampUtil.getEndOfToday();
		
		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setUserId(userId);
		searchVo.setCardType(cardType);
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		searchVo.setCardFrom((short) 1);
		List<Cards> rs = cardService.selectBySearchVo(searchVo);
		
		if (cardType.equals((short)3)) {
			if (rs.size() > 200) return new AsyncResult<Boolean>(true);
		} else {
			if (rs.size() > 10) return new AsyncResult<Boolean>(true);
		}
		Integer score = 10;
		
		String cardTypeName = CardUtil.getCardTypeName(cardType);
		String action = CardUtil.getCardAction(cardType);
		
		return sendScore(userId, score, action, cardId.toString(), cardTypeName);
	}
	
	/**
	 * 消费积分
	 */
	@Async
	@Override
	public Future<Boolean> consumeScore(Long userId, Integer score, String action, String params, String remarks) {
		
		Users u = usersService.selectByPrimaryKey(userId);
		
		if (u == null) return new AsyncResult<Boolean>(true);
		
		//记录积分明细
		UserDetailScore record = userDetailScoreService.initUserDetailScore();
		record.setUserId(userId);
		record.setMobile(u.getMobile());
		record.setScore(score);
		record.setAction(action);
		record.setParams(params);
		record.setIsConsume((short) 1);
		record.setRemarks(remarks);
		
		userDetailScoreService.insert(record);
		
		//更新总积分
		u.setScore(u.getScore() - score);
		usersService.updateByPrimaryKeySelective(u);
		
		return new AsyncResult<Boolean>(true);
	}
	
}
