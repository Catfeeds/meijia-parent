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
import com.simi.po.model.op.AppIndex;
import com.simi.service.ImgService;
import com.simi.service.op.AppIndexService;

@Controller
@RequestMapping(value = "/op")
public class AppIndexController extends BaseController {

	
	@Autowired
	private AppIndexService appIndexService;
	
	@Autowired
	private ImgService imgService;
	
	//加好导航列表
	@AuthPassport
	@RequestMapping(value = "/appIndex_list", method = { RequestMethod.GET })
		public String appIndexList(HttpServletRequest request, Model model) {

			model.addAttribute("requestUrl", request.getServletPath());
			model.addAttribute("requestQuery", request.getQueryString());

			model.addAttribute("searchModel");
			int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
			int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

			PageInfo result = appIndexService.selectByListPage(pageNo, pageSize);

			model.addAttribute("contentModel", result);

			return "op/appIndexList";
		}

	@AuthPassport
	@RequestMapping(value = "/appIndexForm", method = { RequestMethod.GET })
	public String appIndexForm(HttpServletRequest request, Model model, @RequestParam(value = "id") Long id) {

		if (id == null) {
			id = 0L;
		}

		AppIndex appIndex = appIndexService.initAppIndex();
		if (id != null && id > 0) {
			appIndex = appIndexService.selectByPrimaryKey(id);
		}

		model.addAttribute("contentModel", appIndex);

		return "op/appIndexForm";
	}

	// @AuthPassport
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/appIndexForm", method = { RequestMethod.POST })
	public String doAdForm(HttpServletRequest request, Model model, 
			@ModelAttribute("contentModel") AppIndex appIndex, 
			@RequestParam("cardIconFile") MultipartFile file,
			BindingResult result
			) throws IOException {

		Long id = appIndex.getId();
		//Long addTime = Long.valueOf(request.getParameter("addTime"));

		// 更新头像
		String imgUrl = "";
		// 处理 多文件 上传
		Map<String, String> fileMaps = imgService.multiFileUpLoad(request);
		if (fileMaps.get("cardIconFile") != null) {
			imgUrl = fileMaps.get("cardIconFile");
		}
		
		
		if (!StringUtil.isEmpty(imgUrl)) {
			appIndex.setIconUrl(imgUrl+"?p=0");
		}
		// 更新或者新增
		if (id != null && id > 0) {
			//appCardType.setAddTime(addTime);
			appIndex.setUpdateTime(TimeStampUtil.getNow() / 1000);
			appIndexService.updateByPrimaryKeySelective(appIndex);
		} else {
			appIndex.setAddTime(TimeStampUtil.getNow() / 1000);
			appIndex.setUpdateTime(TimeStampUtil.getNow() / 1000);
			appIndexService.insertSelective(appIndex);
		}
		return "redirect:appIndex_list";
	}
}
