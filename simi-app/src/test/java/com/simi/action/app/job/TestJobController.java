package com.simi.action.app.job;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;

public class TestJobController extends JUnitActionBase{
	
	@Test
    public void testGenUserProvince() throws Exception {

		String url = "/app/job/gen_user_province.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	  
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	

	@Test
    public void testSetCardFinish() throws Exception {

		String url = "/app/job/card/set_card_finish.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	  
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	
	@Test
    public void testCardNotify() throws Exception {

		String url = "/app/job/card/card_notify.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	  
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
}
