package com.meijia.utils.htmlparse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 解析51job的mht格式的简历
 * @author bright87
 *
 */
public class QianchengwuyouUtil {
	
	
	public static void main(String[] args) {
		String mhtPath = "E:/2016/06/03/51_mht/51job_陈迪(302834351).mht";
		String htmlPath = "E:/2016/06/03/51_mht/51job_陈迪(302834351).html";
		
		try {
			QianchengwuyouUtil.parse(mhtPath, htmlPath);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static Map<String, String> parse(String mhtPath, String htmlPath) throws IOException {
		boolean mht2html = Mht2HtmlUtil.mht2html(mhtPath, htmlPath, "UTF8");
		if (!mht2html) {
			return null;
		}
		
		File input = new File(htmlPath);
		Document doc = Jsoup.parse(input, "UTF-8", "");
		
		String ID = JSoupUtil.parseByPattenAndSinglRegex(doc, "table > tbody > tr > td > table:eq(1)", "", "\\(ID:([0-9])+\\)", 1);
		String name = JSoupUtil.parseByPatten(doc, "table > tbody > tr > td > table:eq(1) span", "text", "", "");
		String mobile = JSoupUtil.parseByPattenAndSinglRegex(doc, "table > tbody > tr > td > table:eq(1) table", "", "电　话： ([0-9]+)（手机）", 1);
		String gender = JSoupUtil.parseByPattenAndSinglRegex(doc, "table > tbody > tr > td > table:eq(1) table", "", "\\| (男|女) \\|", 1);
		String birthDate = JSoupUtil.parseByPattenAndSinglRegex(doc, "table > tbody > tr > td > table:eq(1) table", "", "（(1982年8月3日)）", 1);
		String workAge = JSoupUtil.parseByPattenAndSinglRegex(doc, "table > tbody > tr > td > table:eq(1) table", "", "(.+)工作经验\\s+?\\|", 1);
		String abodeAddress = JSoupUtil.parseByPatten(doc, "table > tbody > tr > td > table:eq(1) table tr:contains(居住地：) > td:eq(1)", "text", "", "");
		String permanentAddress = JSoupUtil.parseByPatten(doc, "table > tbody > tr > td > table:eq(1) table tr:contains(户　口：) > td:eq(3)", "text", "", "");
		String email = JSoupUtil.parseByPatten(doc, "table > tbody > tr > td > table:eq(1) table tr:contains(E-mail：) > td:eq(1)", "text", "", "");
//		String degree = JSoupUtil.parseByPatten(doc, "table[style=margin:0px auto;line-height:20px;padding:0 0 0 10px;]", "html", "", "<td.+?>学　历：</td>\\s+?<td.+?>本科</td>");
		//> tbody > tr > td:eq(0)
		
		System.out.println("ID: " + ID);
		System.out.println("姓名: " + name);
		System.out.println("手机号：" + mobile);
		System.out.println("性别：" + gender);
		System.out.println("生日：" + birthDate);
		System.out.println("工作经验（年数）：" + workAge);
		System.out.println("居住地：" + abodeAddress);
		System.out.println("户口：" + permanentAddress);
		System.out.println("E-mail：" + email);
//		System.out.println(degree);
		
		Map resume = new HashMap();
		return resume;
	}
	
	/**
	 * 解析简历
	 * @param path
	 * @return
	 */
	public static Map<String, String> parse(String path) {
		String content = QianchengwuyouUtil.getContent(path);
		if ( "" == content || null == content ) {
			return null;
		}
		
		Map resume = new HashMap();
		return resume;
	}
	
	/**
	 * 解析简历ID
	 * @param content 简历内容
	 * @return 解析到的ID或者空字符串
	 */
	public static String parseID(String content) {
		return "";
	}
	
	public static String parseName(String content) {
		
		return "";
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	private static String getContent(String path) {
		String content = Mht2HtmlUtil.mht2html(path);
		return content;
	}
}
