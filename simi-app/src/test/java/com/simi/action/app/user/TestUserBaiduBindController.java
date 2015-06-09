package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;

public class TestUserBaiduBindController extends JUnitActionBase{

	@Test
	public void testSaveUserBaiduBind() throws Exception{

		String url="/app/user/post_baidu_bind.json";
		MockHttpServletRequestBuilder postRequest = post(url);
		postRequest=postRequest.param("mobile","18610807136");
		postRequest=postRequest.param("app_id","sdf");
		postRequest=postRequest.param("channel_id", "b2842483bb");
		postRequest=postRequest.param("app_user_id", "ccccppppp");
		ResultActions resultActions=mockMvc.perform(postRequest);
		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());
		System.out.println("RestultActons:" + resultActions.andReturn().getResponse().getContentAsString());
	}


}
