package com.simi.action.app.record;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.HttpClientUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.dict.DictExpress;
import com.simi.po.model.record.RecordExpress;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.service.ImgService;
import com.simi.service.ValidateService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.dict.ExpressService;
import com.simi.service.record.RecordExpressService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.ExpressSearchVo;
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
	
	@Autowired
	private ExpressService expressService;
	
	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@RequestMapping(value = "post_add_express", method = RequestMethod.POST)
	public AppResultData<Object> postExpress(
			@RequestParam(value = "id",required = false,defaultValue = "0") Long id,
			@RequestParam(value = "company_id",required = false,defaultValue = "0") Long companyId,
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
			@RequestParam(value = "price",required = false,defaultValue = "0") BigDecimal price,
			@RequestParam(value = "is_close",required = false,defaultValue = "0") Short isClose,
			@RequestParam(value = "remarks",required = false,defaultValue = "") String remarks,
			@RequestParam(value = "imgs", required = false) MultipartFile[] imgs
			) throws JsonParseException, JsonMappingException, IOException {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		// 判断是否为注册用户，非注册用户返回 999		
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		//查询服务单号是否存在
		RecordExpressSearchVo searchVo = new RecordExpressSearchVo();
		searchVo.setUserId(userId);
		searchVo.setExpressNo(expressNo);
		List<RecordExpress> rs = recordExpressService.selectBySearchVo(searchVo);
		if (!rs.isEmpty()) {
			RecordExpress r = rs.get(0);
			if (!id.equals(r.getId())) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("快递单号已经存在.");
				return result;
			}
		}
		
		//如果公司ID 没有值，则查找相应的默认公司
		if (companyId.equals(0L)) {
			Xcompany defaultXcompany = xcompanyStaffService.getDefaultCompanyByUserId(userId);
			if (defaultXcompany != null) {
				companyId = defaultXcompany.getCompanyId();
			}
		}
		
		RecordExpress express = recordExpressService.initRecordExpress();
		
		if (id > 0L) express = recordExpressService.selectByPrimaryKey(id);
		
		express.setCompanyId(companyId);
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
		express.setIsClose(isClose);
		
		if (isClose.equals((short)1)) {
			express.setIsCloseTime(TimeStampUtil.getNowSecond());
			express.setIsCloseUserId(userId);
		}
		express.setRemarks(remarks);
		if (express.getId() > 0L) {
			express.setUpdateTime(TimeStampUtil.getNowSecond());
			recordExpressService.updateByPrimaryKeySelective(express);
		} else {
			recordExpressService.insert(express);
		}
		
		//处理图片上传的信息.
		if (imgs != null && imgs.length > 0) {
			Long linkId = express.getId();
			imgService.insertImgs(imgs, userId, linkId, "express");
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
		
//		Users u = userService.selectByPrimaryKey(userId);
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
		
//		Users u = userService.selectByPrimaryKey(userId);
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
	
	/**
	 * 一键结算
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param expressId
	 * @param isClose
	 * @param expressType
	 * @param expressNo
	 * @return
	 */
	@RequestMapping(value = "express_close_by_search", method = RequestMethod.POST)
	public AppResultData<Object> expressClose(
			@RequestParam("userId") Long userId,
			@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
			@RequestParam(value = "expressId", required = false, defaultValue = "0") Long expressId,
			@RequestParam(value = "isClose", required = false, defaultValue = "-1") Short isClose,
			@RequestParam(value = "expressType", required = false, defaultValue = "-1") Short expressType,
			@RequestParam(value = "expressNo", required = false, defaultValue = "") String expressNo
			) {	
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		

		// 判断是否为注册用户，非注册用户返回 999
		AppResultData<Object> v = validateService.validateUser(userId);
		if (v.getStatus() == Constants.ERROR_999) {
			return v;
		}
		
		RecordExpressSearchVo searchVo = new RecordExpressSearchVo();
		
		searchVo.setUserId(userId);
		if (expressId > 0L) searchVo.setExpressId(expressId);
		if (isClose > 0) searchVo.setExpressId(expressId);
		if (expressType > 0) searchVo.setExpressId(expressId);
		if (!StringUtil.isEmpty(expressNo)) searchVo.setExpressNo(expressNo);
		
		if (!StringUtil.isEmpty(startDate)) {
			Long startTime = TimeStampUtil.getMillisOfDayFull(startDate + " 00:00:00") / 1000;
			searchVo.setStartTime(startTime);
		}
		
		if (!StringUtil.isEmpty(endDate)) {
			Long endTime = TimeStampUtil.getMillisOfDayFull(endDate + " 23:59:59") / 1000;
			searchVo.setEndTime(endTime);
		}
		
		List<RecordExpress> list = recordExpressService.selectBySearchVo(searchVo);
		
		Long nowTime = TimeStampUtil.getNowSecond();
		for (RecordExpress item : list) {
			if (item.getIsClose().equals((short)1)) continue;
			if (item.getExpressType().equals((short)0)) continue;
			item.setIsClose((short) 1);
			item.setIsCloseTime(nowTime);
			item.setIsCloseUserId(userId);
			item.setUpdateTime(nowTime);
			recordExpressService.updateByPrimaryKeySelective(item);
		}
		return result;
	}
	
	
	/**
	 * 根据快递单号找快递服务商
	 * @param adType
	 * @return
	 */
	@RequestMapping(value = "get_express_name", method = RequestMethod.GET)
    public AppResultData<Object> selectByNum(
    		@RequestParam("express_no") String expressNo
    		) {

    	AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
    	
    	String url = "http://m.kuaidi100.com/autonumber/auto";
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("num", expressNo);
    	String res = HttpClientUtil.get(url, params);
    	
    	List<Map<String, String>> list = GsonUtil.GsonToListMaps(res);
    	Map<String, String> item = new HashMap<String, String>();
    	if (list.isEmpty()) return result;
		item = list.get(0);
		String ecode = item.get("comCode").toString();
		if (StringUtil.isEmpty(ecode)) return result;
		
		ExpressSearchVo searchVo = new ExpressSearchVo();
		searchVo.setEcode(ecode);
		List<DictExpress> r = expressService.selectBySearchVo(searchVo);
		
		if (r.isEmpty()) return result;
		
		DictExpress a = r.get(0);
		
		result.setData(a.getExpressId());
		
    	
    	
    	return result;
    }	
}
