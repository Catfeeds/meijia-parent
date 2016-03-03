package com.simi.action.app.partner;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Users;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserImgService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.partners.PartnerUserSearchVo;


@Controller
@RequestMapping(value = "/app/partner")
public class PartnerUserController extends BaseController {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserImgService userImgService;	
	
	@Autowired
	private PartnerUserService partnerUserService;
	
	@Autowired
	private PartnersService partnersService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;
	
	@Autowired
	private TagsUsersService tagsUsersService;

	/**
	 * 服务商人员列表
	 * @param partnerId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "get_partnerUser_list", method = RequestMethod.GET)
	public AppResultData<Object> list(
			@RequestParam("partner_id") Long partnerId, 
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Partners parnter = partnersService.selectByPrimaryKey(partnerId);
			if (parnter == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.PARTNERS_NOT_EXIST_MG);
				return result;
			}
		
		PartnerUserSearchVo searchVo  = new PartnerUserSearchVo();
		searchVo.setPartnerId(partnerId);
		PageInfo list = partnerUserService.selectByListPage(searchVo,page, Constants.PAGE_MAX_NUMBER);
		
		//List<Orders> orderList = list.getList();
		
		result.setData(list);
		
		return result;

	}
	@RequestMapping(value="/post_partnerUser_form", method = {RequestMethod.POST})
	public AppResultData<Object> partnerAdd(HttpServletRequest request, 
		//	@RequestParam("parent_id") Long parentId,//服务商id
			@RequestParam("id") Long id,//服务人员Id
			@RequestParam("partner_id") Long partnerId,
			@RequestParam("user_id") Long userId,//用户id
		//	mobile ,name ,partner_service_type_id ,province_d = $('#provinceId').val();
		 //	city_id,region_id ,response_time ,introduction 
			
			@RequestParam("mobile") String mobile,
			@RequestParam("name") String name,
			@RequestParam("partner_service_type_id") Long serviceTypeId,// 服务类别
			@RequestParam("province_id") Long provinceId,
			@RequestParam(value="city_id",required=false) Long cityId,
			//@RequestParam(value="user_id",required=false, defaultValue = "") Long userId,
			@RequestParam(value="region_id",required=false) Long regionId,
			@RequestParam("response_time") short responseTime,//响应时间
			@RequestParam("introduction") String introduction,//服务标准
			@RequestParam(value = "imgUrlFile", required = false) MultipartFile file,
			@RequestParam(value = "tag_ids", required = false, defaultValue = "") String tagIds
			) throws IOException{
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
				ConstantMsg.SUCCESS_0_MSG, ""); 
		
		
		Users u = userService.selectByMobile(mobile);
	//	Long userId = u.getId();
		//创建新用户
		if (u == null) {
			u = userService.genUsers(introduction,mobile, name, (short) 2);
			userId = u.getId();
		} else {
			if (!u.getName().equals(name)) {
				u.setName(name);
			}
			userId = u.getId();
		}
		
		u.setIntroduction(introduction);
		
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
			
			imgUrl = DwzUtil.dwzApi(imgUrl);
			
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
		partnerUser.setProvinceId(provinceId);
		partnerUser.setRegionId(0L);
		partnerUser.setCityId(0L);
		//partnerUser.setCityId(cityId);
		//partnerUser.setRegionId(regionId);
		if (id > 0L) {
			partnerUserService.updateByPrimaryKey(partnerUser);
		} else {
			partnerUser.setId(0L);
			partnerUserService.insert(partnerUser);
		}
		
		//处理标签
	//	String tagIds = request.getParameter("tagIds");
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

		return result;	
	}
	/**
	 * 通过公司Id查找信息
	 * @param partnerId
	 * @return
	 */
	 @RequestMapping(value = "get_company_by_companyId", method = RequestMethod.GET)
	 public AppResultData<Object> getUserDetail(
			 @RequestParam("partner_id") Long partnerId) {
		 AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, "", "");
		 
		Partners partners = partnersService.selectByPrimaryKey(partnerId);
		if (partners == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.XCOMPANY_NOT_EXIST);
			return result;
		}
		result.setData(partners);

		 return result;
	 }
}