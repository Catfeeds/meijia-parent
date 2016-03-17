package com.simi.action.app.record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.common.Imgs;
import com.simi.po.model.record.RecordExpress;
import com.simi.po.model.user.Users;
import com.simi.service.ImgService;
import com.simi.service.ValidateService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.record.RecordExpressService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.ImgSearchVo;
import com.simi.vo.record.RecordExpressSearchVo;
import com.simi.vo.record.RecordExpressVo;

@Controller
@RequestMapping(value = "/app/record")
public class ExpressController extends BaseController {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;
	
	@Autowired
	private RecordExpressService recordExpressService;
	
	@Autowired
	private ImgService imgService;
	
	@Autowired
	private ValidateService validateService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_add_express", method = RequestMethod.POST)
	public AppResultData<Object> postExpress(
			@RequestParam("user_id") Long userId,
			@RequestParam("express_no") String expressNo,
			@RequestParam("express_type") Short expressType,
			@RequestParam("pay_type") Short payType,
			@RequestParam(value = "express_id",required = false,defaultValue = "0") Long expressId,
			@RequestParam(value = "from_addr",required = false,defaultValue = "") String fromAddr,
			@RequestParam(value = "from_name",required = false,defaultValue = "") String fromName,
			@RequestParam(value = "from_tel",required = false,defaultValue = "") String fromTel,
			
			@RequestParam(value = "to_addr",required = false,defaultValue = "") String toAddr,
			@RequestParam(value = "to_name",required = false,defaultValue = "") String toName,
			@RequestParam(value = "to_tel",required = false,defaultValue = "") String toTel,
			
			@RequestParam(value = "remarks",required = false,defaultValue = "") String remarks,
			@RequestParam(value = "imgs", required = false) MultipartFile[] imgs
			) throws JsonParseException, JsonMappingException, IOException {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		Users u = userService.selectByPrimaryKey(userId);
		
		// 判断是否为注册用户，非注册用户返回 999		
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		//查询服务单号是否存在
		RecordExpress express = recordExpressService.selectByExpressNo(expressNo);
		if (express != null) {
			if (!express.getUserId().equals(userId)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("快递单号已经存在.");
				return result;
			}
		}
		
		if (express == null) express = recordExpressService.initRecordExpress();
		express.setUserId(userId);
		express.setExpressNo(expressNo);
		express.setExpressType(expressType);
		express.setExpressId(expressId);
		express.setPayType(payType);
		express.setFromAddr(fromAddr);
		express.setFromName(fromName);
		express.setFromTel(fromTel);
		
		express.setToAddr(toAddr);
		express.setToName(toName);
		express.setToTel(toTel);
		
		express.setRemarks(remarks);
		
		if (express.getId() > 0L) {
			express.setUpdateTime(TimeStampUtil.getNowSecond());
			recordExpressService.updateByPrimaryKeySelective(express);
		} else {
			recordExpressService.insert(express);
		}
		
		//处理图片上传的信息.
		if (imgs != null && imgs.length > 0) {
			
			//先删除指定的图片，再新增图片。
			ImgSearchVo searchVo = new ImgSearchVo();
			searchVo.setLinkId(express.getId());
			searchVo.setLinkType("express");
			List<Imgs> imgList = imgService.selectBySearchVo(searchVo);
			if (!imgList.isEmpty()) {
				for (Imgs item : imgList) {
					imgService.deleteByPrimaryKey(item.getImgId());
				}
			}
			
			for (int i = 0; i < imgs.length; i++) {
				String url = Constants.IMG_SERVER_HOST + "/upload/";
				String fileName = imgs[i].getOriginalFilename();
				String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
				fileType = fileType.toLowerCase();
				String sendResult = ImgServerUtil.sendPostBytes(url, imgs[i].getBytes(), fileType);

				ObjectMapper mapper = new ObjectMapper();

				HashMap<String, Object> o = mapper.readValue(sendResult, HashMap.class);

				HashMap<String, String> info = (HashMap<String, String>) o.get("info");

				String imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();
				Imgs img = imgService.initImg();
				img.setLinkId(express.getId());
				img.setUserId(userId);
				img.setLinkType("express");
				img.setImgUrl(imgUrl);
				imgService.insert(img);

			}
		}
		
		//发送首页消息格式
		//异步产生首页消息信息.
		userMsgAsyncService.newActionAppMsg(userId, express.getId(), "express", "快递", "你登记了一个快递信息.", "");
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "get_list_express", method = RequestMethod.GET)
	public AppResultData<Object> list(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {	
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		Users u = userService.selectByPrimaryKey(userId);
		// 判断是否为注册用户，非注册用户返回 999
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		List<RecordExpressVo> listVo = new ArrayList<RecordExpressVo>();
		RecordExpressSearchVo searchVo =new RecordExpressSearchVo();
		searchVo.setUserId(userId);
		PageInfo plist = recordExpressService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<RecordExpress> list = plist.getList();
		
		if (!list.isEmpty())
			listVo = recordExpressService.getListVos(list);
		
		result.setData(listVo);
		return result;
	}
	
	@RequestMapping(value = "get_detail_express", method = RequestMethod.GET)
	public AppResultData<Object> detail(
			@RequestParam("user_id") Long userId,
			@RequestParam("id") Long id) {	
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		Users u = userService.selectByPrimaryKey(userId);
		// 判断是否为注册用户，非注册用户返回 999
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		RecordExpress item = recordExpressService.selectByPrimaryKey(id);
		if (item == null) return result;
		RecordExpressVo vo = new RecordExpressVo();
		vo = recordExpressService.getListVo(item);
				
		result.setData(vo);
		return result;
	}
}
