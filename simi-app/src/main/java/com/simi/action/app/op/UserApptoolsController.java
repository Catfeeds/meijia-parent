package com.simi.action.app.op;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.op.UserAppTools;
import com.simi.service.op.AppToolsService;
import com.simi.service.op.UserAppToolsService;
import com.simi.vo.AppResultData;
import com.simi.vo.po.AppToolsVo;

@Controller
@RequestMapping(value = "/app/op")
public class UserApptoolsController extends BaseController {


	@Autowired
	private UserAppToolsService userAppToolsService;

	
	/**
	 * 新增应用显示配置接口
	 * @param tId
	 * @param userId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "user_app_tools", method = RequestMethod.POST)
	public AppResultData<Object> PostUserAppTools(
			@RequestParam("t_id") Long tId,
			@RequestParam("user_id") Long userId,
			@RequestParam("status") Short status)  {
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		UserAppTools userAppTools = userAppToolsService.initUserAppTools();
		userAppTools.settId(tId);
		userAppTools.setUserId(userId);
		userAppTools.setStatus(status);
		userAppToolsService.insert(userAppTools);
		
		return result;
	}
	
}
