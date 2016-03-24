package com.meijia.utils.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.TimeStampUtil;
import com.gexin.rp.sdk.base.IQueryResult;
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
	 * @param pushType   notification or  alertClock   是发生消息和闹钟提醒， 区别为设置的声音不一样.
	 */
	public static boolean IOSPushToSingle(HashMap<String, String> params, String pushType) throws Exception {
		
		String cid = "";
		String transmissionContent = "";
		
		if (params.containsKey("cid")) 
			cid = params.get("cid").toString();
		
		if (params.containsKey("transmissionContent")) 
			transmissionContent = params.get("transmissionContent").toString();
		System.out.println("推送消息debug======================");
		System.out.println(params.toString());
		IGtPush push = new IGtPush(pushHost, appKey, masterSecret);
		
		String userStatus = getUserStatus(cid);
		
		TransmissionTemplate template = TransmissionTemplateIos(userStatus, transmissionContent, pushType);
		
		System.out.println(template.toString());
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(72 * 1000 * 3600);
		message.setData(template);
			
		Target target1 = new Target();
		target1.setAppId(appId);
		target1.setClientId(cid);

		try {
			IPushResult ret = push.pushMessageToSingle(message, target1);
			System.out.println("================推送信息====================");
			System.out.println("push cid = " + cid);
			System.out.println("正常：" + ret.getResponse().toString());
			
		} catch (RequestException e) {
			String requstId = e.getRequestId();
			IPushResult ret = push.pushMessageToSingle(message, target1,
					requstId);

			System.out.println("异常：" + ret.getResponse().toString());
		}

		return true;
	}	
	
//	/**
//	 * 推送android 多个设备透传消息推送
//	 * @Param map<String, String> Params
//	 *     key = transmissionContent
//	 * 
//	 */
//	public static boolean IosPushToMulti(List<String> clientIds, HashMap<String, String> params) throws Exception {
//		
//		String transmissionContent = "";
//
//		if (params.containsKey("transmissionContent")) 
//			transmissionContent = params.get("transmissionContent").toString();
//		
//		
//		IGtPush push = new IGtPush(pushHost, appKey, masterSecret);
//		
//		TransmissionTemplate template = TransmissionTemplateIos();
//
//		template.setTransmissionContent(transmissionContent);
//		
//		ListMessage message = new ListMessage();
//		message.setOffline(true);
//		message.setOfflineExpireTime(2 * 1000 * 3600);
//		message.setData(template);
//		
//		List<Target> targets = new ArrayList<Target>();
//		
//		for (String cid : clientIds) {
//			Target target1 = new Target();
//			target1.setAppId(appId);
//			target1.setClientId(cid);
//			targets.add(target1);
//		}
//		
//		String taskId = push.getContentId(message);
//		IPushResult ret = push.pushMessageToList(taskId, targets);
//		System.out.println("正常：" + ret.getResponse().toString());
//				
//		return true;
//	}				
	
	/**
	 * 推送android 单个设备透传消息推送
	 * @Param map<String, String> Params
	 *     key = cid 
	 *     key = title
	 *     key = msgContent
	 *     key = transmissionContent
	 * 
	 */
	public static boolean AndroidPushToSingle(HashMap<String, String> params) throws Exception {
		
		String cid = "";
		String transmissionContent = "";
		
		if (params.containsKey("cid")) 
			cid = params.get("cid").toString();

		if (params.containsKey("transmissionContent")) 
			transmissionContent = params.get("transmissionContent").toString();
		
		System.out.println("==============android推送setclock参数begin=============================");
		System.out.println(params.toString());
		
		
		IGtPush push = new IGtPush(pushHost, appKey, masterSecret);
		
		TransmissionTemplate template = TransmissionTemplateDefault();

		template.setTransmissionContent(transmissionContent);
		
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(72 * 1000 * 3600);
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
		
		System.out.println("==============android推送setclock参数end=============================");
		return true;
	}			
	

	/**
	 * 推送android 多个设备透传消息推送
	 * @Param map<String, String> Params
	 *     key = cid 
	 *     key = title
	 *     key = msgContent
	 *     key = transmissionType
	 *     key = transmissionContent
	 * 
	 */
	public static boolean AndroidToMulti(List<String> clientIds, HashMap<String, String> params) throws Exception {
		
		String transmissionContent = "";

		if (params.containsKey("transmissionContent")) 
			transmissionContent = params.get("transmissionContent").toString();
		
		
		IGtPush push = new IGtPush(pushHost, appKey, masterSecret);
		
		TransmissionTemplate template = TransmissionTemplateDefault();

		template.setTransmissionContent(transmissionContent);
		
		ListMessage message = new ListMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(2 * 1000 * 3600);
		message.setData(template);
		
		List<Target> targets = new ArrayList<Target>();
		
		for (String cid : clientIds) {
			Target target1 = new Target();
			target1.setAppId(appId);
			target1.setClientId(cid);
			targets.add(target1);
		}
		
		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targets);
		System.out.println("正常：" + ret.getResponse().toString());
				
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
	
	/**
	 * 
	 * @param userStatus           用户状态  Online   Offline
	 * @param transmissionContent  透传消息内容
	 * @param pushType   notification or  alertClock   是发生消息和闹钟提醒， 区别为设置的声音不一样.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static TransmissionTemplate TransmissionTemplateIos(String userStatus, String transmissionContent, String pushType)
			throws Exception {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionType(1);
		template.setTransmissionContent("");
		//在线的情况
//		if (userStatus.equals("Online")) {
			template.setTransmissionContent(transmissionContent);
//		}


		
			//************APN高级推送*******************//
			APNPayload apnpayload = new APNPayload();
			apnpayload.setBadge(1);
