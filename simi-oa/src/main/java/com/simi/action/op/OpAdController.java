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
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpChannel;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.service.op.OpAdService;
import com.simi.service.op.OpChannelService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.po.AdSearchVo;

@Controller
@RequestMapping(value = "/op")
public class OpAdController extends BaseController {

	@Autowired
	private OpAdService opAdService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;
	
	@Autowired
	private OpChannelService opChannelService;	

	 @AuthPassport
	@RequestMapping(value = "/ad_list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, AdSearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		//获取频道信息
		OpChannel cardChannel = opChannelService.initOpChannel();
		cardChannel.setName("日程广告位卡片");
		List<OpChannel> opChannels = opChannelService.selectByAll();
		opChannels.add(0, cardChannel);
		model.addAttribute("opChannels", opChannels);
		
		if (searchVo == null) {
			searchVo = new AdSearchVo();
		}		
		
		model.addAttribute("searchModel", searchVo);
		
		
		
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		String adType = searchVo.getAdType();
		if (!StringUtil.isEmpty(adType)) {
			adType = adType + ",";
			searchVo.setAdType(adType);
		}
		
		PageInfo result = opAdService.searchVoListPage(searchVo, pageNo, pageSize);

		model.addAttribute("contentModel", result);
		


		return "op/adList";
	}

	@AuthPassport
	@RequestMapping(value = "/adForm", method = { RequestMethod.GET })
	public String adForm(HttpServletRequest request, Model model, @RequestParam(value = "id") Long id) {

		if (id == null) {
			id = 0L;
		}

		OpAd opAd = opAdService.initAd();
		if (id != null && id > 0) {
			opAd = opAdService.selectByPrimaryKey(id);
		}

		model.addAttribute("contentModel", opAd);
		
		//获取频道信息
		OpChannel cardChannel = opChannelService.initOpChannel();
		cardChannel.setName("日程广告位卡片");
		List<OpChannel> opChannels = opChannelService.selectByAll();
		opChannels.add(0, cardChannel);
		model.addAttribute("opChannels", opChannels);
		// 所有大类信息
		PartnerServiceTypeSearchVo searchVo = new PartnerServiceTypeSearchVo();
		searchVo.setParentId(0L);
		searchVo.setViewType((short) 0);

		List<PartnerServiceType> serviceTypelist = partnerServiceTypeService.selectBySearchVo(searchVo);
		model.addAttribute("serviceTypelist", serviceTypelist);

		return "op/adForm";
	}

	 @AuthPassport
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/adForm", method = { RequestMethod.POST })
	public String doAdForm(HttpServletRequest request, Model model, 
			@ModelAttribute("contentModel") OpAd opAd, 
			@RequestParam("imgUrlFile") MultipartFile file,
			BindingResult result
			) throws IOException {

		Long id = opAd.getId();

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
		
		String gotoType = opAd.getGotoType();
		String gotoUrl = opAd.getGotoUrl();
		
//		if (gotoType.equals("h5")) {
//			if (gotoUrl.indexOf("?") >= 0) {
//				gotoUrl+= "&goto_type=h5";
//			} else {
//				gotoUrl+= "?goto_type=h5";
//			}
//		}
//		
		if (gotoType.equals("app")) {
			gotoUrl = "xcloud://service_type_ids="+opAd.getServiceTypeIds();
			gotoUrl+= "&goto_type=app";
		}
		opAd.setGotoUrl(gotoUrl);
		
		String adType = opAd.getAdType();
		adType+= ",";
		opAd.setAdType(adType);
		
		if (StringUtil.isEmpty(opAd.getServiceTypeIds())) {
			opAd.setServiceTypeIds("");
		}
		
		// 更新或者新增
		if (id != null && id > 0) {
			if (!StringUtil.isEmpty(imgUrl)) {
				opAd.setImgUrl(imgUrl);
			}
			
			opAd.setUpdateTime(TimeStampUtil.getNow() / 1000);
			opAdService.updateByPrimaryKeySelective(opAd);
		} else {

			opAd.setAddTime(TimeStampUtil.getNow() / 1000);
			opAd.setUpdateTime(TimeStampUtil.getNow() / 1000);
			opAd.setImgUrl(imgUrl);

			opAdService.insertSelective(opAd);
		}

		return "redirect:ad_list";
	}
}
