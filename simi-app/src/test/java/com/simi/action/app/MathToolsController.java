package com.simi.action.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;


public class MathToolsController extends JUnitActionBase  {


	@Test
    public void testMathInsurance() throws Exception {

		String url = "/app/insurance/math_insurance.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//通用订单. 无需支付
	    postRequest = postRequest.param("city_id", "2");
	    postRequest = postRequest.param("shebao", "2834");
	    postRequest = postRequest.param("gjj", "1720");

	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	 
    }
}
