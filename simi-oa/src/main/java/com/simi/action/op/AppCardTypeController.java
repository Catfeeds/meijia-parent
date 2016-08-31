package com.simi.action.op;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import com.simi.action.BaseController;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.op.AppCardType;
import com.simi.service.ImgService;
import com.simi.service.op.AppCardTypeService;

@Controller
@RequestMapping(value = "/op")
public class AppCardTypeController extends BaseController {

	
	@Autowired
	private AppCardTypeService appCardTypeService;
	
	@Autowired
	private ImgService imgService;

	
	@AuthPassport
	@RequestMapping(value = "/appCardType_list", method = { RequestMethod.GET })
		public String appToolsList(HttpServletRequest request, Model model) {

			model.addAttribute("requestUrl", request.getServletPath());
			model.addAttribute("requestQuery", request.getQueryString());

			model.addAttribute("searchModel");
			int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
			int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

			PageInfo result = appCardTypeService.selectByListPage(pageNo, pageSize);

			model.addAttribute("contentModel", result);

			return "op/appCardTypeList";
		}

	@AuthPassport
	@RequestMapping(value = "/appCardTypeForm", method = { RequestMethod.GET })
	public String adForm(HttpServletRequest request, Model model, @RequestParam(value = "card_type_id") Long cardTypeId) {

		if (cardTypeId == null) {
			cardTypeId = 0L;
		}

		AppCardType appCardType = appCardTypeService.initAppCardType();
		if (cardTypeId != null && cardTypeId > 0) {
			appCardType = appCardTypeService.selectByPrimaryKey(cardTypeId);
		}

		model.addAttribute("contentModel", appCardType);

		return "op/appCardTypeForm";
	}

	@AuthPassport
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/appCardTypeForm", method = { RequestMethod.POST })
	public String doAdForm(HttpServletRequest request, Model model, 
			@ModelAttribute("contentModel") AppCardType appCardType, 
			@RequestParam("cardIconFile") MultipartFile file,
			BindingResult result
			) throws IOException {

		Long id = appCardType.getCardTypeId();
		//Long addTime = Long.valueOf(request.getParameter("addTime"));

		// 更新头像
		String imgUrl = "";
		// 处理 多文件 上传
		Map<String, String> fileMaps = imgService.multiFileUpLoad(request);
		if (fileMaps.get("cardIconFile") != null) {
			imgUrl = fileMaps.get("cardIconFile");
			
		}

		if (!StringUtil.isEmpty(imgUrl)) {
			appCardType.setCardIcon(imgUrl);
		}else {
			appCardType.setCardIcon("");
		}
		// 更新或者新增
		if (id != null && id > 0) {
			//appCardType.setAddTime(addTime);
			appCardTypeService.updateByPrimaryKeySelective(appCardType);
		} else {

			appCardType.setAddTime(TimeStampUtil.getNow() / 1000);
			appCardTypeService.insertSelective(appCardType);
		}

		return "redirect:appCardType_list";
	}
}
