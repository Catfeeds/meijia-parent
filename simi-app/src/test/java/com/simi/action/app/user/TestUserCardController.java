package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;


public class TestUserCardController extends JUnitActionBase  {

	/**
	 * 		会员充值接口
	 *     ​http://localhost:8080/onecare/app/user/card_buy.json
	 *     http://182.92.160.194/trac/wiki/%E4%BC%9A%E5%91%98%E5%85%85%E5%80%BC%E6%8E%A5%E5%8F%A3%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testCardBuy() throws Exception {

		String url = "/app/user/card_buy.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("user_id", "1");
	    postRequest = postRequest.param("card_type", "0");
	    postRequest = postRequest.param("card_money", "100");
	    postRequest = postRequest.param("pay_type", "1");

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }

}
