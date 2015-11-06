package com.simi.action.app.async;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;

public class TestAsyncController extends JUnitActionBase{
	


	
	@Test
    public void testAsync() throws Exception {
		String url = "/app/async/test.json";
		
		MockHttpServletRequestBuilder getRequest = get(url);
		
	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	    Thread.sleep(6000); // 因为junit结束会结束jvm，所以让它等会异步线程  
    }
	@Test
    public void testSchoolAsync() throws Exception {
		String url = "/app/async/schoolTest.json";
		
		MockHttpServletRequestBuilder getRequest = get(url);
		
	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	    Thread.sleep(6000); // 因为junit结束会结束jvm，所以让它等会异步线程  
    }
}
