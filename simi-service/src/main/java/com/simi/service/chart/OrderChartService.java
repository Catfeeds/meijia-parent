package com.simi.service.chart;

import com.simi.vo.chart.ChartSearchVo;
/**
 *
 * @author :hulj
 * @Date : 2015年8月31日上午10:37:35
 * 
 * 		市场订单图表 、订单收入 图表service
 *
 */
public interface OrderChartService {
	
	int statTotalOrder(ChartSearchVo chartSearchVo);
}
