package com.simi.action.sec;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.google.zxing.Result;
import com.meijia.utils.MeijiaUtil;
import com.simi.action.admin.AdminController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.Users;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UserDetailPayService;
import com.simi.service.user.UsersService;
import com.simi.vo.user.UserApplyVo;
@Controller
@RequestMapping(value = "/sec")
public class SecController extends AdminController {

	@Autowired
	private UsersService usersService;
	

	@Autowired
	private TagsService tagsService;

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
		
		//用户对应的标签列表
		List<TagUsers> tagUserslist = tagsUsersService.selectByUserId(id);
		List<Long> tagIds = new ArrayList<Long>();
		for (TagUsers item : tagUserslist) {
			tagIds.add(item.getTagId());
		}
		//用户对应的标签名列表
		
		
		if (tagIds!=null) {
			List<Tags> tagList =tagsService.selectByTagIds(tagIds);
			//vo.setTagList(tagList);
			List<String> tagNameList = new ArrayList<String>();
			for (Tags item : tagList) {
				tagNameList.add(item.getTagName());
			}
			vo.setTagNamelist(tagNameList);
			
			
			model.addAttribute("tagList", tagNameList);
		}
		
		
		
		model.addAttribute("contentModel", vo);
		//model.addAttribute("tagList", "");
		
		
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
