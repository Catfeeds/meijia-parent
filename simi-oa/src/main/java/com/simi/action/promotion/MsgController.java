package com.simi.action.promotion;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.github.pagehelper.PageInfo;
import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.promotion.Msg;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.simi.service.order.OrdersService;
import com.simi.service.promotion.MsgService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.meijia.utils.JspToHtmlFile;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.promotion.MsgSearchVo;

@Controller
@RequestMapping(value = "/msg")
public class MsgController extends BaseController {

	@Autowired
    private  MsgService msgService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private UserMsgService userMsgService;

	@Autowired
	private OrdersService ordersService;


	/**消息列表
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model,MsgSearchVo msgSearchVo
		) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel",msgSearchVo);

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result=msgService.searchListPage(msgSearchVo, pageNo, pageSize);

		model.addAttribute("contentModel",result);

		return "promotion/msgList";
	}

	/**
	 * 消息列表表单显示
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 */
	//@AuthPassport
	@RequestMapping(value = "/msgForm", method = { RequestMethod.GET })
	public String serviceTypeForm(Model model,
			@RequestParam(value = "id") Long id,
			HttpServletRequest request) {

		if (id == null) {
			id = 0L;
		}

		Msg msg = msgService.initMsg();
		if (id != null && id > 0) {
			msg = msgService.selectByPrimaryKey(id);
		}
//		model.addAttribute("requestUrl", request.getServletPath());
//		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("msgModel", msg);

		return "promotion/msgForm";
	}

	/**
	 * 消息保存数据方法.
	 *
	 * @param request
	 * @param model
	 * @param serviceType
	 * @param result
	 * @return
	 */
	//@AuthPassport
	@RequestMapping(value = "/msgForm", method = { RequestMethod.POST })
	public String doServiceTypeForm(HttpServletRequest request, Model model,
			@ModelAttribute("msg") Msg msg,
			BindingResult result) {

		Long id = Long.valueOf(request.getParameter("id"));
		Long isSended = Long.valueOf(request.getParameter("isSended"));
		
		
		if(id!=null && id >0){
			msg.setUpdateTime(TimeStampUtil.getNow() / 1000);
			msgService.updateByPrimaryKeySelective(msg);
		}else{
			msg.setHtmlUrl("");
			msg.setAddTime(TimeStampUtil.getNow()/1000);
			msg.setUpdateTime(0L);
	        msgService.insertSelective(msg);
		}
		/**
		 * 消息生成静态模板
		 */
		String sourcePath = request.getSession().getServletContext()
				.getRealPath("/WEB-INF/upload/html");
		String destPath = sourcePath + File.separator + msg.getId() + ".html";
		String templatePath = sourcePath + File.separator + "news_detail.html";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", msg.getTitle());
		map.put("content", msg.getContent());
		Msg msgTemp = msgService.selectByPrimaryKey(msg.getId());
		String addTime = TimeStampUtil.timeStampToDateStr(
				msgTemp.getAddTime() * 1000, "yyyy-MM-dd");
		map.put("addTime", addTime);
		JspToHtmlFile.JspToHtmlFile(templatePath, destPath, map);
		msg.setHtmlUrl("/simi-oa/upload/html/" + msg.getId() + ".html");
		msgService.updateByPrimaryKeySelective(msg);
		

		//更新或者新增
		if (id != null && id > 0) {
			 /**
			  * 根据sendGroup判断属于哪个群体
			  * 0=全部用户 1=已注册用户 2=未下单用户
			  * 更改用户群，
			  */
	        short sendGroup =  msg.getSendGroup();
	        List<Users> list = new ArrayList<Users>();
	        if(sendGroup==(short)0){
	        	list = usersService.selectByAll();
	        }else if(sendGroup==(short)1){
	        	List<String> mobileLists = ordersService.selectDistinctMobileLists();
	        	list = usersService.selectUsersHaveOrdered(mobileLists);

	        }else{
	        	List<String> mobileLists = ordersService.selectDistinctMobileLists();
	        	list = usersService.selectUsersNoOrdered(mobileLists);
	        }
	        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Users users = (Users) iterator.next();
				UserMsg userMsg = new UserMsg();
				userMsg.setUserId(users.getId());
				userMsg.setMsgId(msg.getId());
				userMsg.setMobile(users.getMobile());
				userMsg.setIsEnable(msg.getIsEnable());
				userMsg.setIsReaded((short)0);
				userMsgService.updateByPrimaryKeySelective(userMsg);
        	}
		} else {
	        short sendGroup =  msg.getSendGroup();
	        /**
	         * 根据sendGroup判断属于哪个群体 0=全部用户 1=已注册用户 2=未下单用户
	         */
	        List<Users> list = new ArrayList<Users>();
	        if(sendGroup==(short)0){
	        	list = usersService.selectByAll();

	        }else if(sendGroup==(short)1){
	        	List<String> mobileLists = ordersService.selectDistinctMobileLists();
	        	 list = usersService.selectUsersHaveOrdered(mobileLists);

	        }else{
	        	List<String> mobileLists = ordersService.selectDistinctMobileLists();
	        	list = usersService.selectUsersNoOrdered(mobileLists);
	        }
	        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Users users = (Users) iterator.next();
				UserMsg userMsg = new UserMsg();
				userMsg.setUserId(users.getId());
				userMsg.setMsgId(msg.getId());
				userMsg.setMobile(users.getMobile());
				userMsg.setIsEnable(msg.getIsEnable());
				userMsg.setIsReaded((short)0);
				userMsg.setAddTime(TimeStampUtil.getNow()/1000);
				userMsgService.insertSelective(userMsg);
			}
	       
	        if(isSended==0){
	        	//isSended=0表示消息会推送给相应用户
	        	try {
	        		//将消息置为已发送,设置消息的发送时间
					int sendTotal = msgService.pushMsgFromBaidu(msg.getId());
					msg.setSendTotal(sendTotal);
	        		msg.setSendStatus((short)1);
	        		msg.setLastSendTime(TimeStampUtil.getNow()/1000);
	        		msgService.updateByPrimaryKeySelective(msg);
				} catch (PushClientException e) {
					e.printStackTrace();
				} catch (PushServerException e) {
					e.printStackTrace();
				}
	        }
	       }
			return "redirect:list";
	}
	
	/**用户消息列表
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/user_msg_list", method = { RequestMethod.GET })
	public String userMsegList(HttpServletRequest request, Model model) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result=userMsgService.searchListPage(pageNo, pageSize);

		model.addAttribute("contentModel",result);

		return "promotion/userMsgList";
	}











}
