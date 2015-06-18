package com.simi.action.app.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simi.action.app.JUnitActionBase;
import com.meijia.utils.TimeStampUtil;


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
     	
	    postRequest = postRequest.param("user_id", "1");
	    postRequest = postRequest.param("service_type", "1");
	    postRequest = postRequest.param("order_pay_type", "0");
	    postRequest = postRequest.param("remarks", "请我叫个快递，已经打电话给顺丰上门");


	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
}
