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
import com.simi.po.model.resume.HrResumeChange;
import com.simi.po.model.user.UserTrailReal;
import com.simi.po.model.user.Users;
import com.simi.service.resume.HrResumeChangeService;
import com.simi.service.user.UserTrailRealService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.resume.ResumeChangeVo;
import com.simi.vo.resume.ResumeSearchVo;

/**
 *
 * @author :hulj
 * @Date : 2016年4月28日下午4:46:20
 * @Description: 
 *		
 *		简历交换
 */
@Controller
@RequestMapping(value = "/app/resume")
public class ResumeChangeController extends BaseController {
	
	@Autowired
	private HrResumeChangeService resumeChangeService;
	@Autowired
	private UserTrailRealService  userTrRealService;
	@Autowired
	private UsersService userService;
	/**
	 * 
	  * @Title: hrResumeChangeList
	  *
	  *	@Date : 2016年4月28日下午5:01:26
	  * @Description: 
	  * 	简历交换 列表页
	  * 
	  * @param partnerUserId		登录用户id
	  * @param page					分页页码
	  * @return    设定文件
	  * @return AppResultData<Object>    返回类型
	  * @throws
	 */
	@RequestMapping(value = "hr_resume_change_list",method = RequestMethod.GET)
	public AppResultData<Object> hrResumeChangeList(
			@RequestParam("partner_user_id") Long partnerUserId, 
			@RequestParam(value = "page", required = false, defaultValue = "1") int page){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		//1. 校验 用户
		if (partnerUserId > 0L) {
			Users u = userService.selectByPrimaryKey(partnerUserId);
			if (u == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("用户不存在");
				return result;
			}
		}
		
		ResumeSearchVo searchVo = new ResumeSearchVo();
		if (partnerUserId > 0L) searchVo.setUserId(partnerUserId);
		
		//分页
		PageInfo info = resumeChangeService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		
		List<HrResumeChange> list = info.getList();
		
		//转换为 VO
		List<ResumeChangeVo> voList = new ArrayList<ResumeChangeVo>();
		for (HrResumeChange hrResumeChange : list) {
			ResumeChangeVo changeVo = resumeChangeService.transToResumeVo(hrResumeChange);
			voList.add(changeVo);
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
	  * 		简历交换--发布需求 form页	 初始化
	  * 		
	  * @param partnerUserId		登录用户id
	  * @return AppResultData<Object>    返回类型
	  * @throws
	 */
	@RequestMapping(value = "hr_resume_change_form",method = RequestMethod.GET)
	public AppResultData<Object> hrResumeChangeForm(
			@RequestParam("partner_user_id") Long partnerUserId){
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		ResumeChangeVo vo = resumeChangeService.initResumeChangeVo();
		
		UserTrailReal trailReal = userTrRealService.selectByUserId(partnerUserId);
		
		if(trailReal != null){
			
			String city = trailReal.getCity();
			vo.setCityName(city);
		}
		
		Users users = userService.selectByPrimaryKey(partnerUserId);
		String name = users.getName();	
		
		
		//用户信息
		vo.setUserName(name);
		
		//有效期选择
		Map<Long, String> map = new HashMap<Long, String>();
		
		map.put(0L, "长期有效");
		map.put(30L, "30天有效");
		map.put(180L, "6个月有效");
		
		vo.setTimeMap(map);
		
		result.setData(vo);
		
		return result;
	}
	
	
	/**
	 * 
	  * @Title: submitHrResumeChangeForm
	  *
	  *	@Date : 2016年4月28日下午7:18:02
	  * @Description: 
	  * 		提交 需求
	  * @param userId
	  * @param userName
	  * @param cityName	
	  * @param title			标题
	  * @param limitDay			时效
	  * @param content			需求内容
	  * @param link				联系方式
	  * @throws
	 */
	@RequestMapping(value = "hr_resume_change_form",method = RequestMethod.POST)
	public AppResultData<Object> submitHrResumeChangeForm(
			@RequestParam("user_id") Long userId,
			@RequestParam("user_name")String userName,
			@RequestParam("city_name")String cityName,
			@RequestParam("publish_title")String title,
			@RequestParam("publish_limit_day")Short limitDay,
			@RequestParam("publish_need_content")String content,
			@RequestParam("publish_link")String link){
		
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
		
		
		HrResumeChange change = resumeChangeService.initHrResumeChange();
		
		change.setUserId(userId);
		change.setName(userName);
		change.setCityName(cityName);
		change.setLimitDay(limitDay);
		change.setTitle(title);
		change.setContent(content);
		change.setLink(link);
		
		resumeChangeService.insert(change);
		
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
	@RequestMapping(value = "hr_resume_detail",method = RequestMethod.GET)
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
		
		HrResumeChange resumeChange = resumeChangeService.selectByPrimaryKey(resumeId);
		
		ResumeChangeVo changeVo = resumeChangeService.transToResumeVo(resumeChange);
		
		result.setData(changeVo);
		
		return result;
	}
	
	
	
	
}
