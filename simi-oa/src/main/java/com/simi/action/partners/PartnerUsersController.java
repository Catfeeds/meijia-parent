package com.simi.action.partners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.baidu.DwzUtil;
import com.simi.action.BaseController;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.Users;
import com.simi.service.partners.PartnerRefCityService;
import com.simi.service.partners.PartnerRefRegionService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UsersService;
import com.simi.vo.partners.PartnerUserVo;
import com.simi.vo.partners.PartnerUserSearchVo;


/**
 * @description：
 * @author： kerryg
 * @date:2015年8月11日 
 */
@Controller
@RequestMapping(value = "/partners")
public class PartnerUsersController extends BaseController{

	@Autowired
	private UsersService userService;
	
	@Autowired
	private TagsService tagsService;
	
	@Autowired
	private TagsUsersService tagsUsersService;	
	
	@Autowired
	private PartnersService partnersService;

	@Autowired
	private PartnerUserService partnerUserService;
	
	@Autowired
	private PartnerRefRegionService partnerRefRegionService;
	
	@Autowired
	private PartnerRefCityService partnerRefCityService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	/**
	 * 服务人员列表 
	 * @param request
	 * @param model
	 * @param searchVo
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/user_list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model,
			@RequestParam("partnerId") Long partnerId,
			PartnerUserSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
		if (searchVo == null) {
			searchVo = new PartnerUserSearchVo();
		}
		
		searchVo.setPartnerId(partnerId);
		
		model.addAttribute("searchModel", searchVo);
		
		model.addAttribute("partnerId", partnerId);
		
		String companyName = "";
		if (partnerId > 0L) {
			Partners parnter = partnersService.selectByPrimaryKey(partnerId);
			companyName = parnter.getCompanyName();
		}
		
		model.addAttribute("companyName", companyName);
		
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		PageInfo result = partnerUserService.selectByListPage(searchVo, pageNo, pageSize);
		
		

		model.addAttribute("contentModel", result);
		return "partners/partnerUserList";
	}
	

	/**
	 * 跳转到编辑服务人员的页面
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException
	 */
   // @AuthPassport
	@RequestMapping(value = "/user_form", method = { RequestMethod.GET })
	public String userForm(Model model, HttpServletRequest request,
			@RequestParam("id") Long id, @RequestParam("partnerId") Long partnerId,
			HttpServletRequest response)  {
    	
    	if (id == null) {
    		id = 0L;
    	}
    	
    	PartnerUsers partnerUser = partnerUserService.iniPartnerUsers();
    	Users u = userService.initUsers();
    	Long userId = u.getId();
    	
    	List<Tags> tags = tagsService.selectByTagType((short) 2);
    	List<Tags> userTags = new ArrayList<Tags>();
    	String  tagIds = "";
    	if (id > 0L) {
    		partnerUser = partnerUserService.selectByPrimaryKey(id);
    		userId = partnerUser.getUserId();
    		u = userService.selectByPrimaryKey(userId);
    		
    		List<TagUsers> tagUsers = tagsUsersService.selectByUserId(userId);
    		
    		for (TagUsers item : tagUsers) {
    			tagIds += item.getTagId() + ",";
    		}
    		
    	}
    	
    	Partners partner = partnersService.selectByPrimaryKey(partnerId);
    	
    	PartnerUserVo vo = new PartnerUserVo();
    	vo.setId(id);
    	vo.setPartnerId(partnerId);
    	vo.setCompanyName(partner.getCompanyName());
    	vo.setUserId(u.getId());
    	vo.setIntroduction(u.getIntroduction());
    	vo.setHeadImg(u.getHeadImg().trim());
    	vo.setName(u.getName());
    	vo.setMobile(u.getMobile());
    	vo.setResponseTime(partnerUser.getResponseTime());
    	vo.setServiceTypeId(partnerUser.getServiceTypeId());
    	vo.setUserTags(userTags);
    	vo.setProvinceId(partnerUser.getProvinceId());
    	vo.setCityId(partnerUser.getCityId());
    	vo.setRegionId(partnerUser.getRegionId());
    	
    	model.addAttribute("contentModel", vo);
    	model.addAttribute("tags", tags);
    	model.addAttribute("tagIds", tagIds);
    	
    	//服务大类，该公司的服务大类
    	List<PartnerServiceType> partnerServiceType = new ArrayList<PartnerServiceType>();
    	
    	
    	List<PartnerRefServiceType> partnerRefServiceType = partnersService.selectServiceTypeByPartnerIdAndParentId(partnerId, 0L);
    	
    	if (!partnerRefServiceType.isEmpty()) {
	    	List<Long> serviceTypeIds = new ArrayList<Long>();
	    	
	    	for (PartnerRefServiceType item : partnerRefServiceType) {
	    		if (!serviceTypeIds.contains(item.getServiceTypeId())) {
	    			serviceTypeIds.add(item.getServiceTypeId());
	    		}
	    	}
	    	
	    	
	    	partnerServiceType =   partnerServiceTypeService.selectByIds(serviceTypeIds);
    	}
    	
    	model.addAttribute("partnerServiceType", partnerServiceType);
    	
		return "partners/partnerUserForm";
	}
    
	
	/**
	 * 添加/修改服务人员
	 *
	 * @param request
	 * @param model
	 * @param partners
	 * @param result
	 * @return
	 * @throws IOException 
	 */
	//@AuthPassport
	@RequestMapping(value = "/user_form", method = { RequestMethod.POST })
	public String doPartnerForm(HttpServletRequest request, Model model,
			@ModelAttribute("contentModel") PartnerUserVo partnerUserVo, 
			@RequestParam("imgUrlFile") MultipartFile file,
			BindingResult result) throws IOException {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
		Long id = partnerUserVo.getId();
		Long userId = partnerUserVo.getUserId();
		Long partnerId = partnerUserVo.getPartnerId();
		Short responseTime = partnerUserVo.getResponseTime();
		Long serviceTypeId = partnerUserVo.getServiceTypeId();
		String mobile = partnerUserVo.getMobile();
		String name = partnerUserVo.getName();
		
		Users u = userService.selectByMobile(mobile);

		//创建新用户
		if (u == null) {
			u = userService.genUsers(partnerUserVo.getIntroduction(),mobile, name, (short) 2);
			userId = u.getId();
		} else {
			if (!u.getName().equals(name)) {
				u.setName(name);
			}
			userId = u.getId();
		}
		
		u.setIntroduction(partnerUserVo.getIntroduction());
		
		u.setUserType((short) 2);
		if (serviceTypeId.equals(75L)) {
			u.setUserType((short) 1);
		}
		
		
		
		u.setIsApproval((short) 2);
		//更新头像 
		if (file != null && !file.isEmpty()) {
			String url = Constants.IMG_SERVER_HOST + "/upload/";
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
			fileType = fileType.toLowerCase();
			String sendResult = ImgServerUtil.sendPostBytes(url, file.getBytes(), fileType);

			ObjectMapper mapper = new ObjectMapper();

			HashMap<String, Object> o = mapper.readValue(sendResult, HashMap.class);

			String ret = o.get("ret").toString();

			HashMap<String, String> info = (HashMap<String, String>) o.get("info");

			String imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();
						
			u.setHeadImg(imgUrl);
			
			

		}
		
		userService.updateByPrimaryKeySelective(u);
		
		//更新服务商用户表
		PartnerUsers partnerUser = partnerUserService.iniPartnerUsers();
		if (id > 0L) {
			partnerUser = partnerUserService.selectByPrimaryKey(id);
		}
		
		partnerUser.setPartnerId(partnerId);
		partnerUser.setUserId(userId);
		partnerUser.setResponseTime(responseTime);
		partnerUser.setServiceTypeId(serviceTypeId);
		partnerUser.setPartnerId(partnerId);
		partnerUser.setProvinceId(partnerUserVo.getProvinceId());
		partnerUser.setCityId(partnerUserVo.getCityId());
		partnerUser.setRegionId(partnerUserVo.getRegionId());
		if (id > 0L) {
			partnerUserService.updateByPrimaryKey(partnerUser);
		} else {
			partnerUser.setId(0L);
			partnerUserService.insert(partnerUser);
		}
		
		//处理标签
		String tagIds = request.getParameter("tagIds");
		if (!StringUtil.isEmpty(tagIds)) {
			tagsUsersService.deleteByUserId(userId);
			String[] tagList = StringUtil.convertStrToArray(tagIds);
			
			for (int i = 0; i < tagList.length; i++) {
				if (StringUtil.isEmpty(tagList[i])) continue;
				
				TagUsers record = new TagUsers();
				record.setId(0L);
				record.setUserId(userId);
				record.setTagId(Long.valueOf(tagList[i]));
				record.setAddTime(TimeStampUtil.getNowSecond());
				tagsUsersService.insert(record);
			}
		}
		
		
		return "redirect:user_list?partnerId=" + partnerId;
	} 
}
