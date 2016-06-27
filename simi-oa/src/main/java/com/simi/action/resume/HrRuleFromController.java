package com.simi.action.resume;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ConfigPropertiesUtil;
import com.meijia.utils.FileUtil;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.resume.po.model.dict.HrFrom;
import com.resume.po.model.rule.HrRuleFrom;
import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.service.CacheDictService;
import com.simi.service.dict.DictService;
import com.simi.service.resume.HrFromService;
import com.simi.service.resume.HrRuleFromService;
import com.simi.vo.resume.HrRuleDetailVo;
import com.simi.vo.resume.HrRuleFromVo;
import com.simi.vo.resume.HrRuleJsouplVo;
import com.simi.vo.resume.HrRuleReglVo;
import com.simi.vo.resume.ResumeRuleSearchVo;

@Controller
@RequestMapping(value = "/resume")
public class HrRuleFromController extends BaseController {

	@Autowired
	private HrRuleFromService hrRuleFromService;
	
	@Autowired
	private HrFromService hrFromService;
	
	@Autowired
	private CacheDictService dictCacheService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@AuthPassport
	@RequestMapping(value = "/hrRuleFromList", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, ResumeRuleSearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel");
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);
		
		if (searchVo == null) {
			searchVo = new ResumeRuleSearchVo();
		}
		
		
		PageInfo result = hrRuleFromService.selectByListPage(searchVo, pageNo, pageSize);
		List<HrRuleFrom> list = result.getList();
		
		List<HrRuleFromVo> listVo = hrRuleFromService.getVos(list);
		for (int i = 0; i < list.size(); i++) {
			HrRuleFrom item = list.get(i);
			for (int j = 0; j < listVo.size(); j++) {
				HrRuleFromVo vo = listVo.get(j);
				if (vo.getId().equals(item.getId())) {
					list.set(i, vo);
					break;
				}
			}
			
		}
		result = new PageInfo(list);
		
		model.addAttribute("contentModel", result);
		
		//来源定义
		
		List<HrFrom> hrFroms = dictCacheService.loadHrFrom(false);
		
		model.addAttribute("hrFroms", hrFroms);
	
		return "resume/hrRuleFromList";
	}

	@AuthPassport
	@RequestMapping(value = "/hrRuleFromForm", method = { RequestMethod.GET })
	public String fromForm(Model model, @RequestParam(value = "id") Long id, HttpServletRequest request) {

		if (id == null) id = 0L;


		HrRuleFrom record = hrRuleFromService.initHrDictFrom();
		if ( id != null && id > 0) {
			record = hrRuleFromService.selectByPrimaryKey(id);
		}
		
		HrRuleDetailVo vo = new HrRuleDetailVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(record, vo);
		
		if (id.equals(0L)) {
			vo.setSampleType("textarea");
		} else if (StringUtil.isEmpty(vo.getSampleSrc())) {
			vo.setSampleType("upload");
		} else {
			vo.setSampleType("textarea");
		}
		
		if (StringUtil.isEmpty(vo.getTextOrHtml())) vo.setTextOrHtml("text");
		
		
		//处理json的情况:
		if (!StringUtil.isEmpty(vo.getMatchPatten())) {
			
			String utf8 = "";
			try {
				utf8 = new String(vo.getMatchPatten().getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			if (vo.getMatchType().equals("reg")) {
				HrRuleReglVo ruleVo = new HrRuleReglVo();

				ruleVo = GsonUtil.GsonToObject(utf8, HrRuleReglVo.class);
				
				BeanUtilsExp.copyPropertiesIgnoreNull(ruleVo, vo);
			}
			
			if (vo.getMatchType().equals("jsoup")) {
				HrRuleJsouplVo ruleVo = new HrRuleJsouplVo();
				
				ruleVo = GsonUtil.GsonToObject(utf8, HrRuleJsouplVo.class);
				
				BeanUtilsExp.copyPropertiesIgnoreNull(ruleVo, vo);
			} 
		}
		
		
		model.addAttribute("contentModel", vo);
		
		//简历来源集合
		List<HrFrom> hrFroms = dictCacheService.loadHrFrom(false);
		
		model.addAttribute("hrFroms", hrFroms);
		
		return "resume/hrRuleFromForm";
	}

	@AuthPassport
	@RequestMapping(value = "/hrRuleFromForm", method = { RequestMethod.POST })
	public String doFromForm(HttpServletRequest request, Model model, @ModelAttribute("contentModel") HrRuleDetailVo vo, BindingResult result) throws IOException {

		Long id = vo.getId();
		
		
		//正式的目录为
		String filePath = ConfigPropertiesUtil.getKey("resume.rule");
		
		String ruleFile = "";
		//将文件进行保存
		String samplePath = vo.getSamplePath();
		String sampleType = request.getParameter("sampleType");

		if (sampleType.equals("upload")) {
			
			if (id.equals(0L)) {
				String fileName = FileUtil.getFilePrefix(samplePath);
				String fileExt = FileUtil.getExtend(samplePath);
				
				ruleFile = filePath + File.separatorChar + fileName + "." + fileExt;
				
				FileUtil.copyFile(samplePath, ruleFile);
				
				File tmpFile = new File(samplePath);
				FileUtils.forceDelete(tmpFile);
			} else {
				ruleFile = samplePath;
			}
		} else {
			
			if (id > 0L) {
				File of = new File(samplePath);
				FileUtils.forceDelete(of);
			}

			String fileReName = OrderNoUtil.getUploadFileName().toString() + ".html";
			ruleFile = filePath + "/" + fileReName;
			File f = new File(ruleFile);
			//将源码写入文件
			FileUtils.writeStringToFile(f, vo.getSampleSrc(), "utf-8");
		}
		
		vo.setSamplePath(ruleFile);

		HrRuleFrom record = hrRuleFromService.initHrDictFrom();
		if ( id != null && id > 0) {
			record = hrRuleFromService.selectByPrimaryKey(id);
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(vo, record);
		
		String matchPatten = "";
		//处理json的情况.
		if (vo.getMatchType().equals("reg")) {
			HrRuleReglVo ruleRegVo = new HrRuleReglVo();
			vo.setMatchDictTypeId(0L);
			BeanUtilsExp.copyPropertiesIgnoreNull(vo, ruleRegVo);
			matchPatten = GsonUtil.GsonString(ruleRegVo);
		}
		
		if (vo.getMatchType().equals("jsoup")) {
			HrRuleJsouplVo ruleJsouplVo = new HrRuleJsouplVo();
			vo.setMatchDictTypeId(0L);
			BeanUtilsExp.copyPropertiesIgnoreNull(vo, ruleJsouplVo);
			matchPatten = GsonUtil.GsonString(ruleJsouplVo);
		}
		record.setMatchPatten(matchPatten);
		
		// 更新或者新增
		if (id != null && id > 0) {
			hrRuleFromService.updateByPrimaryKeySelective(record);
		} else {
			record.setId(id);
			record.setAddTime(TimeStampUtil.getNowSecond());
			hrRuleFromService.insert(record);
		}

		return "redirect:hrRuleFromList";
	}
}
