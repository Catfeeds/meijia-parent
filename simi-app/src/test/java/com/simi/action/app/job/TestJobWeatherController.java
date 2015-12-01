package com.simi.action.app.job;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;

public class TestJobWeatherController extends JUnitActionBase{
	
	/**
	 *  优惠劵即将过期通知， 如果优惠劵离过期还有一天，则发送短信.
	 * @param request
	 * @return
	 */
	@Test
    public void testGetWeather() throws Exception {

		String url = "/app/job/weather/get_weather.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	


	
}
