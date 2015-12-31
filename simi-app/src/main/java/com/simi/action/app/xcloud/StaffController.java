package com.simi.action.app.xcloud;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.UserCompanySearchVo;
import com.simi.vo.xcloud.StaffListVo;

@Controller
@RequestMapping(value = "/app/company/")
public class StaffController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private XcompanyDeptService xCompanyDeptService;
	
	@Autowired
	private XcompanyStaffService xCompanyStaffService;	

	@RequestMapping(value = "/get-staffs", method = { RequestMethod.GET })
	public AppResultData<Object> getByDept(HttpServletRequest request,
			@RequestParam("user_id") Long userId, 
			@RequestParam("company_id") Long companyId,
			@RequestParam(value = "dept_id", required = false, defaultValue = "0") Long deptId
			) {	
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = usersService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
				
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setStatus((short) 1);
		
		if (deptId > 0L) {
			searchVo.setDeptId(deptId);
		}
		List<XcompanyStaff> list = xCompanyStaffService.selectBySearchVo(searchVo);
		
		List<StaffListVo> staffList = xCompanyStaffService.changeToStaffLisVos(companyId, list);	
		
		
		result.setData(staffList);
		
		return result;
	}

}
