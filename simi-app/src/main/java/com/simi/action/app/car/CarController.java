package com.simi.action.app.car;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.push.PushUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.card.CardComment;
import com.simi.po.model.card.CardLog;
import com.simi.po.model.card.Cards;
import com.simi.po.model.order.OrderLog;
import com.simi.po.model.order.OrderPrices;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserCar;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.async.NoticeSmsAsyncService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardCommentService;
import com.simi.service.card.CardLogService;
import com.simi.service.card.CardService;
import com.simi.service.card.CardZanService;
import com.simi.service.order.OrderLogService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserCarService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UsersService;
import com.simi.utils.CardUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.card.CardCommentViewVo;
import com.simi.vo.card.CardListVo;
import com.simi.vo.card.CardRemindVo;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.CardViewVo;

@Controller
@RequestMapping(value = "/app/car")
public class CarController extends BaseController {
	@Autowired
	private UsersService userService;

	@Autowired
	private UserCarService userCarService;
	
	@Autowired
	private UserPushBindService userPushBindService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderPricesService orderPricesService;
	
	@Autowired
	private OrderLogService orderLogService;
	
	@Autowired
	private NoticeSmsAsyncService noticeSmsAsyncService;
	
	@Autowired
	private NoticeAppAsyncService noticeAppAsyncService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	@Autowired
	private UserDetailPayService userDetailPayService;

	@SuppressWarnings({ "deprecation" })
	@RequestMapping(value = "cap", method = { RequestMethod.POST })
	@ResponseBody
	public String doCap(@RequestBody String content) throws JsonParseException, JsonMappingException, IOException {
		
		
		System.out.println("pre_content = " + content);
		content = URLDecoder.decode(content);
		System.out.println("after_content = " + content);

		JSONObject dataJson;

		try {
			dataJson = new JSONObject(content);
			JSONObject alarmInfoPlate = dataJson.getJSONObject("AlarmInfoPlate");
			JSONObject resultJons = alarmInfoPlate.getJSONObject("result");
			JSONObject plateResult = resultJons.getJSONObject("PlateResult");
			String carNo = plateResult.getString("license");
			String colorType = plateResult.getString("colorType");

			String carColor = getCarColor(colorType);
			
//			System.out.print("车牌号：" + carNo);
//			String imagePath = plateResult.getString("imagePath");
//			imagePath = "http://192.168.1.108" + imagePath;
//			System.out.println("imagePath = " + imagePath);			
			
			carNo = carNo.trim();
			UserCar userCar = userCarService.selectByCarNo(carNo);
			
			if (userCar == null) return "";
			
			Long userId = userCar.getUserId();
			
			Users u = userService.selectByPrimaryKey(userId);
			
			BigDecimal orderMoney = new BigDecimal(5.0);//原价
			BigDecimal orderPay = new BigDecimal(5.0);//折扣价
			//查询用户余额
			if(u.getRestMoney().compareTo(orderPay) < 0) {
				return "";
			}
			//扣除用户余额
			u.setRestMoney(u.getRestMoney().subtract(orderPay));
			u.setUpdateTime(TimeStampUtil.getNowSecond());
			userService.updateByPrimaryKeySelective(u);
			
			Long serviceTypeId = 258L;
			//生成订单扣款
			PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
			
			// 调用公共订单号类，生成唯一订单号
	    	Orders order = null;
	    	String orderNo = "";
	    
	    	orderNo = String.valueOf(OrderNoUtil.genOrderNo());
			order = ordersService.initOrders();
			
			order.setOrderNo(orderNo);
			order.setServiceTypeId(serviceTypeId);
			order.setUserId(userId);
			order.setMobile(u.getMobile());
			order.setServiceContent(serviceType.getName());

			order.setOrderStatus(Constants.ORDER_TYPE_2);
			
			String remarks = serviceType.getName() + "扣款" + MathBigDecimalUtil.round2(orderMoney) + "元";
 			order.setRemarks(remarks);
			
			ordersService.insert(order);
			Long orderId = order.getOrderId();
			
			//记录订单日志.
			OrderLog orderLog = orderLogService.initOrderLog(order);
			orderLogService.insert(orderLog);
			
			//保存订单价格信息
			OrderPrices orderPrice = orderPricesService.initOrderPrices();
			

			orderPrice.setOrderId(orderId);
			orderPrice.setOrderNo(orderNo);
			orderPrice.setUserId(userId);
			orderPrice.setMobile(u.getMobile());
			orderPrice.setOrderMoney(orderMoney);
			orderPrice.setOrderPay(orderPay);
			orderPricesService.insert(orderPrice);
			
			//记录用户消费明细
			userDetailPayService.userDetailPayForOrder(u, order, orderPrice, "", "", "");
			
			//发送用户短信
			noticeSmsAsyncService.noticeOrderCarUser(orderId);
			
			//生成用户首页消息信息
			userMsgAsyncService.newActionAppMsg(userId, orderId, "expy", serviceType.getName(), remarks);
			
			//推送信息
			String capImg = "http://img.51xingzheng.cn/8afe58fcc9dc9dafb302a560841f48be";
			noticeAppAsyncService.pushMsgToExpr(orderId, carNo, carColor, capImg);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	@RequestMapping(value = "reg", method = { RequestMethod.POST })
	@ResponseBody
	public void regist(HttpServletRequest request) {

		String deviceName = request.getParameter("device_name");

		System.out.println("deviceName = " + deviceName);
	}

	public String getCarColor(String colorType) {
		String carColor = "";
		switch (colorType) {
		case "0":
			carColor = "未知";
			break;
		case "1":
			carColor = "蓝色";
			break;
		case "2":
			carColor = "黄色";
			break;
		case "3":
			carColor = "白色";
			break;
		case "4":
			carColor = "黑色";
			break;
		case "5":
			carColor = "绿色";
			break;
		}
		return carColor;
	}

}