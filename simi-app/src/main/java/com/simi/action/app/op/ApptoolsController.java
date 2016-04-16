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
import com.simi.vo.AppResultData;
import com.simi.vo.ApptoolsSearchVo;
import com.simi.vo.po.AppToolsVo;

@Controller
@RequestMapping(value = "/app/op")
public class ApptoolsController extends BaseController {


	@Autowired
	private AppToolsService appToolsService;

	
	/**
	 * 获得应用中心列表接口
	 * @param appType
	 * @return
	 */
	@RequestMapping(value = "get_appTools", method = RequestMethod.GET)
	public AppResultData<Object> getAppTools(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "app_type", required = false, defaultValue="xcloud") String appType,
			@RequestParam(value = "t", required = false, defaultValue="0") Long t
			) {
		
		ApptoolsSearchVo searchVo = new ApptoolsSearchVo();
		searchVo.setAppType(appType);
		searchVo.setIsOnline((short) 0);
		List<AppTools> appTools = appToolsService.selectBySearchVo(searchVo);
         
		List<AppToolsVo> vo = new ArrayList<AppToolsVo>();
		
		for (AppTools item : appTools) {
			AppToolsVo listVo = new AppToolsVo();
			listVo = appToolsService.getAppToolsVo(item,userId);
			vo.add(listVo);
		}
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, vo);
		return result;
	}
	
	/**
	 * 获得应用中心列表接口
	 * @param appType
	 * @return
	 */
	@RequestMapping(value = "get_apptools_status", method = RequestMethod.GET)
	public AppResultData<Object> getAppToolsStatus(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "app_type", required = false, defaultValue="xcloud") String appType,
			@RequestParam(value = "t", required = false, defaultValue="0") Long t
			) {
		
		
		List<UserAppTools> list = appToolsService.getUserAppToos(userId);
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, list);
		return result;
	}	
	
}
