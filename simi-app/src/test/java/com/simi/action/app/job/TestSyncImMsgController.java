package com.simi.action.app.job;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.JUnitActionBase;

public class TestSyncImMsgController extends JUnitActionBase{
	
	/**
	 * 	   
	 *     ​http://localhost:8080/simi/app/user/login.json
	 *     http://182.92.160.194/trac/wiki/%E7%94%A8%E6%88%B7%E7%99%BB%E9%99%86%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testPostLogin() throws Exception {

		String url = "/app/job/sync_im_yesterday.json";

     	MockHttpServletRequestBuilder postRequest = get(url);
//	    postRequest = postRequest.param("mobile", "18610807136");
//	    postRequest = postRequest.param("sms_token", " 000000");
//	    postRequest = postRequest.param("login_from", "1");
//	
	  
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }

}
