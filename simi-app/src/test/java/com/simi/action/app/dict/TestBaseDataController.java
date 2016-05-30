package com.simi.action.app.dict;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;


public class TestBaseDataController extends JUnitActionBase  {

	@Test
    public void testGetBaseDatas() throws Exception {

		
		String url = "/app/get_base_datas.json";
		String params = "?t_city=&t_apptools=&t_express=&t_assets=";
		MockHttpServletRequestBuilder getRequest = get(url + params);
	    ResultActions resultActions = this.mockMvc.perform(getRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());
	    //打印所有的信息
//	    resultActions.andDo(MockMvcResultHandlers.print());

	    System.out.print("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	    

    }
	
	@Test
    public void testGetBaseDataAda() throws Exception {

		
		String url = "/app/dict/get_ads.json";
		String params = "?";
		MockHttpServletRequestBuilder getRequest = get(url + params);
	    ResultActions resultActions = this.mockMvc.perform(getRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());
	    //打印所有的信息
//	    resultActions.andDo(MockMvcResultHandlers.print());

	    System.out.print("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	    

    }	

}
