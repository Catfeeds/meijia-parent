package com.simi.action.promotion;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.promotion.Channel;
import com.simi.service.promotion.ChannelService;
import com.meijia.utils.QrCodeUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.baidu.DwzUtil;
import com.simi.vo.chan.ChannelSearchVo;

@Controller
@RequestMapping(value = "/chan")
public class ChannelController extends BaseController {

	@Autowired
	private ChannelService channelService;

	@AuthPassport
	@RequestMapping(value = "/channel", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model,
			ChannelSearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel",searchVo);

		int pageNo = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		PageInfo result = channelService.searchVoListPage(searchVo,pageNo, pageSize);

		model.addAttribute("channelModel", result);

		return "chan/channel";
	}

	@AuthPassport
	@RequestMapping(value = "/channelForm", method = { RequestMethod.GET })
	public String channelForm(Model model, @RequestParam(value = "id") Long id,
			HttpServletRequest request) {

		if (id == null) {
			id = 0L;
		}

		Channel channel = channelService.initChannel();
		if (id != null && id > 0) {
			channel = channelService.selectByPrimaryKey(id);

		}

		model.addAttribute("channelModel", channel);

		return "chan/channelForm";
	}

	@AuthPassport
	@RequestMapping(value = "/channelForm", method = { RequestMethod.POST })
	public String doChannelForm(HttpServletRequest request, Model model,
			@ModelAttribute("channel") Channel channel, BindingResult result)
			throws IOException, WriterException {

		Long id = Long.valueOf(request.getParameter("id"));
		/**
		 * 生成二维码图片
		 */

		if (id != null && id > 0) {

			channel.setAddTime(TimeStampUtil.getNow() / 1000);
			channel.setUpdateTime(TimeStampUtil.getNow() / 1000);
			channel.setTotalDownloads(0);
			channelService.updateByPrimaryKeySelective(channel);

		} else {

			channel.setId(Long.valueOf(request.getParameter("id")));
			channel.setShortUrl("");
			channel.setQrcodeUrl("");
			channel.setTotalDownloads(0);
			channel.setAddTime(TimeStampUtil.getNow() / 1000);
			channel.setUpdateTime(0L);


			if(channel.getIsQrcode()==1){


				String tinyUrl=DwzUtil.dwzApi(channel.getDownloadUrl());
				channel.setShortUrl(tinyUrl);

				String logoPath = request.getSession().getServletContext().getRealPath("/WEB-INF/images/onecare/");
				String logoFile = logoPath +"\\"+ "logo-yuan.png";

				String destPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/qrcode/");
				String destname = channel.getToken();
				String destFile = destPath+"\\"+destname+".jpg";

				QrCodeUtil.encode(tinyUrl, 800, 800, logoFile,  destFile);

				 String channelQrCode =  "/simi-oa/upload/qrcode/"+destname+".jpg";
				 channel.setQrcodeUrl(channelQrCode);

			}


			channelService.insertSelective(channel);
		}

		return "redirect:channel";
	}
	
	@AuthPassport
	@RequestMapping(value = "/stat", method = { RequestMethod.GET })
	public String stat(HttpServletRequest request, Model model,
			ChannelSearchVo searchVo) {
		
		return "chan/channelStat";
	}

}
