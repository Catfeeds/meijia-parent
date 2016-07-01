package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;

public class TestUserMsgController extends JUnitActionBase{
	@Test
	public void testSaveMsg() throws Exception{

		String url="/app/user/post_msg_read.json";
		MockHttpServletRequestBuilder postRequest = post(url);
		postRequest=postRequest.param("mobile","333333333");
		postRequest=postRequest.param("msg_id", "333333333");

		ResultActions resultActions=mockMvc.perform(postRequest);
		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());
		System.out.println("RestultActons:" + resultActions.andReturn().getResponse().getContentAsString());
	}
//	@Test
//	public void getMsg() throws Exception{
//
//		String url="/app/user/get_msg.json";
//
//		String params = "?mobile=18500143331&page=1";
//		MockHttpServletRequestBuilder postRequest = get(url+params);
//
//
//		ResultActions resultActions=mockMvc.perform(postRequest);
//		resultActions.andExpect(content().contentType(this.mediaType));
//		resultActions.andExpect(status().isOk());
//		System.out.println("RestultActons:" + resultActions.andReturn().getResponse().getContentAsString());
//	}
	@Test
	public void testGetNewMsg() throws Exception{

		String url="/app/user/get_new_msg.json";
		MockHttpServletRequestBuilder postRequest = post(url);
		postRequest=postRequest.param("user_id","90");

		ResultActions resultActions=mockMvc.perform(postRequest);
		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());
		System.out.println("RestultActons:" + resultActions.andReturn().getResponse().getContentAsString());
	}

	
	@Test
	public void testGetUserMsg() throws Exception{

		String url="/app/user/get_msg.json";

		String params = "?user_id=17&service_date=2016-07-08&page=1";
		MockHttpServletRequestBuilder postRequest = get(url+params);


		ResultActions resultActions=mockMvc.perform(postRequest);
		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());
		System.out.println("RestultActons:" + resultActions.andReturn().getResponse().getContentAsString());
	}	
	
	@Test
    public void testTotalByMonth() throws Exception {

		String url = "/app/user/msg/total_by_month.json";
		String params = "?user_id=77&year=2016&month= 7";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
}
