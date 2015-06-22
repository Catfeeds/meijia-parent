package com.simi.action.app.order;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.OrderCards;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrderCardsService;
import com.simi.service.order.OrderPricesService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.user.UsersService;
import com.meijia.utils.HttpClientUtil;
import com.meijia.utils.MathBigDeciamlUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;
import com.meijia.wx.utils.MD5Util;
import com.meijia.wx.utils.WxUtil;
import com.meijia.wx.utils.XMLUtil;

@Controller
@RequestMapping(value = "/app/order")
public class OrderWxPayController extends BaseController {
	@Autowired
	private UsersService userService;
	
	@Autowired
    private OrderQueryService orderQueryService;
	
	@Autowired
	OrderPricesService orderPricesService;
	
	@Autowired
	private OrderCardsService orderCardService;

	/**
	 * 下单微信预支付接口
	 * http://mch.weixin.qq.com/wiki/doc/api/app.php?chapter=8_3
	 * http://mch.weixin.qq.com/wiki/doc/api/app.php?chapter=9_1
	 * @param request
	 * @param response
	 * @param mobile   
	 * @param orderNo
	 * @param orderType  // 订单类型 0 = 订单支付 1= 充值卡充值 2 = 管家卡购买
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 * 
	 */
	@RequestMapping(value = "wx_pre", method = RequestMethod.POST)
	public AppResultData<Object> wxPre(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("user_id") Long userId, 
			@RequestParam("order_no") String orderNo,
			@RequestParam("order_type") Short orderType) throws UnsupportedEncodingException {
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.getUserById(userId);
		
		if (u == null) {// 判断是否为注册用户，非注册用户返回 999
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		String mobile = u.getMobile();
		
		String wxPay = "0";
		
		if (orderType.equals(Constants.ORDER_TYPE_0)) {
			Orders order = orderQueryService.selectByOrderNo(orderNo);
			
			if (order == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
				return result;
			}
			
			// 订单已经支付过，不需要重复支付
			if (order.getOrderStatus()
					.equals(Constants.ORDER_STATUS_4_PAY_DONE)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("订单已经支付过！");
				return result;
			}
			
			BigDecimal orderPayNow  = orderPricesService.getPayByOrder(orderNo, "0");
			wxPay = orderPayNow.toString();
		}
		
		//处理充值卡充值的情况
		if (orderType.equals(Constants.ORDER_TYPE_1)) {
			OrderCards orderCard = orderCardService.selectByOrderCardsNo(orderNo);
			
			if (orderCard == null || orderCard.getId().equals(0)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
				return result;
			}

			// 订单已经支付过，不需要重复支付
			if (orderCard.getOrderStatus()
					.equals(Constants.PAY_STATUS_1)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("订单已经支付过！");
				return result;
			}
			
			BigDecimal cardPay = orderCard.getCardPay();
			BigDecimal p1 = new BigDecimal(100);
			BigDecimal p2 = MathBigDeciamlUtil.mul(cardPay, p1);
			BigDecimal orderPayNow = MathBigDeciamlUtil.round(p2, 0);

			wxPay = orderPayNow.toString();
		}		
		
		//处理私密卡购买的情况
		if (orderType.equals(Constants.ORDER_TYPE_2)) {
			OrderCards orderCard = orderCardService.selectByOrderCardsNo(orderNo);
			
			if (orderCard == null || orderCard.getId().equals(0)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
				return result;
			}

			// 订单已经支付过，不需要重复支付
			if (orderCard.getOrderStatus()
					.equals(Constants.PAY_STATUS_1)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("订单已经支付过！");
				return result;
			}
			
			BigDecimal cardPay = orderCard.getCardPay();
			BigDecimal p1 = new BigDecimal(100);
			BigDecimal p2 = MathBigDeciamlUtil.mul(cardPay, p1);
			BigDecimal orderPayNow = MathBigDeciamlUtil.round(p2, 0);

			wxPay = orderPayNow.toString();
		}			

		
		
		//测试
		wxPay = "1";
		
		//基础信息
		String appId = WxUtil.appId;
		String mchId = WxUtil.mchId;
		String wxKey = WxUtil.wxKey;
		String notifyUrl = WxUtil.getNotifyUrl(orderType);
		String openid = "";
		
		Map<String, Object> resultData = new HashMap<String, Object>();

		String timeStamp = TimeStampUtil.getNowSecond().toString();
		String nonceStr = WxUtil.getNonceStr();
					
		
		
		
		

		// 签名参数
		String[] s = new String[10];
		s[0] = "appid=" + appId;
		s[1] = "nonce_str=" + nonceStr;
		s[2] = "body=" + mobile;
		s[3] = "out_trade_no=" + orderNo;
		s[4] = "total_fee=" + wxPay;
		s[5] = "spbill_create_ip=" + request.getRemoteAddr();
		s[6] = "notify_url=" + notifyUrl;
		s[7] = "trade_type=APP";
		s[8] = "mch_id=" + mchId;
		//s[9] = "openid=" + openid;
		s[9] = "attach=" + mobile;
		Arrays.sort(s);
		String sign = "";
		for (String string : s) {
			sign += string + "&";
		}
		sign = MD5Util.MD5Encode(sign + "key=" + wxKey, "GBK")
				.toUpperCase();

		String xml = "";
		xml += "<xml>";
		xml += "<appid>" + appId + "</appid>";
		xml += "<mch_id>" + mchId + "</mch_id>";
		xml += "<nonce_str>" + nonceStr + "</nonce_str>";
		xml += "<sign>" + sign + "</sign>";
		xml += "<body><![CDATA[" + mobile + "]]></body>";
		xml += "<out_trade_no>" + orderNo + "</out_trade_no>";
		xml += "<total_fee>" + wxPay + "</total_fee>";
		xml += "<spbill_create_ip>" + request.getRemoteAddr()
				+ "</spbill_create_ip>";
		xml += "<notify_url>" + notifyUrl
				+ "</notify_url>";
		xml += "<trade_type>APP</trade_type>";
//		xml += "<openid></openid>";
		xml += "<attach><![CDATA[" + mobile + "]]></attach>";
		xml += "</xml>";
		
		
		String reqResult = HttpClientUtil.post_xml(WxUtil.payUrl, xml);
		System.out.println("统一支付订单调用");
		System.out.println(xml);
		System.out.println(reqResult);
		if (reqResult.indexOf("该订单已支付") >=0) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("订单已经支付过！");
			return result;	
		}
		
		String prepayId = "";
		String nonceStr1 = "";
		try {
			Map resultMap = XMLUtil.doXMLParse(reqResult);
			sign = resultMap.get("sign").toString();
			prepayId = resultMap.get("prepay_id").toString();
			nonceStr1 = resultMap.get("nonce_str").toString();
		
		
			if (prepayId.equals("")) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("微信验证失败,请重新支付!");
				return result;	
			}

			String paySign = "";
			String b[] = new String[6];
			b[0] = "appid=" + appId;
			b[1] = "partnerid=" + mchId;
			b[2] = "prepayid=" + prepayId;
			b[3] = "package=Sign=WXPay";
			b[4] = "noncestr=" + nonceStr;
			b[5] = "timestamp=" + timeStamp;			
			Arrays.sort(b);
			for (String string : b) {
				paySign += string + "&";
			}
			String f = paySign + "key=" + wxKey;
			paySign = MD5Util.MD5Encode(f, "GBK").toUpperCase();
			
			resultData.put("userId", userId);
			resultData.put("mobile", mobile);
			resultData.put("appId", appId);
			resultData.put("partnerId", mchId);
			resultData.put("prepayId", prepayId);
			resultData.put("package", "Sign=WXPay");
			resultData.put("timeStamp", timeStamp);
			resultData.put("nonceStr", nonceStr);
			resultData.put("orderNo", orderNo);
			resultData.put("sign", paySign);
			resultData.put("signType", "MD5");
			resultData.put("notifyUrl", notifyUrl);
	
			result.setData(resultData);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
			result.setStatus(Constants.ERROR_999);
			result.setMsg("微信验证失败,请重新支付!");
			return result;	
		}

