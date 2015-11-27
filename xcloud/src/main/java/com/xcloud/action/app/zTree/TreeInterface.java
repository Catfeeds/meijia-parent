package com.xcloud.action.app.zTree;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.service.admin.AdminRoleService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.vo.AppResultData;
import com.xcloud.action.BaseController;

@Controller
@RequestMapping(value = "/interface-zTree")
public class TreeInterface extends BaseController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private AdminRoleService adminRoleService;
	
	@Autowired 
	private XcompanyDeptService xcompanyDeptService;

	@Autowired
	private UsersMapper usersMapper;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "zTree-nodes", method = RequestMethod.GET)
	public AppResultData<Object> zTreezNodes(
			HttpServletRequest request,
			@RequestParam(value = "parent_id", required = true, defaultValue = "0") Long parentId
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, null);
		
	   // Long xcompanyId = Long.valueOf(request.getParameter("xcompanyId"));  
	    String treeStr = ""; 
		parentId = 0L;
	    List<XcompanyDept> list= xcompanyDeptService.selectByParentId(parentId);
	    if(list!=null){  
	        for(int i=0,len=list.size();i<len;i++){  
	        	
	            //treeStr += "{id:'deptId',pId:'companyId',name:'name' ,isParent:true},";
	            treeStr += "{id:'2',pId:'1',name:'上级部' ,isParent:true},";
	        }  
	    }  
	    treeStr = "["+treeStr.substring(0,treeStr.length()-1)+"]";  
	 //   这里ajax返回treeStr  	
	    
		result.setData(treeStr);
		return result;
		
	}


}
