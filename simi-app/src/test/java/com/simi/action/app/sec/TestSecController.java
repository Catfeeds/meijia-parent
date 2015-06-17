package com.simi.action.app.sec;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	  
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	
	@Test
    public void testGetUsers() throws Exception {
		String url = "/app/sec/get_users.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("mobile", "18249516801");
	    postRequest = postRequest.param("sec_id", "2");
	
	    postRequest = postRequest.param("im_username", "hhhxxx");
	    postRequest = postRequest.param("im_password", "hxhxhx");
	    postRequest = postRequest.param("senior_range", "2015-02-25");
	    postRequest = postRequest.param("is_senior", "1");
	    postRequest = postRequest.param("im_senior_username", "zzzzzzzzrrrrrrrrr");
	    postRequest = postRequest.param("im_senior_nickname", "zrgj");
	    postRequest = postRequest.param("im_robot_username", "jqrrrrrrr");
	    postRequest = postRequest.param("im_robot_nickname", "jqrgj");

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

	
    }

}
