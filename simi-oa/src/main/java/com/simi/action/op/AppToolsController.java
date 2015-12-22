package com.simi.action.op;

import java.io.IOException;
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
import com.simi.po.model.op.AppTools;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpChannel;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.op.AppToolsService;
import com.simi.service.op.OpAdService;
import com.simi.service.op.OpChannelService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.po.AdSearchVo;

@Controller
@RequestMapping(value = "/op")
public class AppToolsController extends BaseController {

	
	@Autowired
	private AppToolsService appToolsService;
	
	@Autowired
	private OpAdService opAdService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private OpChannelService opChannelService;	

	 @AuthPassport
	@RequestMapping(value = "/appTools_list", method = { RequestMethod.GET })
		public String appToolsList(HttpServletRequest request, Model model) {

			model.addAttribute("requestUrl", request.getServletPath());
			model.addAttribute("requestQuery", request.getQueryString());

			model.addAttribute("searchModel");
			int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
			int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

			PageInfo result = appToolsService.selectByListPage(pageNo, pageSize);

			model.addAttribute("contentModel", result);

			return "op/appToolsList";
		}

	//@AuthPassport
	@RequestMapping(value = "/appToolsForm", method = { RequestMethod.GET })
	public String adForm(HttpServletRequest request, Model model, @RequestParam(value = "t_id") Long tId) {

		if (tId == null) {
			tId = 0L;
		}

		AppTools appTools = appToolsService.initAppTools();
		if (tId != null && tId > 0) {
			appTools = appToolsService.selectByPrimaryKey(tId);
		}

		model.addAttribute("contentModel", appTools);

		return "op/appToolsForm";
	}

	// @AuthPassport
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/appToolsForm", method = { RequestMethod.POST })
	public String doAdForm(HttpServletRequest request, Model model, 
			@ModelAttribute("contentModel") AppTools appTools, 
			@RequestParam("logoFile") MultipartFile file,
			BindingResult result
			) throws IOException {

		Long id = appTools.gettId();

		// 更新头像
		String imgUrl = "";
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

			imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();

			imgUrl = DwzUtil.dwzApi(imgUrl);
		}
		if (!StringUtil.isEmpty(imgUrl)) {
			appTools.setLogo(imgUrl);
		}
		// 更新或者新增
		if (id != null && id > 0) {
		
			appToolsService.updateByPrimaryKeySelective(appTools);
		} else {

			appTools.setAddTime(TimeStampUtil.getNow() / 1000);
			appTools.setLogo(imgUrl);
			appToolsService.insertSelective(appTools);
		}

		return "redirect:appTools_list";
	}
}
