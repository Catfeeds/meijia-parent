package com.simi.action.app.sec;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;

public class TestSecController extends JUnitActionBase{
	
	/**
	 * 		秘书登陆接口 单元测试
	 *     ​http://localhost:8080/onecare/app/user/login.json
	 *     http://182.92.160.194/trac/wiki/%E7%94%A8%E6%88%B7%E7%99%BB%E9%99%86%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testPostLogin() throws Exception {

		String url = "/app/sec/login.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("mobile", "18249516801");
	    postRequest = postRequest.param("sms_token", "550478");
	    postRequest = postRequest.param("login_from", "0");

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }
}
