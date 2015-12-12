package com.simi.action.app.job;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;

public class TestJobWeatherController extends JUnitActionBase{
	
	@Test
    public void testGetWeather() throws Exception {

		String url = "/app/job/weather/get_weather.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testDelWeather() throws Exception {

		String url = "/app/job/weather/del_weather.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testGetSomeWeather() throws Exception {

		String url = "/app/job/weather/get_some_weather.json?city_id=2";

     	MockHttpServletRequestBuilder postRequest = get(url);

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	


	
}
