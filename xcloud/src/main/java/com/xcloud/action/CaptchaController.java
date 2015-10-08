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
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;
import com.xcloud.auth.AuthPassport;


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
}
