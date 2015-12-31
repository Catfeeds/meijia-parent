package com.simi.action.msg;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.simi.action.admin.AdminController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.msg.Msg;
import com.simi.service.msg.MsgService;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.MsgSearchVo;
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
    /**
     * 秘书详细信息列表展示
     * @param id
     * @param model
     * @return
     */
    //@AuthPassport
	@RequestMapping(value = "/msgForm",method = RequestMethod.GET)
	public String  orderDetail(HttpServletRequest request,
			@RequestParam(value = "msg_id") Long msgId,Model model){
		
    	//Long msgIdsss = Long.valueOf(request.getParameter("msgId"));
    	
    	if (msgId == null) {
    		msgId = 0L;
		}

		Msg msg = msgService.initMsg();
		if (msgId != null && msgId > 0) {
			msg = msgService.selectByPrimaryKey(msgId);
		}

		model.addAttribute("contentModel", msg);
		
		return "msg/msgForm";
	}
    
    /**
     * 信息修改保存
     * @param model
     * @param id
     * @param request
     * @return
     */
  //  @AuthPassport
	@RequestMapping(value = "/saveMsgForm", method = { RequestMethod.POST})
	public String adForm(Model model,
			/*@RequestParam(value = "id") Long id,
			@RequestParam(value = "isApproval") short isApproval,*/
			@ModelAttribute("contentModel") Msg msg,
			BindingResult result,HttpServletRequest request)throws IOException {
    	
    	Long msgId = msg.getMsgId();
    	
    	Msg record = msgService.initMsg();

    //	BeanUtilsExp.copyPropertiesIgnoreNull(msg, record);
    
    	if (msgId > 0L ) {
    		record = msg;
    		msgService.updateByPrimaryKeySelective(record);
		}else {
			msgService.insertSelective(record);
		}
    	
    	return "redirect:/msg/list";
    }
 
}
