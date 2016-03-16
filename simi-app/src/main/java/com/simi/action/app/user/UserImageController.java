package com.simi.action.app.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserImgs;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserImgService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserImgVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserImageController extends BaseController {

	@Autowired
	private UsersService userService;

	@Autowired
	private UserImgService userImgService;
	
	/**
	 * 用户头像上传接口
	 * 
	 * @throws IOException
	 * 
	 *             参考 ：http://www.concretepage.com/spring-4/spring-4-mvc-single-
	 *             multiple-file-upload-example-with-tomcat
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_user_head_img", method = RequestMethod.POST)
	public AppResultData<Object> userHeadImg(HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam("file") MultipartFile file) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		HashMap<String, String> img = new HashMap<String, String>();
		if (file != null && !file.isEmpty()) {

			String url = Constants.IMG_SERVER_HOST + "/upload/";
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			
			System.out.println("fileName = " + fileName);
			System.out.println("fileType = " + fileType);
			
			fileType = fileType.toLowerCase();
			String sendResult = ImgServerUtil.sendPostBytes(url,
					file.getBytes(), fileType);

			ObjectMapper mapper = new ObjectMapper();

			HashMap<String, Object> o = mapper.readValue(sendResult,
					HashMap.class);
			System.out.println(o.toString());
			String ret = o.get("ret").toString();
			
			HashMap<String, String> info = (HashMap<String, String>) o
					.get("info");

			String imgUrl = Constants.IMG_SERVER_HOST + "/"
					+ info.get("md5").toString();
			imgUrl = ImgServerUtil.getImgSize(imgUrl, "100", "100");
			u.setHeadImg(imgUrl);

			userService.updateByPrimaryKeySelective(u);
			
			//更新用户二维码
			userImgService.genUserQrCode(u.getId());

			img.put("user_id", userId.toString());
			img.put("head_img", ImgServerUtil.getImgSize(imgUrl, "100", "100"));
		}

		result.setData(img);
		return result;

	}

	/**
	 * 用户图片上传
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_user_img", method = RequestMethod.POST)
	public AppResultData<Object> userImg(HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam("file") MultipartFile[] files) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		List<HashMap<String, String>> imgs = new ArrayList<HashMap<String, String>>();

		if (files != null && files.length > 0) {

			for (int i = 0; i < files.length; i++) {
				String url = Constants.IMG_SERVER_HOST + "/upload/";
				String fileName = files[i].getOriginalFilename();
				String fileType = fileName
						.substring(fileName.lastIndexOf(".") + 1);
				fileType = fileType.toLowerCase();
				String sendResult = ImgServerUtil.sendPostBytes(url,
						files[i].getBytes(), fileType);

				ObjectMapper mapper = new ObjectMapper();

				HashMap<String, Object> o = mapper.readValue(sendResult,
						HashMap.class);

				String ret = o.get("ret").toString();

				HashMap<String, String> info = (HashMap<String, String>) o
						.get("info");

				String imgUrl = Constants.IMG_SERVER_HOST + "/"
						+ info.get("md5").toString();

				UserImgs userImg = new UserImgs();
				userImg.setImgId(0L);
				userImg.setImgUrl(imgUrl);
				userImg.setUserId(userId);
				userImg.setAddTime(TimeStampUtil.getNowSecond());
				userImgService.insert(userImg);

				HashMap<String, String> img = new HashMap<String, String>();
				img.put("user_id", userId.toString());
				img.put("img", imgUrl);
				img.put("img_trumb", ImgServerUtil.getImgSize(imgUrl, "400", "400"));
				
				imgs.add(img);
			}
		}

		result.setData(imgs);
		return result;
	}

	/**
	 * 获取用户图片
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "get_user_imgs", method = RequestMethod.GET)
	public AppResultData<Object> getUserImgs(HttpServletRequest request,
			@RequestParam("user_id") Long userId) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		List<UserImgs> userImgs = userImgService.selectByUserId(userId);

		List<UserImgVo> userImgVos = new ArrayList<UserImgVo>();

		for (UserImgs item : userImgs) {
			UserImgVo vo = new UserImgVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			vo.setImgTrumb(ImgServerUtil.getImgSize(item.getImgUrl(), "400", "400"));
			vo.setImg(item.getImgUrl());
			userImgVos.add(vo);
		}
		result.setData(userImgVos);

		return result;
	}

	/**
	 * 用户图片删除
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "del_user_img", method = RequestMethod.POST)
	public AppResultData<Object> delUserImg(HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam("img_id") Long imgId) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		UserImgs userImg = userImgService.selectByPrimaryKey(imgId);

		if (userImg != null) {
			userImgService.deleteByPrimaryKey(imgId);
		}
		return result;
	}
	
	/**
	 * 获取用户二维码
	 * 
	 * @throws IOException
	 * @throws WriterException 
	 */
	@RequestMapping(value = "get_qrcode", method = RequestMethod.GET)
	public AppResultData<Object> getUserQRCode(
			HttpServletRequest request,
			@RequestParam("user_id") Long userId) throws IOException, WriterException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		String qrCode = u.getQrCode();

		if (!StringUtil.isEmpty(qrCode)) {
			result.setData(qrCode);
			return result;
		}
		
		qrCode = userImgService.genUserQrCode(u.getId());
		
		result.setData(qrCode);
		
		return result;
	}	

}
