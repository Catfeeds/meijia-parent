package com.simi.action.app.partner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.baidu.DwzUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.user.Users;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderListVo;
import com.simi.vo.partners.PartnerServicePriceDetailVo;
import com.simi.vo.partners.PartnerServicePriceDetailVoAll;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;

@Controller
@RequestMapping(value = "/app/partner")
public class PartnerServicePriceController extends BaseController {


	
	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired 
	private PartnerUserService partnerUserService;
	
	@Autowired
	private PartnersService partnersService;
	
	@Autowired
	private UsersService usersService;
	
	/**
	 * 服务报价列表接口
	 * @param partnerUserId
	 * @param page
	 * @return
	 */
	
	@RequestMapping(value = "get_partner_service_price_list", method = RequestMethod.GET)
	public AppResultData<Object> list(
			@RequestParam("user_id") Long userId
	) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users users = usersService.selectByPrimaryKey(userId);
			if (users == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.PARTNERS_NOT_EXIST_MG);
				return result;
			}
			if (users != null && users.getUserType() == 0) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.USERS_NOT_REGIETER_STORE);
				return result;
			}
			if (users != null && users.getUserType() == 1) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.SEC_NOT_REGIETER_STORE);
				return result;
			}
			
		PartnerUsers partnerUsers = partnerUserService.selectByUserId(userId);
		
		/*if (partnerUsers == null) {
			return result;
		}*/
		
		Long partnerId = partnerUsers.getPartnerId();
		Long serviceTypeId = partnerUsers.getServiceTypeId();

		//List<PartnerServiceType> list = partnerServiceTypeService.selectByPartnerIdIn(partnerId);
		//服务价格
		List<Long> partnerIds = new ArrayList<Long>();
		partnerIds.add(0L);
		partnerIds.add(partnerId);
		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(serviceTypeId);
		searchVo.setViewType((short) 1);
		searchVo.setPartnerIds(partnerIds);
		List<PartnerServiceType> list = partnerServiceTypeService.selectBySearchVo(searchVo);

	     /*   Iterator<PartnerServiceType> listIterator = list.iterator();  
	        while (listIterator.hasNext()) {  
	        	PartnerServiceType partnerServiceType = listIterator.next();  
	        	PartnerServicePriceDetail detail = partnerServicePriceDetailService.selectByServicePriceId(partnerServiceType.getId());
	        	if (detail !=null) {
					if (detail.getUserId() != 0 && detail.getUserId() != userId ) {
						listIterator.remove();  
					}
	        } 
	        }*/
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			
			PartnerServiceType partnerServiceType = (PartnerServiceType) iterator.next();
			PartnerServicePriceDetail detail = partnerServicePriceDetailService.selectByServicePriceId(partnerServiceType.getId());
			
			if (detail !=null) {
				if (detail.getUserId() != 0 && detail.getUserId() != userId ) {
					iterator.remove();
				}
				}
		}
		
		
		
		/*if (list !=null) {
			//synchronized(this){
		for (int i = 0; i < list.size(); i++) {
			PartnerServiceType partnerServiceType = list.get(i);
			//PartnerUsers pusers = partnerUserService.selectByServiceTypeIdAndPartnerId(partnerServiceType.getId(), partnerServiceType.getPartnerId());
			//PartnerServicePriceDetail detail = partnerServicePriceDetailService.selectByPrimaryKey(partnerServiceType.getId());
			PartnerServicePriceDetail detail = partnerServicePriceDetailService.selectByServicePriceId(partnerServiceType.getId());
			if (detail !=null) {
			if (detail.getUserId() != 0 && detail.getUserId() != userId ) {
				list.remove(i);
			}
			}
		}//}
		}*/
	    List<PartnerServicePriceDetailVoAll> listVo = new ArrayList<PartnerServicePriceDetailVoAll>();
		for (PartnerServiceType item : list) {
			PartnerServicePriceDetailVoAll vo = new PartnerServicePriceDetailVoAll();
			vo = partnerServiceTypeService.getPartnerPriceList(item,userId);
			listVo.add(vo);  
		}
		result.setData(listVo);
		
		return result;

	}
	/**
	 * 通过userId在关联表里面获得partnerId和serviceTypeId
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_partnerId_by_user_id", method = RequestMethod.GET)
	public AppResultData<Object> getPartnerId(@RequestParam("user_id") Long userId) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		PartnerUsers partnerUsers = partnerUserService.selectByUserId(userId);
		
		result.setData(partnerUsers);
		
		return result;
	}
	/**
	 * 获取服务价格详情接口
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "get_partner_service_price_detail", method = RequestMethod.GET)
	public AppResultData<Object> getPartnerServicePriceDetail(Model model,
			@RequestParam("service_price_id") Long servicePriceId) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		PartnerServicePriceDetail record = partnerServicePriceDetailService.selectByServicePriceId(servicePriceId);

		PartnerServicePriceDetailVo vo =new  PartnerServicePriceDetailVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(record,vo);
		
		//服务类别名称
		PartnerServiceType prices = partnerServiceTypeService.selectByPrimaryKey(servicePriceId);
		vo.setName(prices.getName());
		
		result.setData(vo);
		return result;
	}

		@RequestMapping(value="/post_partner_service_price_add", method = {RequestMethod.POST})
		public AppResultData<Object> partnerAdd(HttpServletRequest request, 
			//	@RequestParam("parent_id") Long parentId,//服务商id
				@RequestParam("partner_id") Long partnerId,
				@RequestParam("service_type_id") Long serviceTypeId,// 服务类别
				@RequestParam("user_id") Long userId,//用户id
				
				@RequestParam("no") Integer no,
				@RequestParam("name") String name,
				@RequestParam("title") String title,
				@RequestParam("price") BigDecimal price,
				@RequestParam("dis_price") BigDecimal disPrice,
				@RequestParam("order_type") short orderType,
				@RequestParam("content_standard") String contentStandard,//服务标准
				@RequestParam("content_desc") String contentDesc,//服务说明
				@RequestParam("content_flow") String contentFlow,//服务流程
				@RequestParam(value = "order_duration", required = false, defaultValue = "0") short orderDuration,
				@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
				@RequestParam(value = "is_addr", required = false, defaultValue = "0") short isAddr,
				@RequestParam(value = "imgUrlFile", required = false) MultipartFile file
				) throws IOException{
			
			AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
					ConstantMsg.SUCCESS_0_MSG, ""); 
			
			PartnerServiceType partnerServiceType = partnerServiceTypeService.initPartnerServiceType();
			
			/*if (serviceTypeId > 0L) {
				partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);
			}*/
			partnerServiceType.setParentId(serviceTypeId);
			partnerServiceType.setName(name);
			partnerServiceType.setViewType((short) 1);
			partnerServiceType.setNo(no);
			partnerServiceType.setPartnerId(partnerId);
		//	if (serviceTypeId.equals(0L)) {
				partnerServiceTypeService.insertSelective(partnerServiceType);
				serviceTypeId = partnerServiceType.getId();
		//	} else {
		//		partnerServiceTypeService.updateByPrimaryKey(partnerServiceType);
		//	}
			//先删除后增加
			PartnerServicePriceDetail record = partnerServicePriceDetailService.initPartnerServicePriceDetail();
			//PartnerUsers partnerUsers = partnerUserService.selectByServiceTypeIdAndPartnerId(serviceTypeId,partnerId);
			
			/*if (serviceTypeId > 0L) {
				record = partnerServicePriceDetailService.selectByServicePriceId(serviceTypeId);
			}*/
		/*	if (partnerUsers !=null) {
				record.setUserId(partnerUsers.getUserId());
			}else {
				record.setUserId(0L);
			}*/
			record.setUserId(userId);
			record.setServicePriceId(serviceTypeId);
			record.setServiceTitle(title);
			
			record.setPrice(price);
			record.setDisPrice(disPrice);
			record.setOrderType(orderType);
			record.setOrderDuration(orderDuration);
			record.setIsAddr(isAddr);
			record.setContentStandard(contentStandard);
			record.setContentDesc(contentDesc);
			record.setContentFlow(contentFlow);

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
				record.setImgUrl(imgUrl);

			}
			/*if (serviceTypeId > 0L) {
				partnerServicePriceDetailService.updateByPrimaryKeySelective(record);
			} else {*/
				partnerServicePriceDetailService.insert(record);
			//}
			
			return result;	
		}
}
