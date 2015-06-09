package com.simi.action.app.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;

public class TestOrderPayController  extends JUnitActionBase  {

	/**
	 * 		订单支付接口 单元测试
	 *     ​http://localhost:8080/onecare/app/order/post_pay.json
	 *     http://182.92.160.194/trac/wiki/%E8%AE%A2%E5%8D%95%E6%8F%90%E4%BA%A4%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testPay() throws Exception {
		String url = "/app/order/post_pay.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("mobile", "18610807136");
	    postRequest = postRequest.param("order_id", "554");
	    postRequest = postRequest.param("order_no", "591185785416318976");
	    postRequest = postRequest.param("pay_type", "0");
	    postRequest = postRequest.param("card_passwd", "4GZT43L5");
	    postRequest = postRequest.param("score", "0");

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	}
}
