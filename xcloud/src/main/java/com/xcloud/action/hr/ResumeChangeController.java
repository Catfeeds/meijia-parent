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
import com.simi.po.model.resume.HrResumeChange;
import com.simi.po.model.user.Users;
import com.simi.service.resume.HrResumeChangeService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.resume.JobHunterVo;
import com.simi.vo.resume.ResumeChangeSearchVo;
import com.simi.vo.resume.ResumeChangeVo;
import com.simi.vo.resume.ResumeSearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

/**
 *
 * @author :hulj
 * @Date : 2016年5月23日下午12:10:13
 * @Description: 
 *		
 *	 云平台--人事--	简历交换
 *		
 */
@Controller
@RequestMapping(value = "/hr/resume")
public class ResumeChangeController extends BaseController {
	
	
	@Autowired
	private HrResumeChangeService resumeService;
	
	@Autowired
	private UsersService userService;
	
	/**
	 * 
	  * @Title: jobPubList
	  *
	  *	@Date : 2016年5月23日下午12:12:14
	  * @Description: 	
	  * 		简历交换--简历交换大厅
	  * @param searchVo
	  * @param request
	  * @param model
	  * @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@RequestMapping(value = "resume_exchange_list",method = RequestMethod.GET)
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
		
		List<HrResumeChange> list = resumeService.selectBySearchVo(searchVo);
		
		HrResumeChange change = null;
		
		for (int i = 0; i < list.size(); i++) {
			change = list.get(i);
			ResumeChangeVo resumeVo = resumeService.transToResumeVo(change);
			list.set(i, resumeVo);
		}
		
		PageInfo result = new PageInfo(list);
		
		model.addAttribute("resumeExChangeModel", result);
		
		return "hr/resume-exchange-list";
	}
	
	/**
	 * 
	  * @Title: tojobForm
	  *
	  *	@Date : 2016年5月23日下午2:03:59
	  * @Description: 
	  * 		
	  * 	 跳转到 发布简历交换需求页面	
	  * @param model
	  * @param request
	  * @param id
	  * @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@AuthPassport
	@RequestMapping(value = "/resume_exchange_form", method = { RequestMethod.GET })
	public String tojobForm(Model model, HttpServletRequest request,
			@RequestParam("id")Long id) {
	
		if(id == null){
			id = 0L;
		}
		
		ResumeChangeVo changeVo = resumeService.initResumeChangeVo();
		
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();

		Users users = userService.selectByPrimaryKey(userId);
		
		//如果是新增, 则  发布人 为 当前登录用户
		changeVo.setUserId(userId);
		changeVo.setName(users.getName());
		
		
		if(id > 0L){
			HrResumeChange change = resumeService.selectByPrimaryKey(id);
			
			changeVo = resumeService.transToResumeVo(change);
		}
		
		//所在城市。 由 ip 获得所在城市
		long ipAddr = IPUtil.getIpAddr(request);
		
		String cityByIp = IPUtil.getCityByIp(IPUtil.longToIP(ipAddr));
		
		changeVo.setIpTransCity(cityByIp);
		
		model.addAttribute("resumeExchangeVoModel", changeVo);
		
		return "hr/resume-exchange-form";
	}
	
	
	/**
	 * 
	  * @Title: submitJobForm
	  *
	  *	@Date : 2016年5月23日下午2:13:43
	  * @Description: 
	  * 
	  * 		提交 简历交换 需求
	  * 
	  * @param hunterVo
	  * @param bindingResult
	  * @return    设定文件
	  * @return AppResultData<Object>    返回类型
	  * @throws
	 */
	@AuthPassport
	@RequestMapping(value = "/resume_exchange_form", method = { RequestMethod.POST })
	public AppResultData<Object> submitJobForm(
			@ModelAttribute("resumeExchangeVoModel")ResumeChangeVo changeVo, BindingResult bindingResult){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users users = userService.selectByPrimaryKey(changeVo.getUserId());
		
		if(users == null){
			result.setData("用户不存在");
			result.setStatus(Constants.ERROR_999);
			return result;
		}
		
		if(!users.getName().equals(changeVo.getName())){
			result.setData("用户不匹配");
			result.setStatus(Constants.ERROR_999);
			return result;
		}
		
		
		Long id = changeVo.getId();
		
		HrResumeChange change = resumeService.initHrResumeChange();
		
		if(id == 0L){
			
			BeanUtilsExp.copyPropertiesIgnoreNull(changeVo, change);
			resumeService.insert(change);
		}else{
			
			change = resumeService.selectByPrimaryKey(id);
			BeanUtilsExp.copyPropertiesIgnoreNull(changeVo, change);
			change.setUpdateTime(TimeStampUtil.getNowSecond());
			
			resumeService.updateByPrimaryKey(change);
		}
		
		return result;
	}
	
}	
