package com.simi.action.app.resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.resume.HrJobHunter;
import com.simi.po.model.user.UserTrailReal;
import com.simi.po.model.user.Users;
import com.simi.service.resume.HrJobHunterService;
import com.simi.service.user.UserTrailRealService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.resume.JobHunterVo;
import com.simi.vo.resume.ResumeSearchVo;

/**
 *
 * @author :hulj
 * @Date : 2016年4月29日下午3:24:21
 * @Description: 
 *			
 *		
 */
@Controller
@RequestMapping(value = "/app/resume")
public class JobHunterController extends BaseController {
	
	@Autowired
	private UserTrailRealService  userTrRealService;
	@Autowired
	private UsersService userService;
	
	@Autowired
	private HrJobHunterService hunterService;
	
	/**
	 * 
	  * @Title: hrResumeChangeList
	  *
	  *	@Date : 2016年4月28日下午5:01:26
	  * @Description: 
	  * 	赏金猎人 列表页
	  * @param partnerUserId		登录用户id
	  * @param page					分页页码
	  * @return    设定文件
	  * @return AppResultData<Object>    返回类型
	  * @throws
	 */
	@RequestMapping(value = "hr_job_hunter_list.json",method = RequestMethod.GET)
	public AppResultData<Object> hrResumeChangeList(
			@RequestParam("partner_user_id") Long partnerUserId, 
			@RequestParam(value = "page", required = false, defaultValue = "1") int page){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		//1. 校验 用户
		Users u = userService.selectByPrimaryKey(partnerUserId);
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("用户不存在");
			return result;
		}
		
		ResumeSearchVo searchVo = new ResumeSearchVo();
		searchVo.setUserId(partnerUserId);
		
		//分页
		PageInfo info = hunterService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		
		List<HrJobHunter> list = info.getList();
		
		//转换为 VO
		List<JobHunterVo> voList = new ArrayList<JobHunterVo>();
		for (HrJobHunter hunter : list) {
			JobHunterVo vo = hunterService.transToHunterVo(hunter);
			voList.add(vo);
		}
		
		result.setData(voList);
		
		return result;
	}
	
	/**
	 * 
	  * @Title: hrResumeChangeForm
	  *
	  *	@Date : 2016年4月28日下午5:43:11
	  * @Description: 
	  * 		赏金猎人--发布需求 form页	 初始化
	  * 		
	  * @param partnerUserId		登录用户id
	  * @return AppResultData<Object>    返回类型
	  * @throws
	 */
	@RequestMapping(value = "hr_job_hunter_form.json",method = RequestMethod.GET)
	public AppResultData<Object> hrHunterChangeForm(
			@RequestParam("partner_user_id") Long partnerUserId){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		UserTrailReal trailReal = userTrRealService.selectByUserId(partnerUserId);
		String city = trailReal.getCity();
		
		Users users = userService.selectByPrimaryKey(partnerUserId);
		String name = users.getName();	
		
		JobHunterVo hunterVo = hunterService.initHunterVo();
		
		//用户信息
		hunterVo.setUserName(name);
		hunterVo.setCityName(city);
		
		//有效期选择
		Map<Long, String> map = new HashMap<Long, String>();
		
		map.put(0L, "长期有效");
		map.put(30L, "30天有效");
		map.put(180L, "180天有效");
		
		hunterVo.setTimeMap(map);
		
		result.setData(hunterVo);
		
		return result;
	}
	
	
	/**
	 * 
	  * @Title: submitHrHunterChangeForm
	  *
	  *	@Date : 2016年4月29日下午4:33:17
	  * @Description: 
	  * 		提交 需求	
	  * @param userId
	  * @param userName
	  * @param cityName
	  * @param title
	  * @param limitDay
	  * @param jobRes				岗位职责
	  * @param jobReq				任职要求
	  * @param remarks				补充说明
	  * @throws
	 */
	@RequestMapping(value = "hr_job_hunter_form.json",method = RequestMethod.POST)
	public AppResultData<Object> submitHrHunterChangeForm(
			@RequestParam("user_id") Long userId,
			@RequestParam("user_name")String userName,
			@RequestParam("city_name")String cityName,
			@RequestParam("reward")String reward,
			@RequestParam("publish_title")String title,
			@RequestParam("publish_limit_day")Short limitDay,
			@RequestParam("publish_job_res")String jobRes,
			@RequestParam("publish_job_req")String jobReq,
			@RequestParam("remarks")String remarks){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users users = userService.selectByPrimaryKey(userId);
		
		if(users == null){
			result.setData("用户不存在");
			result.setStatus(Constants.ERROR_999);
			return result;
		}
		
		if(!users.getName().equals(userName)){
			result.setData("用户不匹配");
			result.setStatus(Constants.ERROR_999);
			return result;
		}
		
		HrJobHunter hunter = hunterService.initHrJobHunter();
		
		hunter.setUserId(userId);
		hunter.setName(userName);
		hunter.setCityName(cityName);
		hunter.setLimitDay(limitDay);
		hunter.setTitle(title);
		hunter.setJobRes(jobRes);
		hunter.setJobReq(jobReq);
		hunter.setRemarks(remarks);
		hunter.setReward(reward);
		
		hunterService.insert(hunter);
		
		return result;
	}
	
	/**
	 * 
	  * @Title: resumeDetailForm
	  *
	  *	@Date : 2016年4月29日下午2:44:03
	  * @Description: 
				简历详情
	  * @param userId			登陆用户Id
	  * @param resumeId			主键
	  * @throws
	 */
	@RequestMapping(value = "hr_job_hunter_detail.json",method = RequestMethod.GET)
	public AppResultData<Object> resumeDetailForm(
			@RequestParam("partner_user_id")Long userId,
			@RequestParam("resume_id")Long resumeId){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users users = userService.selectByPrimaryKey(userId);
		
		if(users == null){
			result.setData("用户不存在");
			result.setStatus(Constants.ERROR_999);
			return result;
		}
		
		HrJobHunter hunter = hunterService.selectByPrimaryKey(resumeId);
		
		JobHunterVo hunterVo = hunterService.transToHunterVo(hunter);
		
		result.setData(hunterVo);
		
		return result;
	}
	
	
	
	
	
}
