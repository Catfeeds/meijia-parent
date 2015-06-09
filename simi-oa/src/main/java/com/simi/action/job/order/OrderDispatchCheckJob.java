package com.simi.action.job.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simi.action.BaseController;
import com.simi.service.order.OrderQueryService;
import com.simi.service.order.OrdersService;

/**
 * 提醒订单已付款，但是超过两个小时未派工的
 *
 * 执行频率  每小时第10分钟执行
 * @author kerry
 *
 */
@Controller
public class OrderDispatchCheckJob extends BaseController {
	@Autowired
	private OrdersService ordersService;

	@Autowired
    private OrderQueryService orderQueryService;

	public void remindCheck(){
//		//查询所用已支付状态的订单 order_status = 2
//		List<Orders> list = orderQueryService.queryOrdersByState(Constants.ORDER_STATS_2_PAID);
//System.out.println("我的邮件提醒");
//		SendMailUtil sendMailUtil = new SendMailUtil();
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
//		StringBuffer htmlContent = new StringBuffer("<h1>表格</h1><br><table border='1'>"
//													 + "<tr align='center'>"
//													 + "<td>序号</td><td>订单号</td>"
//													 + "<td>服务类型</td><td>用户手机号</td>"
//													 + "<td>服务开始时间</td><td>服务结束时间</td>"
//													 + "<td>服务状态</td></tr>");
//		StringBuffer subject = new StringBuffer("有");
//		Long  addTime = TimeStampUtil.getNow();
//		Long  now = TimeStampUtil.getNow();
//		int count = 0;
//		String serviceType = null;
//		Short serviceTypeId = 0;
//		String date = null;
//		String time = null;
//		String endTime = null;
//		Long startTime = 0L;
//		Long serviceTime = 0L;
//		for (Orders orders : list) {
//			addTime = orders.getAddTime();
//			//判断是否存在已经支付但未派工超过两个小时的订单
//			if(addTime-now/6000>=120){
//				count++;
//		        serviceTypeId = orders.getServiceType();
//				if(serviceTypeId==1){
//					serviceType = "做饭";
//				}else if (serviceTypeId==2) {
//					serviceType = "洗衣";
//				}else if (serviceTypeId==3) {
//					serviceType = "家电清洗";
//				}else if (serviceTypeId==4) {
//					serviceType = "保洁";
//				}else if (serviceTypeId==5) {
//					serviceType = "擦玻璃";
//				}else if (serviceTypeId==6) {
//					serviceType = "管道疏通";
//				}else{
//					serviceType = "新居开荒";
//				}
//				date = sdf1.format(new Date(orders.getServiceDate()*1000));
//				time = sdf2.format(orders.getStartTime()*1000);
//				startTime =orders.getStartTime();
//			    serviceTime = (long) (orders.getServiceHours()*3600);
//				endTime = sdf2.format((serviceTime+startTime)*1000);
//				htmlContent.append("<tr align='center'><td>"+orders.getId()+"</td>"
//		        								 + "<td>"+orders.getOrderNo()+"</td>"
//		        								 + "<td>"+serviceType+"</td>"
//		        								 + "<td>"+orders.getMobile()+"</td>"
//		        								 + "<td>"+date+" "+time+"</td>"
//		        								 + "<td>"+date+" "+endTime+"</td>"
//		        								 + "<td>"+"2=已付款，未派工"+"</td></tr>");
//			}
//		}
//			subject.append(count+"个订单超过2个小时未派工");
//			htmlContent.append("</table>");
//			sendMailUtil.sendHtml(Constants.TO_EMAIL, subject.toString(), htmlContent.toString());
	}
}
