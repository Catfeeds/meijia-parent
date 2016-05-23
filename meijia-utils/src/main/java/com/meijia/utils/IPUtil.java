package com.meijia.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class IPUtil {
	public static long getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}

		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}

		return ipToLong(ip);
	}

	public static long ipToLong(String strIP)
	// 将127.0.0.1 形式的IP地址转换成10进制整数，这里没有进行任何错误处理
	{
		int j = 0;
		int i = 0;
		long[] ip = new long[4];
		int position1 = strIP.indexOf(".");
		int position2 = strIP.indexOf(".", position1 + 1);
		int position3 = strIP.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIP.substring(0, position1));
		ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIP.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3]; // ip1*256*256*256+ip2*256*256+ip3*256+ip4
	}

	public static String longToIP(long longIP)
	// 将10进制整数形式转换成127.0.0.1形式的IP地址，在命令提示符下输入ping 3396362403L
	{
		StringBuffer sb = new StringBuffer("");
		sb.append(String.valueOf(longIP >>> 24));// 直接右移24位
		sb.append(".");
		sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16)); // 将高8位置0，然后右移16位
		sb.append(".");
		sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
		sb.append(".");
		sb.append(String.valueOf(longIP & 0x000000FF));
		
		//多了一个点
//		sb.append(".");
		return sb.toString();
	}
	
	
	/**
	 * 淘宝ip查询城市接口
	 * http://ip.taobao.com/instructions.php
	 * String res = "{\"code\":0,\"data\":{\"country\":\"\u4e2d\u56fd\",\"country_id\":\"CN\",\"area\":\"\u534e\u5317\",\"area_id\":\"100000\",\"region\":\"\u5317\u4eac\u5e02\",\"region_id\":\"110000\",\"city\":\"\u5317\u4eac\u5e02\",\"city_id\":\"110100\",\"county\":\"\",\"county_id\":\"-1\",\"isp\":\"\u6559\u80b2\u7f51\",\"isp_id\":\"100027\",\"ip\":\"202.112.96.163\"}}";
	 * @param ip
	 * @return city
	 */
	public static String getCityByIp(String ip) {
		String result = "";
		
		if (StringUtil.isEmpty(ip)) return result;
		
		String url = "http://ip.taobao.com//service/getIpInfo.php";
		Map<String, String> params = new HashMap<String, String>();
		params.put("ip", ip);
		
		String res = HttpClientUtil.get(url, params);
		
		if (StringUtil.isEmpty(res)) return result;
		
		 String jsonString2 = "[" + res + "]";
		JSONArray array = JSONArray.fromObject(jsonString2);
			
		JSONObject jsonObject = array.getJSONObject(0);    
			
		String code = jsonObject.getString("code");
		
		if (code.equals("0")) {
			String data = jsonObject.getString("data");
			
			String dataStr = "[" + data + "]";
			
			JSONArray dataArray = JSONArray.fromObject(dataStr);
			JSONObject dataObject = dataArray.getJSONObject(0);
			
			result = dataObject.getString("city");
		}


		return result;
	}

	public static void main(String[] args) {
		System.out.println("IP地址的各种表现形式：\r\n");
		System.out.print("32位二进制形式：");
		System.out.println(Long.toBinaryString(3396362403L));
		System.out.print("十进制形式：");
		System.out.println(ipToLong("202.112.96.163"));
		System.out.print("普通形式：");
		System.out.println(longToIP(3396362403L));
		
//		System.out.println(IPUtil.getCityByIp("202.112.96.163."));
		
		
		System.out.println(IPUtil.getCityByIp(longToIP(3396362403L)));
		
		
//		String longToIP = IPUtil.longToIP(2130706433L);
//		System.out.println(longToIP);
//		String cityByIp = IPUtil.getCityByIp(longToIP);
//		System.out.println(cityByIp);
	}
}
