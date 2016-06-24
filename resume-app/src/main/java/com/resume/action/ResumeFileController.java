package com.resume.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.meijia.utils.ConfigPropertiesUtil;
import com.meijia.utils.FileUtil;
import com.meijia.utils.OrderNoUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.htmlparse.JSoupUtil;
import com.meijia.utils.htmlparse.Mht2HtmlUtil;
import com.meijia.utils.htmlparse.Word2HtmlUtil;
import com.resume.po.model.rule.HrRuleFrom;
import com.resume.po.model.rule.HrRules;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.user.Users;
import com.simi.service.resume.HrRuleFromService;
import com.simi.service.resume.HrRuleService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/")
public class ResumeFileController extends BaseController {
	
	@Autowired
	private HrRuleFromService hrRuleFromService;
	
	@Autowired
	private HrRuleService hrRuleService;

	@RequestMapping(value = "uploadRuleFile", method = RequestMethod.POST)
	public AppResultData<Object> cityList(
	// @RequestParam("ruleType") String ruleType,
			@RequestParam("file") MultipartFile file) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		String fileFName = "";

		if (file != null && !file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);

			String filePath = ConfigPropertiesUtil.getKey("resume.rule.tmp");
			String fileReName = OrderNoUtil.getUploadFileName().toString();

			fileFName = fileReName + "." + fileType;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, fileFName));
			
			//如果是mht 或者为word，则需要转换为html格式的
			if (fileType.equals("mht")) {
				String fileContent = Mht2HtmlUtil.mht2html(filePath + File.pathSeparator + fileFName);
				
				if (StringUtil.isEmpty(fileContent)) {
					result.setData("");
					return result;
				}
				
				File f = new File(filePath + File.separatorChar + fileReName + ".html");
				FileUtils.writeStringToFile(f, fileContent, "utf-8");
				fileFName = fileReName + ".html";
			}
			
			if (fileType.equals("doc") || fileType.equals("DOC")) {
				String wordPath = filePath + File.separatorChar + fileFName;
				String htmlPath = filePath + File.separatorChar + fileReName + ".html";
				fileFName = fileReName + ".html";
				if (Word2HtmlUtil.checkWordIsHtml(wordPath)) {
					FileUtil.copyFile(wordPath, htmlPath);
				} else {
					boolean s = Word2HtmlUtil.doc2html(wordPath, htmlPath);
					
					if (!s) {
						result.setData("");
						return result;
					}
				}
			}
			
			result.setData(fileFName);
		}

		return result;
	}

	@RequestMapping(value = "downloadRuleFile", method = RequestMethod.GET)
	public String download(@RequestParam("ruleType") String ruleType,
			@RequestParam("id") Long id,	
		 HttpServletRequest request, HttpServletResponse response) {
		
		String simplePath = "";
		if (ruleType.equals("ruleFrom")) {
			HrRuleFrom item = hrRuleFromService.selectByPrimaryKey(id);
			simplePath = item.getSamplePath();
		}
		
		if (ruleType.equals("rule")) {
			HrRules item = hrRuleService.selectByPrimaryKey(id);
			simplePath = item.getSamplePath();
		}
		
		String fileName = FileUtil.getFilePrefix(simplePath);
		String fileExt = FileUtil.getExtend(simplePath);
		
		String realName = fileName + "." + fileExt;
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + realName);
		try {
			InputStream inputStream = new FileInputStream(new File(simplePath));

			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			// 这里主要关闭。
			os.close();

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
		return null;
	}
}
