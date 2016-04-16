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
		String params = "?user_id=18&t_city=1441036800&t_apptools=1460694481&t_express=1457523382&t_assets=1459233449";
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
		String params = "";
		MockHttpServletRequestBuilder getRequest = get(url + params);
	    ResultActions resultActions = this.mockMvc.perform(getRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());
	    //打印所有的信息
//	    resultActions.andDo(MockMvcResultHandlers.print());

	    System.out.print("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	    

    }	

}
