package com.simi.service.impl.async;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.meijia.utils.MathDoubleUtil;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.service.async.RecordAsyncService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.record.RecordRatesService;
import com.simi.service.user.UsersService;
import com.simi.vo.record.RecordRateSearchVo;

@Service
public class RecordAsyncServiceImpl implements RecordAsyncService {

	@Autowired
	public UsersService usersService;
		
	@Autowired
	private RecordRatesService recordRatesService;
	
	@Autowired
	private PartnerUserService partnerUserService;
	
	/**
	 * 计算总评价数
	 */
	@Async
	@Override
	public Future<Boolean> avgRate(Short rateType, Long linkId) {
		
		int total = 0;
		int totalRate = 0;
		
		RecordRateSearchVo searchVo = new RecordRateSearchVo();
		searchVo.setRateType(rateType);
		searchVo.setLinkId(linkId);
		HashMap resultMap = recordRatesService.totalByLinkId(searchVo);
		
		if (resultMap != null) {
			total = Integer.valueOf(resultMap.get("total").toString());
			totalRate = Integer.valueOf(resultMap.get("total_rate").toString());
		}
		
		total = total + 1;
		totalRate = totalRate + 5;
		
		Double d = MathDoubleUtil.div(Double.valueOf(totalRate), Double.valueOf(total), 2);
		
		System.out.println(d);
		double x = d - Math.floor(d);
		
		System.out.println(x);
		
		if (x > 0 && x < 0.5)  {
			d = Math.floor(d) + 0.5;
		}
		if (x > 0.5) d = Math.floor(d) + 1;
		
		if (d > 5) d = (double) 5;
		
		
		//更新服务人员的总评价
		PartnerUsers partnerUser = partnerUserService.selectByUserId(linkId);
		
		if (partnerUser == null) return new AsyncResult<Boolean>(true);
		
		partnerUser.setTotalRate(new BigDecimal(d));
		
		partnerUserService.updateByPrimaryKey(partnerUser);
		
		return new AsyncResult<Boolean>(true);
	}
	

}
