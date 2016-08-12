package com.simi.action.app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.push.PushUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/pushtest")
public class PushTestController extends BaseController {
	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserPushBindService userPushBindService;

	@RequestMapping(value = "push-setclock", method = RequestMethod.GET)
	public AppResultData<Object> pushSetClock(@RequestParam("card_id") Long cardId,
			@RequestParam(value = "client_id", required = false, defaultValue = "b10510a6a8d000fb024af47271f8a49f") String clientId,

			@RequestParam(value = "device_type", required = false, defaultValue = "ios") String deviceType) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cid", clientId);

		HashMap<String, String> tranParams = new HashMap<String, String>();

		Long time1 = TimeStampUtil.getNowSecond() + 120;

		String timeStr1 = TimeStampUtil.timeStampToDateStr(time1, "MM-dd HH:mm");

		String timeStr = time1.toString();
		tranParams.put("is", "true");
		tranParams.put("ac", "s");
		tranParams.put("ci", cardId.toString());
		tranParams.put("ct", "1");
		tranParams.put("st", timeStr);
		tranParams.put("re", timeStr);
		tranParams.put("rt", "会议安排");
		tranParams.put("rc", timeStr1 + "请参加华北区电话会议,请提前10分钟进入会议室B11");

		// JsonObject jsonParams = JsonUtil.mapTojson(tranParams);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonParams = objectMapper.writeValueAsString(tranParams);
		System.out.println(jsonParams);

		params.put("transmissionContent", jsonParams);

		if (deviceType.equals("ios")) {
			PushUtil.IOSPushToSingle(params, "notification");
		}

		if (deviceType.equals("android")) {
			PushUtil.AndroidPushToSingle(params);
		}

		return result;
	}

	@RequestMapping(value = "push-alarm", method = RequestMethod.GET)
	public AppResultData<Object> pushAlarm(@RequestParam("card_id") Long cardId,
			@RequestParam(value = "client_id", required = false, defaultValue = "b10510a6a8d000fb024af47271f8a49f") String clientId,

			@RequestParam(value = "device_type", required = false, defaultValue = "ios") String deviceType) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cid", clientId);

		HashMap<String, String> tranParams = new HashMap<String, String>();

		Long time1 = TimeStampUtil.getNowSecond();

		String timeStr1 = TimeStampUtil.timeStampToDateStr(time1, "MM-dd HH:mm");

		String timeStr = time1.toString();
		tranParams.put("is", "true");
		tranParams.put("ac", "a");
		tranParams.put("ci", cardId.toString());
		tranParams.put("ct", "1");
		tranParams.put("st", timeStr);
		tranParams.put("re", timeStr);
		tranParams.put("rt", "会议安排");
		tranParams.put("rc", timeStr1 + "请参加华北区电话会议,请提前10分钟进入会议室B11");
				
		// JsonObject jsonParams = JsonUtil.mapTojson(tranParams);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonParams = objectMapper.writeValueAsString(tranParams);
		System.out.println(jsonParams);

		params.put("transmissionContent", jsonParams);

		if (deviceType.equals("ios")) {
			PushUtil.IOSPushToSingle(params, "notification");
		}

		if (deviceType.equals("android")) {
			PushUtil.AndroidPushToSingle(params);
		}

		return result;
	}

	@RequestMapping(value = "push-msg", method = RequestMethod.GET)
	public AppResultData<Object> pushMsg(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "category", required = false, defaultValue = "app") String category,
			@RequestParam(value = "action", required = false, defaultValue = "qa") String action,
			@RequestParam(value = "params", required = false, defaultValue = "") String gparams,
			@RequestParam(value = "goto_url", required = false, defaultValue = "") String gotoUrl) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		// 发送推送消息（接受者）
		UserPushBind userPushBind = userPushBindService.selectByUserId(userId);
		
		if (userPushBind == null) return result;
		
		String clientId = userPushBind.getClientId();
		String deviceType = userPushBind.getDeviceType();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cid", clientId);

		HashMap<String, String> tranParams = new HashMap<String, String>();

		Long time1 = TimeStampUtil.getNow();

		String timeStr1 = TimeStampUtil.timeStampToDateStr(time1, "MM-dd HH:mm");

		tranParams.put("is", "true");
		tranParams.put("ac", "m");
		tranParams.put("ci", "0");
		tranParams.put("ct", "0");
		tranParams.put("st", "");
		tranParams.put("re", "0");
		tranParams.put("rt", "新消息");
		tranParams.put("rc", "你在" + timeStr1 + "有一条新的消息");
		
		//跳转信息
		tranParams.put("ca", category);
		tranParams.put("aj", action);
		tranParams.put("pa", gparams);
		tranParams.put("go", gotoUrl);

		// JsonObject jsonParams = JsonUtil.mapTojson(tranParams);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonParams = objectMapper.writeValueAsString(tranParams);
		System.out.println(jsonParams);

		params.put("transmissionContent", jsonParams);

		if (deviceType.equals("ios")) {
			PushUtil.IOSPushToSingle(params, "notification");
		}

		if (deviceType.equals("android")) {
			PushUtil.AndroidPushToSingle(params);
		}

		return result;
	}

	@RequestMapping(value = "car-msg", method = RequestMethod.GET)
	public AppResultData<Object> cardMsg(
			@RequestParam(value = "client_id", required = false, defaultValue = "b10510a6a8d000fb024af47271f8a49f") String clientId,
			@RequestParam(value = "device_type", required = false, defaultValue = "ios") String deviceType) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		List<UserPushBind> exists = userPushBindService.selectByClientId(clientId);
		if (exists.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("client_id 不存在");
			return result;
		}
		
		UserPushBind userBind = exists.get(0);
		if (!userBind.getDeviceType().equals(deviceType)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("设备类型不正确。");
			return result;
		}
		
		
		
		Long userId = userBind.getUserId();
		Users u = userService.selectByPrimaryKey(userId);
		String mobile = u.getMobile();
		BigDecimal b = u.getRestMoney();
		String restMoney = MathBigDecimalUtil.round2(b);
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cid", clientId);

		HashMap<String, String> tranParams = new HashMap<String, String>();

		Long time1 = TimeStampUtil.getNow();

		String timeStr1 = TimeStampUtil.timeStampToDateStr(time1, "yyyy-MM-dd HH:mm:ss");
				
		tranParams.put("is", "false");
		tranParams.put("ac", "car-msg");
		tranParams.put("cn", "京EX9603");
		tranParams.put("car_color", "白色");
		tranParams.put("mobile", mobile);
		tranParams.put("ocx_time", timeStr1);
		tranParams.put("order_money", "10元");
		tranParams.put("rest_money", restMoney);
		tranParams.put("user_id", userId.toString());
		tranParams.put("cap_img", "http://img.bolohr.com/8afe58fcc9dc9dafb302a560841f48be");
		tranParams.put("remind_content", "");
		

		// JsonObject jsonParams = JsonUtil.mapTojson(tranParams);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonParams = objectMapper.writeValueAsString(tranParams);
		System.out.println(jsonParams);

		params.put("transmissionContent", jsonParams);

		if (deviceType.equals("ios")) {
			PushUtil.IOSPushToSingle(params, "notification");
		}

		if (deviceType.equals("android")) {
			PushUtil.AndroidPushToSingle(params);
		}

		return result;
	}
}
