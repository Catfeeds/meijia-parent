package com.simi.action.msg;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.vo.AppResultData;
import com.simi.action.admin.AdminController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.oa.auth.AccountAuth;
import com.simi.oa.auth.AuthHelper;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.msg.Msg;
import com.simi.po.model.user.Users;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.msg.MsgService;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.MsgSearchVo;
import com.simi.vo.msg.OaMsgVo;
import com.simi.vo.user.UserSearchVo;
@Controller
@RequestMapping(value = "/msg")
public class MsgController extends AdminController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UserSmsTokenService userSmsTokenService;
	
	@Autowired
	private MsgService msgService;
	
	@Autowired
	private TagsService tagsService;

	@Autowired
	private TagsUsersService tagsUsersService;
	
	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	private UserDetailPayService userDetailPayService;
	
	@Autowired
	private AdminAccountService accountService;
	
	@Autowired
	private NoticeAppAsyncService noticeAsyncService;
	
	@Autowired
	private UserLoginedService userLoginedService;
	
	/**
	 * 消息列表
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
    @AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String msgList(HttpServletRequest request, MsgSearchVo searchVo,Model model) throws UnsupportedEncodingException {
    	
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		//分页
		PageHelper.startPage(pageNo, pageSize);
		//若搜索条件为空，则展示全部
		if (searchVo == null) {
			searchVo = new MsgSearchVo();
		}
		model.addAttribute("searchVoModel",searchVo);
		//设置中文 参数 编码，解决 中文 乱码
		searchVo.setTitle(new String(searchVo.getTitle().getBytes("iso-8859-1"),"utf-8"));
	
		List<Msg> list = msgService.selectMsgListBySearchVo(searchVo,pageNo,pageSize);
		
		PageInfo result = new PageInfo(list);
		
		model.addAttribute("contentModel", result);

		return "msg/msgList";
	}
    
    /*
     * 消息 信息 form
     */
    @AuthPassport
	@RequestMapping(value = "/msgForm",method = RequestMethod.GET)
	public String  orderDetail(HttpServletRequest request,
			@RequestParam(value = "id") Long msgId,Model model) throws ParseException{
		
    	//Long msgIdsss = Long.valueOf(request.getParameter("msgId"));
    	
    	if (msgId == null) {
    		msgId = 0L;
		}

		Msg msg = msgService.initMsg();
		if (msgId != null && msgId > 0) {
			msg = msgService.selectByPrimaryKey(msgId);
		}
//		Long sendTime = msg.getSendTime();
//		
//		// 时间戳 转为 UTC格式 date类型的 时间，供前台展示
//		Date date = DateUtil.timeStampToDate(sendTime);
//		
		//转换 发送时间  sendTime 时间戳--> utc 时间
//		model.addAttribute("sendTimeDate", date);
		
		
		//默认 保存 并立即 发送 
		OaMsgVo msgVo = msgService.initOaMsgVo(msg);
		
		model.addAttribute("contentModel", msgVo);
		
		return "msg/msgForm";
	}
    
    
    /**
     * 保存并 发送  消息
     * @param model
     * @param id
     * @param request
     * @return
     */
    @AuthPassport
	@RequestMapping(value = "/saveMsgForm.json", method = { RequestMethod.POST})
	public AppResultData<Object> adForm(Model model,
			@ModelAttribute("contentModel") OaMsgVo msgVo,
			BindingResult result,HttpServletRequest request)throws IOException {
    	
    	AppResultData<Object> returnRe = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
    	
    	Long msgId = msgVo.getMsgId();
    	
    	Msg record = msgService.initMsg();
    	
    	BeanUtilsExp.copyPropertiesIgnoreNull(msgVo, record);
    	
    	//入库
    	if (msgId > 0L ) {
    		msgService.updateByPrimaryKeySelective(record);
		}
    	
    	Short send = msgVo.getIsSend();
    	
    	//只有 msgId 和 发送状态 同时 为 0 ，才视为新增 
    	if(msgId == 0L && send == 0){
    		msgService.insertSelective(record);
    	}else{
    		msgService.updateByPrimaryKeySelective(record);
    	}
    	
    	//是否 可用
    	Short isEnable = msgVo.getIsEnable();
    	
    	if(isEnable == 0){
    		// 如果 选择 不可用. 则 不发送
    		returnRe.setStatus(Constants.ERROR_100);
    		return returnRe;
    	}
    	
    	//发送方式    1= 保存并立即发送   0= 测试发送
    	Short sendWay = msgVo.getSendWay();
    	
    	String title = msgVo.getTitle();
    	String content = msgVo.getContent();
    	
    	if(sendWay == 0){
    		
    		//如果是 测试 发送, 发消息 给 运营部人员
    		
    		Long roleId = 3L;
    		List<AdminAccount> adminAccounts = accountService.selectByRoleId(roleId);
    		
    		for (AdminAccount adminAccount : adminAccounts) {
				
    			if (!StringUtil.isEmpty(adminAccount.getMobile())) {
    				Users uu = usersService.selectByMobile(adminAccount.getMobile());
    				
    				//异步推送 给 测试 人员（运营部），消息
    				noticeAsyncService.pushMsgToDevice(uu.getId(),title,content);
    			}
			}
    			
    	}else{
    		
    		Short selectUserType = msgVo.getUserType();
    		
    		List<Users> userList = new ArrayList<Users>();
    		
    		//最近一个月 登录过的用户。
    		List<Long> lastMonthUser = userLoginedService.selectUserIdsLastMonth();
    		
    		UserSearchVo  searchVo = new UserSearchVo();
    		
    		if(selectUserType == 0){
    			//选择 普通用户（一个月的）
    			searchVo.setUserType(Constants.OA_PUSH_USER_TYPE_0);
    			searchVo.setUserIds(lastMonthUser);
    			
    			userList = usersService.selectBySearchVo(searchVo);
    			
    		}
    		if(selectUserType == 1){
    			//选择秘书
    			searchVo.setUserType(Constants.OA_PUSH_USER_TYPE_1);
    			
    			userList = usersService.selectBySearchVo(searchVo);
    			
    		}
    		if(selectUserType == 2){
    			//选择 服务商
    			searchVo.setUserType(Constants.OA_PUSH_USER_TYPE_2);
    			
    			userList = usersService.selectBySearchVo(searchVo);
    		}
    		
    		if(selectUserType == 3){
    			
    			//选择 的 全部用户 = 一个月的普通用户+秘书+服务商
    			searchVo.setUserType(Constants.OA_PUSH_USER_TYPE_0);
    			searchVo.setUserIds(lastMonthUser);
    			userList = usersService.selectBySearchVo(searchVo);
    			
    			searchVo = new UserSearchVo();
    			searchVo.setUserType(Constants.OA_PUSH_USER_TYPE_1);
    			List<Users> msList = usersService.selectBySearchVo(searchVo);
    			
    			userList.addAll(msList);
    			
    			searchVo = new UserSearchVo();
    			searchVo.setUserType(Constants.OA_PUSH_USER_TYPE_2);
    			List<Users> fwList = usersService.selectBySearchVo(searchVo);
    			
    			userList.addAll(fwList);
    		}
    		
    		//异步推送
			for (Users users : userList) {
				noticeAsyncService.pushMsgToDevice(users.getId(), title,content);
			}	
			
    	}
    	
    	if(send == 0){
    		record.setIsSend((short)1);
    		msgService.updateByPrimaryKeySelective(record);
    	}
    	
    	return returnRe;
    }
 
}
