package com.meijia.utils.push;

import java.util.HashMap;
import java.util.Map;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * 百度配置文件
 *
 */
public class PushUtil {
	
	public static String appId = "6t18dZQ2Vn9nv7ozqdl4EA";
	
	public static String appKey = "QjVBVFMC7U8kmpjwnKCtc7";

	public static String appSecret = "tt7n6qd2Gi8sQqWMz0rN45";
	
	public static String masterSecret = "FysNCHYFnQ62dXClQIqAg8";
	
	public static String pushHost = "http://sdk.open.api.igexin.com/apiex.htm";
	
	// 推送消息的有效时间
	public static int msgExpires = 3600;
	
	/**
	 * 推送IOS 多个设备推送
	 * @return
	 * @throws PushClientException 
	 * @throws PushServerException 
	 */
	public static boolean IOSPushNotificationToMultiDevice(
			String[] cids,
			String msgContent, 
			Map<String, String> params)  {

		
		return true;
	}
	
	/**
	 * 推送IOS 单个设备推送
	 * @Param map<String, String> Params
	 *     key = cid 
	 *     key = title
	 *     key = msgContent
	 *     key = transmissionType
	 *     key = transmissionContent
	 * 
	 */
	public static boolean IOSPushNotificationToSingle(HashMap<String, String> params) throws Exception {
		
		String cid = "";
		String title = "";
		String msgContent = "";
		int transmissonType = 2;
		String transmissionContent = "";
		
		if (params.containsKey("cid")) 
			cid = params.get("cid").toString();
		
		if (params.containsKey("title")) 
			title = params.get("title").toString();
		
		if (params.containsKey("msgContent")) 
			msgContent = params.get("msgContent").toString();
		
		if (params.containsKey("transmissonType")) 
			transmissonType = Integer.parseInt(params.get("transmissonType").toString());
		 
		if (params.containsKey("transmissionContent")) 
			transmissionContent = params.get("transmissionContent").toString();
		
		
		IGtPush push = new IGtPush(pushHost, appKey, masterSecret);
		
		TransmissionTemplate template = IosTransmissionTemplatePush();
//		template.setTitle(title);
//		template.setText(msgContent);
		template.setTransmissionType(transmissonType);
		template.setTransmissionContent(transmissionContent);
		
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(2 * 1000 * 3600);
		message.setData(template);
			
		Target target1 = new Target();
		target1.setAppId(appId);
		target1.setClientId(cid);

		try {
			IPushResult ret = push.pushMessageToSingle(message, target1);
			System.out.println("正常：" + ret.getResponse().toString());
			
		} catch (RequestException e) {
			String requstId = e.getRequestId();
			IPushResult ret = push.pushMessageToSingle(message, target1,
					requstId);

			System.out.println("异常：" + ret.getResponse().toString());
		}

		Thread.sleep(3);
		
		
		return true;
	}	
	
	
	/**
	 * 推送IOS 单个设备推送
	 * @Param map<String, String> Params
	 *     key = cid 
	 *     key = title
	 *     key = msgContent
	 *     key = transmissionType
	 *     key = transmissionContent
	 * 
	 */
	public static boolean AndroidPushTransmissionToSingle(HashMap<String, String> params) throws Exception {
		
		String cid = "";
		String title = "";
		String msgContent = "";
		int transmissonType = 2;
		String transmissionContent = "";
		
		if (params.containsKey("cid")) 
			cid = params.get("cid").toString();
		
		if (params.containsKey("title")) 
			title = params.get("title").toString();
		
		if (params.containsKey("msgContent")) 
			msgContent = params.get("msgContent").toString();
		
		if (params.containsKey("transmissonType")) 
			transmissonType = Integer.parseInt(params.get("transmissonType").toString());
		 
		if (params.containsKey("transmissionContent")) 
			transmissionContent = params.get("transmissionContent").toString();
		
		
		IGtPush push = new IGtPush(pushHost, appKey, masterSecret);
		
		TransmissionTemplate template = TransmissionTemplateDefault();

		template.setTransmissionType(transmissonType);
		template.setTransmissionContent(transmissionContent);
		
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(2 * 1000 * 3600);
		message.setData(template);
			
		Target target1 = new Target();
		target1.setAppId(appId);
		target1.setClientId(cid);

		try {
			IPushResult ret = push.pushMessageToSingle(message, target1);
			System.out.println("正常：" + ret.getResponse().toString());
			
		} catch (RequestException e) {
			String requstId = e.getRequestId();
			IPushResult ret = push.pushMessageToSingle(message, target1,
					requstId);

			System.out.println("异常：" + ret.getResponse().toString());
		}

		Thread.sleep(3);
		
		
		return true;
	}		
	
	
	public static TransmissionTemplate TransmissionTemplateDefault()
			throws Exception {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionType(2);
		template.setTransmissionContent("");
//		template.setPushInfo("actionLocKey", 3, "message", "sound", "payload",
//				"locKey", "locArgs", "launchImage");
		return template;
	}	
	
	public static TransmissionTemplate IosTransmissionTemplatePush()
			throws Exception {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionType(1);
		template.setTransmissionContent("");
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
//		template.setPushInfo("", 1, "", "", "", "", "", "");
		
		//**********APN简单推送********//
		APNPayload apnpayload = new APNPayload();
//		com.gexin.rp.sdk.base.payload.APNPayload.SimpleAlertMsg alertMsg = new com.gexin.rp.sdk.base.payload.APNPayload.SimpleAlertMsg(
//				"hahahaha");
//		apnpayload.setAlertMsg(alertMsg);
		apnpayload.setBadge(5);
//		apnpayload.setContentAvailable(1);
//		apnpayload.setCategory("ACTIONABLE");
		template.setAPNInfo(apnpayload);
		
			//************APN高级推送*******************//
//			APNPayload apnpayload = new APNPayload();
//			apnpayload.setBadge(4);
//			apnpayload.setSound("test2.wav");
//			apnpayload.setContentAvailable(1);
//			apnpayload.setCategory("ACTIONABLE");
//			APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
//			alertMsg.setBody("body");
//			alertMsg.setActionLocKey("ActionLockey");
//			alertMsg.setLocKey("LocKey");
//			alertMsg.addLocArg("loc-args");
//			alertMsg.setLaunchImage("launch-image");
//			// IOS8.2以上版本支持
//			alertMsg.setTitle("Title");
//			alertMsg.setTitleLocKey("TitleLocKey");
//			alertMsg.addTitleLocArg("TitleLocArg");
//
//			apnpayload.setAlertMsg(alertMsg);
//			template.setAPNInfo(apnpayload);
		
		
		return template;
	}	
	
	
	
	
	public static void main(String[] args) 
			throws PushClientException,PushServerException {
		
		Map<String, String> params = new HashMap<String, String>();
//		params.put("url", "http://www/yougeguanjia.com/onecare-oa/upload/html/2.html");
		params.put("msgid", "2");
//		String[] channelIds = {"5411241166191134005", "4880573112432126390"};
		String[] channelIds = {"4880573112432126390"};
		String msgContent = "仅售180元，价值158元首席设计师洗剪吹，长短发不限！（另有其他套餐可选）";
		PushUtil.IOSPushNotificationToMultiDevice(channelIds, msgContent, params);

//		BaiduUtil.IOSPushNotificationToAll("测试url2", params);
	}	

}
