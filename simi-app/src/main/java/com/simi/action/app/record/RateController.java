package com.simi.action.app.record;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.MobileUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.record.RecordRates;
import com.simi.po.model.user.Users;
import com.simi.service.async.RecordAsyncService;
import com.simi.service.record.RecordRatesService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.record.RecordRateSearchVo;
import com.simi.vo.record.RecordRateVo;

@Controller
@RequestMapping(value = "/app/record")
public class RateController extends BaseController {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private RecordRatesService recordRatesService;
	
	@Autowired
	private RecordAsyncService recordAsyncService;
	
	@RequestMapping(value = "post_rate", method = RequestMethod.POST)
	public AppResultData<Object> postRate(
			@RequestParam("rate_type") Short rateType,  // 0 = 订单  1 = 服务商
			@RequestParam("link_id") Long linkId,
			@RequestParam(value = "user_id",required = false,defaultValue = "0") Long userId,
			@RequestParam(value = "name",required = false,defaultValue = "") String name,
			@RequestParam("rate") Short rate,
			@RequestParam("rate_content") String rateContent){
		
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		if (userId.equals(0L) && StringUtil.isEmpty(name)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("评价人为空.");
			return result;
		}
		
		if (userId > 0L && StringUtil.isEmpty(name)) {
			Users u = userService.selectByPrimaryKey(userId);
			name = u.getName();
			if (StringUtil.isEmpty(name)) name = MobileUtil.getMobileStar(u.getMobile());
		}
		
		RecordRates record = recordRatesService.initRecordRates();
		
		record.setRateType(rateType);
		record.setLinkId(linkId);
		record.setUserId(userId);
		record.setName(name);
		record.setRate(rate);
		record.setRateContent(rateContent);
		
		recordRatesService.insertSelective(record);
		
		//对于服务人员评价需要算出总的平均值.
		if (rateType.equals((short)1)) {
			recordAsyncService.avgRate(rateType, linkId);
		}
		
		return result;
	}
	
	@RequestMapping(value = "get_rates", method = RequestMethod.GET)
	public AppResultData<Object> getRate(
			@RequestParam("rate_type") Short rateType,
			@RequestParam("link_id") Long linkId,
			@RequestParam(value = "page",required = false,defaultValue = "1") int page){
		
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		RecordRateSearchVo searchVo = new RecordRateSearchVo();
		searchVo.setRateType(rateType);
		searchVo.setLinkId(linkId);
		
		PageInfo pageInfo = recordRatesService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<RecordRates> list = pageInfo.getList();
		List<RecordRateVo> vos = new ArrayList<RecordRateVo>();
		if (list.isEmpty()) return result;
		
		
		for (int i = 0; i < list.size(); i++) {
			RecordRates item = list.get(i);
			
			RecordRateVo vo = new RecordRateVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			Long userId = item.getUserId();
			String name = item.getName();
			
			Users u = null;
			
			if (userId > 0L)  u = userService.selectByPrimaryKey(userId);
			
			
			if (StringUtil.isEmpty(name) && u != null) {
				name = u.getName();
				if (StringUtil.isEmpty(name)) name = MobileUtil.getMobileStar(u.getMobile());
			}
			
			String headImg = Constants.DEFAULT_HEAD_IMG;
			
			if (userId  > 0L) {
				headImg = u.getHeadImg();
			}
			
			vo.setName(name);
			vo.setHeadImg(headImg);
			
			vo.setAddTimeStr(TimeStampUtil.fromTodayStr(vo.getAddTime() * 1000));
			vos.add(vo);
			
		}
		
		result.setData(vos);
		
		return result;
	}
}
