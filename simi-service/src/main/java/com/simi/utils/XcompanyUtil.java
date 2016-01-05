package com.simi.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.QrCodeUtil;
import com.simi.common.Constants;

/**
 * 公司的常用静态方法
 *
 */
public class XcompanyUtil {

	public static String getStaffTypeName(Short staffType) {
		String staffTypeName = "";
		switch (staffType) {
			case 0:
				staffTypeName = "全职";
				break;
			case 1:
				staffTypeName = "兼职";
				break;
			case 2:
				staffTypeName = "实习";
				break;
			default:
				staffTypeName = "全职";
		}
		return staffTypeName;
	}
	
	public static Short getStaffType(String staffTypeName) {
		Short staffType = 0;
		switch (staffTypeName) {
			case "全职":
				staffType = 0;
				break;
			case "兼职":
				staffType = 1;
				break;
			case "实习":
				staffType = 2;
				break;
			default:
				staffType =0;
		}
		return staffType;
	}	
	
	@SuppressWarnings("unchecked")
	public static String genQrCode(String invitationCode) throws WriterException, IOException {
		
		String qrCodeLogo = "http://img.51xingzheng.cn/c9778e512787866532e425e550023262";
		
		String contents = "xcloud-h5://action=company-join";
		contents += "&p=" + invitationCode;

		BufferedImage qrCodeImg = QrCodeUtil.genBarcode(contents, 800, 800, qrCodeLogo);

		ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
		try {
			boolean resultWrite = ImageIO.write(qrCodeImg, "png", imageStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		imageStream.flush();
		byte[] imgBytes = imageStream.toByteArray();

		String url = Constants.IMG_SERVER_HOST + "/upload/";
		String sendResult = ImgServerUtil.sendPostBytes(url, imgBytes, "png");

		ObjectMapper mapper = new ObjectMapper();

		HashMap<String, Object> o = mapper.readValue(sendResult, HashMap.class);

		String ret = o.get("ret").toString();

		HashMap<String, String> info = (HashMap<String, String>) o.get("info");

		String imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();
		
		return imgUrl;
	}
}
