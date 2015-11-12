package com.simi.action.partners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.common.extension.StringHelper;
import com.simi.action.admin.AdminController;
import com.simi.common.Constants;
import com.simi.models.AuthorityEditModel;
import com.simi.models.TreeModel;
import com.simi.models.extention.TreeModelExtension;
import com.simi.oa.auth.AuthPassport;
import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.admin.AdminAuthorityService;
import com.simi.service.partners.PartnerServicePriceDetailService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.vo.partners.PartnerServicePriceDetailVo;
import com.simi.vo.partners.PartnerServicePriceVo;


@Controller
@RequestMapping(value = "/partnerServicePrice")
public class PartnerServicePriceController extends AdminController {

	@Autowired
	private AdminAuthorityService adminAuthorityService;
	
	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private PartnerServicePriceDetailService partnerServiceTypeDetailService;

	/**
	 * 树形展示权限列表
	 * @param request
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String chain(HttpServletRequest request, Model model) {
		if (!model.containsAttribute("contentModel")) {
			
			String expanded = ServletRequestUtils.getStringParameter(request,"expanded", null);
			List<TreeModel> children = TreeModelExtension.ToTreeModels(partnerServiceTypeService.listChain((short) 1), null, null,
					StringHelper.toIntegerList(expanded, ","));
			List<TreeModel> treeModels = new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0", "0", "根节点", false, false, false, children)));
			String jsonString = JSONArray.fromObject(treeModels,new JsonConfig()).toString();
			model.addAttribute("contentModel", jsonString);
		}
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		return "partners/partnerServicePriceList";
	}

	/**
	 * 根据页面选择的id,增加新节点
	 * @param request
	 * @param model
	 * @param adminAuthorityVo
	 * @param id
	 * @param result
	 * @return 权限的树形展示页面
	 */
	@AuthPassport
	@RequestMapping(value = "/form/{id}", method = { RequestMethod.POST })
	public String add(HttpServletRequest request,Model model,
			@ModelAttribute("contentModel") PartnerServicePriceDetailVo partnerServiceTypeDetailVo,
			@RequestParam("imgUrlFile") MultipartFile file, BindingResult result) throws IOException {

		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		
		
		Long servicePriceId = partnerServiceTypeDetailVo.getServicePriceId();
		
		PartnerServiceType partnerServiceType = partnerServiceTypeService.initPartnerServiceType();
		
		if (servicePriceId > 0L) {
			partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(servicePriceId);
		}
		
		partnerServiceType.setParentId(partnerServiceTypeDetailVo.getParentId());
		partnerServiceType.setName(partnerServiceTypeDetailVo.getName());
		partnerServiceType.setViewType((short) 1);
		partnerServiceType.setNo(partnerServiceTypeDetailVo.getNo());
		if (servicePriceId.equals(0L)) {
			partnerServiceTypeService.insertSelective(partnerServiceType);
			servicePriceId = partnerServiceType.getId();
		} else {
			partnerServiceTypeService.updateByPrimaryKey(partnerServiceType);
		}
		
		
		//先删除后增加
		Long id = partnerServiceTypeDetailVo.getId();
		PartnerServicePriceDetail record = partnerServiceTypeDetailService.initPartnerServicePriceDetail();
		
		if (id > 0L) {
			record = partnerServiceTypeDetailService.selectByPrimaryKey(id);
		}
		
		record.setServicePriceId(servicePriceId);
		record.setServiceTitle(partnerServiceTypeDetailVo.getServiceTitle());
		record.setUserId(0L);
		record.setPrice(partnerServiceTypeDetailVo.getPrice());
		record.setDisPrice(partnerServiceTypeDetailVo.getDisPrice());
		record.setOrderType(partnerServiceTypeDetailVo.getOrderType());
		record.setOrderDuration(partnerServiceTypeDetailVo.getOrderDuration());
		record.setContentStandard(partnerServiceTypeDetailVo.getContentStandard());
		record.setContentDesc(partnerServiceTypeDetailVo.getContentDesc());
		record.setContentFlow(partnerServiceTypeDetailVo.getContentFlow());
		
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
			partnerServiceTypeDetailService.updateByPrimaryKeySelective(record);
		} else {
			partnerServiceTypeDetailService.insert(record);
		}
		
		
		if (returnUrl == null)	returnUrl = "partners/partnerServiceTypeList";
		return "redirect:" + returnUrl;
	}
	
	/**
	 * 根据id编辑对应的权限
	 * @param request
	 * @param model
	 * @param id
	 * @return 跳转到编辑页面
	 */
	@AuthPassport
	@RequestMapping(value = "/form/{id}", method = { RequestMethod.GET })
	public String edit(HttpServletRequest request, Model model,
			@PathVariable(value = "id") Long id) {
		
		PartnerServicePriceDetailVo vo = new PartnerServicePriceDetailVo();
		
		PartnerServiceType partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(id);
		
		PartnerServicePriceDetail partnerServiceTypeDetail = partnerServiceTypeDetailService.selectByServicePriceId(id);
		
		if (partnerServiceTypeDetail == null) {
			partnerServiceTypeDetail = partnerServiceTypeDetailService.initPartnerServicePriceDetail();
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(partnerServiceTypeDetail, vo);
		vo.setName(partnerServiceType.getName());
		vo.setParentId(partnerServiceType.getParentId());
		vo.setNo(partnerServiceType.getNo());
		model.addAttribute("contentModel", vo);
		
		List<TreeModel> treeModels;
		PartnerServicePriceDetailVo editModel = (PartnerServicePriceDetailVo) model.asMap().get("contentModel");
		String expanded = ServletRequestUtils.getStringParameter(request,"expanded", null);
		if (editModel.getParentId() != null && editModel.getParentId() > 0) {
			List<TreeModel> children = TreeModelExtension.ToTreeModels(partnerServiceTypeService.listChain((short) 1), editModel.getParentId().intValue(), null, StringHelper.toIntegerList(expanded, ","));
			treeModels = new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0", "0", "根节点", false, false, false, children)));
		} else {
			List<TreeModel> children = TreeModelExtension.ToTreeModels( partnerServiceTypeService.listChain((short) 1), null, null, StringHelper.toIntegerList(expanded, ","));
			treeModels = new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0", "0", "根节点", false, true, false, children)));
		}
		model.addAttribute("treeDataSource", JSONArray.fromObject(treeModels, new JsonConfig()).toString());

		return "partners/partnerServicePriceForm";
	}

	/**
	 * 根据id删除权限
	 * @param request
	 * @param model
	 * @param id
	 * @return 跳转到权限树形展示
	 */
	@AuthPassport
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.GET })
	public String delete(HttpServletRequest request, Model model,@PathVariable(value = "id") String id) {
		Long serviceTypeId = Long.valueOf(id.trim());
		//根据id查找出对应的该权限对象
		//int result = adminAuthorityService.deleteAuthorityByRecurision(adminAuthority);
		
		partnerServiceTypeDetailService.deleteByServiceTypeId(serviceTypeId);
		partnerServiceTypeService.deleteByPrimaryKey(serviceTypeId);
		
		String returnUrl = ServletRequestUtils.getStringParameter(request,
				"returnUrl", null);
		if (returnUrl == null)
			returnUrl = "partnerServicePrice/list";
		return "redirect:" + returnUrl;
	}

}
