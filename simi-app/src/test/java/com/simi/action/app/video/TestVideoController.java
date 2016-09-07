package com.simi.action.app.video;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.eclipse.jetty.util.UrlEncoded;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;


public class TestVideoController extends JUnitActionBase  {

	/**
	 * 	   获取频道列表接口
	 */
	@Test
    public void testChannels() throws Exception {
		String url = "/app/video/channels.json";

		String params = "";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	
	
	/**
	 * 	   获取频道列表接口
	 */
	@Test
    public void testList() throws Exception {
		String url = "/app/video/list.json";

//		String params = "?channel_id=307";
		String params = "?keyword=自行车";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	
	
	/**
	 * 	   获取频道列表接口
	 */
	@Test
    public void testDetail() throws Exception {
		String url = "/app/video/detail.json";

//		String params = "?channel_id=307";
		String params = "?article_id=313&user_id=18";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	
	@Test
    public void testJoin() throws Exception {
		String url = "/app/video/join.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("user_id", "18");
	    postRequest = postRequest.param("article_id", "313");


	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	
	@Test
    public void testRelate() throws Exception {
		String url = "/app/video/relate.json";

//		String params = "?channel_id=307";
		String params = "?article_id=313";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
}
