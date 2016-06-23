package com.meijia.utils.htmlparse;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestZhilianHtml {
	public static void main(String[] args) {
		String wordPath = "E:/2016/06/05/renjing.html";
		
		try {
			TestZhilianHtml.parse(wordPath);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 解析智联word简历
	 * @param wordPath 简历word文档路径
	 * @throws IOException
	 * html > body > div > table:eq(2)
	 */
	public static void parse(String wordPath) throws IOException {
		File input = new File(wordPath);
		Document doc = Jsoup.parse(input, "UTF-8", "");
		
		String ID = JSoupUtil.parseByPattenAndSinglRegex(doc, "html > body > div > p:eq(1)", "html", "", "<span[\\s\\S]+?>ID</span>\\s*<span[\\s\\S]+?>：</span>\\s*<span[\\s\\S]+?>(.+?)\\s*?<span", 1);
		String baseInfoPatten = "html > body > div > table:eq(2)";
		String name = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "<span[\\s\\S]+?>(.+)<span.+?>", 1);
		String gender = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "男|女", 0);
		String birthDate = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "[0-9]+年[0-9]+月", 0);
		String degree = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "初中|中技|高中|中专|大专|本科|硕士|MBA|EMBA|博士", 0);
		String maritalStatus = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "已婚|未婚|离异", 0);
		String abodeAddress = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "现居住地：(.+?)\\s*?\\|", 1);
		String permanentAddress = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "户口：(.+?)\\s*?\\|", 1);
		String politicsStatus = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "中共党员(含预备党员)|团员|群众|民主党派|无党派人士 ", 0);
		String overseasExperience = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "有海外工作/学习经验", 0);
		String IDNumber = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "身份证：(.+?)<br\\s*?/?>", 1);
		String mobile = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "手机：(.+?)<br\\s*?/?>", 1);
		String email = JSoupUtil.parseByPattenAndSinglRegex(doc, baseInfoPatten, "html", "", "E-mail：<a.+?>(.+?)</a>", 1);
		String jobIntensionPatten = "table[style=border-collapse:collapse;border:none;mso-yfti-tbllook:1184;mso-padding-alt:0cm 5.4pt 0cm 5.4pt;mso-border-insideh:none;mso-border-insidev:none]";
		String hopeCity = JSoupUtil.parseByPattenAndSinglRegex(doc, jobIntensionPatten, "html", "", "[\\s\\S]", 0);
		
		System.out.println("ID：" + ID);
		System.out.println("姓名：" + name);
		System.out.println("性别：" + gender);
		System.out.println("生日：" + birthDate);
		System.out.println("学历：" + degree);
		System.out.println("婚姻状态：" + maritalStatus);
		System.out.println("现居住地：" + abodeAddress);
		System.out.println("户口：" + permanentAddress);
		System.out.println("政治面貌：" + politicsStatus);
		System.out.println("海外工作/学习经验：" + overseasExperience);
		System.out.println("身份证号：" + IDNumber);
		System.out.println("手机：" + mobile);
		System.out.println("E-mail：" + email);
		System.out.println(hopeCity);
	}
}
