package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.simi.action.app.JUnitActionBase;

public class TestUserRemindController extends JUnitActionBase {

	@Test
	public void testPostSaveUserReminds() throws Exception{
		String url = "/app/user/post_user_remind.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("mobile", "13520256623");
	    postRequest = postRequest.param("remind_id", "0");
	    postRequest = postRequest.param("start_date","2015-1-19");
	    postRequest = postRequest.param("start_time","18:40");
	    postRequest = postRequest.param("cycle_type","0");
	    postRequest = postRequest.param("remind_to_name","哈哈");
	    postRequest = postRequest.param("remind_to_mobile","18612514665");
	    postRequest = postRequest.param("remarks","上班了");
	    postRequest = postRequest.param("remind_type","0,1,0");
	    postRequest = postRequest.param("remind_title","你好啊");

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());
	    //打印所有的信息
//	    resultActions.andDo(MockMvcResultHandlers.print());

	    System.out.print("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	}
	@Test
	public void testGetQueryUserRemindsByMobile() throws Exception{
		String url = "/app/user/get_user_remind.json";
		String params = "?mobile=13146012753";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());
	    //打印所有的信息
	    resultActions.andDo(MockMvcResultHandlers.print());

	    System.out.print("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	}
	@Test
	public void testPostDeleteByRemindId() throws Exception{
		String url = "/app/user/post_user_remind_del.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("mobile", "18210219795");
	    postRequest = postRequest.param("remind_id", "15");

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());
	    //打印所有的信息
//	    resultActions.andDo(MockMvcResultHandlers.print());

	    System.out.print("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	}

}
