package com.simi.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.oa.auth.AuthPassport;
import com.simi.service.chart.OrderChartService;
import com.simi.service.chart.UserChartService;
import com.simi.vo.chart.ChartSearchVo;


@Controller
@RequestMapping(value = "/home")
public class HomeController extends AdminController {

	@Autowired
	private UserChartService userChartService;	
	
	@Autowired
	private OrderChartService orderChartService;	
	
    @AuthPassport
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
    	
    	
    	ChartSearchVo chartSearchVo = new ChartSearchVo();
		Long startTime = 0L;
		Long endTime = 0L;
		
		//总用户数
    	int totalUser = userChartService.statTotalUser(chartSearchVo);
    	
    	//总订单数
    	int totalOrder = orderChartService.statTotalOrder(chartSearchVo) ;    	
    	
    	//今日新增用户数
    	String startTimeStr = DateUtil.getBeginOfDay();
    	String endTimeStr = DateUtil.getEndOfDay();
    	startTime = TimeStampUtil.getMillisOfDayFull(startTimeStr) / 1000;
		endTime = TimeStampUtil.getMillisOfDayFull(endTimeStr) / 1000;
    	
		chartSearchVo.setStartTime(startTime);
		chartSearchVo.setEndTime(endTime);
    	int totalUserToday = userChartService.statTotalUser(chartSearchVo);
    	
    	//今日订单数
    	int totalOrderToday = orderChartService.statTotalOrder(chartSearchVo);
    	
    	model.addAttribute("totalUser", totalUser);
    	model.addAttribute("totalUserToday", totalUserToday);
    	model.addAttribute("totalOrder", totalOrder);
    	model.addAttribute("totalOrderToday", totalOrderToday);
    	
        return "home/index";
    }

    @RequestMapping(value = "/notfound")
    public ModelAndView notfound() {

    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("404");

    	return mv;
    }
}
