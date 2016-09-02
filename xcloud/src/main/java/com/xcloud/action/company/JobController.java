package com.xcloud.action.company;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyJob;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyJobService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CompanyJobSearchVo;
import com.simi.vo.xcloud.XcompanyJobVo;
import com.simi.vo.xcloud.company.DeptSearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/job")
public class JobController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyDeptService xcompanyDeptService;
	
	@Autowired
	private XcompanyJobService jobService;
	
	
	@AuthPassport
	@RequestMapping(value = "/job_list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request,CompanyJobSearchVo searchVo) {
		
		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		
		if(searchVo == null){
			searchVo = new CompanyJobSearchVo();
		}
		
		List<XcompanyJob> jobList = jobService.selectByListPage(searchVo, pageNo, pageSize);
		
		XcompanyJob job;
		
		for (int i = 0, j= jobList.size(); i < j; i++) {
			job = jobList.get(i);
			XcompanyJobVo jobVo = jobService.transToVO(job);
			jobList.set(i, jobVo);
		}
		
		PageInfo result = new PageInfo(jobList);
		
		model.addAttribute("jobListModel", result);
		model.addAttribute("searchVoModel", searchVo);
	
		return "staffs/job-list";
	}		

	/*
	 * 跳转到职位 管理 form
	 */
	@AuthPassport
	@RequestMapping(value = "job_form", method = { RequestMethod.GET })
	public String tojobForm(Model model, HttpServletRequest request,
			@RequestParam("id")Long id) {
	
		if(id == null){
			id = 0L;
		}
		
		XcompanyJobVo jobVo = jobService.initVo();
		
		if(id > 0L){
			XcompanyJob xcompanyJob = jobService.selectByPrimaryKey(id);
			
			jobVo = jobService.transToVO(xcompanyJob);
		}
		
		// 获取登录的用户所在 公司
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();
		
		//1. 得到所在公司的 所有 在职的 员工
//		UserCompanySearchVo searchVo = new UserCompanySearchVo();
//		
//		searchVo.setCompanyId(companyId);
//		searchVo.setStatus((short) 1);
//		
//		List<XcompanyStaff> list = xcompanyStaffService.selectBySearchVo(searchVo);
//		
//		List<Long> userIdList = new ArrayList<Long>();
//		userIdList.add(0L);
//		
//		for (XcompanyStaff xcompanyStaff : list) {
//			userIdList.add(xcompanyStaff.getUserId());
//		}
//		
//		//从用户表得到 员工 的具体信息
//		UserSearchVo userSearchVo = new UserSearchVo();
//				
//		userSearchVo.setUserIds(userIdList);
//		
//		List<Users> userList = usersService.selectBySearchVo(userSearchVo);
		
//		jobVo.setUserList(userList);
		
		//2.该公司下 可选部门列表
		DeptSearchVo  deptSearchVo = new DeptSearchVo();
		
		deptSearchVo.setCompanyId(companyId);
		
		List<XcompanyDept> deptList = xcompanyDeptService.selectBySearchVo(deptSearchVo);
		
		jobVo.setDeptList(deptList);
		
		//3. 登录用户所在公司id
		jobVo.setCompanyId(companyId);
		
		model.addAttribute("jobVoModel", jobVo);
		
		return "staffs/job-form";
	}
	
	/*
	 *   提交 新增/修改 职位
	 */
	@AuthPassport
	@RequestMapping(value = "job_form", method = { RequestMethod.POST })
	public AppResultData<Object> submitJobForm(HttpServletRequest request,
			@ModelAttribute("jobVoModel")XcompanyJobVo jobVo, BindingResult bindingResult){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Long jobId = jobVo.getJobId();
		
		XcompanyJob xcompanyJob = jobService.initJob();
		
		/*
		 *  2016年6月14日10:56:08   
		 *  
		 *  	对于 xcompany_job表  的 userId 字段， 表示为当前操作人。而不是职位对应的人
		 * 
		 */
		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long userId = accountAuth.getUserId();
		
		
		if(jobId == 0L){
			
			BeanUtilsExp.copyPropertiesIgnoreNull(jobVo,xcompanyJob);
			
			xcompanyJob.setUserId(userId);
			
			jobService.insert(xcompanyJob);
		}else{
			xcompanyJob = jobService.selectByPrimaryKey(jobId);
			
			BeanUtilsExp.copyPropertiesIgnoreNull(jobVo,xcompanyJob);
			
			xcompanyJob.setUserId(userId);
			xcompanyJob.setUpdateTime(TimeStampUtil.getNowSecond());
			
			jobService.updateByPrimaryKey(xcompanyJob);
		}
		
		return result;
	}
}
