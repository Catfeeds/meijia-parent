package com.simi.action.admin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simi.action.BaseController;
import com.simi.oa.auth.AccountAuth;
import com.simi.oa.auth.AccountRole;
import com.simi.oa.auth.AuthHelper;
import com.simi.oa.auth.AuthorityMenu;
import com.simi.oa.auth.PermissionMenu;
import com.simi.oa.vo.account.AccountLoginVo;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.admin.AdminAuthority;
import com.simi.po.model.admin.AdminRole;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.admin.AdminAuthorityService;
import com.simi.service.admin.AdminRoleService;


@Controller
@RequestMapping(value = "/account")
public class AdminLoginController extends BaseController {

	@Autowired
    private AdminAccountService adminAccountService;

	@Autowired
    private AdminRoleService adminRoleService;

	@Autowired
    private AdminAuthorityService adminAuthorityService;

	@RequestMapping(value="/login", method = {RequestMethod.GET})
    public String login(Model model){
		if(!model.containsAttribute("contentModel"))
            model.addAttribute("contentModel", new AccountLoginVo());
        return "account/login";
    }

	@RequestMapping(value="/login", method = {RequestMethod.POST})
	public String login(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") AccountLoginVo accountLoginVo, BindingResult result) throws ValidationException, NoSuchAlgorithmException{
		//如果有验证错误 返回到form页面
        if (result.hasErrors())
            return login(model);

        String username = accountLoginVo.getUsername().trim();
        String password = accountLoginVo.getPassword().trim();
        AdminAccount account = adminAccountService.login(username, password);

    	if ( account == null ) {
        	result.addError(new FieldError("contentModel","username","用户名或密码错误。"));
        	result.addError(new FieldError("contentModel","password","用户名或密码错误。"));
        	return login(model);
    	}

        if ( account.getEnable() == 0 ) {
        	result.addError(new FieldError("contentModel","username","此用户被禁用，不能登录。"));
        	return login(model);
		}

        if (account.getRoleId().equals(0)) {
        	result.addError(new FieldError("contentModel","username","此用户当前未被授权，不能登录。"));
        	return login(model);
        }

		Long userId = account.getId();
		String name = account.getName();
		Long roleId = account.getRoleId();

		AdminRole role = adminRoleService.selectByPrimaryKey(roleId);

        AccountAuth accountAuth=new AccountAuth(userId, name, username);
        AccountRole accountRole=new AccountRole(role.getId(), role.getName());
    	List<AuthorityMenu> authorityMenus = new ArrayList<AuthorityMenu>();
    	List<AdminAuthority> roleAuthorities= adminAuthorityService.selectByRole(roleId);

    	//获得自身权限列表.

    	for(AdminAuthority authority :roleAuthorities){

    		if(authority.getParentId() == null || authority.getParentId() ==  0 ) {

    			AuthorityMenu authorityMenu=new AuthorityMenu(authority.getId(), authority.getName(), authority.getItemIcon(), authority.getUrl());

    			List<AuthorityMenu> childrenAuthorityMenus=new ArrayList<AuthorityMenu>();
    			for(AdminAuthority subAuthority :roleAuthorities) {
    				if(subAuthority.getParentId() !=null &&
    				   subAuthority.getParentId().equals(authority.getId())
    				   )
    					childrenAuthorityMenus.add(new AuthorityMenu(subAuthority.getId(), subAuthority.getName(), subAuthority.getItemIcon(), subAuthority.getUrl()));
    			}
    			authorityMenu.setChildrens(childrenAuthorityMenus);
    			authorityMenus.add(authorityMenu);
    		}
    	}

    	//获得上级的权限列表菜单.
		List<PermissionMenu> permissionMenus=new ArrayList<PermissionMenu>();

		for(AdminAuthority authority : roleAuthorities){
    		List<AdminAuthority> parentAuthorities=new ArrayList<AdminAuthority>();
    		AdminAuthority tempAuthority = authority;
    		while(tempAuthority.getParentId() != null && tempAuthority.getParentId() > 0 ) {
    			parentAuthorities.add(tempAuthority);
    			tempAuthority = adminAuthorityService.selectByPrimaryKey(tempAuthority.getParentId());
    		}

    		if(parentAuthorities.size()>=2)
    			permissionMenus.add(new PermissionMenu(parentAuthorities.get(parentAuthorities.size()-1).getId(),parentAuthorities.get(parentAuthorities.size()-1).getName(),parentAuthorities.get(parentAuthorities.size()-2).getId(),parentAuthorities.get(parentAuthorities.size()-2).getName(),authority.getName(),authority.getMatchUrl(), authority.getUrl()));
    		else if(parentAuthorities.size()==1)
    			permissionMenus.add(new PermissionMenu(parentAuthorities.get(0).getId(),parentAuthorities.get(0).getName(),authority.getId(),authority.getName(),authority.getName(),authority.getMatchUrl(), authority.getUrl()));
    		else
    			permissionMenus.add(new PermissionMenu(authority.getId(),authority.getName(),null,null,authority.getName(),authority.getMatchUrl(), authority.getUrl()));
    	}

    	accountRole.setAuthorityMenus(authorityMenus);
    	accountRole.setPermissionMenus(permissionMenus);
    	accountAuth.setAccountRole(accountRole);
    	AuthHelper.setSessionAccountAuth(request, accountAuth);


        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="/home/index";
    	return "redirect:"+returnUrl;

	}

	@RequestMapping(value="/logout", method = {RequestMethod.GET})
	public String logout(HttpServletRequest request) {
		AuthHelper.removeSessionAccountAuth(request, "accountAuth");
		return "redirect:/account/login";
	}

}
