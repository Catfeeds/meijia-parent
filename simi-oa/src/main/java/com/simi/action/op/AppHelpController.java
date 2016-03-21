package com.simi.action.op;

import java.io.IOException;
import java.util.HashMap;

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
import com.simi.po.model.op.AppHelp;
import com.simi.service.op.AppHelpService;

@Controller
@RequestMapping(value = "/op")
public class AppHelpController extends BaseController {

	
	@Autowired
	private AppHelpService appHelpService;
	//加好导航列表
	@AuthPassport
	@RequestMapping(value = "/appHelp_list", method = { RequestMethod.GET })
		public String appIndexList(HttpServletRequest request, Model model) {

			model.addAttribute("requestUrl", request.getServletPath());
			model.addAttribute("requestQuery", request.getQueryString());

			model.addAttribute("searchModel");
			int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
			int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

			PageInfo result = appHelpService.selectByListPage(pageNo, pageSize);

			model.addAttribute("contentModel", result);

			return "op/appHelpList";
		}

	@AuthPassport
	@RequestMapping(value = "/appHelpForm", method = { RequestMethod.GET })
	public String appIndexForm(HttpServletRequest request, Model model, @RequestParam(value = "id") Long id) {

		if (id == null) {
			id = 0L;
		}
		AppHelp appHelp = appHelpService.initAppHelp();
		if (id != null && id > 0) {
			appHelp = appHelpService.selectByPrimaryKey(id);
		}
		model.addAttribute("contentModel", appHelp);
		return "op/appHelpForm";
	}

	@AuthPassport
//	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/appHelpForm", method = { RequestMethod.POST })
	public String doAdForm(HttpServletRequest request, Model model, 
			@ModelAttribute("contentModel") AppHelp appHelp, 
			@RequestParam("cardIconFile") MultipartFile file,
			BindingResult result
			) throws IOException {
		Long id = appHelp.getId();
		//Long addTime = Long.valueOf(request.getParameter("addTime"));
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

		}
		if (!StringUtil.isEmpty(imgUrl)) {
			appHelp.setImgUrl(imgUrl+"?p=0");
		}
		// 更新或者新增
		if (id != null && id > 0) {
			//appCardType.setAddTime(addTime);
			appHelpService.updateByPrimaryKeySelective(appHelp);
		} else {
			if (imgUrl=="") {
				appHelp.setImgUrl("");
			}
			appHelp.setAddTime(TimeStampUtil.getNow() / 1000);
			appHelpService.insertSelective(appHelp);
		}
		return "redirect:appHelp_list";
	}
}
