package com.resume.action.parse;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.ConfigPropertiesUtil;
import com.meijia.utils.FileUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.htmlparse.JSoupUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/")
public class ParseController extends BaseController {

	@RequestMapping(value = "resumeTestJsoupParse", method = RequestMethod.POST)
	public AppResultData<Object> resumeTestJsoupParse(
			@RequestParam(value = "fileType", required = false, defaultValue = "") String fileType,
			@RequestParam(value = "sampleType", required = false, defaultValue = "") String sampleType,
			@RequestParam(value = "samplePath", required = false, defaultValue = "") String samplePath,
			@RequestParam(value = "sampleSrc", required = false, defaultValue = "") String sampleSrc,
			@RequestParam("findPatten") String findPatten,
			@RequestParam(value = "findIndex", required = false, defaultValue = "0") int findIndex,
			@RequestParam(value = "textOrHtml", required = false, defaultValue = "text") String textOrHtml,
			@RequestParam(value = "attrName", required = false, defaultValue = "") String attrName,
			@RequestParam(value = "removeRegex", required = false, defaultValue = "") String removeRegex,
			@RequestParam(value = "resultRegex", required = false, defaultValue = "") String resultRegex,
			@RequestParam(value = "resultIndex", required = false, defaultValue = "0") int resultIndex
			) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (fileType.equals("word") && StringUtil.isEmpty(samplePath)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("需要上传样本文件");
			return result;
		}
		
		Document doc = null;
		
		 if (!StringUtil.isEmpty(sampleSrc)) {
			 doc = Jsoup.parse(sampleSrc);
		 } else if (!StringUtil.isEmpty(samplePath)) {
			String filePath = ConfigPropertiesUtil.getKey("resume.rule.tmp");
			File input = new File(filePath + "/" + samplePath);
			
			doc = Jsoup.parse(input, "UTF-8", "");
		}
		
		if (doc == null) return result;
		
		if (!StringUtil.isEmpty(resultRegex)) {
			resultRegex = java.net.URLDecoder.decode(resultRegex, "UTF-8");
			resultRegex =  resultRegex.replace("\\\\", "\\");
		}
		
		String matchResult = "";
		
		if (StringUtil.isEmpty(resultRegex)) {
			matchResult = JSoupUtil.parseByPatten(doc, findPatten, findIndex, textOrHtml, attrName, removeRegex);
		} else {
			matchResult = JSoupUtil.parseByPattenAndSinglRegex(doc, findPatten, findIndex, textOrHtml, attrName, resultRegex, resultIndex);
		}

		result.setData(matchResult);
		return result;
	}
	
	@RequestMapping(value = "resumeTestRegexParse", method = RequestMethod.POST)
	public AppResultData<Object> resumeTestRegexParse(
			@RequestParam(value = "fileType", required = false, defaultValue = "") String fileType,
			@RequestParam(value = "sampleType", required = false, defaultValue = "") String sampleType,
			@RequestParam(value = "samplePath", required = false, defaultValue = "") String samplePath,
			@RequestParam(value = "sampleSrc", required = false, defaultValue = "") String sampleSrc,
			@RequestParam("blockRegex") String blockRegex,
			@RequestParam(value = "blockMatchIndex", required = false, defaultValue = "0") int blockMatchIndex,
			@RequestParam(value = "fieldRegex", required = false, defaultValue = "") String fieldRegex,
			@RequestParam(value = "fieldMatchIndex", required = false, defaultValue = "0") int fieldMatchIndex
			) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (fileType.equals("word") && StringUtil.isEmpty(samplePath)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("需要上传样本文件");
			return result;
		}
		
		String content = "";
		
		if (!StringUtil.isEmpty(samplePath)) {
			
			File input = new File(samplePath);
			
			content = FileUtil.getFileContent(input, "UTF-8");
		} else if (!StringUtil.isEmpty(sampleSrc)) {
			content = sampleSrc;
		}
		String matchResult = "";
		
		if (!StringUtil.isEmpty(blockRegex)) {
			blockRegex = java.net.URLDecoder.decode(blockRegex, "UTF-8");
			blockRegex =  blockRegex.replace("\\\\", "\\");
		}
		if (!StringUtil.isEmpty(fieldRegex)) {
			fieldRegex = java.net.URLDecoder.decode(fieldRegex, "UTF-8");
			 fieldRegex =  fieldRegex.replace("\\\\", "\\");
		}

		try {
			matchResult = JSoupUtil.parseByRegex(content, blockRegex, blockMatchIndex, fieldRegex, fieldMatchIndex);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(Constants.ERROR_999);
			result.setData(e.getMessage());
			return result;
		}
		

		result.setData(matchResult);
		return result;
	}
}
