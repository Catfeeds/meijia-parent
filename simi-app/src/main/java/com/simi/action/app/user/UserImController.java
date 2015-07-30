package com.simi.action.app.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.simi.action.app.BaseController;
import com.simi.common.Constants;
import com.simi.po.model.user.UserImHistory;
import com.simi.service.user.UserImHistoryService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserImHistoryVo;
import com.simi.vo.user.UserImVo;


@Controller
@RequestMapping(value = "/app/user")
public class UserImController extends BaseController {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserImHistoryService userImHistoryService;	
	/**
	 * 两个用户的聊天记录
	 * 
	 * @param from_im_user
	 * @param to_im_user
	 * @return
	 */

	@RequestMapping(value = "get_im_history", method = RequestMethod.GET)
	public AppResultData<Object> getImHistory(
			@RequestParam("from_im_user") String fromImUser,
			@RequestParam("to_im_user") String toImUser,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");

		int pageNo = page;
		int pageSize = Constants.PAGE_MAX_NUMBER;
		
		PageInfo list = userImHistoryService.selectByImUserListPage(fromImUser, toImUser, pageNo, pageSize);
		
		List<UserImHistory> imHistoryList = list.getList();
		
//		UserImHistory item = null;
//		
//		ObjectMapper mapper = new ObjectMapper();
//		
//		List<UserImHistoryVo> resultList = new ArrayList<UserImHistoryVo>();
//		for (int i = 0; i < imHistoryList.size(); i++) {
//			item = imHistoryList.get(i);
//			String imContentStr = item.getImContent();
//			UserImHistoryVo vo = new UserImHistoryVo();
//			vo.setImDay("");
//			vo.setImName("");
//			vo.setImText("");
//			vo.setImTime("");
//			vo.setImType("");
//			JsonNode contentJsonNode = mapper.readValue(imContentStr, JsonNode.class);
//			if (contentJsonNode.get("bodies") != null) {
//				 if (contentJsonNode.get("bodies").get(0) != null &&
//				     contentJsonNode.get("bodies").get(0).get("type") != null &&
//				     ) {
//					 vo.setImText(contentJsonNode.get("bodies").get(0).get("type"));
//				 }
//			}
//			
//			
//			resultList.add(vo);
//			
//			
//		}
		
		result.setData(imHistoryList);
		
		return result;
	}
	
	/**
	 * 两个用户的聊天记录
	 * 
	 * @param from_im_user
	 * @param to_im_user
	 * @return
	 */

	@RequestMapping(value = "get_user_and_im", method = RequestMethod.GET)
	public AppResultData<Object> getUserAndIm(
			@RequestParam("sec_id") Long secId,
			@RequestParam("im_user_name") String imUserName) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");

		List<UserImVo> list = userImHistoryService.getAllImUserLastIm(secId, imUserName);
		result.setData(list);
		
		return result;
	}	

}