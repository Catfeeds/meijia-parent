package com.simi.action.app.partner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

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
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.vo.AppResultData;
import com.simi.vo.partners.PartnerServicePriceDetailVo;

@Controller
@RequestMapping(value = "/app/partner")
public class PartnerServicePriceController extends BaseController {


	
	@Autowired
	private PartnerServicePriceDetailService partnerServicePriceDetailService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
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
				@RequestParam("parent_id") Long parentId,//服务商id
				@RequestParam("service_price_id") Long servicePriceId,// 服务类别
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
				@RequestParam(value = "imgUrlFile", required = false) MultipartFile file,
				@RequestParam(value = "partner_id", required = false, defaultValue = "0") Long partnerId,
				@RequestParam(value = "order_duration", required = false, defaultValue = "0") short orderDuration,
				@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
				@RequestParam(value = "is_addr", required = false, defaultValue = "0") short isAddr
				
				) throws IOException{
			
			AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0,
					ConstantMsg.SUCCESS_0_MSG, ""); 
			
			PartnerServiceType partnerServiceType = partnerServiceTypeService.initPartnerServiceType();
			
			if (servicePriceId > 0L) {
				partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(servicePriceId);
			}
			partnerServiceType.setParentId(parentId);
			partnerServiceType.setName(name);
			partnerServiceType.setViewType((short) 1);
			partnerServiceType.setNo(no);
			partnerServiceType.setPartnerId(partnerId);
			if (servicePriceId.equals(0L)) {
				partnerServiceTypeService.insertSelective(partnerServiceType);
				servicePriceId = partnerServiceType.getId();
			} else {
				partnerServiceTypeService.updateByPrimaryKey(partnerServiceType);
			}
			//先删除后增加
			PartnerServicePriceDetail record = partnerServicePriceDetailService.initPartnerServicePriceDetail();
			
			if (id > 0L) {
				record = partnerServicePriceDetailService.selectByPrimaryKey(id);
			}
			record.setServicePriceId(servicePriceId);
			record.setServiceTitle(title);
			record.setUserId(0L);
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
			if (id > 0L) {
				partnerServicePriceDetailService.updateByPrimaryKeySelective(record);
			} else {
				partnerServicePriceDetailService.insert(record);
			}
			
			return result;	
		}
}
