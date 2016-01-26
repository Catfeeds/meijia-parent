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
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.feed.FeedImgs;
import com.simi.po.model.feed.Feeds;
import com.simi.po.model.user.UserLeave;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserLeaveService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.UserLeaveSearchVo;
import com.simi.vo.UserMsgSearchVo;
import com.simi.vo.user.UserMsgVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserLeaveController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserLeaveService userLeaveService;	
	

	// 用户请假接口
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_imgs", method = RequestMethod.POST)
	public AppResultData<Object> postImgs(
			@RequestParam("compnay_id") Long companyId,
			@RequestParam("user_id") Long userId,
			@RequestParam("leave_type") Short leaveType,
			@RequestParam("start_date") String startDateStr,
			@RequestParam("end_date") String endDateStr,
			@RequestParam("total_days") String totalDays,
			@RequestParam("remarks") String remarks,
			@RequestParam(value = "pass_users", required = false, defaultValue = "") String passUsers,
			@RequestParam(value = "imgs", required = false, defaultValue = "") MultipartFile[] files
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
		
		List<Long> status = new ArrayList<Long>();
		status.add(0L);
		status.add(1L);
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
		
		//处理审批人员问题.
		
		
		
		//生成动态消息
//		userMsgAsyncService.newFeedMsg(fid);
		
//		result.setData(fid);

		return result;
	}	
	
}