		return result;
	}
	
	
	// 2. 微信查询接口
	/**
	 * 
	 * @param request
	 * @param response
	 * @param mobile   
	 * @param orderNo
	 * @param orderType  // 订单类型 0 = 订单支付 1= 充值卡充值 2 = 管家卡购买
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "wx_order_query", method = RequestMethod.POST)	 
	public AppResultData<Object> orderQuery(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("user_id") Long userId, 
			@RequestParam("order_no") String orderNo,
			@RequestParam("order_type") Short orderType) throws UnsupportedEncodingException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "SUCCESS");

		Users u = userService.getUserById(userId);
		
		if (u == null) {// 判断是否为注册用户，非注册用户返回 999
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		String mobile = u.getMobile();		
		
		if (orderType.equals("0")) {
			Orders order = orderQueryService.selectByOrderNo(orderNo);
			
			if (order == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
				result.setData("");
				return result;
			}
			
			// 订单已经支付过，不需要重复支付
//			if (order.getOrderStatus()
//					.equals(Constants.ORDER_STATS_2_PAID)) {
//				return result;
//			}
			
		}
		
		//处理充值卡充值的情况
		if (orderType.equals(Constants.ORDER_TYPE_1)) {
			OrderCards orderCard = orderCardService.selectByOrderCardsNo(orderNo);
			
			if (orderCard == null || orderCard.getId().equals(0)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.ORDER_NO_NOT_EXIST_MG);
				result.setData("");
				return result;
			}

			// 订单已经支付过，不需要重复支付
			if (orderCard.getOrderStatus()
					.equals(Constants.PAY_STATUS_1)) {
				return result;
			}
		}
		
		//基础信息
		String appId = WxUtil.appId;
		String mchId = WxUtil.mchId;
		String wxKey = WxUtil.wxKey;
		String notifyUrl = WxUtil.getNotifyUrl(orderType);	
		String orderQueryUrl = WxUtil.orderQueryUrl;
		String openid = "";
		
		Map<String, Object> resultData = new HashMap<String, Object>();

		String timeStamp = TimeStampUtil.getNowSecond().toString();
		String nonceStr = WxUtil.getNonceStr();

		// 签名参数
		String[] s = new String[4];
		s[0] = "appid=" + appId;
		s[1] = "nonce_str=" + nonceStr;
		s[2] = "out_trade_no=" + orderNo;
		s[3] = "mch_id=" + mchId;
		Arrays.sort(s);
		String sign = "";
		for (String string : s) {
			sign += string + "&";
		}
		sign = MD5Util.MD5Encode(sign + "key=" + wxKey, "GBK")
				.toUpperCase();		

		String xml = "";
		xml += "<xml>";
		xml += "<appid>" + appId + "</appid>";
		xml += "<mch_id>" + mchId + "</mch_id>";
		xml += "<nonce_str>" + nonceStr + "</nonce_str>";
		xml += "<out_trade_no>" + orderNo + "</out_trade_no>";
		xml += "<sign>" + sign + "</sign>";		
		xml += "</xml>";
		
		
		String reqResult = HttpClientUtil.post_xml(orderQueryUrl, xml);		
		try {
			Map resultMap = XMLUtil.doXMLParse(reqResult);
			String resultCode = resultMap.get("result_code").toString();
			
			if (!resultCode.equals("SUCCESS")) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("订单支付不成功");
				result.setData("");
				return result;
			}
			
			result.setData(resultCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setStatus(Constants.ERROR_999);
			result.setMsg("订单支付不成功");
			result.setData("");
			return result;
		} 
		
		return result;
		
	}
}
