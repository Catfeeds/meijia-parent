package com.simi.service.impl.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.meijia.utils.ChartUtil;
import com.meijia.utils.MathDoubleUtil;
import com.meijia.utils.StringUtil;
import com.simi.po.dao.order.OrdersMapper;
import com.simi.po.dao.user.UserLoginedMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.service.chart.UserChartService;
import com.simi.vo.chart.ChartDataVo;
import com.simi.vo.chart.ChartSearchVo;

/**
 * @description：
 * @author： kerryg
 * @date:2015年9月15日 
 */
@Service
public class UserChartServiceImpl implements UserChartService {

	@Autowired
	private UsersMapper usersMapper;

	/**
	 * 统计用户数
	 */
	@Override
	public int statTotalUser(ChartSearchVo chartSearchVo) {
		int totalUser = 0;

		totalUser = usersMapper.statTotalUser(chartSearchVo);

		return totalUser;
	}
}
