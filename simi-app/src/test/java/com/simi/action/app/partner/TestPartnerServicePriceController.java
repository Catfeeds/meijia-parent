package com.simi.action.app.partner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.JUnitActionBase;

public class TestPartnerServicePriceController extends JUnitActionBase {

	/**
	 * 获取验证码接口单元测试 ​http://localhost:8080/onecare/app/get_sms_token.json
	 * http://182.92.160.194/trac/wiki/%E8%
	 * 8E%B7%E5%8F%96%E9%AA%8C%E8%AF%81%E7%A0%81%E6%8E%A5%E5%8F%A3
	 */
	@Test
	public void getPartnerServicePriceDetail() throws Exception {

		String url = "/app/partner/get_partner_service_price_detail.json";
		String params = "?service_price_id=68";
		MockHttpServletRequestBuilder getRequest = get(url + params);

		ResultActions resultActions = this.mockMvc.perform(getRequest);
		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	}
	@Test
    public void testPartnerAdd() throws Exception {

		String url = "/app/partner/post_partner_service_price_add.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//通用订单. 无需支付
	    postRequest = postRequest.param("parent_id", "0");//服务商id
	    postRequest = postRequest.param("service_price_id", "193");// 服务类别
	    postRequest = postRequest.param("user_id", "1");//用户id
	    
	    postRequest = postRequest.param("no", "88");
	    postRequest = postRequest.param("name", "免费咨询");
	    postRequest = postRequest.param("title", "针对企业IT信息qwertyuytreqwetrytr");
	    postRequest = postRequest.param("price", "300");
	    postRequest = postRequest.param("dis_price", "188");
	    postRequest = postRequest.param("order_type", "0");
	    postRequest = postRequest.param("order_duration", "0");
	    postRequest = postRequest.param("content_standard", "沟通企业情况与信息化诉求");
	    postRequest = postRequest.param("content_desc", "企业信息化是一套全面的体系，包括企业互联网网站建设、微信公众号开设、内部办公网络搭建等多个方面");
	    postRequest = postRequest.param("content_flow", "从企业自身规模和发展阶段，以及所处行业和地区，分析研究企业信息化的需求与对应解决方案。");
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	    Thread.sleep(200000); // 因为junit结束会结束jvm，所以让它等会异步线程  
    }
	
}
