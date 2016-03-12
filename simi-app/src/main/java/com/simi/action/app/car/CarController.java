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
import com.meijia.utils.MathBigDeciamlUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.push.PushUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.card.CardComment;
import com.simi.po.model.card.CardLog;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardCommentService;
import com.simi.service.card.CardLogService;
import com.simi.service.card.CardService;
import com.simi.service.card.CardZanService;
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

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "cap", method = {RequestMethod.POST }) 
    @ResponseBody  
    public void doCap(@RequestBody String content) throws JsonParseException, JsonMappingException, IOException { 
        
		System.out.println("pre_content = " + content);
		content = URLDecoder.decode(content);
		System.out.println("after_content = " + content);
		
		JSONObject dataJson;

		try {
			dataJson = new JSONObject(content);
			JSONObject alarmInfoPlate = dataJson.getJSONObject("AlarmInfoPlate");
			JSONObject result = alarmInfoPlate.getJSONObject("result");
			JSONObject plateResult = result.getJSONObject("PlateResult");
			String carNo = plateResult.getString("license");
			String colorType = plateResult.getString("colorType");
			
			String carColor = getCarColor(colorType);
			
			System.out.print("车牌号：" + carNo);
			String imagePath = plateResult.getString("imagePath");
			imagePath = "http://192.168.1.108" + imagePath;
			System.out.println("imagePath = " + imagePath);
			
			Long userId = 99L;
			Users u = userService.selectByPrimaryKey(userId);
			String mobile = u.getMobile();
			BigDecimal b = u.getRestMoney();
			String restMoney = MathBigDeciamlUtil.round2(b);
			
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("cid", "4a4c5176135534bfaf0067313002bb7e");

			HashMap<String, String> tranParams = new HashMap<String, String>();

			Long time1 = TimeStampUtil.getNow();

			String timeStr1 = TimeStampUtil.timeStampToDateStr(time1, "yyyy-MM-dd HH:mm:ss");
					
			tranParams.put("is_show", "false");
			tranParams.put("action", "car-msg");
			tranParams.put("car_no", carNo);
			tranParams.put("car_color", carColor);
			tranParams.put("mobile", mobile);
			tranParams.put("ocx_time", timeStr1);
			tranParams.put("order_money", "10元");
			tranParams.put("rest_money", restMoney);
			tranParams.put("user_id", userId.toString());
			tranParams.put("cap_img", "http://img.51xingzheng.cn/8afe58fcc9dc9dafb302a560841f48be");
			tranParams.put("remind_content", "");
			
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonParams = objectMapper.writeValueAsString(tranParams);
			System.out.println(jsonParams);

			params.put("transmissionContent", jsonParams);

			
			try {
				PushUtil.IOSPushToSingle(params, "notification");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			userId = 77L;
			u = userService.selectByPrimaryKey(userId);
			mobile = u.getMobile();
			b = u.getRestMoney();
			restMoney = MathBigDeciamlUtil.round2(b);
			
			params = new HashMap<String, String>();
			params.put("cid", "15d2bee6caad6d57c8f14290256b1ec5");

			tranParams = new HashMap<String, String>();
					
			tranParams.put("is_show", "false");
			tranParams.put("action", "car-msg");
			tranParams.put("car_no", carNo);
			tranParams.put("car_color", carColor);
			tranParams.put("mobile", mobile);
			tranParams.put("ocx_time", timeStr1);
			tranParams.put("order_money", "10元");
			tranParams.put("rest_money", restMoney);
			tranParams.put("user_id", userId.toString());
			tranParams.put("cap_img", "http://img.51xingzheng.cn/8afe58fcc9dc9dafb302a560841f48be");
			tranParams.put("remind_content", "");
			
			objectMapper = new ObjectMapper();
			jsonParams = objectMapper.writeValueAsString(tranParams);


			params.put("transmissionContent", jsonParams);

			try {
				PushUtil.AndroidPushToSingle(params);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    } 
	
	@RequestMapping(value = "reg", method = {RequestMethod.POST }) 
    @ResponseBody  
    public void regist(HttpServletRequest request)  { 
		
		String deviceName = request.getParameter("device_name");
		
		System.out.println("deviceName = " + deviceName);
	}
	
	
	public String getCarColor(String colorType) {
		String carColor = "";
		switch (colorType) {
			case "0" :
				carColor = "未知";
				break;
			case "1" :
				carColor = "蓝色";
				break;
			case "2" :
				carColor = "黄色";
				break;
			case "3" :
				carColor = "白色";
				break;
			case "4" :
				carColor = "黑色";
				break;	
			case "5" :
				carColor = "绿色";
				break;	
		}
		return carColor;
	}

}
