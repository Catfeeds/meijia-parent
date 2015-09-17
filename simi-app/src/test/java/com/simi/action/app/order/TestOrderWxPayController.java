package com.simi.action.app.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;


public class TestOrderWxPayController extends JUnitActionBase  {

	/**
	 * 		微信统一支付订单接口 单元测试
	 *     ​http://localhost:8080/onecare/app/order/wx_pre.json
	 *     http://182.92.160.194/trac/wiki/%E8%AE%A2%E5%8D%95%E6%8F%90%E4%BA%A4%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testWxPre() throws Exception {

		String url = "/app/order/wx_pre.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("user_id", "95");//13911489629
	    postRequest = postRequest.param("order_no", "619450529705103360");
	    postRequest = postRequest.param("order_type", "1");
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	
	/**
	 * 		微信统一支付订单接口 单元测试
	 *     ​http://localhost:8080/onecare/app/order/wx_pre.json
	 *     http://182.92.160.194/trac/wiki/%E8%AE%A2%E5%8D%95%E6%8F%90%E4%BA%A4%E6%8E%A5%E5%8F%A3
	 */
//	@Test
//    public void testWxOrderQuery() throws Exception {
//
//		String url = "/app/order/wx_order_query.json";
//
//     	MockHttpServletRequestBuilder postRequest = post(url);
//	    postRequest = postRequest.param("mobile", "13520256623");//13911489629
//	    postRequest = postRequest.param("order_no", "603087266314715136");
//	    postRequest = postRequest.param("order_type", "0");
//	    ResultActions resultActions = mockMvc.perform(postRequest);
//
//	    resultActions.andExpect(content().contentType(this.mediaType));
//	    resultActions.andExpect(status().isOk());
//
//
//	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
//
//    }	
}
