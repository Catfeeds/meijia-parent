package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;


public class TestUserExchangeController extends JUnitActionBase  {

	/**
	 * 		会员充值接口
	 *     ​http://localhost:8080/onecare/app/user/post_score_exchange.json
	 *     http://182.92.160.194/trac/wiki/%E7%A7%AF%E5%88%86%E5%85%91%E6%8D%A2%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void TestcardBuy() throws Exception {

		String url = "/app/user/post_score_exchange.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("mobile", "18612514665");
	    postRequest = postRequest.param("exchange_id", "0");


	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }

}
