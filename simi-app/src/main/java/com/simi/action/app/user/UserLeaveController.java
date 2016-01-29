package com.simi.action.app.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meijia.utils.DateUtil;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserLeave;
import com.simi.po.model.user.UserLeavePass;
import com.simi.po.model.user.Users;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.user.UserLeavePassService;
import com.simi.service.user.UserLeaveService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.UserLeaveSearchVo;
import com.simi.vo.card.LinkManVo;
import com.simi.vo.user.UserLeaveDetailVo;
import com.simi.vo.user.UserLeaveListVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserLeaveController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserLeaveService userLeaveService;	
	
	@Autowired
	private UserLeavePassService userLeavePassService;		
	
	@Autowired
	private UsersAsyncService usersAsyncService;	
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	

	// 用户请假接口
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_leave", method = RequestMethod.POST)
	public AppResultData<Object> postImgs(
			@RequestParam("company_id") Long companyId,
			@RequestParam("user_id") Long userId,
			@RequestParam("leave_type") Short leaveType,
			@RequestParam("start_date") String startDateStr,
			@RequestParam("end_date") String endDateStr,
			@RequestParam("total_days") String totalDays,
			@RequestParam("remarks") String remarks,
			@RequestParam("pass_users") String passUsers,
			@RequestParam(value = "imgs", required = false) MultipartFile[] files
			) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		//验证时间是否正确		
		if (!startDateStr.equals(endDateStr)) {
			
			if (!DateUtil.compare(startDateStr, endDateStr)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("结束日期必须大于等于开始日期");
				return result;
			}
		}
		
		Date startDate = DateUtil.parse(startDateStr);
		Date endDate = DateUtil.parse(endDateStr);
		
		//查询时间段内是否已经请过假.
		UserLeaveSearchVo  searchVo = new UserLeaveSearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setUserId(userId);
		searchVo.setStartDate(startDate);
		searchVo.setEndDate(endDate);
		
		List<Short> status = new ArrayList<Short>();
		status.add((short) 0);
		status.add((short) 1);
		searchVo.setStatus(status);
		
		List<UserLeave> rsList = userLeaveService.selectBySearchVo(searchVo);
		
		if (!rsList.isEmpty()) {
			if (!DateUtil.compare(startDateStr, endDateStr)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("你已经有相关日期的请假记录!");
				return result;
			}
		}
		
		//插入数据
		UserLeave record = userLeaveService.initUserLeave();

		record.setCompanyId(companyId);
		record.setUserId(userId);
		record.setLeaveType(leaveType);
		record.setStartDate(startDate);
		record.setEndDate(endDate);
		record.setTotalDays(totalDays);
		record.setRemarks(remarks);
		record.setStatus((short) 0);
				
		// 处理图片问题
		String imgs = "";
		if (files != null && files.length > 0) {

			for (int i = 0; i < files.length; i++) {
				String url = Constants.IMG_SERVER_HOST + "/upload/";
				String fileName = files[i].getOriginalFilename();
				String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
				fileType = fileType.toLowerCase();
				String sendResult = ImgServerUtil.sendPostBytes(url, files[i].getBytes(), fileType);

				ObjectMapper mapper = new ObjectMapper();

				HashMap<String, Object> o = mapper.readValue(sendResult, HashMap.class);

				String ret = o.get("ret").toString();

				HashMap<String, String> info = (HashMap<String, String>) o.get("info");

				String imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();
				
				imgs+= imgUrl + ",";

			}
		}
		
		if (!StringUtil.isEmpty(imgs)) {
			record.setImgs(imgs);
		}
		
		userLeaveService.insert(record);
		
		Long leaveId = record.getLeaveId();
		//处理多个审批人员的问题
		
		if (!StringUtil.isEmpty(passUsers)) {
			Gson gson = new Gson();
			List<LinkManVo> linkManList = gson.fromJson(passUsers, new TypeToken<List<LinkManVo>>(){}.getType() ); 
			System.out.println(linkManList.toString());
			if (linkManList != null) {
				
				LinkManVo item = null;
				for (int i = 0; i < linkManList.size(); i++) {
					System.out.println(linkManList.get(i).toString());
					item = linkManList.get(i);
					String mobile = item.getMobile();
					mobile = mobile.replaceAll(" ", "");  
					
					Long newUserId = 0L;
					newUserId = item.getUser_id();
					
					if (newUserId == null || newUserId.equals(0L)) {
						if (StringUtil.isEmpty(mobile)) continue;
						if (!RegexUtil.isMobile(mobile)) continue;
					}

					
					//根据手机号找出对应的userID, 如果没有则直接新增用户.
					
					Users newUser = null;
					
					if (newUserId != null && newUserId > 0L) {
						newUser = userService.selectByPrimaryKey(newUserId);
					} else {
						newUser = userService.selectByMobile(mobile);
					}
					
					if (newUser == null) {
						newUser = userService.genUser(mobile, item.getName(), (short) 3);					
						usersAsyncService.genImUser(newUser.getId());
					}
					
					newUserId = newUser.getId();
					

					UserLeavePass userLeavePass =  userLeavePassService.initUserLeavePass();

					userLeavePass.setLeaveId(leaveId);
					userLeavePass.setCompanyId(companyId);
					userLeavePass.setUserId(userId);
					userLeavePass.setPassUserId(newUserId);
					userLeavePass.setPassStatus((short) 0);
					
					userLeavePassService.insert(userLeavePass);

				}
			}
		}
		
		//生成请假消息，审批人推送消息
		userMsgAsyncService.newLeaveMsg(userId, leaveId);
		
		result.setData(leaveId);

		return result;
	}	
	
	// 用户请假列表接口
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "leave_list", method = RequestMethod.GET)
	public AppResultData<Object> list(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "leave_from", required = false, defaultValue = "0") String leaveFrom,
			@RequestParam(value = "leave_type", required = false, defaultValue = "") String leaveTypeParam,
			@RequestParam(value = "status", required = false, defaultValue = "") String statusParam,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserLeaveSearchVo searchVo = new UserLeaveSearchVo();
		if (leaveFrom.equals("1")) {
			searchVo.setPassUserId(userId);
		} else {
			searchVo.setUserId(userId);
		}
		
		
		if (!StringUtil.isEmpty(leaveTypeParam)) {
			Short leaveType = Short.valueOf(leaveTypeParam);
			searchVo.setLeaveType(leaveType);
		}
		
		if (!StringUtil.isEmpty(statusParam)) {
			Short status = Short.valueOf(statusParam);
			List<Short> statusAry = new ArrayList<Short>();
			statusAry.add(status);
			searchVo.setStatus(statusAry);
		}
		
		PageInfo  pageInfo = userLeaveService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<UserLeave> list = pageInfo.getList();
		
		List<UserLeaveListVo> listVo = userLeaveService.changeToListVo(list);
		
		result.setData(listVo);
		
		return result;
	}
	
	// 用户请假详情接口
	@RequestMapping(value = "leave_detail", method = RequestMethod.GET)
	public AppResultData<Object> leaveDetail(
			@RequestParam("user_id") Long userId,
			@RequestParam("leave_id") Long leaveId) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserLeave userLeave = userLeaveService.selectByPrimaryKey(leaveId);
		
		if (userLeave == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("请假信息不存在!");
			return result;
		}
		
