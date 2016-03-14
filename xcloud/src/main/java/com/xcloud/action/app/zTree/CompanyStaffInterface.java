/*package com.xcloud.action.app.zTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.admin.AdminRoleService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/interface-staff")
public class CompanyStaffInterface extends BaseController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private AdminRoleService adminRoleService;
	
	@Autowired 
	private XcompanyDeptService xcompanyDeptService;

	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired 
	private XcompanyStaffService xcompanyStaffService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "list", method = RequestMethod.GET)
    public Map<String, Object> getStaff(HttpServletRequest request,
    		//@RequestParam(value = "partner_ids", required = false, defaultValue = "0") String partnerIds,
    		@RequestParam(value = "start", required = false, defaultValue = "0") int start,
    	//	@RequestParam(value = "order_no", required = false, defaultValue = "") String orderNo,
    		@RequestParam(value = "dept_id", required = false) Long deptId
    		) {

		Map<String, Object> result = new HashMap<String, Object>();
		int end = start + Constant.DEFAULT_PAGE_SIZE;
		
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();
		// deptId = 2L;
		if (deptId == null || deptId == 0L) {
			List<XcompanyStaff> list = xcompanyStaffService
					.selectByCompanyId(companyId);

			List<Long> userIds = new ArrayList<Long>();
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					userIds.add(list.get(i).getUserId());
				}
				PageInfo results = usersService.searchVoListPage(userIds,
						end, start);
				result.put("recordsTotal", list);
	    		result.put("recordsFiltered", list);
	    		
	    		result.put("data", results);
			}
		} else {
			List<XcompanyStaff> list = xcompanyStaffService
					.selectByCompanyIdAndDeptId(companyId, deptId);

			List<Long> userIds = new ArrayList<Long>();
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					userIds.add(list.get(i).getUserId());
				}
				PageInfo results = usersService.searchVoListPage(userIds,
						end, start);

		    result.put("recordsTotal", list);
    		result.put("recordsFiltered", list);
    		
    		result.put("data", results);
			}	
    }
		return result;
}
}*/