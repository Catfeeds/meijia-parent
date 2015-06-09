package com.simi.action.app.promotion;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;


import com.simi.po.model.promotion.Channel;
import com.simi.service.promotion.ChannelService;
import com.simi.vo.AppResultData;
import com.simi.vo.StatVo;

@Controller
@RequestMapping(value = "/interface-promotion")
public class ChannelInterface extends BaseController {


    @Autowired
    private ChannelService channelService;
	/**
	 * 检查服务类型名称是否重复
	 * @param request
	 * @param model
	 * @param
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "check-token-dumplicate", method = RequestMethod.POST)
	public  AppResultData<Boolean> checkToken(
			@RequestParam(value = "token", required = true, defaultValue = "") String token


			) {

		AppResultData<Boolean> result = new AppResultData<Boolean>(
		Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, false);
		Channel record = channelService.selectByToken(token);

		if(record != null && record.getId() < 0){
			result.setMsg("名称已经存在");
			result.setData(true);
		}else{
			result.setData(false);
		}
		return result;
	}
	
	@RequestMapping(value = "stat-total", method = RequestMethod.POST)
	public  AppResultData<Map> statTotal() {

		AppResultData<Map> result = new AppResultData<Map>(
		Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, null);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//总体统计
		List<StatVo> list = new ArrayList<StatVo>();
		StatVo vo1 = new StatVo();
		vo1.setName("1月");
		vo1.setValue("10");

		StatVo vo2 = new StatVo();
		vo2.setName("2月");
		vo2.setValue("20");
		
		StatVo vo3 = new StatVo();
		vo3.setName("3月");
		vo3.setValue("30");
		
		StatVo vo4 = new StatVo();
		vo4.setName("4月");
		vo4.setValue("40");		

		list.add(vo1);
		list.add(vo2);
		list.add(vo3);
		list.add(vo4);
		
		//线上
		List<StatVo> listOnline = new ArrayList<StatVo>();
		StatVo onelineVo1 = new StatVo();
		onelineVo1.setName("1月");
		onelineVo1.setValue("4");

		StatVo onelineVo2 = new StatVo();
		onelineVo2.setName("2月");
		onelineVo2.setValue("2");
		
		StatVo onelineVo3 = new StatVo();
		onelineVo3.setName("3月");
		onelineVo3.setValue("3");
		
		StatVo onelineVo4 = new StatVo();
		onelineVo4.setName("4月");
		onelineVo4.setValue("8");		

		listOnline.add(onelineVo1);
		listOnline.add(onelineVo2);
		listOnline.add(onelineVo3);
		listOnline.add(onelineVo4);		
		
		//线下
		List<StatVo> listOffline = new ArrayList<StatVo>();
		StatVo offlineVo1 = new StatVo();
		offlineVo1.setName("1月");
		offlineVo1.setValue("16");

		StatVo offlineVo2 = new StatVo();
		offlineVo2.setName("2月");
		offlineVo2.setValue("18");
		
		StatVo offlineVo3 = new StatVo();
		offlineVo3.setName("3月");
		offlineVo3.setValue("17");
		
		StatVo offlineVo4 = new StatVo();
		offlineVo4.setName("4月");
		offlineVo4.setValue("12");		

		listOffline.add(offlineVo1);
		listOffline.add(offlineVo2);
		listOffline.add(offlineVo3);
		listOffline.add(offlineVo4);
		
		resultMap.put("total", list);
		resultMap.put("totalOnline", listOnline);
		resultMap.put("totalOffline", listOffline);
		result.setData(resultMap);
		return result;
	}

}
