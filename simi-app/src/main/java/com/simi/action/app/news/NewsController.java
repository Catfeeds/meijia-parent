package com.simi.action.app.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.HttpClientUtil;
import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/news")
public class NewsController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private XCompanySettingService xCompanySettingService;
	
	@RequestMapping(value = "topline", method = RequestMethod.GET)
	public AppResultData<Object> topline(
			@RequestParam(value = "type", required = false, defaultValue="") String newsType,
			@RequestParam(value = "count", required = false, defaultValue="10") int count,
			@RequestParam(value = "timestamp", required = false, defaultValue="0") Long timeStamp,
			@RequestParam(value = "token", required = false, defaultValue="") String token
			) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (StringUtil.isEmpty(newsType) || timeStamp.equals(0L) || StringUtil.isEmpty(token)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("参数不正确");
			return result;
		}
		
		String slug = "";
		if (newsType.equals("topline")) slug = "首页精选";
		if (newsType.equals("info ")) slug = "职场";
		
		String wpUrl = "http://bolohr.com/";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("json", "get_tag_posts");
		paramMap.put("count", String.valueOf(count));
		paramMap.put("order", "DESC");
		paramMap.put("slug", slug);
		paramMap.put("include", "id,title,modified,url");
		
		String content = HttpClientUtil.get(wpUrl, paramMap);
		System.out.println(content);
		
		JSONObject dataJson;
		List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
		try {
			dataJson = new JSONObject(content);
			String status = dataJson.getString("status");
			if (!status.equals("ok"))
				return result;

			JSONArray elements = dataJson.getJSONArray("posts");
			
			for (int i = 0; i < elements.length(); i++) {
				JSONObject element = elements.getJSONObject(i);
				String id = element.getString("id");
				String title = element.getString("title");
				String modified = element.getString("modified");
				String url = element.getString("url");
				System.out.println(title);
				
				Map<String, String> item = new HashMap<String, String>();
				item.put("id", id);
				item.put("title", title);
				item.put("modified", modified);
				item.put("url", url);
				datas.add(item);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.setData(datas);
		
		return result;
	}
}
