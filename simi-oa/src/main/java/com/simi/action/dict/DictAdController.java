package com.simi.action.dict;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.BaseController;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.dict.DictAd;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.total.TotalHit;
import com.simi.service.ImgService;
import com.simi.service.dict.AdService;
import com.simi.service.op.AppToolsService;
import com.simi.service.total.TotalHitService;
import com.simi.vo.ApptoolsSearchVo;
import com.simi.vo.dict.DictAdVo;
import com.simi.vo.total.TotalHitSearchVo;

@Controller
@RequestMapping(value = "/dict")
public class DictAdController extends BaseController {

	@Autowired
	private AdService adService;
	
	@Autowired
	private AppToolsService appToolsService;
	
	@Autowired
	private TotalHitService hitService;
	
	@Autowired
	private ImgService imgService;
	
	/*
	 * 
	 *  运营平台--运营策略--广告位管理
	 * 
	 */
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
			
			//点击次数
			TotalHitSearchVo hitSearchVo = new TotalHitSearchVo();
			
			hitSearchVo.setLinkId(item.getId());
			//广告位点击
			hitSearchVo.setLinkType(Constants.TOTAL_HIT_LINK_TYPE_DICT_AD);
			
			List<TotalHit> hitList = hitService.selectBySearchVo(hitSearchVo);
			
			int total = 0;
			
			if(hitList.size() > 0){
				TotalHit totalHit = hitList.get(0);
				total = totalHit.getTotal();
			}
			
			vo.setTotalHit(total);
			
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


		// 处理 多文件 上传
		Map<String, String> fileMaps = imgService.multiFileUpLoad(request);	
		if (fileMaps.get("imgUrl") != null) {
			String imgUrl = fileMaps.get("imgUrl").toString();
			
			if (!StringUtil.isEmpty(imgUrl)) dictAd.setImgUrl(imgUrl);
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
