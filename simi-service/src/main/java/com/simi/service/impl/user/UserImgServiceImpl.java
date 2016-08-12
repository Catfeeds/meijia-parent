package com.simi.service.impl.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.QrCodeUtil;
import com.meijia.utils.StringUtil;
import com.simi.service.user.UserImgService;
import com.simi.service.user.UsersService;
import com.simi.common.Constants;
import com.simi.po.dao.user.UserImgsMapper;
import com.simi.po.model.user.UserImgs;
import com.simi.po.model.user.Users;

@Service
public class UserImgServiceImpl implements UserImgService{

	@Autowired
	private UserImgsMapper userImgMapper;
	
	@Autowired
	private UsersService userService;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userImgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserImgs record) {
		return userImgMapper.insert(record);
	}

	@Override
	public List<UserImgs> selectByUserId(Long userId) {
		return userImgMapper.selectByUserId(userId);
	}

	@Override
	public UserImgs selectByPrimaryKey(Long id) {
		return userImgMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserImgs record) {
		return userImgMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserImgs record) {
		return userImgMapper.updateByPrimaryKeySelective(record);
	}	
	
	//生成用户二维码
	@SuppressWarnings("unchecked")
	@Override
	public String genUserQrCode(Long userId) {
		
		Users u = userService.selectByPrimaryKey(userId);
		String qrCodeLogo = userService.getHeadImg(u);
		if (StringUtil.isEmpty(qrCodeLogo)) {
			qrCodeLogo = Constants.DEFAULT_LOGO;
		}
		
		String qrUrl = "http://www.bolohr.com/d/open.html?";
		String contents = qrUrl + "category=app";
			   contents+= "&action=add_friend";
			   contents+= "&params="+u.getId().toString();
		
		BufferedImage qrCodeImg = null;
		try {
			qrCodeImg = QrCodeUtil.genBarcode(contents, 800, 800, qrCodeLogo);
		} catch (WriterException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ByteArrayOutputStream imageStream = new ByteArrayOutputStream();  
        try {  
            boolean resultWrite = ImageIO.write(qrCodeImg, "png", imageStream);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
        try {
			imageStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        byte[] imgBytes = imageStream.toByteArray();  
		
        String url = Constants.IMG_SERVER_HOST + "/upload/";
		String sendResult = ImgServerUtil.sendPostBytes(url, imgBytes, "png");

		ObjectMapper mapper = new ObjectMapper();

		HashMap<String, Object> o = new HashMap<String, Object>();
		try {
			o = mapper.readValue(sendResult, HashMap.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String ret = o.get("ret").toString();

		HashMap<String, String> info = (HashMap<String, String>) o.get("info");

		String imgUrl = Constants.IMG_SERVER_HOST + "/"+ info.get("md5").toString();		
		
		u.setQrCode(imgUrl);
		userService.updateByPrimaryKeySelective(u);
		
		return imgUrl;
	}
}