package com.simi.action.app.user;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.user.Users;
import com.simi.service.promotion.ChannelService;
import com.simi.service.user.UsersService;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.StatVo;

@Controller
@RequestMapping(value = "/interface-user")
public class UserInterface extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private UsersMapper usersMapper;

	@RequestMapping(value = "userStat-total", method = RequestMethod.POST)
	public AppResultData<List> statTotal() {

		AppResultData<List> result = new AppResultData<List>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, null);

		List<HashMap> resultMap = new ArrayList<HashMap>();
		
		//查询数据库统计值
		
		Long startTime = TimeStampUtil.getNowSecond();
		
		//六个月之前的timestamp
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.MONTH, DateUtil.getMonth() - 6);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Long endTime = TimeStampUtil.getMillisOfDate(cal.getTime()) / 1000;
		
		
		HashMap map = new HashMap();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<HashMap> usersList = usersMapper.selectUserStat(map);
		

		// 循环六个月之内的用户
		//获得当前六个月的初始化值
		List<String> list = DateUtil.getLastMonth(DateUtil.getMonth(), 6);
		
		HashMap<String, String> item = null;
		for (int i = 0; i < list.size(); i++) {
			String monthTags = list.get(i);
			String valueString = "0";
			// 把DateUtil中的对应的数据放到usersList中
			for (int j = 0; j < usersList.size(); j++) {
				HashMap userStat = usersList.get(j);
				if (userStat.get("names").equals(monthTags)) {
					valueString = userStat.get("value").toString();
				}

			}
			item = new HashMap<String, String>();
			item.put("names", monthTags);
			item.put("value", valueString);
			resultMap.add(item);
		}
		result.setData(resultMap);
		return result;
		
	}

}
