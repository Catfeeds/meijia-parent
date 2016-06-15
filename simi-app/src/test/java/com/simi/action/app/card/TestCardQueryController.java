package com.simi.action.app.card;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.meijia.utils.GsonUtil;
import com.simi.action.app.JUnitActionBase;
import com.simi.vo.card.LinkManVo;


public class TestCardQueryController extends JUnitActionBase  {
	
	@Test
    public void testGetDetail() throws Exception {

		String url = "/app/card/get_detail.json";
		String params = "?card_id=2&user_id=1";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	
	@Test
    public void testGetList() throws Exception {

		String url = "/app/card/get_list.json";
		String params = "?user_id=18&card_from=0";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testGetComment() throws Exception {

		String url = "/app/card/get_comment_list.json";
		String params = "?card_id=14&user_id=1";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }		
	
	@Test
    public void testTotalByMonth() throws Exception {

		String url = "/app/card/total_by_month.json";
		String params = "?user_id=1&year=2015&month=10";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testGetReminds() throws Exception {

		String url = "/app/card/get_reminds.json";
		String params = "?user_id=1";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
}
