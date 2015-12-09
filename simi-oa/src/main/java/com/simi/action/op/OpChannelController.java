package com.simi.action.op;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.BaseController;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.dict.DictAd;
import com.simi.po.model.op.OpAd;
import com.simi.po.model.op.OpChannel;
import com.simi.service.dict.AdService;
import com.simi.service.op.OpAdService;
import com.simi.service.op.OpChannelService;

@Controller
@RequestMapping(value = "/op")
public class OpChannelController extends BaseController {

	@Autowired
	private OpChannelService opChannelService;

	@AuthPassport
	@RequestMapping(value = "/channel_list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel");
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result = opChannelService.searchVoListPage(pageNo, pageSize);

		model.addAttribute("contentModel", result);

		return "op/channelList";
	}

	//@AuthPassport
	@RequestMapping(value = "/channelForm", method = { RequestMethod.GET })
	public String adForm(Model model, @RequestParam(value = "channel_id") Long channelId, HttpServletRequest request) {

		if (channelId == null) {
			channelId = 0L;
		}

		OpChannel opChannel= opChannelService.initOpChannel();
		if (channelId != null && channelId > 0) {
			opChannel = opChannelService.selectByPrimaryKey(channelId);

		}

		model.addAttribute("channelModel", opChannel);

		return "op/channelForm";
	}
	
//	@AuthPassport
	@RequestMapping(value = "/channelForm", method = { RequestMethod.POST })
	public String doAdForm(HttpServletRequest request, Model model, @ModelAttribute("opChannel") OpChannel opChannel, BindingResult result) throws IOException {

		Long channelId = Long.valueOf(request.getParameter("channelId"));

		// 更新或者新增
		if (channelId != null && channelId > 0) {
			//opChannel.setUpdateTime(TimeStampUtil.getNow() / 1000);
			opChannel.setAddTime(TimeStampUtil.getNow() / 1000);
			opChannelService.updateByPrimaryKeySelective(opChannel);
		} else {
		
			opChannel.setAddTime(TimeStampUtil.getNow() / 1000);
			opChannelService.insertSelective(opChannel);
		}

		return "redirect:channel_list";
	}

	// 删除
	/*
	 * @RequestMapping(value ="/delete/{id}", method = {RequestMethod.GET})
	 * public String deleterAdminRole(Model model,@PathVariable(value="id")
	 * String id,HttpServletRequest response) { Long ids = 0L; if (id != null &&
	 * NumberUtils.isNumber(id)) { ids = Long.valueOf(id.trim()); } String path
	 * = "redirect:/dict/ad"; int result = adService.deleteByPrimaryKey(ids);
	 * if(result>0){ return path; }else{ return "error"; } }
	 */

}