//		if (!userLeave.getUserId().equals(userId)) {
//			result.setStatus(Constants.ERROR_999);
//			result.setMsg("请假信息不存在!");
//			return result;
//		}
		
		UserLeaveDetailVo data = userLeaveService.changeToDetailVo(userLeave);
		result.setData(data);
		
		return result;
	}
	
	// 用户请假审批接口
	@RequestMapping(value = "leave_pass", method = RequestMethod.POST)
	public AppResultData<Object> leaveaPass(
			@RequestParam("pass_user_id") Long userId,
			@RequestParam("leave_id") Long leaveId,
			@RequestParam("status") Short status,
			@RequestParam(value = "remarks", required = false, defaultValue = "") String remarks
			) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserLeave userLeave = userLeaveService.selectByPrimaryKey(leaveId);
		
		if (userLeave == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("请假信息不存在!");
			return result;
		}
		
		if (userLeave.getStatus().equals((short)3)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("请假信息已撤销!");
			return result;
		}
		
		List<UserLeavePass> userLeavePass = userLeavePassService.selectByLeaveId(leaveId);
		

		UserLeavePass leavePass = null;
		for (UserLeavePass item : userLeavePass) {
			if (item.getPassUserId().equals(userId)) {
				leavePass = item;
				break;
			}
		}
		
		if (leavePass == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您不是请假审批人!");
			return result;
		}
		
		leavePass.setPassStatus(status);
		leavePass.setRemarks(remarks);
		leavePass.setUpdateTime(TimeStampUtil.getNowSecond());
		userLeavePassService.updateByPrimaryKey(leavePass);
		
		
		userLeavePass = userLeavePassService.selectByLeaveId(leaveId);
		Boolean isDone = true;
		Short passStatusAll = 1;
		for (UserLeavePass item : userLeavePass) {
			if (item.getPassStatus().equals((short)0)) {
				isDone = false;
			}
			if (item.getPassStatus().equals((short)2)) {
				passStatusAll = 2;
			}
		}
		
		if (isDone) {
			userLeave.setStatus(passStatusAll);
			userLeave.setUpdateTime(TimeStampUtil.getNowSecond());
			userLeaveService.updateByPrimaryKey(userLeave);
		}
		
		return result;
	}	
	
	// 用户请假取消接口
	@RequestMapping(value = "leave_cancel", method = RequestMethod.POST)
	public AppResultData<Object> leaveaPass(
			@RequestParam("user_id") Long userId,
			@RequestParam("leave_id") Long leaveId
			) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		UserLeave userLeave = userLeaveService.selectByPrimaryKey(leaveId);
		
		if (userLeave == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("请假信息不存在!");
			return result;
		}
		
		if (!userLeave.getStatus().equals((short)0)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("请假信息目前状态不能取消!");
			return result;
		}
		
		
		userLeave.setStatus((short) 3);
		userLeave.setUpdateTime(TimeStampUtil.getNowSecond());
		userLeaveService.updateByPrimaryKey(userLeave);
		
		
		return result;
	}		
}
