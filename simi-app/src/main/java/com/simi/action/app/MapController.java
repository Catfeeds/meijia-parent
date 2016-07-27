package com.simi.action.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.baidu.BaiduConfigUtil;
import com.meijia.utils.vo.AppResultData;
import com.meijia.utils.HttpClientUtil;
import com.meijia.utils.IPUtil;
import com.meijia.utils.StringUtil;
import com.meijia.wx.utils.JsonUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;

@Controller
@RequestMapping(value = "/app/map")
public class MapController {

	@RequestMapping(value = "autocomplete", method = RequestMethod.GET)
	public AppResultData<Object> autoComplete(@RequestParam("query") String query,
			@RequestParam(value = "region", required = false, defaultValue = "北京市") String region) {

		String ak = BaiduConfigUtil.getInstance().getRb().getString("ak");

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (StringUtil.isEmpty(query)) {

			return result;
		}

		Map<String, String> params = new HashMap<String, String>();
		// query = URLDecoder.decode(query);
//		try {
//			query = new String(query.getBytes("iso-8859-1"), "utf-8");
//			region = new String(region.getBytes("iso-8859-1"), "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		params.put("query", query);
		params.put("region", region);
		params.put("output", "json");
		params.put("ak", ak);
		String url = "http://api.map.baidu.com/place/v2/suggestion";
		String getResult = HttpClientUtil.get(url, params);

		Map<String, Object> m = JsonUtil.stringToMap(getResult);
		if (m.get("message").toString().equals("ok")) {
			result.setData(m.get("result"));
			System.out.println(m.get("result"));
		}

		return result;
	}

	@RequestMapping(value = "ip", method = RequestMethod.GET)
	public AppResultData<Object> ipLocation(
			HttpServletRequest request,
			@RequestParam(value = "ip", required = false, defaultValue = "") String ip) {

		String ak = BaiduConfigUtil.getInstance().getRb().getString("ak");

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Map<String, String> params = new HashMap<String, String>();
		
		if (StringUtil.isEmpty(ip)) {
			Long longIP = IPUtil.getIpAddr(request);
			ip = IPUtil.longToIP(longIP);
		}
		
		if (StringUtil.isEmpty(ip) || ip.equals("127.0.0.1") || ip.equals("localhost")) {
			return result;
		}
		
		
		params.put("ip", ip);
		params.put("coor", "bd09ll");
		params.put("output", "json");
		params.put("ak", ak);
		String url = "http://api.map.baidu.com/location/ip";
		String getResult = HttpClientUtil.get(url, params);

		Map<String, Object> m = JsonUtil.stringToMap(getResult);
//		if (m.get("message").toString().equals("ok")) {
//			result.setData(m.get("result"));
//			System.out.println(m.get("result"));
//		}
		System.out.println(m.toString());

		return result;
	}

}
