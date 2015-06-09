package com.simi.action.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;
import com.simi.models.TreeModel;
import com.simi.models.extention.TreeModelExtension;
import com.simi.oa.common.ArrayHelper;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.admin.AdminAuthority;
import com.simi.po.model.admin.AdminRole;
import com.simi.service.admin.AdminAuthorityService;
import com.simi.service.admin.AdminRoleService;
import com.meijia.utils.common.extension.StringHelper;
import com.simi.vo.admin.AdminRoleSearchVo;
import com.simi.vo.admin.AdminRoleVo;


@Controller
@RequestMapping(value = "/role")
public class AdminRoleController extends AdminController {

	@Autowired
	private AdminRoleService adminRoleService;

	@Autowired
	private AdminAuthorityService adminAuthorityService;

	//@AuthPassport
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String list1(HttpServletRequest request, Model model, AdminRoleSearchVo searchModel){
    	model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
        model.addAttribute("searchModel", searchModel);

        int pageNo = ServletRequestUtils.getIntParameter(request,ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		PageInfo result = adminRoleService.searchVoListPage(searchModel, pageNo, pageSize);
		model.addAttribute("contentModel",result);
        return "role/list";
    }


	//@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.GET})
	public String toAddAdminRole(@ModelAttribute("adminRole") AdminRole adminRole){

        return "role/addAdminRole";
	}

	//@AuthPassport
	@RequestMapping(value ="/addAdminRole", method = {RequestMethod.POST})
    public String addAdminRole(HttpServletRequest request, Model model,
    		@ModelAttribute("adminRole") AdminRole adminRole, BindingResult result)  {

		adminRole.setVersion(0L);
		adminRole.setEnable((short)0);
		adminRoleService.insertSelective(adminRole);

    	return "redirect:list";
    }

	//@AuthPassport
	@RequestMapping(value ="/edit/{id}", method = {RequestMethod.GET})
	public String editAdminRoleById(Model model,@PathVariable(value="id") String id,HttpServletRequest response)  {
		Long ids = 0L;
		if (id != null && NumberUtils.isNumber(id)) {
			ids = Long.valueOf(id.trim());
		}
		AdminRole  adminRole=  adminRoleService.selectByPrimaryKey(ids);
		model.addAttribute("adminRole",adminRole);

		return "role/editAdminRole";
	}

	//@AuthPassport
	@RequestMapping(value ="/saveAdminRole", method = {RequestMethod.POST})
	 public String saveAdminRole(HttpServletRequest request, Model model,
	    		@ModelAttribute("adminRole") AdminRole adminRole, BindingResult result)  {
			adminRole.setId(Long.valueOf(request.getParameter("id")));
			adminRoleService.updateByPrimaryKeySelective(adminRole);
	    	return "redirect:list";
	    }
	//@AuthPassport
	@RequestMapping(value ="/enable/{id}", method = {RequestMethod.GET})
	public String enableAdminRole(Model model,@PathVariable(value="id") String id,HttpServletRequest response)  {
		Long ids = 0L;
		if (id != null && NumberUtils.isNumber(id)) {
			ids = Long.valueOf(id.trim());
		}
		AdminRole adminRole = adminRoleService.selectByPrimaryKey(ids);
		adminRole.setEnable(ConstantOa.ROLE_ENABLE);
		adminRoleService.updateByPrimaryKeySelective(adminRole);
		return "redirect:/role/list";
	}
	//@AuthPassport
	@RequestMapping(value ="/disable/{id}", method = {RequestMethod.GET})
	public String disableAdminRole(Model model,@PathVariable(value="id") String id,HttpServletRequest response)  {
		Long ids = 0L;
		if (id != null && NumberUtils.isNumber(id)) {
			ids = Long.valueOf(id.trim());
		}
		AdminRole adminRole = adminRoleService.selectByPrimaryKey(ids);
		adminRole.setEnable(ConstantOa.ROLE_DISABLE);
		adminRoleService.updateByPrimaryKeySelective(adminRole);
		return "redirect:/role/list";
	}
	//@AuthPassport
	@RequestMapping(value ="/delete/{id}", method = {RequestMethod.GET})
	public String deleterAdminRole(Model model,@PathVariable(value="id") String id,HttpServletRequest response)  {
		Long ids = 0L;
		if (id != null && NumberUtils.isNumber(id)) {
			ids = Long.valueOf(id.trim());
		}
		String path = "redirect:/role/list";
		int result = adminRoleService.deleteByPrimaryKey(ids);
		if(result>0){
			return path;
		}else{
			return "error";
		}
	}
	/**
	 * 给对应id的用户绑定角色
	 * @param request
	 * @param model
	 * @param id
	 * @return 跳转到绑定权限的页面
	 */
	//@AuthPassport
	@RequestMapping(value="/bind/{id}", method = {RequestMethod.GET})
	public String bind(HttpServletRequest request, Model model, @PathVariable(value="id") String id) {
		
		Long ids = 0L;
		if (id != null && NumberUtils.isNumber(id)) {
			ids = Long.valueOf(id.trim());
		}
		//AdminRole role=adminRoleService.selectByPrimaryKey(ids);
		AdminRoleVo adminRoleVo = (AdminRoleVo)adminRoleService.selectAdminRoleVoByPrimaryKey(ids);
		List<Long> checkedAuthorityIds = new ArrayList<Long>();
		List<Integer> checkedAuthorityIntegers = new ArrayList<Integer>();

		if(adminRoleVo.getChildList()!=null){
			List<AdminAuthority> roleAuthorities=adminRoleVo.getChildList();
			for (Iterator iterator  = roleAuthorities.iterator(); iterator.hasNext();) {
				AdminAuthority adminAuthority = (AdminAuthority) iterator.next();
				if(adminAuthority!=null){
					checkedAuthorityIds.add(adminAuthority.getId());
					checkedAuthorityIntegers.add(adminAuthority.getId().intValue());
				}
			}
		}
		if(!model.containsAttribute("contentModel")){
			Long[] checkedAuthorityIdsArray=new Long[checkedAuthorityIds.size()];
			checkedAuthorityIds.toArray(checkedAuthorityIdsArray);
			adminRoleVo.setAuthorityIds(checkedAuthorityIdsArray);
			model.addAttribute("contentModel", adminRoleVo);
		}

		String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
		List<TreeModel> children=TreeModelExtension.ToTreeModels(adminAuthorityService.listChain(), null, checkedAuthorityIntegers, StringHelper.toIntegerList( expanded, ","));
		List<TreeModel> treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel(null,null,"根节点",false,false,false,children)));
		model.addAttribute(treeDataSourceName, JSONArray .fromObject(treeModels, new JsonConfig()).toString());

		return "role/bind";
	}
	/**
	 * 将权限和角色的对应关系，保存
	 * @param request
	 * @param model
	 * @param adminRoleVo
	 * @param id
	 * @param result
	 * @return 跳转到角色列表页面
	 */
	//@AuthPassport
	@RequestMapping(value="/bind/{id}", method = {RequestMethod.POST})
	public String bind(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") AdminRoleVo adminRoleVo,
			@PathVariable(value="id") String id, BindingResult result) {
        if(result.hasErrors())
            return bind(request, model, id);

        adminRoleService.saveRoleToAuthorize(Long.valueOf(id), ArrayHelper.removeArrayLongItem(adminRoleVo.getAuthorityIds(), new Integer(0)));
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="role/list";
    	return "redirect:"+returnUrl;
	}
}
