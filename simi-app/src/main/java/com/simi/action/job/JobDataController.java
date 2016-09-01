package com.simi.action.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.zxing.WriterException;
import com.meijia.utils.DateUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.Xcompany;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.async.CardAsyncService;
import com.simi.service.card.CardService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.utils.CardUtil;
import com.simi.utils.XcompanyUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.xcloud.CompanySearchVo;

@Controller
@RequestMapping(value = "/app/job/data")
public class JobDataController extends BaseController {

	@Autowired
	private XCompanyService xCompanyService;
	
	/**
	 * 重新生成公司二维码
	 * @throws IOException 
	 * @throws WriterException 
	 */
	@RequestMapping(value = "re-company-qrcode", method = RequestMethod.GET)
	public AppResultData<Object> companyQrCode(HttpServletRequest request) throws WriterException, IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		String reqHost = request.getRemoteHost();
		if (reqHost.equals("localhost") || reqHost.equals("127.0.0.1")) {
			CompanySearchVo searchVo = new CompanySearchVo();
			List<Xcompany> list = xCompanyService.selectBySearchVo(searchVo);
			
			for (Xcompany item : list) {
				String imgUrl = XcompanyUtil.genQrCode(item.getInvitationCode());

				item.setQrCode(imgUrl);
				xCompanyService.updateByPrimaryKeySelective(item);
			}
		}
		return result;
	}


}
