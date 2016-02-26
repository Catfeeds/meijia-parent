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
import com.sun.tools.corba.se.idl.constExpr.And;

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
		//查出默认显示中的用户选择不显示的  is_default = 1 is_del=0 得到集合C
		List<AppTools> appToolsCList = new ArrayList<AppTools>();
		for (int i = 0; i < appToolsBList.size(); i++) {
			UserAppTools userAppToolsB = appToolsBList.get(i);
			AppTools appToolsB = appToolsService.selectByPrimaryKey(userAppToolsB.gettId());
			if (appToolsB == null)continue;
			if (appToolsB.getIsDefault() == 1 && appToolsB.getIsDel()==0) {
				appToolsCList.add(appToolsB);
			}
		}
		//3. 集合A排查掉集合C，得到集合A   默认的，并且现实的
	//	List<AppTools> appToolsDList = new ArrayList<AppTools>();
		
	     //  appToolsAList.remove(appToolsCList);
	      // appToolsDList.addAll(appToolsAList);
	      // appToolsDList.addAll(appToolsCList);
		for (int i = 0; i < appToolsCList.size(); i++) {
	        AppTools appToolsC = appToolsCList.get(i);
	        for (int j = 0; j < appToolsAList.size();j++) {
	        AppTools appToolsA = appToolsAList.get(j);
				if (appToolsC.gettId().equals(appToolsA.gettId())) {
					appToolsAList.remove(j);
					}
				}
	        //	appToolsDList.add(appToolsA);
			}
	     /*  for (int i = 0; i < appToolsCList.size(); i++) {
	        	AppTools appToolsC = appToolsCList.get(i);
	        	appToolsDList.add(appToolsC);
			}*/
	       
		//4. 读取user_app_tools where status = 1  得到集合E    用户选择显示的
	       List<UserAppTools> appToolsEList = userAppToolsService.selectByUserIdAndStatusOne(userId);
	       List<AppTools> appToolsFList = new ArrayList<AppTools>();
			for (int i = 0; i < appToolsEList.size(); i++) {
				UserAppTools userAppToolsE = appToolsEList.get(i);
				AppTools appToolsE = appToolsService.selectByPrimaryKey(userAppToolsE.gettId());
					appToolsFList.add(appToolsE);
			}
		//5. 合并集合A和集合F，返回
			appToolsAList.addAll(appToolsFList);
	        //新的list集合，用来放最后的结果集  
	      /*  List<AppTools>  list=new ArrayList<AppTools>();  
	        //把追加到一起的list循环放入set集合中  
	        for (int i = 0; i < appToolsDList.size(); i++) {
	        	AppTools appToolsD = appToolsDList.get(i);
	        	list.add(appToolsD);
			}*/
	    //   list.addAll(appToolsDList);
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, appToolsAList);
		
		return result;
	}
	
}
