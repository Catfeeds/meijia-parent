package com.meijia.utils.htmlparse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import com.meijia.utils.FileUtil;

/**
 * word文件转为html
 * 如果是那种其他格式文件只修改后缀生成的word文档是会报错的。
 * Invalid header signature; read 0x6D78206C6D74683C, expected 0xE11AB1A1E011CFD0 - Your file appears not to be a valid OLE2 document
 * [Ljava.lang.StackTraceElement;@3d4cff90
 * @author bright87
 *
 */
public class Word2HtmlUtil {
	public static void main(String[] args) {
		String wordPath = "E:/2016/06/05/gaoxiaojie.docx";
		String htmlPath = "E:/2016/06/05/gaoxiaojie_docx.html";
		
		wordPath = "E:/2016/06/05/lujinguang.doc";
		htmlPath = "E:/2016/06/05/lujinguang_doc/lujinguang_doc.html";
		
		//智联企业账号导出的word简历
//		wordPath = "E:/2016/06/05/renjing.doc";
//		htmlPath = "E:/2016/06/05/renjing_doc/renjing_doc.html";
		
		wordPath = "/data/resume/file/rule/tmp/745832705425420288.doc";
		htmlPath = "/data/resume/file/rule/tmp/745832705425420288.html";
		
		
		boolean result = Word2HtmlUtil.word2html(wordPath, htmlPath);
		if (result) {
			System.out.println(result);
		} else {
			System.out.println("转换错误！");
		}
		
	}
	
	/**
	 * 检测word是否为html格式，内容如果包含 html xmlns 则为html形式
	 */
	public static boolean checkWordIsHtml(String wordPath) {
		
		File file = new File(wordPath);
		String content = FileUtil.getFileContent(file, "utf8");
		
		if (content.indexOf("html") >=0 || content.indexOf("xmlns") >= 0) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * docx/doc转为html
	 * @param wordPath
	 * @param htmlPath
	 * @return
	 */
	public static boolean word2html(String wordPath, String htmlPath) {
		File file = new File(wordPath);
		if ( file.getName().endsWith(".docx") || file.getName().endsWith(".DOCX") ) {
			return Word2HtmlUtil.docx2html(wordPath, htmlPath);
		} else if ( file.getName().endsWith(".doc") || file.getName().endsWith(".DOC") ) {
			return Word2HtmlUtil.doc2html(wordPath, htmlPath);
		} else {
			return false;
		}
	}
	
	/**
	 * word2003转为html，doc转为html
	 * 参考：http://www.cnblogs.com/always-online/p/4800131.html
	 * @param wordPath word文档路径
	 * @param htmlPath html文件路径
	 * @return
	 */
	public static boolean doc2html(String wordPath, final String htmlPath) {
		File wordFile = new File(wordPath);
		
		//文件不存在
		if (!wordFile.exists()) {
			return false;
		}
		
		//文件后缀不正确
		if (!wordFile.getName().endsWith(".doc") && !wordFile.getName().endsWith(".DOC")) {
			return false;
		}
		
		try {
			InputStream input = new FileInputStream(wordFile);
			HWPFDocument wordDocument = new HWPFDocument(input);
			WordToHtmlConverter word2HtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		
			//设置图片存放的位置
			word2HtmlConverter.setPicturesManager(new PicturesManager() {
				public String savePicture(byte[] content, PictureType pictureType, 
					String suggestedName, float widthInches, float heightInches) {
					File htmlFile = new File(htmlPath);
					String htmlDir = htmlFile.getParentFile() + "/";
					File imageFolderFile = new File(htmlDir);  
					if(!imageFolderFile.exists()){//图片目录不存在则创建
						imageFolderFile.mkdirs();
					}
					File file = new File(htmlDir + suggestedName);
					try {
						OutputStream os = new FileOutputStream(file);
						os.write(content);
						os.close();
					} catch (FileNotFoundException e) {
							e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return htmlDir + suggestedName;
				}
			});
			
			//解析word文档
			word2HtmlConverter.processDocument(wordDocument);
			Document htmlDocument = word2HtmlConverter.getDocument();
			
			File htmlFile = new File(htmlPath);
			OutputStream outStream = new FileOutputStream(htmlFile);
			
			//也可以使用字符数组流获取解析的内容
//			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
//			OutputStream outStream = new BufferedOutputStream(baos);
			DOMSource domSource = new DOMSource(htmlDocument);
			StreamResult streamResult = new StreamResult(outStream);
			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer serializer = factory.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");

			serializer.transform(domSource, streamResult);
			 
			//也可以使用字符数组流获取解析的内容
//			String content = baos.toString();
//			System.out.println(content);
//			baos.close();
			outStream.close();
			
			return true;
		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			System.out.println(e.getStackTrace());
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * word2007转为html，docx转为html
	 * @param wordPath word文档路径
	 * @param htmlPath html文件路径
	 * @return bool
	 */
	public static boolean docx2html(String wordPath, String htmlPath) {
		File wordFile = new File(wordPath);
		
		//文件不存在
		if (!wordFile.exists()) {
			return false;
		}
		
		//文件后缀不正确
		if (!wordFile.getName().endsWith(".docx") && !wordFile.getName().endsWith(".DOCX")) {
			return false;
		}
		
		try {
			//1) 加载word文档生成 XWPFDocument对象
			InputStream in = new FileInputStream(wordFile);  
			XWPFDocument document = new XWPFDocument(in);
			
			//2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
			File htmlFile = new File(htmlPath);
			String htmlDir = htmlFile.getParentFile() + "/";
			File imageFolderFile = new File(htmlDir);  
			XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));  
			options.setExtractor(new FileImageExtractor(imageFolderFile));  
			options.setIgnoreStylesIfUnused(false);  
			options.setFragment(true);
			
			//3) 将 XWPFDocument转换成XHTML
			 OutputStream out = new FileOutputStream(htmlFile);  
			 XHTMLConverter.getInstance().convert(document, out, options);
			 
			 return true;
		} catch (IOException e) {
//			e.getStackTrace();
			return false;
		}
	}
}
