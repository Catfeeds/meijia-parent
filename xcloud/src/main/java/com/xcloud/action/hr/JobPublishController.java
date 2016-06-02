package com.xcloud.action.hr;

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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.IPUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.resume.HrJobHunter;
import com.simi.po.model.user.Users;
import com.simi.service.resume.HrJobHunterService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.resume.JobHunterVo;
import com.simi.vo.resume.ResumeChangeSearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

/**
 *
 * @author :hulj
 * @Date : 2016年5月21日下午4:25:45
 * @Description: 
 *		
 *		云平台--赏金猎人
 */
@Controller
@RequestMapping(value = "/hr/hunter")
public class JobPublishController extends BaseController {
	
	@Autowired
	private HrJobHunterService jobHunterService;
	
	@Autowired
	private UsersService userService;
	
	/**
	 * 
	  * @Title: jobPubList
	  *
	  *	@Date : 2016年5月21日下午4:37:39
	  * @Description: 
	  * 		职位悬赏大厅
	  * @param searchVo
	  * @param request
	  * @param model
	  * @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@RequestMapping(value = "job_publish_list",method = RequestMethod.GET)
	public String jobPubList(ResumeChangeSearchVo searchVo, HttpServletRequest request,Model model){
		
		int pageNo = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);
		
		
		PageHelper.startPage(pageNo, pageSize);
		
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long userId = accountAuth.getUserId();
		
		//显示 当前登录人的 简历
		searchVo.setUserId(userId);
		
		List<HrJobHunter> list = jobHunterService.selectBySearchVo(searchVo);
		
		HrJobHunter hunter  = null;
		for (int i = 0; i < list.size(); i++) {
			hunter = list.get(i);
			JobHunterVo hunterVo = jobHunterService.transToHunterVo(hunter);
			list.set(i, hunterVo);
		}
		
		PageInfo result = new PageInfo(list);
		
		model.addAttribute("jobHunterModel", result);
		
		return "hr/job-hunter-list";
	}
	
	/**
	 * 
	  * @Title: tojobForm
	  *
	  *	@Date : 2016年5月23日上午11:21:26
	  * @Description: 
	  * 	跳转到  发布简历  form 页	
	  * @param id
	  * @throws
	 */
	@AuthPassport
	@RequestMapping(value = "/job_hunter_form", method = { RequestMethod.GET })
	public String tojobForm(Model model, HttpServletRequest request,
			@RequestParam("id")Long id) {
	
		if(id == null){
			id = 0L;
		}
		
		
		JobHunterVo jobHunterVo = jobHunterService.initHunterVo();
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();

		Users users = userService.selectByPrimaryKey(userId);
		
		//如果是新增, 则  发布人 为 当前登录用户
		jobHunterVo.setUserId(userId);
		jobHunterVo.setName(users.getName());
		
		
		if(id > 0L){
			HrJobHunter	jobHunter = jobHunterService.selectByPrimaryKey(id);
			
			jobHunterVo = jobHunterService.transToHunterVo(jobHunter);
		}
		
		//所在城市。 由 ip 获得所在城市
		long ipAddr = IPUtil.getIpAddr(request);
		
		String cityByIp = IPUtil.getCityByIp(IPUtil.longToIP(ipAddr));
		
		jobHunterVo.setIpTransCity(cityByIp);
		
		model.addAttribute("jobHunterVoModel", jobHunterVo);
		
		return "hr/job-hunter-form";
	}
	
	/**
	 * 
	  * @Title: submitJobForm
	  *
	  *	@Date : 2016年5月23日上午11:22:11
	  * @Description: 
	  * 	 提交 简历 
	  *    	
	  * @param hunterVo
	 */
	@AuthPassport
	@RequestMapping(value = "/job_hunter_form", method = { RequestMethod.POST })
	public AppResultData<Object> submitJobForm(
			@ModelAttribute("jobHunterVoModel")JobHunterVo hunterVo, BindingResult bindingResult){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users users = userService.selectByPrimaryKey(hunterVo.getUserId());
		
		if(users == null){
			result.setData("用户不存在");
			result.setStatus(Constants.ERROR_999);
			return result;
		}
		
		if(!users.getName().equals(hunterVo.getName())){
			result.setData("用户不匹配");
			result.setStatus(Constants.ERROR_999);
			return result;
		}
		
		
		
		Long id = hunterVo.getId();
		
		HrJobHunter hunter = jobHunterService.initHrJobHunter();
		
		if(id == 0L){
			
			BeanUtilsExp.copyPropertiesIgnoreNull(hunterVo, hunter);
			
			jobHunterService.insert(hunter);
		}else{
			
			hunter = jobHunterService.selectByPrimaryKey(id);
			BeanUtilsExp.copyPropertiesIgnoreNull(hunterVo, hunter);
			hunter.setUpdateTime(TimeStampUtil.getNowSecond());
			
			jobHunterService.updateByPrimaryKey(hunter);
		}
		
		
		return result;
	}
	
	
	
}
