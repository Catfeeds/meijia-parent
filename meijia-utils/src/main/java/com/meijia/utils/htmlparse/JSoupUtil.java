package com.meijia.utils.htmlparse;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.meijia.utils.FileUtil;
import com.meijia.utils.StringUtil;

public class JSoupUtil {

	public static final String USER_AGENT = "Mozilla";

	private static final int TIMEOUT = 3000;

	public static Document get(String url) throws IOException {
		// TODO: review user agent and timeout
		return Jsoup.connect(url).userAgent(USER_AGENT).timeout(TIMEOUT).get();
	}

	public static Document post(String url, Map<String, String> data) throws IOException {
		return Jsoup.connect(url).userAgent(USER_AGENT).timeout(TIMEOUT).data(data).post();
	}
	
	/**
	 * 用jsoup 基础选择器，找出相应的值，范例如下：
	 * <meta charset="utf-8">
	 * <div class="resume-preview-main-title">
	 *	<div class="main-title-fl fc6699cc">xxxx</div>
	 *	<div class="main-title-fr">手机 ：13161070900</div>
	 *	</div>
	 *
	 *  找utf-8   JSoupUtil.parseByPatten(doc, "meta[charset]", "charset", "")
	 *  找xxxx  ，则为 JSoupUtil.parseByPatten(doc, "div.main-title-fl", "", "")
	 *  找手机号, 则为 JSoupUtil.parseByPatten(doc, "div.main-title-fr", "", "手机 ：")
	 * @param doc        jsoup Docuemnt
	 * @param patten     查找表达式
	 * @param attrName   属性
	 * @param replaceStr 最终结果替换的字符串
	 * @return
	 */
	public static String parseByPatten(Document doc, 
			String findPatten, 
			int findIndex,
			String textOrHtml,
			String attrName, 
			String removeRegex) {
		String content = "";
		Element item = null;
		try {
			item = doc.select(findPatten).get(findIndex);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		
		if (item == null) return content;
		
		if (textOrHtml.equals("html")) {
			content = item.html();
		} else {
			content = item.text();
		}
		
		if (!StringUtil.isEmpty(attrName))
			content = item.attr(attrName);

		if (!StringUtil.isEmpty(removeRegex))
			content = content.replaceAll(removeRegex, "");

		content.replace(" ", "");
		return content;
	}
	
	/**
	 * jsoup 解析标签后，用正则表达式进行分离，获得相应的值
	 * 
	 * <div class="summary-top">
			<span>女&nbsp;&nbsp;&nbsp;&nbsp;24岁(1991年4月)&nbsp;&nbsp;&nbsp;&nbsp;1年工作经验&nbsp;&nbsp;&nbsp;&nbsp;本科&nbsp;&nbsp;&nbsp;&nbsp;未婚</span>
			<br>
			现居住地：北京 | 户口：忻州 | 中共党员(含预备党员) | 有海外工作/学习经验
		</div>
		
		JSoupUtil.parseByPattenAndSinglRegex(doc, "div.summary-top > span", "", "/男|女")
		女    24岁(1991年4月)    1年工作经验    本科    未婚
	 * @param doc        jsoup Docuemnt
	 * @param patten     查找表达式
	 * @param textOrHtml 获取文本还是带html标签的内容 text or html
	 * @param attrName   属性
	 * @param regex 	 查找的正则表达式
	 * @param index		分组索引
	 * @return
	 */
	public static String parseByPattenAndSinglRegex(
			Document doc, 
			String findPatten, 
			int findIndex,
			String textOrHtml, 
			String attrName, 
			String resultRegex, int resultIndex) {

		String result = "";
		String content = "";
		Element item = doc.select(findPatten).get(findIndex);
		if (textOrHtml.equals("html")) {
			content = item.html();
		} else {
			content = item.text();
		}
		if (!StringUtil.isEmpty(attrName))
			content = item.attr(attrName);
		
//		System.out.println(content);
		try {
			Pattern pat = Pattern.compile(resultRegex, Pattern.CASE_INSENSITIVE);
			Matcher mat = pat.matcher(content);

			boolean rs = mat.find();

			if (!rs)
				return result;

			result = mat.group(resultIndex);
		} catch (Exception e) {
			return "";
		}

		return result;
	}
	
	/**
	 * 转义正则特殊字符 （$()*+.[]?\^{},|）
	 * 
	 * @param keyword
	 * @return
	 */
	public static String escapeExprSpecialWord(String keyword) {
		if (StringUtils.isEmpty(keyword)) {
			String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
			for (String key : fbsArr) {
				if (keyword.contains(key)) {
					keyword = keyword.replace(key, "\\" + key);
				}
			}
		}
		return keyword;
	}
	
	public static String getClassResource(Class<?> c) {
		  String path = c.getResource(c.getSimpleName() + ".class").getPath().replace(c.getSimpleName() + ".class", "");
		  return path;
	}
	
	/**
	 * 用正则表达式解析内容
	 * 1.根据blockRegex提供的正则表达式匹配某一块内容，如果匹配失败返回空字符串。
	 * 2.从blockRegex匹配结果中取出索引为blockMatchIndex的内容，如果数组中不存在blockMatchIndex
	 * 返回空字符串
	 * 3.用fieldRegex匹配最中内容，如果fieldRegex为空字符串直接返回blockRegex匹配到的内容
	 * @param str 原始内容
	 * @param blockRegex 匹配某一块内容的正则表达式，例如匹配整个求职意向的。
	 * @param fieldRegex 匹配某个具体字段的正则表达式，例如求职意向中的期望工作地点。
	 * @return
	 */
	public static String parseByRegex(
			String str, 
			String blockRegex, 
			int blockMatchIndex, 
			String fieldRegex, 
			int fieldMatchIndex) {
		boolean validateIndex = 0 <= blockMatchIndex && 0 <= fieldMatchIndex;
		if ( StringUtil.isEmpty(str) || StringUtil.isEmpty(blockRegex) || !validateIndex ) {
			return "";
		}
		
		//匹配某一区块的内容 
		String block = "";
		String[] matchResult = RegExpUtil.match(blockRegex, str);
		if ( blockMatchIndex <= (matchResult.length - 1) ) {
			block = matchResult[blockMatchIndex];
		} else {
			return "";
		}
		
		//匹配字段的正则表达式为空，直接返回区块的匹配结果。
		if (StringUtil.isEmpty(fieldRegex)) {
			return block;
		}
		
		//匹配具体字段内容
		matchResult = RegExpUtil.match(fieldRegex, block);
		if (fieldMatchIndex <= (matchResult.length - 1)) {
			return matchResult[fieldMatchIndex];
		} else {
			return "";
		}
		
	}
	


	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//智联简历
		String path = JSoupUtil.getClassResource(JSoupUtil.class) + "/src/";
		String filename = "resume-template-zhilian.html";
		File input = new File(path + filename);
		Document doc = Jsoup.parse(input, "UTF-8", "");

		// Element item = doc.select("meta[charset]").first();
		// System.out.println(item);
		// String item1 = item.attr("charset");
		// System.out.print(item1);
		
		//基本信息 --> 户口
		String resume = FileUtil.getFileContent(input, "UTF-8");
		String baseInfoRegex = "<div\\s*?class=\"resume-preview-main-title\\s+?posR\">([\\s\\S]+?)<div\\s+?class=\"resume-preview-all\">";
		String permanentAddressRegex = "户口：(.+?)\\s*?\\|";
		String permanentAddress = JSoupUtil.parseByRegex(resume, baseInfoRegex, 1, permanentAddressRegex, 1);
		System.out.println("户口：" + permanentAddress);
		
//		System.out.println("match chartset = " + JSoupUtil.parseByPatten(doc, "meta[charset]", "text", "charset", ""));
//		System.out.println("match ID = " + JSoupUtil.parseByPatten(doc, "span.resume-left-tips-id", "text", "", "ID:"));
//		System.out.println("match 姓名 = " + JSoupUtil.parseByPatten(doc, "div.main-title-fl", "text", "", ""));
//		System.out.println("match 手机号 = " + JSoupUtil.parseByPatten(doc, "div.main-title-fr", "text", "", "手机 ："));
//		System.out.println("match 性别 = " + JSoupUtil.parseByPattenAndSinglRegex(doc, "div.summary-top > span", "text", "", "/男|女", 0));
//		System.out.println("match 生日 = " + JSoupUtil.parseByPattenAndSinglRegex(doc, "div.summary-top > span", "text" , "", "(([0-9]+年[0-9]+月))", 0));
		System.out.println("match 工作经验 = " + JSoupUtil.parseByPattenAndSinglRegex(doc, "div.summary-top > span",0,  "text" , "", "(([0-9]+年工作经验))", 0));
//		System.out.println("match 学历 = " + JSoupUtil.parseByPattenAndSinglRegex(doc, "div.summary-top > span","text" , "", "/小学|初中|高中|大专|专科|本科|硕士|博士|博士后", 0));
//		System.out.println("match 婚姻状态 = " + JSoupUtil.parseByPattenAndSinglRegex(doc, "div.summary-top > span","text" , "", "/已婚|未婚|保密|离异", 0));
//		System.out.println("match 现居住地 = " + JSoupUtil.parseByPattenAndSinglRegex(doc, "div.summary-top", "text", "",  "现居住地：(.+?)\\s*?\\|", 1));
//		System.out.println("match 户口 = " + JSoupUtil.parseByPattenAndSinglRegex(doc, "div.summary-top", "text", "", "户口：(.+?)\\s*?\\|", 1));
		System.out.println("match 政治面貌 = " + JSoupUtil.parseByPattenAndSinglRegex(doc, "div.summary-top", 0, "text" , "", "中共党员\\(含预备党员\\) |团员|群众|民主党派|无党派人士|无可奉告", 0));
		System.out.println("match 海外经验 = " + JSoupUtil.parseByPattenAndSinglRegex(doc, "div.summary-top", 0, "text" , "", "有海外工作\\/学习经验", 1));
		System.out.println("match 身份证 = " + JSoupUtil.parseByPattenAndSinglRegex(doc, "div.summary-bottom", 0, "text", "",  "身份证：(.+?)\\s*?\\ ", 1));
//		System.out.println("match 手机 = " + JSoupUtil.parseByPattenAndBetweenRegex(doc, "div.summary-bottom", "", "手机：", " ", ""));
//		System.out.println("match 邮箱 = " + JSoupUtil.parseByPatten(doc, "a[href^=mailto]", "text", "", ""));
//		System.out.println("match 头像 = " + JSoupUtil.parseByPatten(doc, "img.headerImg", "text", "src", ""));
		System.out.println("match 期望工作地区 = " + JSoupUtil.parseByPatten(doc, "div.resume-preview-top  table tr:eq(0) td:eq(1)", 0, "text", "", ""));
//		System.out.println("match 期望月薪： = " + JSoupUtil.parseTableByPatten(doc, "div.resume-preview-top > table","期望月薪：", 1));
//		System.out.println("match 目前状况： = " + JSoupUtil.parseTableByPatten(doc, "div.resume-preview-top > table","目前状况：", 1));
//		System.out.println("match 期望工作性质： = " + JSoupUtil.parseTableByPatten(doc, "div.resume-preview-top > table","期望工作性质：", 1));
//		System.out.println("match 期望从事职业： = " + JSoupUtil.parseTableByPatten(doc, "div.resume-preview-top > table","期望从事职业：", 1));
//		System.out.println("match 期望从事行业： = " + JSoupUtil.parseTableByPatten(doc, "div.resume-preview-top > table","期望从事行业：", 1));
//		System.out.println("match 自我评价 = " + JSoupUtil.parseByPatten(doc, "div.resume-preview-dl", "html", "", ""));
		System.out.println("match 工作经历 = " + JSoupUtil.parseByPatten(doc, "div.resume-preview-all:contains(工作经历) h2", 0, "html", "", ""));
//		System.out.println("match 项目经历 = " + JSoupUtil.parseByPatten(doc, "div.resume-preview-all:contains(项目经历)", "html","", "<h3.*>项目经历</h3>"));
//		System.out.println("match 教育经历 = " + JSoupUtil.parseByPatten(doc, "div.resume-preview-all:contains(教育经历)", "html","", "<h3.*>教育经历</h3>"));
//		System.out.println("match 培训经历 = " + JSoupUtil.parseByPatten(doc, "div.resume-preview-all:contains(培训经历)", "html","", "<h3.*>培训经历</h3>"));
//		System.out.println("match 证书 = " + JSoupUtil.parseByPatten(doc, "div.resume-preview-all:contains(证书)","", "html", "<h3.*>证书</h3>"));
//		System.out.println("match 语言能力 = " + JSoupUtil.parseByPatten(doc, "div.resume-preview-all:contains(语言能力)", "html", "", "<h3.*>语言能力</h3>"));
//		System.out.println("match 专业技能 = " + JSoupUtil.parseByPatten(doc, "div.resume-preview-all:contains(专业技能)", "html", "", "<h3.*>专业技能</h3>"));
//		System.out.println("match 兴趣爱好 = " + JSoupUtil.parseByPatten(doc, "div.resume-preview-all:contains(兴趣爱好)", "text", "", "兴趣爱好"));	
//		System.out.println("match 附件 = " + JSoupUtil.parseByPatten(doc, "div.resume-preview-all:contains(附件)", "html", "", "<h3.*>附件</h3>"));		
	}

}
