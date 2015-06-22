package com.simi.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import com.meijia.utils.AppSubmit;
import com.meijia.wx.utils.XMLUtil;

/**
 * 微信支付订单
 *
 * @author jing
 *
 */
public class WxNotifyOrderSenior extends HttpServlet {

	/**
    *
    */
	private static final long serialVersionUID = 1L;
	private String pay_sign_key; // 微信支付申请人邮箱获取

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获取请求数据

		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
//		System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
		outSteam.close();
		inStream.close();
		String result = new String(outSteam.toByteArray(), "utf-8");
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			map = XMLUtil.doXMLParse(result);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try {
			Map<String,String> params = new HashMap<String,String>();
			//用户手机号
			//通知ID
			String notify_id = new String(map.get("nonce_str").toString().getBytes("ISO-8859-1"),"UTF-8");
	
			params.put("notify_id", notify_id);
	
			//通知时间
			String notify_time = new String(map.get("time_end").toString().getBytes("ISO-8859-1"),"UTF-8");
	
			params.put("notify_time", notify_time);
	
			//商户订单号
			String out_trade_no = new String(map.get("out_trade_no").toString().getBytes("ISO-8859-1"),"UTF-8");
	
			params.put("senior_order_no", out_trade_no);
	
			//买家账号
			String open_id  = new String(map.get("openid").toString().getBytes("ISO-8859-1"),"UTF-8");
			params.put("pay_account", open_id );
	
			//支付宝交易号
			String trade_no = new String(map.get("transaction_id").toString().getBytes("ISO-8859-1"),"UTF-8");
	
			params.put("trade_no", trade_no);
	
			//交易状态
			String trade_status = new String(map.get("result_code").toString().getBytes("ISO-8859-1"),"UTF-8");
	
			params.put("trade_status", trade_status);
	
			//支付方式 0 = 余额支付 1 = 支付宝 2 = 微信支付 3 = 智慧支付 4 = 上门刷卡（保留，站位）
			params.put("pay_type", "2");
	
			//通知参数
			//params.put("notify_params", notify_params.toString());
			System.out.println(params.toString());
			String url = "http://localhost/simi/app/user/senior_online_pay.json";
			
			System.out.println(url);
			AppSubmit.appAliay(url, params);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getOutputStream().write("success".getBytes());

	}
}
