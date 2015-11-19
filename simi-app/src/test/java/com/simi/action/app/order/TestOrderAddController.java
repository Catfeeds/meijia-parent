package com.simi.action.app.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;


public class TestOrderAddController extends JUnitActionBase  {

	/**
	 * 		提交订单接口 单元测试
	 *     ​http://localhost:8080/onecare/app/order/post_add.json
	 *     http://182.92.160.194/trac/wiki/%E8%AE%A2%E5%8D%95%E6%8F%90%E4%BA%A4%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testSaveOrder() throws Exception {

		String url = "/app/order/post_add.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//通用订单. 无需支付
	    postRequest = postRequest.param("user_id", "77");
	    postRequest = postRequest.param("partner_user_id", "274");
	    postRequest = postRequest.param("service_type_id", "180");
	    postRequest = postRequest.param("service_price_id", "183");
	    postRequest = postRequest.param("mobile", "13146012753");
	    postRequest = postRequest.param("pay_type", "0");
//	    postRequest = postRequest.param("user_coupon_id", "266");
	    
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	    Thread.sleep(200000); // 因为junit结束会结束jvm，所以让它等会异步线程  
    }
}
