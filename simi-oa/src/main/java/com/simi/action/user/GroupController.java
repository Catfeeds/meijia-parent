package com.simi.action.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.admin.AdminController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.user.Groups;
import com.simi.service.user.GroupService;
import com.simi.service.user.GroupUserService;
import com.simi.vo.user.GroupVo;
import com.simi.vo.user.GroupsSearchVo;

@Controller
@RequestMapping(value = "/user")
public class GroupController extends AdminController {

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupUserService groupUserService;

	@AuthPassport
	@RequestMapping(value = "/groupList", method = { RequestMethod.GET })
	public String groupList(HttpServletRequest request, Model model) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());


		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		GroupsSearchVo searchVo = new GroupsSearchVo();
		PageInfo result = groupService.selectByListPage(searchVo, pageNo, pageSize);
		
		List<Groups> list = result.getList();
		List<GroupVo> vos = new ArrayList<GroupVo>();
		
		for (int i = 0; i < list.size(); i++) {
			Groups item = list.get(i);
			GroupVo vo = new GroupVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			int total = groupUserService.totalByGroupId(vo.getGroupId());
			vo.setTotal(total);
			list.set(i, vo);
		}
		
		model.addAttribute("contentModel", result);

		return "user/groupList";
	}
	
	@AuthPassport
	@RequestMapping(value = "/groupForm", method = { RequestMethod.GET })
	public String groupForm(
		HttpServletRequest request, Model model, @RequestParam(value = "id") Long id) {

		Groups g = groupService.initGroups();
		
		if (id > 0L) g = groupService.selectByPrimaryKey(id);
		
		model.addAttribute("contentModel", g);
		
		return "user/groupForm";
	}
	
	@AuthPassport
	@RequestMapping(value = "/groupForm", method = { RequestMethod.POST })
	public String doGroupForm(
		HttpServletRequest request, Model model, @ModelAttribute("contentModel") Groups record, BindingResult result) {

		String name = record.getName();
		Long groupId = record.getGroupId();
		GroupsSearchVo searchVo = new GroupsSearchVo();
		searchVo.setName(name);
		List<Groups> list = groupService.selectBySearchVo(searchVo);
		if (!list.isEmpty()) {
			Groups item = list.get(0);
			if (groupId.equals(0L) || !item.getGroupId().equals(groupId)) {
				result.addError(new FieldError("contentModel","name","名称重复"));
				model.addAttribute("contentModel", record);
				return groupForm(request, model, groupId);
			}
		}
		
		if (groupId.equals(0L)) {
			record.setAddTime(TimeStampUtil.getNowSecond());
			groupService.insert(record);
		} else {
			groupService.updateByPrimaryKeySelective(record);
		}
		
		return "redirect:groupList";
	}

}
