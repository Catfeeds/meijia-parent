package com.simi.action.op;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.BaseController;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.card.CardImgs;
import com.simi.po.model.common.Imgs;
import com.simi.po.model.op.OpAutoFeed;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.ImgService;
import com.simi.service.op.OpAutoFeedService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.ImgSearchVo;
import com.simi.vo.op.OpAutoFeedVo;
import com.simi.vo.op.OpExtVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;

@Controller
@RequestMapping(value = "/op")
public class OpAutoFeedController extends BaseController {

	@Autowired
	private OpAutoFeedService opAutoFeedService;
	
	@Autowired
	ImgService imgService;	

	@AuthPassport
	@RequestMapping(value = "/autofeed", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model) {
		
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel");
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		PageInfo result = opAutoFeedService.selectByListPage(pageNo, pageSize);
		List<OpAutoFeed> list = result.getList();
		
		for (int i =0; i< list.size(); i++) {
			OpAutoFeed item = list.get(i);
			OpAutoFeedVo vo = new OpAutoFeedVo();
			
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			ImgSearchVo isearchVo = new ImgSearchVo();
			isearchVo.setLinkId(vo.getId());
			isearchVo.setLinkType("op_auto_feed");
			List<Imgs> imgList = imgService.selectBySearchVo(isearchVo);
			vo.setImgs(imgList);
			
			list.set(i, vo);
		}
		
		result = new PageInfo(list);
		
		model.addAttribute("contentModel", result);

		return "op/autofeed";
	}

	@AuthPassport
	@RequestMapping(value = "/autoFeedForm", method = { RequestMethod.GET })
	public String adForm(Model model, @RequestParam(value = "id") Long id, HttpServletRequest request) {

		if (id == null) id = 0L;

		OpAutoFeed record = opAutoFeedService.initOpAutoFeed();
		if (id != null && id > 0) {
			record = opAutoFeedService.selectByPrimaryKey(id);
		}
		
		OpAutoFeedVo vo = new OpAutoFeedVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(record, vo);
		
		ImgSearchVo isearchVo = new ImgSearchVo();
		isearchVo.setLinkId(vo.getId());
		isearchVo.setLinkType("op_auto_feed");
		List<Imgs> imgList = imgService.selectBySearchVo(isearchVo);
		vo.setImgs(imgList);
		
		model.addAttribute("contentModel", vo);

		return "op/autoFeedForm";
	}
	
	@AuthPassport
	@RequestMapping(value = "/autoFeedForm", method = { RequestMethod.POST })
	public String doAdForm(HttpServletRequest request, Model model, 
			@ModelAttribute("contentModel") OpAutoFeed vo, 
			@RequestParam("imgUrlFile") List<MultipartFile> files,
			BindingResult result) throws IOException {

		Long id = Long.valueOf(request.getParameter("id"));
		
		OpAutoFeed record = opAutoFeedService.initOpAutoFeed();
		if (id != null && id > 0) {
			record = opAutoFeedService.selectByPrimaryKey(id);
		}
		
//		BeanUtilsExp.copyPropertiesIgnoreNull(vo, record);
		record.setTitle(vo.getTitle());
		record.setContent(vo.getContent());
		record.setStatus(vo.getStatus());
		
		if (id != null && id > 0) {
			opAutoFeedService.updateByPrimaryKeySelective(record);
		} else {
			record.setAddTime(TimeStampUtil.getNowSecond());
			opAutoFeedService.insertSelective(record);
		}
		
		id = record.getId();
		
		boolean haveFiles = false;
		if (files != null && !files.isEmpty()) {
			for (int i = 0; i < files.size(); i++) {
				if (files.get(i).getSize() > 0L) {
					haveFiles = true;
					break;
				}
			}
		}
				
		if (haveFiles) {
			//删除原有的图片
			ImgSearchVo isearchVo = new ImgSearchVo();
			isearchVo.setLinkId(id);
			isearchVo.setLinkType("op_auto_feed");
			List<Imgs> imgList = imgService.selectBySearchVo(isearchVo);
			for (Imgs item : imgList) {
				imgService.deleteByPrimaryKey(item.getImgId());
			}
			
			for (int i = 0; i < files.size(); i++) {
				String url = Constants.IMG_SERVER_HOST + "/upload/";
				String fileName = files.get(i).getOriginalFilename();
				String fileType = fileName
						.substring(fileName.lastIndexOf(".") + 1);
				fileType = fileType.toLowerCase();
				String sendResult = ImgServerUtil.sendPostBytes(url, files.get(i).getBytes(), fileType);

				ObjectMapper mapper = new ObjectMapper();

				HashMap<String, Object> o = mapper.readValue(sendResult, HashMap.class);

				String ret = o.get("ret").toString();

				HashMap<String, String> info = (HashMap<String, String>) o.get("info");

				String imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();

				Imgs img = imgService.initImg();
				img.setImgUrl(imgUrl);
				img.setLinkId(id);
				img.setLinkType("op_auto_feed");
				imgService.insert(img);
			}
		}
		
		return "redirect:autofeed";
	}

}
