package com.simi.service.impl.chart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.po.dao.user.UsersMapper;
import com.simi.service.chart.UserChartService;
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
	public int statByTotalUser(ChartSearchVo chartSearchVo) {
		int totalUser = 0;

		totalUser = usersMapper.statByTotalUser(chartSearchVo);

		return totalUser;
	}
}
