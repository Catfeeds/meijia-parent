package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.eclipse.jetty.util.UrlEncoded;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.simi.action.app.JUnitActionBase;


public class TestUserSeniorController extends JUnitActionBase  {

	/**
	 * 		管家卡购买接口
	 *     ​http://localhost:8080/onecare/app/user/senior_buy.json
	 *     http://182.92.160.194/trac/wiki/%E7%AE%A1%E5%AE%B6%E5%8D%A1%E8%B4%AD%E4%B9%B0%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void TestSeniorBuy() throws Exception {

		String url = "/app/user/senior_buy.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("mobile", "18612514665");
	    postRequest = postRequest.param("senior_type", "1");
	    postRequest = postRequest.param("pay_type", "0");

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }

}
