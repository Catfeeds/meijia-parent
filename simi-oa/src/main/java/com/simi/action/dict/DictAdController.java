package com.simi.action.dict;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.BaseController;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.dict.DictAd;
import com.simi.po.model.op.AppTools;
import com.simi.service.dict.AdService;
import com.simi.service.op.AppToolsService;
import com.simi.vo.ApptoolsSearchVo;
import com.simi.vo.dict.DictAdVo;

@Controller
@RequestMapping(value = "/dict")
public class DictAdController extends BaseController {

	@Autowired
	private AdService adService;
	
	@Autowired
	private AppToolsService appToolsService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@AuthPassport
	@RequestMapping(value = "/ad", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel");
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result = adService.searchVoListPage(pageNo, pageSize);
		List<DictAd> list = result.getList();

		for (int i = 0 ; i < list.size(); i++) {
			DictAd item = list.get(i);
			DictAdVo vo = new DictAdVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			vo.setAdTypeName("首页");
			if (vo.getAdType() > 0L) {
				AppTools tools = appToolsService.selectByPrimaryKey(vo.getAdType());
				if (tools != null) {
					vo.setAdTypeName(tools.getName());
				}
			}
			list.set(i, vo);
		}
		result = new PageInfo(list);
		
		model.addAttribute("contentModel", result);

		return "dict/ad";
	}

	@AuthPassport
	@RequestMapping(value = "/adForm", method = { RequestMethod.GET })
	public String adForm(Model model, @RequestParam(value = "id") Long id, HttpServletRequest request) {

		if (id == null) {
			id = 0L;
		}

		DictAd dictAd = adService.initAd();
		if (id != null && id > 0) {
			dictAd = adService.selectByPrimaryKey(id);
		}

		model.addAttribute("adModel", dictAd);
		
		//应用列表
		ApptoolsSearchVo searchVo = new ApptoolsSearchVo();
		searchVo.setIsOnline((short) 0);
		List<AppTools> apptools = appToolsService.selectBySearchVo(searchVo);
		model.addAttribute("apptools", apptools);
		return "dict/adForm";
	}

	@SuppressWarnings("unchecked")
	@AuthPassport
	@RequestMapping(value = "/adForm", method = { RequestMethod.POST })
	public String doAdForm(HttpServletRequest request, Model model, @ModelAttribute("dictAd") DictAd dictAd, BindingResult result) throws IOException {

		Long id = Long.valueOf(request.getParameter("id"));

		// 创建一个通用的多部分解析器.
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/ad");
		String addr = request.getRemoteAddr();
		int port = request.getServerPort();
		if (multipartResolver.isMultipart(request)) {
			// 判断 request 是否有文件上传,即多部分请求...
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
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

					dictAd.setImgUrl(imgUrl);

				}
			}
		}

		// 更新或者新增
		if (id != null && id > 0) {
			dictAd.setUpdateTime(TimeStampUtil.getNow() / 1000);
			adService.updateByPrimaryKeySelective(dictAd);
		} else {
			dictAd.setId(Long.valueOf(request.getParameter("id")));
			dictAd.setAddTime(TimeStampUtil.getNow() / 1000);
			dictAd.setUpdateTime(0L);
			dictAd.setEnable((short) 1);


			adService.insertSelective(dictAd);
		}

		return "redirect:ad";
	}
}
