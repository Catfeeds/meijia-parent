package com.meijia.utils.htmlparse;

import java.io.File;
import java.io.IOException;

import com.meijia.utils.FileUtil;

public class TestZhilianWord {
	public static void main(String[] args) {
		String path = JSoupUtil.getClassResource(JSoupUtil.class) + "/src/";
		String filename = "resume-template-zhilian.doc";
		
		TestZhilianWord.parse(path + filename);
	}
	
	/**
	 * 解析智联word简历
	 * @param wordPath 简历word文档路径
	 * @throws IOException
	 * html > body > div > table:eq(2)
	 */
	public static void parse(String wordPath) {
		//读取简历内容
		File input = new File(wordPath);
		String resume = FileUtil.getFileContent(input, "UTF-8");
		
		//ID
		String idExp = "<span\\s+?lang=EN-US\\s+?.+?>ID</span><span.+?>：</span><span.+?>(.+?)\\s*?<span.+?>";
		String id = JSoupUtil.parseByRegex(resume, idExp, 1, "", 0);
		
		//个人基本信息
		String baseInfoExp = "<table\\s+?class=MsoTableGrid\\s+?border=0 cellspacing=0 cellpadding=0 width=712.+?>(.+?)<table\\s+?class=MsoTableGrid.+?>";
		String nameExp = "<p.+?><b.+?><span.+?>(.+?)<span\\s+?lang=EN-US><o:p></o:p></span></span></b></p>";
		String name = JSoupUtil.parseByRegex(resume, baseInfoExp, 1, nameExp, 1);
		String genderExp = "<span.+?>(男|女)&nbsp;";
		String gender = JSoupUtil.parseByRegex(resume, baseInfoExp, 1, genderExp, 1);
		String birthdateExp = "([0-9]+年[0-9]+月)";
		String birthdate = JSoupUtil.parseByRegex(resume, baseInfoExp, 1, birthdateExp, 1);
		String degreeExp = "初中|中技|高中|中专|大专|本科|硕士|MBA|EMBA|博士";
		String degree = JSoupUtil.parseByRegex(resume, baseInfoExp, 1, degreeExp, 0);
		String maritalStatusExp = "已婚|未婚|离异";
		String maritalStatus = JSoupUtil.parseByRegex(resume, baseInfoExp, 1, maritalStatusExp, 0);
		String abodeAddressExp = "现居住地：(.+?)\\s+?\\|";
		String abodeAddress = JSoupUtil.parseByRegex(resume, baseInfoExp, 1, abodeAddressExp, 1);
		String permanentAddressExp = "户口：(.+?)</span>";
		String permanentAddress = JSoupUtil.parseByRegex(resume, baseInfoExp, 1, permanentAddressExp, 1);
		String IDNumberExp = "身份证：(.+?)<br\\s+?/>";
		String IDNumber = JSoupUtil.parseByRegex(resume, baseInfoExp, 1, IDNumberExp, 1);
		String mobileExp = "手机：([0-9]{11})<br\\s+?/>";
		String mobile = JSoupUtil.parseByRegex(resume, baseInfoExp, 1, mobileExp, 1);
		String emailExp = "E-mail：<a.+?>(.+?)</a>";
		String email = JSoupUtil.parseByRegex(resume, baseInfoExp, 1, emailExp, 1);
		
		//求职意向
		String jobIntentionExp = ">求职意向</span></b>.+?<table\\s+?class=MsoTableGrid.+?>(.+?)</table>";
		String hopeCityExp = ">期望工作地区：<span.+?><o:p></o:p></span></span></p>\\s*?</td>\\s*?<td.+?>\\s*?<p.+?><span.+?>\\s*?(.+?)<span.+?>";
		String hopeCity = JSoupUtil.parseByRegex(resume, jobIntentionExp, 1, hopeCityExp, 1);
		String hopeSlaryExp = ">期望月薪：<span.+?><o:p></o:p></span></span></p>\\s*?</td>\\s*?<td.+?>\\s*?<p.+?><span.+?>(.+?)<span.+?>";
		String hopeSlary = JSoupUtil.parseByRegex(resume, jobIntentionExp, 1, hopeSlaryExp, 1);
		String currentStatusExp = ">目前状况：<span.+?><o:p></o:p></span></span></p>\\s*?</td>\\s*?<td.+?>\\s*?<p.+?><span.+?>\\s*?(.+?)<span.+?>";
		String currentStatus = JSoupUtil.parseByRegex(resume, jobIntentionExp, 1, currentStatusExp, 1);
		
		System.out.println("ID：" + id);
		System.out.println("姓名：" + name);
		System.out.println("性别：" + gender);
		System.out.println("生日：" + birthdate);
		System.out.println("学历：" + degree);
		System.out.println("婚姻状态：" + maritalStatus);
		System.out.println("现居住地：" + abodeAddress);
		System.out.println("户口：" + permanentAddress);
		System.out.println("身份证：" + IDNumber);
		System.out.println("手机号：" + mobile);
		System.out.println("E-mail:" + email);
		System.out.println("期望工作地：" + hopeCity);
		System.out.println("期望薪资：" + hopeSlary);
		System.out.println("目前状况：" + currentStatus);
	}
}
