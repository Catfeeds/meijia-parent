package com.meijia.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * HTTP工具类
 *
 *
 */
public class HttpClientUtil {

	private static Log log = LogFactory.getLog(HttpClientUtil.class);

	/**
	 * 定义编码格式 UTF-8
	 */
	public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";

	private static final String URL_PARAM_CONNECT_FLAG = "&";

	private static final String EMPTY = "";

	private static MultiThreadedHttpConnectionManager connectionManager = null;

	private static int connectionTimeOut = 25000;

	private static int socketTimeOut = 25000;

	private static int maxConnectionPerHost = 20;

	private static int maxTotalConnections = 20;

	private static HttpClient client;

	static {
		connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
		connectionManager.getParams().setSoTimeout(socketTimeOut);
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
		connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
		client = new HttpClient(connectionManager);
	}

	/**
	 * POST方式提交数据
	 *
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果
	 * @throws IOException
	 *             IO异常
	 */
	public static String post(String url, Map<String, String> params) {

		String response = EMPTY;
		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_PARAM_DECODECHARSET_UTF8);
			// 将表单的值放入postMethod中
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				String value = params.get(key);
				postMethod.addParameter(key, value);
			}
			// 执行postMethod
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				response = postMethod.getResponseBodyAsString();
			} else {
				log.error("响应状态码 = " + postMethod.getStatusCode());
			}
		} catch (HttpException e) {
			log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("发生网络异常", e);
			e.printStackTrace();
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}

		return response;
	}
	
	/**
	 * POST方式提交数据
	 *
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果
	 * @throws IOException
	 *             IO异常
	 */
	public static String postByAuth(String url, String username, String password, Map<String, String> params) {

		String response = EMPTY;
		PostMethod postMethod = null;
		
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);  

		client.getState().setCredentials(AuthScope.ANY, creds); 
		try {
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_PARAM_DECODECHARSET_UTF8);
			// 将表单的值放入postMethod中
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				String value = params.get(key);
				postMethod.addParameter(key, value);
			}
			// 执行postMethod
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				response = postMethod.getResponseBodyAsString();
			} else {
				log.error("响应状态码 = " + postMethod.getStatusCode());
			}
		} catch (HttpException e) {
			log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("发生网络异常", e);
			e.printStackTrace();
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}

		return response;
	}	

	/**
	 * GET方式提交数据
	 *
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果
	 * @throws IOException
	 *             IO异常
	 */
	public static String get(String url, Map<String, String> params) {

		String response = EMPTY;
		GetMethod getMethod = null;
		StringBuffer strtTotalURL = new StringBuffer(EMPTY);

		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL.append(url).append("?").append(getUrl(params));
		} else {
			strtTotalURL.append(url).append("&").append(getUrl(params));
		}
		log.debug("GET请求URL = \n" + strtTotalURL.toString());

		try {
			getMethod = new GetMethod(strtTotalURL.toString());
			getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_PARAM_DECODECHARSET_UTF8);
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				response = getMethod.getResponseBodyAsString();
			} else {
				log.debug("响应状态码 = " + getMethod.getStatusCode());
			}
		} catch (HttpException e) {
			log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("发生网络异常", e);
			e.printStackTrace();
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}

		return response;
	}
	
	/**
	 * GET方式提交数据
	 *
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果
	 * @throws IOException
	 *             IO异常
	 */
	public static String get(String url, Map<String, String> params, Map<String, String> headers) {

		String response = EMPTY;
		GetMethod getMethod = null;
		StringBuffer strtTotalURL = new StringBuffer(EMPTY);

		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL.append(url).append("?").append(getUrl(params));
		} else {
			strtTotalURL.append(url).append("&").append(getUrl(params));
		}
		log.debug("GET请求URL = \n" + strtTotalURL.toString());

		try {
			getMethod = new GetMethod(strtTotalURL.toString());
			getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_PARAM_DECODECHARSET_UTF8);
			
			Set<String> keys = headers.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				if (headers.containsKey(key)) {
					String val = headers.get(key);
					String str = val != null ? val : "";
					getMethod.setRequestHeader(key, str);
				}
			}
			
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				response = getMethod.getResponseBodyAsString();
			} else {
				log.debug("响应状态码 = " + getMethod.getStatusCode());
			}
		} catch (HttpException e) {
			log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("发生网络异常", e);
			e.printStackTrace();
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}

		return response;
	}	

	public static String post_xml(String urlStr, String xml) {
		try {
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");

			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
			System.out.println("urlStr=" + urlStr);
			System.out.println("xmlInfo=" + xml);
			// xml = new String(xml.getBytes("ISO-8859-1"));

			out.write(xml);
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = "";
			String result = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println(line);
				result += line;
			}
			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 据Map生成URL字符串
	 *
	 * @param map
	 *            Map
	 * @param valueEnc
	 *            URL编码
	 * @return URL
	 */
	public static String getUrl(Map<String, String> map) {

		if (null == map || map.keySet().size() == 0) {
			return (EMPTY);
		}
		StringBuffer url = new StringBuffer();
		Set<String> keys = map.keySet();
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			String key = it.next();
			if (map.containsKey(key)) {
				String val = map.get(key);
				String str = val != null ? val : EMPTY;
				try {
					str = URLEncoder.encode(str, URL_PARAM_DECODECHARSET_UTF8);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = EMPTY;
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}

		return (strURL);
	}

	public static void main(String[] args) {
//		 Map<String, String> params = new HashMap<String, String>();
//		// params.put("merNo", "301100100001630");
//		// params.put("signType", "MD5");
//		// params.put("merBindAgrNo", "00003018007000006450000013866742");
//		// params.put("interfaceVersion", "1.0.0.0");
//		// params.put("amount", "1000");
//		// params.put("orderDate", "20120823");
//		// params.put("orderNo", "UDP1208230917531231111");
//		// params.put("merReqTime", "20120823091802");
//		// params.put("goodsDesc", "为号码交费充值元");
//		// params.put("goodsName", "中国联通交费充值");
//		// params.put("userIdeMark", "3");
//		// params.put("bankAgrMode", "9");
//		// params.put("signMsg", "3ced24a118461043901d47815e6905a9");
//		// System.out.println(HttpClientUtil.getUrl(params));
//		// System.out.println(HttpClientUtil.post("xxxx", params));
//		//开门
////		String username = "admin";
////		String password = "888888";
////		String url = "http://192.168.0.67/cdor.cgi?open=1";
//////		params.put("open", "1");
////		System.out.println(HttpClientUtil.postByAuth(url, username, password, params));
//		
//		//
//		
//		String apiKey = "8cca3859e9b222f51c51698f8016e293";
//		int year = 2016;
//		// 获取年度的假日列表url
//		String yearUrl = "http://apis.baidu.com/xiaogg/holiday/holiday";
//
//		Map<String, String> yearParams = new HashMap<String, String>();
//		yearParams.put("d", String.valueOf(year));
//		
//		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("apikey", apiKey);
//		
////		String yearRes = HttpClientUtil.get(yearUrl, yearParams, headers);
//		
//		String yearRes = "{\"2016\":[\"0207\",\"0208\",\"0209\",\"0210\",\"0211\",\"0212\",\"0213\",\"0404\",\"0501\",\"0502\",\"0609\",\"0610\",\"0611\",\"0915\",\"0916\",\"0917\",\"1001\",\"1002\",\"1003\",\"1004\",\"1005\",\"1006\",\"1007\",\"0101\"]}";
//		System.out.println(yearRes);
//		
//		JSONObject ja = JSONObject.fromObject(yearRes);
//		if (ja.containsKey("errNum")) {
//			System.out.println(ja.get("errNum") + ":" + ja.get("errMsg"));
//		}
//		
//		System.out.println(ja);
//		
//		String list = ja.get(String.valueOf(year)).toString();
//		System.out.println(list);
//		
//		JSONArray jsonArray = JSONArray.fromObject(list);
//		
//		Iterator it = jsonArray.iterator();
//		
//		while (it.hasNext()) {
//			System.out.println(it.next().toString());
//			String datePart = it.next().toString();
//			String dateStr = String.valueOf(year) + datePart;
//			Date date = DateUtil.parse(dateStr, "yyyyMMdd");
//			String day = DateUtil.formatDate(date);
//			System.out.println(day);
//        }
		int count = 10;
		String wpUrl = "http://bolohr.com/";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("json", "get_tag_posts");
		paramMap.put("count", String.valueOf(count));
		paramMap.put("order", "DESC");
		paramMap.put("slug", "首页精选");
		paramMap.put("include", "id,title,modified,url");
		
//		String params = HttpClientUtil.getUrl(paramMap);
//		wpUrl = wpUrl + "?" + params;
		
		
	}

}