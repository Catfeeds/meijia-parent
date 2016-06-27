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
public class Test51Mht {
	
	
	public static void main(String[] args) {
		String mhtPath = "E:/2016/06/03/51_mht/51job_陈迪(302834351).mht";
		String htmlPath = "E:/2016/06/03/51_mht/51job_陈迪(302834351).html";
		
		mhtPath = "E:/2016/06/03/51_mht/51job_顾维营(93782328).mht";
		htmlPath = "E:/2016/06/03/51_mht/51job_顾维营(93782328).html";
		
		String path = JSoupUtil.getClassResource(JSoupUtil.class) + "/src/";
		String filename = "resume-template-51job.mht";
		String targetName = "resume-template-51job-mht.html";
		mhtPath = path + filename;
		htmlPath = path + targetName;
		System.out.println(htmlPath);
		try {
			Test51Mht.parse(mhtPath, htmlPath);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 解析
	 * @param mhtPath
	 * @param htmlPath
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> parse(String mhtPath, String htmlPath) throws IOException {
		boolean mht2html = Mht2HtmlUtil.mht2html(mhtPath, htmlPath, "UTF8");
		if (!mht2html) {
			return null;
		}
		
		File input = new File(htmlPath);
		Document doc = Jsoup.parse(input, "UTF-8", "");
		
		String ID = JSoupUtil.parseByPattenAndSinglRegex(doc, "table:eq(1)", 0, "text" , "", "\\(ID:([0-9]+)\\)", 1);
		String name = JSoupUtil.parseByPatten(doc, "table:eq(1) span", 0, "text", "", "");
		String mobile = JSoupUtil.parseByPattenAndSinglRegex(doc, "table:eq(1)", 0, "text" , "", "电　话： ([0-9]+)（手机）", 1);
		String gender = JSoupUtil.parseByPattenAndSinglRegex(doc, "table:eq(1)", 0, "text" , "", "\\| (男|女) \\|", 1);
		String birthDate = JSoupUtil.parseByPattenAndSinglRegex(doc, "table:eq(1)", 0, "text" , "", "（(1982年8月3日)）", 1);
		String workAge = JSoupUtil.parseByPattenAndSinglRegex(doc, "table:eq(1) table", 0, "text" , "", "(.+)工作经验\\s+?\\|", 1);
		String abodeAddress = JSoupUtil.parseByPatten(doc, "table:eq(1) table tr:contains(居住地：) > td:eq(1)", 0, "text", "", "");
		String permanentAddress = JSoupUtil.parseByPatten(doc, "table:eq(1) table tr:contains(户　口：) > td:eq(3)", 0, "text", "", "");
		String email = JSoupUtil.parseByPatten(doc, "table:eq(1) table tr:contains(E-mail：) > td:eq(1)", 0, "text", "", "");
		String maritalStatus = JSoupUtil.parseByPattenAndSinglRegex(doc, "table:eq(1)", 0, "text" , "", "已婚 |未婚|保密", 0);
		String degree = JSoupUtil.parseByPattenAndSinglRegex(doc, "table[style=margin:0px auto;line-height:20px;padding:0 0 0 10px;]", 0, "html", "", "<td.+?>学　历：</td>\\s+?<td.+?>(.+)</td>", 1);
		String selfAssessment = JSoupUtil.parseByPattenAndSinglRegex(doc, "html > body > table:eq(2) > tbody > tr > td > table > tbody > tr > td > table:eq(7) > tbody", 0, "html" , "", "<span.+>(.+)</span>", 1);
		String jobIntensionPatten = "html > body > table:eq(2) > tbody > tr > td > table > tbody > tr > td > table:eq(8) > tbody";
		String arrivalTime = JSoupUtil.parseByPattenAndSinglRegex(doc, jobIntensionPatten, 0, "html", "", "<td.+>\\s?到岗时间：\\s?<span.+>(.+)</span></td>", 1);
		String jobCategory = JSoupUtil.parseByPattenAndSinglRegex(doc, jobIntensionPatten, 0, "html", "", "<td.+>\\s?工作性质：\\s?<span.+>(.+)</span></td>", 1);
		String hopeTrade = JSoupUtil.parseByPattenAndSinglRegex(doc, jobIntensionPatten, 0, "html", "", "<td.+>\\s?希望行业：\\s?<span.+>(.+)</span></td>", 1);
		String hopeCity = JSoupUtil.parseByPattenAndSinglRegex(doc, jobIntensionPatten, 0, "html", "", "<td.+>\\s?目标地点：\\s?<span.+>(.+)</span></td>", 1);
		String hopeSlary = JSoupUtil.parseByPattenAndSinglRegex(doc, jobIntensionPatten, 0, "html", "", "<td.+>\\s?期望月薪：\\s?<span.+>(.+)</span></td>", 1);
		String jobDuty = JSoupUtil.parseByPattenAndSinglRegex(doc, jobIntensionPatten, 0, "html", "", "<td.+>\\s?目标职能：\\s?<span.+>(.+)</span></td>", 1);;
		String workStatus = JSoupUtil.parseByPattenAndSinglRegex(doc, jobIntensionPatten, 0, "html", "", "<td.+>\\s?求职状态：\\s?<span.+>(.+)</span></td>", 1);
		String experienceAndSkills = "html > body > table:eq(2) > tbody > tr > td > table > tbody > tr > td > table:eq(9)";
		String work = JSoupUtil.parseByPattenAndSinglRegex(doc, experienceAndSkills, 0, "html", "", "<td.+>工作经验</td>[\\s\\S]+?(<table.+>[\\s\\S]+?</table>)", 1);
		String project = JSoupUtil.parseByPattenAndSinglRegex(doc, experienceAndSkills, 0, "html", "", "<td.+>项目经验</td>[\\s\\S]+?(<table.+>[\\s\\S]+?</table>)", 1);
		String education = JSoupUtil.parseByPattenAndSinglRegex(doc, experienceAndSkills, 0, "html", "", "<td.+>教育经历</td>[\\s\\S]+?(<table.+>[\\s\\S]+?</table>)", 1);
		String awards = JSoupUtil.parseByPattenAndSinglRegex(doc, experienceAndSkills, 0, "html", "", "<td.+>所获奖项</td>[\\s\\S]+?(<table.+>[\\s\\S]+?</table>)", 1);
		String dutyInSchool = JSoupUtil.parseByPattenAndSinglRegex(doc, experienceAndSkills, 0, "html", "", "<td.+>校内职务</td>[\\s\\S]+?(<table.+>[\\s\\S]+?</table>)", 1);
		String training = JSoupUtil.parseByPattenAndSinglRegex(doc, experienceAndSkills, 0, "html", "", "<td.+>培训经历</td>[\\s\\S]+?(<table.+>[\\s\\S]+?</table>)", 1);
		String certificate = JSoupUtil.parseByPattenAndSinglRegex(doc, experienceAndSkills, 0, "html", "", "<td.+>\\s+?证.+?书\\s+?</td>[\\s\\S]+?(<table.+>[\\s\\S]+?</table>)", 1);
		String lang = JSoupUtil.parseByPattenAndSinglRegex(doc, experienceAndSkills, 0, "html", "", "<td.+>语言能力</td>[\\s\\S]+?(<table.+>[\\s\\S]+?</table>)", 1);
		String skills = JSoupUtil.parseByPattenAndSinglRegex(doc, experienceAndSkills, 0, "html", "", "<td.+>IT 技能</td>[\\s\\S]+?(<table.+>[\\s\\S]+?</table>)", 1);
		String other = JSoupUtil.parseByPattenAndSinglRegex(doc, experienceAndSkills, 0, "html", "", "<td.+>其他信息</td>[\\s\\S]+?(<table.+>[\\s\\S]+?</table>)", 1);
		String coverLetter = JSoupUtil.parseByPattenAndSinglRegex(doc, "html > body > table:eq(2)", 0, "html", "", "<td.+>\\s+?求 职 信\\s+?</td>[\\s\\S]+?(<div.+>[\\s\\S]+?</div>)", 1);
		
		System.out.println("ID: " + ID);
		System.out.println("姓名: " + name);
		System.out.println("手机号：" + mobile);
		System.out.println("性别：" + gender);
		System.out.println("生日：" + birthDate);
		System.out.println("工作经验（年数）：" + workAge);
		System.out.println("居住地：" + abodeAddress);
		System.out.println("户口：" + permanentAddress);
		System.out.println("E-mail：" + email);
		System.out.println("婚姻状况：" + maritalStatus);
		System.out.println("最高学历：" + degree);
		System.out.println("自我评价：" + selfAssessment);
		System.out.println("到岗时间：" + arrivalTime);
		System.out.println("工作性质：" + jobCategory);
		System.out.println("希望行业：" + hopeTrade);
		System.out.println("目标地点：" + hopeCity);
		System.out.println("期望月薪：" + hopeSlary);
		System.out.println("目标只能：" + jobDuty);
		System.out.println("求职状态：" + workStatus);
		System.out.println("工作经验：" + work);
		System.out.println("项目经验：" + project);
		System.out.println("教育经历：" + education);
		System.out.println("所获奖项：" + awards);
		System.out.println("校内职务：" + dutyInSchool);
		System.out.println("培训经历：" + training);
		System.out.println("证书：" + certificate);
		System.out.println("语言能力：" + lang);
		System.out.println("技能：" + skills);
		System.out.println("其他信息：" + other);
		System.out.println("求职信：" + coverLetter);
		
		Map resume = new HashMap();
		return resume;
	}
	
	/**
	 * 解析简历
	 * @param path
	 * @return
	 */
	public static Map<String, String> parse(String path) {
		String content = Test51Mht.getContent(path);
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
