package com.xcloud.action.company;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.StringUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.vo.AppResultData;
import com.xcloud.action.BaseController;
import com.xcloud.vo.LoginVo;

@Controller
@RequestMapping(value = "/company")
public class CompanyDeptController extends BaseController {
	
	@Autowired
	private XCompanyService xCompanyService;
	
	@Autowired
	private XcompanyDeptService xcompanyDeptService;	
	
	@RequestMapping(value = "get_depts", method = RequestMethod.GET)
	public AppResultData<Object> getComapnyDepts(
			HttpServletRequest request,
			@RequestParam(value = "parent_id", required = true, defaultValue = "0") Long parentId,
			@RequestParam(value = "company_id", required = true, defaultValue = "0") Long xcompanyId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, null);


		if (xcompanyId == 0) {

			return result;
		}

		List<XcompanyDept> list = xcompanyDeptService.selectByParentIdAndXcompanyId(parentId, xcompanyId);

		result.setData(list);
		return result;

	}	
		
	@RequestMapping(value = "edit_dept", method = RequestMethod.POST)
	public AppResultData<Object> editComapnyDept(
			HttpServletRequest request,
			@RequestParam(value = "company_id", required = true, defaultValue = "0") Long xcompanyId,
			@RequestParam(value = "dept_id", required = true, defaultValue = "0") Long deptId,
			@RequestParam(value = "name", required = true, defaultValue = "0") String name) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, null);


		if (xcompanyId.equals(0L) || deptId.equals(0L) || StringUtil.isEmpty(name)) {
			return result;
		}
		
		List<XcompanyDept> list = xcompanyDeptService.selectByXcompanyId(xcompanyId);
		
		boolean canRename = true;
		for (XcompanyDept item : list) {
			if (item.getName().equals(name) && !item.getDeptId().equals(deptId)) {
				canRename = false;
				break;
			}
		}
		
		XcompanyDept xCompanyDept = xcompanyDeptService.selectByPrimaryKey(deptId);
		
		
		if (!canRename) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("部门名称重复");
			result.setData(xCompanyDept);
			return result;
		}
		
		xCompanyDept.setName(name);
		xcompanyDeptService.updateByPrimaryKeySelective(xCompanyDept);

		return result;

	}	
	
	@RequestMapping(value = "del_dept", method = RequestMethod.POST)
	public AppResultData<Object> delComapnyDept(
			HttpServletRequest request,
			@RequestParam(value = "company_id", required = true, defaultValue = "0") Long xcompanyId,
			@RequestParam(value = "dept_id", required = true, defaultValue = "0") Long deptId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, null);


		if (xcompanyId.equals(0L) || deptId.equals(0L) ) {
			return result;
		}
		
		List<XcompanyDept> list = xcompanyDeptService.selectByParentId(deptId);
		
		if (!list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("有下级部门,无法删除!");
			return result;
		}
		
		//在判断当前是否有员工，有则无法删除
		
		
		xcompanyDeptService.deleteByPrimaryKey(deptId);

		return result;

	}		
}
