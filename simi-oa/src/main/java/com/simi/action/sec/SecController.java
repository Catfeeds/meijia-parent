package com.simi.action.sec;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.mchange.v2.beans.BeansUtils;
import com.meijia.utils.DateUtil;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.admin.AdminController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.Users;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UsersService;
import com.simi.vo.user.UserApplyVo;
import com.sun.tools.classfile.Annotation.element_value;
@Controller
@RequestMapping(value = "/sec")
public class SecController extends AdminController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private TagsUsersService tagsUsersService;
	
	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	private UserDetailPayService userDetailPayService;

	
	/**
	 *  秘书申请列表
	 * @param request
	 * @param model
	 * @return
	 */
    @AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String secList(HttpServletRequest request, Model model) {
    	
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
	
		PageInfo result = usersService.selectByIsAppRovalYes(pageNo,
				pageSize);
		
		model.addAttribute("contentModel", result);

		return "sec/list";
	}
	
	/**
	 *  秘书申请列表
	 * @param request
	 * @param model
	 * @return
	 */
    @AuthPassport
	@RequestMapping(value = "/applyList", method = { RequestMethod.GET })
	public String applyList(HttpServletRequest request, Model model) {
    	
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
	
		PageInfo result = usersService.selectByIsAppRoval(pageNo,
				pageSize);
		
		model.addAttribute("contentModel", result);

		return "sec/applyList";
	}
    /**
     * 秘书详细信息列表展示
     * @param id
     * @param model
     * @return
     */
    @AuthPassport
	@RequestMapping(value = "/applyForm",method = RequestMethod.GET)
	public String  orderDetail(Long id ,Model model){
		
		Users u = usersService.selectVoByUserId(id);
		UserApplyVo vo = new UserApplyVo();
		
		vo.setId(u.getId());
		vo.setMobile(u.getMobile());
		vo.setProvinceName(u.getProvinceName());
		vo.setThirdType(u.getThirdType());
		vo.setOpenId(u.getOpenId());
		vo.setName(u.getName());
		vo.setRealName(u.getRealName());
		vo.setBirthDay(u.getBirthDay());
		vo.setIdCard(u.getIdCard());
		vo.setDegreeId(u.getDegreeId());
		vo.setMajor(u.getMajor());
		vo.setSex(u.getSex());
		vo.setHeadImg(u.getHeadImg());
		vo.setRestMoney(u.getRestMoney());
		vo.setUserType(u.getUserType());
		vo.setIsApproval(u.getIsApproval());
		vo.setAddFrom(u.getAddFrom());
		vo.setScore(u.getScore());
		vo.setAddTime(u.getAddTime());
		vo.setUpdateTime(u.getUpdateTime());
	
		/*
		try {
			BeanUtils.copyProperties(vo, u);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		//出生日期
        /*Date birthday = u.getBirthDay();
       
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(birthday);
		vo.setBirthDay(DateUtil.parse(dateStr));*/
		//性别
		String sex = u.getSex();
		if (sex == "0") {
			vo.setSexName("男");
		}else {
			vo.setSexName("女");
		}
		
		//是否审批名称
		short isApproval = u.getIsApproval();
		if (isApproval == 0) {
			vo.setIsApprovalName("未审批");
		}else {
			vo.setIsApprovalName("已审批");
		}
		
		//学历名称
		short degreeId = u.getDegreeId();
		if (degreeId == 0) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}if (degreeId == 1) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}if (degreeId == 2) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}if (degreeId == 3) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}if (degreeId == 4) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}
		if (degreeId == 5) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}
		if (degreeId == 6) {
			vo.setDegreeName(MeijiaUtil.getDegreeName(degreeId));
		}
		
		/*//用户标签列表
		List<TagUsers> list = tagsUsersService.select*/
		
		
		
		model.addAttribute("contentModel", vo);
		
		return "sec/applyListForm";
	}
    
    /**
     * 审批结果保存
     * @param model
     * @param id
     * @param request
     * @return
     */
    @AuthPassport
	@RequestMapping(value = "/saveApplyListForm", method = { RequestMethod.POST})
	public String adForm(Model model,
			@RequestParam(value = "id") Long id,
			HttpServletRequest request) {
    	
    	if (id == null) {
			id = 0L; 
		}
    	Users u= usersService.selectByPrimaryKey(id);
    	
    	u.setIsApproval((short)1);
    
    	usersService.updateByPrimaryKeySelective(u);
    	
    	model.addAttribute("contentModel", u);
    	
    	return "redirect:/sec/applyList";
    	
    }
}
