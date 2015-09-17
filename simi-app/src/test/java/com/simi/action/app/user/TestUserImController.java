package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;


public class TestUserImController extends JUnitActionBase  {

	/**
	 * 		地址提交接口
	 *     ​http://localhost:8080/simi/app/user/get_im_history.json
	 */
	@Test
    public void testGetImHistory() throws Exception {

		String url = "/app/user/get_im_history.json";
		String params = "?from_im_user=simi-sec-1&to_im_user=simi-user-118&page=1";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	
	/**
	 * 		
	 *    获取当前用户所有好友的最新一条聊天信息 ​
	 */
	@Test
    public void testGetUserAndIm() throws Exception {

		String url = "/app/user/get_im_last.json";
		String params = "?user_id=95";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	/**
	 * 		
	 *    生成所有好友的最新一条信息
	 */
	@Test
    public void testGenLastImAll() throws Exception {

		String url = "/app/user/gen_last_im.json";
		MockHttpServletRequestBuilder getRequest = get(url);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }		

}
