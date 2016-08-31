package com.simi.action.partners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.common.extension.StringHelper;
import com.simi.action.admin.AdminController;
import com.simi.common.Constants;
import com.simi.models.TreeModel;
import com.simi.models.extention.TreeModelExtension;
import com.simi.oa.auth.AuthPassport;
import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.ImgService;
import com.simi.service.admin.AdminAuthorityService;
import com.simi.service.partners.PartnerServicePriceService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.vo.partners.PartnerServicePriceVo;

@Controller
@RequestMapping(value = "/partnerServicePrice")
public class PartnerServicePriceController extends AdminController {

	@Autowired
	private AdminAuthorityService adminAuthorityService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private PartnerServicePriceService partnerServicePriceService;
	
	@Autowired
	private ImgService imgService;

	/**
	 * 树形展示权限列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String chain(HttpServletRequest request, Model model) {
		if (!model.containsAttribute("contentModel")) {

			String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
			List<TreeModel> children = TreeModelExtension.ToTreeModels(partnerServicePriceService.listChain(new ArrayList<Long>()), null, null,
					StringHelper.toIntegerList(expanded, ","));
			List<TreeModel> treeModels = new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0", "0", "根节点", false, false, false, children)));
			String jsonString = JSONArray.fromObject(treeModels, new JsonConfig()).toString();
			model.addAttribute("contentModel", jsonString);
		}
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		return "partners/partnerServicePriceList";
	}

	/**
	 * 根据页面选择的id,增加新节点
	 * 
	 * @param request
	 * @param model
	 * @param adminAuthorityVo
	 * @param id
	 * @param result
	 * @return 权限的树形展示页面
	 */
	@AuthPassport
	@RequestMapping(value = "/form", method = { RequestMethod.POST })
	public String add(HttpServletRequest request, Model model, @ModelAttribute("contentModel") PartnerServicePriceVo vo,
			@RequestParam("imgUrlFile") MultipartFile file, BindingResult result) throws IOException {

		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);

		Long serviceTypeId = vo.getServiceTypeId();
		Long servicePriceId = vo.getServicePriceId();

		// 先删除后增加

		PartnerServicePrice record = partnerServicePriceService.initPartnerServicePrice();
		if (servicePriceId > 0L) {
			record = partnerServicePriceService.selectByPrimaryKey(servicePriceId);
		}

		record.setServicePriceId(servicePriceId);
		record.setServiceTypeId(serviceTypeId);
		record.setName(vo.getName());
		record.setServiceTitle(vo.getServiceTitle());

		record.setPrice(vo.getPrice());
		record.setDisPrice(vo.getDisPrice());
		record.setOrderType(vo.getOrderType());
		record.setOrderDuration(vo.getOrderDuration());
		record.setIsAddr(vo.getIsAddr());
		record.setContentStandard(vo.getContentStandard());
		record.setContentDesc(vo.getContentDesc());
		record.setContentFlow(vo.getContentFlow());
		
		
		// 处理 多文件 上传
		Map<String, String> fileMaps = imgService.multiFileUpLoad(request);
		if (fileMaps.get("imgUrlFile") != null) {
			String imgUrl = fileMaps.get("imgUrlFile");
			
			if (!StringUtil.isEmpty(imgUrl)) record.setImgUrl(imgUrl);
		}
		
		if (record.getUserId() == null)
			record.setUserId(0L);

		if (servicePriceId > 0L) {
			partnerServicePriceService.updateByPrimaryKeySelective(record);
		} else {
			record.setUserId(0L);
			record.setPartnerId(0L);
			record.setVideoUrl("");
			record.setExtendId(0L);
			record.setCategory("");
			record.setAction("");
			record.setParams("");
			record.setGotoUrl("");
			record.setTags("");
			partnerServicePriceService.insert(record);
		}

		return "redirect:/partnerServicePrice/list";
	}

	/**
	 * 根据id编辑对应的权限
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return 跳转到编辑页面
	 */
	@AuthPassport
	@RequestMapping(value = "/form", method = { RequestMethod.GET })
	public String edit(HttpServletRequest request, Model model, 
			@RequestParam(value = "serviceTypeId") Long serviceTypeId, 
			@RequestParam(value = "servicePriceId") Long servicePriceId) {

		PartnerServicePrice servicePrice = partnerServicePriceService.selectByPrimaryKey(servicePriceId);

		if (servicePrice == null) {
			servicePrice = partnerServicePriceService.initPartnerServicePrice();
		}

		PartnerServicePriceVo vo = new PartnerServicePriceVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(servicePrice, vo);
		vo.setId(servicePrice.getServicePriceId());
		model.addAttribute("contentModel", vo);

		
		return "partners/partnerServicePriceForm";
	}

	/**
	 * 根据id删除权限
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return 跳转到权限树形展示
	 */
	@AuthPassport
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.GET })
	public String delete(HttpServletRequest request, Model model, @PathVariable(value = "id") String id) {
		Long serviceTypeId = Long.valueOf(id.trim());
		// 根据id查找出对应的该权限对象
		// int result =
		// adminAuthorityService.deleteAuthorityByRecurision(adminAuthority);

		partnerServicePriceService.deleteByPrimaryKey(serviceTypeId);

		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if (returnUrl == null)
			returnUrl = "partnerServicePrice/list";
		return "redirect:" + returnUrl;
	}

}
