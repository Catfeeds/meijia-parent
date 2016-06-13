package com.xcloud.action.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.IPUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.resume.HrJobHunter;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.resume.JobHunterVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.simi.vo.xcloud.XcompanyDeptVo;
import com.simi.vo.xcloud.company.DeptSearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

/**
 * 
* @author hulj 
* @date 2016年6月7日 下午2:38:38 
* @Description: 
*		
*  通讯录-- 部门管理
 */
@Controller
@RequestMapping("/staff/dept")
public class DeptController extends BaseController {
	
	@Autowired
	private XcompanyDeptService deptService;
	
	@Autowired
	private XcompanyStaffService xcompanyStaffService;
	
	@Autowired
	private UsersService userService;
	
	/**
	 *	部门列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "dept_list",method = RequestMethod.GET )
	public String deptList(Model model, HttpServletRequest request, DeptSearchVo searchVo){
		
		int pageNo = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		
		if(searchVo == null){
			searchVo = new DeptSearchVo();
		}
		
		List<XcompanyDept> list = deptService.selectByListPage(searchVo, pageNo, pageSize);
		
		XcompanyDept dept;
		
		for (int i = 0, j= list.size(); i < j; i++) {
			
			dept = list.get(i);
			XcompanyDeptVo deptVo = deptService.transToVo(dept);
			list.set(i, deptVo);
		}
		
		PageInfo result = new PageInfo(list);
		
		model.addAttribute("deptListModel", result);
		model.addAttribute("searchVoModel", searchVo);
		
		return "/staffs/dept-list";
	}
	
	
	/*
	 * 跳转到部门 管理 form
	 */
	@AuthPassport
	@RequestMapping(value = "dept_form", method = { RequestMethod.GET })
	public String tojobForm(Model model, HttpServletRequest request,
			@RequestParam("id")Long id) {
	
		if(id == null){
			id = 0L;
		}
		
		XcompanyDeptVo xcompanyDeptVo = deptService.initVo();
		
		if(id > 0L){
			XcompanyDept xcompanyDept = deptService.selectByPrimaryKey(id);
			
			xcompanyDeptVo =  deptService.transToVo(xcompanyDept);
		}
		
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();
		
		//负责人 列表选择
		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		
		searchVo.setCompanyId(companyId);
		searchVo.setStatus((short) 1);
		
		List<XcompanyStaff> list = xcompanyStaffService.selectBySearchVo(searchVo);
		
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(0L);
		
		for (XcompanyStaff xcompanyStaff : list) {
			userIdList.add(xcompanyStaff.getUserId());
		}
		
		//从用户表得到  负责人 的具体信息
		UserSearchVo userSearchVo = new UserSearchVo();
				
		userSearchVo.setUserIds(userIdList);
		
		List<Users> userList = userService.selectBySearchVo(userSearchVo);
		
		xcompanyDeptVo.setLeadUserList(userList);
		
		//公司id
		xcompanyDeptVo.setCompanyId(companyId);
		
		model.addAttribute("deptVoModel", xcompanyDeptVo);
		
		return "staffs/dept-form";
	}
	
	/*
	 *   提交 新增/修改 部门
	 */
	@AuthPassport
	@RequestMapping(value = "dept_form.json", method = { RequestMethod.POST })
	public AppResultData<Object> submitJobForm(
			@ModelAttribute("deptVoModel")XcompanyDeptVo deptVo, BindingResult bindingResult){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		
		Long deptId = deptVo.getDeptId();
		
		XcompanyDept xcompanyDept = deptService.initXcompanyDept();
		
		if(deptId == 0L){
			
			BeanUtilsExp.copyPropertiesIgnoreNull(deptVo,xcompanyDept);
				
			deptService.insert(xcompanyDept);
		}else{
			
			xcompanyDept = deptService.selectByPrimaryKey(deptId);
			
			BeanUtilsExp.copyPropertiesIgnoreNull(deptVo, xcompanyDept);
			
			deptService.updateByPrimaryKey(xcompanyDept);
		}
		
		return result;
	}
	
}
