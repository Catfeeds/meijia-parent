package com.xcloud.action;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.code.kaptcha.Producer;
import com.meijia.utils.StringUtil;
import com.meijia.utils.vo.AppResultData;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;


@Controller
@RequestMapping(value = "/")
public class CaptchaController extends BaseController {
	
	@Autowired  
	private Producer captchaProducer = null;  
	
    @RequestMapping(value = "/captcha")
    public String getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  

    	HttpSession session = request.getSession();  
        String code = (String) session.getAttribute("session-captcha");  
//        System.out.println("******************验证码是: " + code);  

        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
        String capText = captchaProducer.createText();  
        session.setAttribute("session-captcha", capText);
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    }
    
    
	@RequestMapping(value = "check_captcha", method = RequestMethod.GET)
    public AppResultData<Object> checkCaptcha (
    		HttpServletRequest request,
    		@RequestParam("token") String token) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if (StringUtil.isEmpty(token)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("验证码不正确");
			return result;
		}
		
		
		String captchaServer = (String) request.getSession().getAttribute("session-captcha");  
		
		if (!token.equals(captchaServer)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("验证码不正确");
			return result;			
		}
		
    	return result;
    }    
}
