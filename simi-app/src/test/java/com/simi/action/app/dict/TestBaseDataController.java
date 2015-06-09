package com.simi.action.app.dict;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.simi.action.app.JUnitActionBase;


public class TestBaseDataController extends JUnitActionBase  {

	@Test
    public void testGetBaseDatas() throws Exception {

		MockHttpServletRequestBuilder getRequest = get("/app/dict/get_base_datas.json");

	    ResultActions resultActions = this.mockMvc.perform(getRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());
	    //打印所有的信息
//	    resultActions.andDo(MockMvcResultHandlers.print());

	    System.out.print("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	    //判断status 是否为 0 = 正常.
	    
	    /*
	     *  post
	     	MockHttpServletRequestBuilder postRequest = post("/confirm");
		    postRequest = postRequest.param("selection", "1");

		    ResultActions resultActions = mockMvc.perform(postRequest);

		    resultActions.andDo(print());
		    resultActions.andExpect(content().contentType(mediaType));
		    resultActions.andExpect(status().isOk());
	     *
	     */

    }

}