//			apnpayload.setSound("");
			apnpayload.setContentAvailable(1);
//			apnpayload.setCategory("cardView");
			
			if (pushType.equals("alertClock")) {
				apnpayload.setSound("simivoice.caf");
			}
			APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
			
			
//			if (userStatus.equals("Offline")) {
				apnpayload.addCustomMsg("a", transmissionContent);
				
				HashMap<String, String> tranParams = GsonUtil.GsonToObject(transmissionContent, HashMap.class);
				String isShow = tranParams.get("is_show").toString().trim();
				String remindContent = tranParams.get("remind_content").toString();
				if (isShow.equals("true")) {
					alertMsg.setBody(remindContent);
				}
//			}

			apnpayload.setAlertMsg(alertMsg);
			template.setAPNInfo(apnpayload);
		
		
		return template;
	}	
	
	public static String getUserStatus(String cid) {
		String status = "";
	    IGtPush push = new IGtPush(pushHost, appKey, masterSecret);
	    IQueryResult abc = push.getClientIdStatus(appId, cid);
	    status = abc.getResponse().get("result").toString().trim();
	    return status;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) 
			throws Exception {
		
		String clientId = "fc883ba0ec0a77e38b26d2b59e1a1676";
		
		getUserStatus(clientId);
		
		
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cid", clientId);
		
		/*透传消息格式
		 *  title : 标题   :  通知栏标题
		 *  text  : 内容   :  通知栏内容
		 *  
		 *  说明： title 和 text 应该是成对出现，如果没有 title,text ,则只会有 todo操作的功能，只有
		 *  title和text都有值的情况下，才会弹出提示框信息
		 *  
		 *  todo  : 后续操作   get_reminds  = 需要获取闹钟信息列表.
		 *   
		 *  
		 */
		 HashMap<String, String> tranParams = new HashMap<String, String>();
		 
		 Long time1 = TimeStampUtil.getMillisOfDayFull("2015-12-21 14:43:00");
		 String timeStr = time1.toString();
		 tranParams.put("is_show", "true");		
		 tranParams.put("action", "alarm");		
		 tranParams.put("card_id", "0");
		 tranParams.put("card_type", "0");
		 tranParams.put("service_time", "0");
		 tranParams.put("remind_time", "0");
		 tranParams.put("remind_title", "事务提醒");
		 tranParams.put("remind_content", "测试声音 	new-mail.caf");
		 tranParams.put("sound", "new-mail");
		 tranParams.put("sound_id", "1000");
		 
		 String jsonParams = GsonUtil.GsonString(tranParams);
		 
//		 tranParams = GsonUtil.GsonToObject(jsonParams, HashMap.class);
//		 
//		 System.out.println("is_show=" + tranParams.get("is_show").toString());

//		 
		params.put("transmissionContent", jsonParams);
		PushUtil.AndroidPushToSingle(params);
		
//		PushUtil.IOSPushToSingle(params, "alertClock");
		
//		params = new HashMap<String, String>();
//		params.put("cid", clientId);
//		params.put("title", "测试推送消息");
//		params.put("msgContent", "会议安排安排");
//		PushUtil.AndroidPushNotificationToSingle(params);

	}	

}
