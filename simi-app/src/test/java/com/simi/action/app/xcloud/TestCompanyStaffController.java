package com.simi.action.app.xcloud;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.eclipse.jetty.util.UrlEncoded;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;


public class TestCompanyStaffController extends JUnitActionBase  {

	
	
	@Test
    public void testReg() throws Exception {
		
		String url = "/app/company/set_default.json";
		String params = "?user_id=18&company_id=6";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());
		
	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
}
