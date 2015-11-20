package com.simi.action.app.job;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;

public class TestJobOrderController extends JUnitActionBase{
	
	@Test
    public void testOrderMore60min() throws Exception {

		String url = "/app/job/order/check_order_more_60min.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	@Test
    public void testOrderMore7Days() throws Exception {

		String url = "/app/job/order/check_order_more_7Day.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	


	
}
