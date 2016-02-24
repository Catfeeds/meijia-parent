package com.simi.action.app.op;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

@Controller
@RequestMapping(value = "/app/op")
public class AppIndexController extends BaseController {


	@Autowired
    private AppToolsService appToolsService;

	@Autowired
	private UserAppToolsService userAppToolsService;
	
	/**
	 * 获得导航列表接口
	 * @param appType
	 * @return
	 */
	@RequestMapping(value = "get_appIndexList", method = RequestMethod.GET)
	public AppResultData<Object> getAppIndexList(
			@RequestParam(value = "app_type", required = false, defaultValue="xcloud") String appType,
			@RequestParam("user_id") Long userId) {
		
		//1. 读取 app_tools where is_default = 1 , 的到集合A 
		List<AppTools> appToolsAList = appToolsService.selectByAppType(appType);
		//2. 读取user_app_tools where status = 0  得到集合B 
		List<UserAppTools> appToolsBList = userAppToolsService.selectByUserIdAndStatus(userId);
		//查出用户勾选的不显示的为系统默认显示的集合
		List<AppTools> appToolsCList = new ArrayList<AppTools>();
		for (int i = 0; i < appToolsBList.size(); i++) {
			UserAppTools userAppToolsB = appToolsBList.get(i);
			AppTools appToolsB = appToolsService.selectByPrimaryKey(userAppToolsB.gettId());
			if (appToolsB == null)continue;
			if (appToolsB.getIsDefault() == 1) {
				appToolsCList.add(appToolsB);
			}
		}
		//3. 集合A排查掉集合C，得到集合D   默认的，并且现实的
		List<AppTools> appToolsDList = new ArrayList<AppTools>();
		
	       appToolsAList.remove(appToolsCList);
	       appToolsDList.addAll(appToolsAList);
	       appToolsDList.addAll(appToolsCList);
       
		//4. 读取user_app_tools where status = 1  得到集合E    用户选择显示的
	       List<UserAppTools> appToolsEList = userAppToolsService.selectByUserIdAndStatusOne(userId);
	       List<AppTools> appToolsFList = new ArrayList<AppTools>();
			for (int i = 0; i < appToolsEList.size(); i++) {
				UserAppTools userAppToolsE = appToolsEList.get(i);
				AppTools appToolsE = appToolsService.selectByPrimaryKey(userAppToolsE.gettId());
					appToolsFList.add(appToolsE);
			}
		//5. 合并集合D和集合F，返回
			appToolsDList.addAll(appToolsFList);
	        //新的list集合，用来放最后的结果集  
	        List<AppTools>  list=new ArrayList<AppTools>();  
	        //把追加到一起的list循环放入set集合中  
	        /*Set<AppTools> set=new HashSet<AppTools>();  
	        for (int i = 0; i < appToolsDList.size(); i++) {  
	            AppTools str=appToolsDList.get(i);  
	            set.add(str);             
	        }  
	        //把set集合遍历添加在list中  
	        for (Iterator<AppTools> it = set.iterator(); it.hasNext();){  
	            list.add(it.next());
	        }  */
	        for (int i = 0; i < appToolsDList.size(); i++) {
	        	AppTools appToolsD = appToolsDList.get(i);
	        	list.add(appToolsD);
			}
	    //   list.addAll(appToolsDList);
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, list);
		
		return result;
	}
	
}
