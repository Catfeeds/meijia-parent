package com.simi.action.app.partner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
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

	
}
