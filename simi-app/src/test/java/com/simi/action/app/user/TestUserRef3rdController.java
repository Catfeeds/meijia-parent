package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;


public class TestUserRef3rdController extends JUnitActionBase  {


	/**
	 * 	      第三方用户登录接口
	 *    ​http://182.92.160.194/onecare/app/user/login-3rd.json
	 *    http://182.92.160.194:8080/trac/wiki/%E7%AC%AC%E4%B8%89%E6%96%B9%E7%99%BB%E9%99%86%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testLogin3rd() throws Exception {

		String url = "/app/user/login-3rd.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("openid", "kerryg123");
	    postRequest = postRequest.param("3rd_type", "weixin");
	    postRequest = postRequest.param("name", "林夕一梦");
	    postRequest = postRequest.param("head_img", "www.baidu.com");
	    postRequest = postRequest.param("login_from", "0");

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }
}
