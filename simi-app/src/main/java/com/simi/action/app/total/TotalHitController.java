package com.simi.action.app.total;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.StringUtil;
import com.meijia.utils.vo.AppResultData;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.total.TotalHit;
import com.simi.service.total.TotalHitService;
import com.simi.vo.total.TotalHitSearchVo;

/**
 *
 * @author :hulj
 * @Date : 2016年5月18日下午6:40:53
 * @Description: 
 *		
 *			点击次数
 */
@Controller
@RequestMapping(value="/app/total")
public class TotalHitController extends BaseController {
	
	@Autowired
	private TotalHitService hitService;
	
	/**
	 * 
	  * @Title: addHit
	  *
	  *	@Date : 2016年5月19日上午10:50:16
	  * @Description: 
	  * 	 平台 不同类别 项目  的   点击次数
	  * @param linkId
	  * @param linkType
	  * @return    设定文件
	  * @return AppResultData<Object>    返回类型
	  * @throws
	 */
	@RequestMapping(value = "add_hit",method = RequestMethod.GET)
	public AppResultData<Object> addHit(
			@RequestParam("link_id")Long linkId,
			@RequestParam("link_type")String linkType){
		
		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if(linkId == null 
					|| linkId <= 0L 
					|| StringUtil.isEmpty(linkType)){
			// 参数 不 合法, 不做处理,直接返回
			return result;
		}
		
		TotalHitSearchVo searchVo = new TotalHitSearchVo();
		
		searchVo.setLinkId(linkId);
		searchVo.setLinkType(linkType);
		
		/*
		 * 	参数过滤后
		 *  理论上, 按照 linkType 和 linkId 可以唯一标识一条 记录
		 */
		List<TotalHit> list = hitService.selectBySearchVo(searchVo);
		
		TotalHit totalHit = hitService.initTotalHit();
		
		if(list.size() > 0){
			
			totalHit = list.get(0);
			
			//点击次数+1
			totalHit.setTotal(totalHit.getTotal()+1);
			hitService.updateByPrimaryKeySelective(totalHit);
		}else{
			//生成一条新纪录
			totalHit.setLinkType(linkType);
			totalHit.setLinkId(linkId);
			totalHit.setTotal(1L);
			
			hitService.insertSelective(totalHit);
		}
		return result;
	}
	
}
