package com.simi.service.impl.op;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import com.simi.service.op.AppToolsService;
import com.simi.service.op.OpChannelService;
import com.simi.vo.po.AppToolsVo;
import com.simi.common.Constants;
import com.simi.po.dao.op.AppToolsMapper;
import com.simi.po.dao.op.UserAppToolsMapper;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.op.UserAppTools;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.QrCodeUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class AppToolsServiceImpl implements AppToolsService {

	@Autowired
	private AppToolsMapper appToolsMapper;
	
	@Autowired
	private UserAppToolsMapper userAppToolsMapper;
	
	@Autowired
	private OpChannelService opChannelService;		


	@Override
	public AppTools initAppTools() {

		    AppTools record = new AppTools();
		    
		    record.settId(0L);
		    record.setNo((short)0L);
		    record.setName("");
		    record.setLogo("");
		    record.setAppType("");
		    record.setMenuType("");
		    record.setOpenType("");
		    record.setUrl("");
		    record.setAction("");
		    record.setParams("");
		    record.setIsDefault((short)0L);
		    record.setIsDel((short)0L);
		    record.setIsPartner((short)0L);
		    record.setIsOnline((short)0);
		    record.setAppProvider("");
		    record.setAppDescribe("");
		    record.setAuthUrl("");
		    record.setQrCode("");
		    record.setAddTime(TimeStampUtil.getNow()/1000);
		    record.setUpdateTime(TimeStampUtil.getNowSecond());
			return record;
		}


	@Override
	public AppTools selectByPrimaryKey(Long tId) {
		
		return appToolsMapper.selectByPrimaryKey(tId);
	}


	@Override
	public int updateByPrimaryKeySelective(AppTools record) {
		
		return appToolsMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public Long insertSelective(AppTools record) {
		
		return appToolsMapper.insertSelective(record);
	}


	@Override
	public int deleteByPrimaryKey(Long tId) {

		return appToolsMapper.deleteByPrimaryKey(tId);
	}


	@SuppressWarnings("rawtypes")
	@Override
	public PageInfo selectByListPage(int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		
		List<AppTools> list = appToolsMapper.selectByListPage();
		
		PageInfo result = new PageInfo(list);
		
		return result;
	}


	@Override
	public List<AppTools> selectByAppType(String appType) {
		
		return appToolsMapper.selectByAppType(appType);
	}
	
	@Override
	public AppTools selectByAction(String action) {
		
		return appToolsMapper.selectByAction(action);
	}

	@Override
	public List<AppTools> selectByAppTypeAndStatus(String appType) {
		
		return appToolsMapper.selectByAppTypeAndStatus(appType);
	}

	@Override
	public AppToolsVo getAppToolsVo(AppTools item,Long userId) {
		
		AppToolsVo vo = new AppToolsVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
		
		if (StringUtil.isEmpty(vo.getQrCode())) {
			String qrCode = genQrCode(item);
			vo.setQrCode(qrCode);
		}
		//添加时间返回‘yyyy-mm-dd’
		Long addTime = item.getAddTime()*1000;
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime, "yyyy-MM-dd"));
		
		//应用状态
		UserAppTools userAppTools = userAppToolsMapper.selectByUserIdAndTid(userId,item.gettId());
		if (userAppTools != null) {
			vo.setStatus(userAppTools.getStatus());	
		}
		vo.setUserId(userId);
		return vo;
	}


	@Override
	public List<AppTools> selectByAppTypeAll(String appType) {

		return appToolsMapper.selectByAppTypeAll(appType);
	}


	@Override
	public PageInfo selectByListPage(String appType, int pageNo, int pageSize,Long userId) {
		
		PageHelper.startPage(pageNo, pageSize);
		
		//List<AppToolsVo> appToolsVoList = new ArrayList<AppToolsVo>();
		List<AppTools> list = appToolsMapper.selectByAppTypeAll(appType);
		if (!list.isEmpty()) {
			/*for (AppTools item : list) {
				AppToolsVo vo = getAppToolsVo(item, userId);
				appToolsVoList.add(vo);
			}*/
			for (int i = 0; i < list.size(); i++) {
				AppTools appTools = list.get(i);
				AppToolsVo vo = getAppToolsVo(appTools, userId);
				/*if (vo.getStatus() == null){
					vo.setStatus((short)0);
				}*/
				list.set(i, vo);
			}
		}
		
		PageInfo result = new PageInfo(list);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String genQrCode(AppTools item) {
		String qrCode = "";
		
		qrCode = item.getQrCode();
		
		if (!StringUtil.isEmpty(qrCode)) return qrCode;
		
		String qrUrl = "http://www.51xingzheng.cn/d/open.html";
		String category = item.getOpenType();
		String action = item.getAction();
		String params = item.getParams();
		String gotoUrl = item.getUrl();
		qrUrl+="?category="+category;
		qrUrl+="&action="+action;
		if (!StringUtil.isEmpty(params)) {
			qrUrl+="&params="+params;
		}
		
		if (category.equals("h5") && !StringUtil.isEmpty(gotoUrl)) {
			qrUrl+="goto_url=" + gotoUrl;
		}
		
		BufferedImage qrCodeImg = null;
		try {
			qrCodeImg = QrCodeUtil.genBarcode(qrUrl, 800, 800, Constants.DEFAULT_LOGO);
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
		
		item.setQrCode(imgUrl);
		updateByPrimaryKeySelective(item);
		
		return imgUrl;
	}
	
}
