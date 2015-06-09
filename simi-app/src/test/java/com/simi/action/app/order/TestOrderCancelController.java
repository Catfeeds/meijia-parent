package com.simi.action.app.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;

public class TestOrderCancelController extends JUnitActionBase {

	
	/**
	 * 1. 订单取消接口 单元测试
	 * http://localhost:8080/onecare/app/order/post_order_cancel.json
	 * http://182.92.160.194/trac/wiki/%E8%AE%A2%E5%8D%95%E6%8F%90%E4%BA%A4%E6%
	 * 8E%A5%E5%8F%A3
	 */
	@Test
	public void testPreCancelOrder() throws Exception {

		String url = "/app/order/pre_order_cancel.json";
		String params = "?mobile=18612514665&order_no=575911524799873024";
		MockHttpServletRequestBuilder postRequest = post(url + params);
		ResultActions resultActions = mockMvc.perform(postRequest);

		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: "
				+ resultActions.andReturn().getResponse().getContentAsString());
	}	
	
	/**
	 * 1. 订单取消接口 单元测试
	 * http://localhost:8080/onecare/app/order/post_order_cancel.json
	 * http://182.92.160.194/trac/wiki/%E8%AE%A2%E5%8D%95%E6%8F%90%E4%BA%A4%E6%
	 * 8E%A5%E5%8F%A3
	 */
	@Test
	public void testCancelOrder() throws Exception {

		String url = "/app/order/post_order_cancel.json";
		String params = "?mobile=18612514665&order_no=575911524799873024";
		MockHttpServletRequestBuilder postRequest = post(url + params);
		ResultActions resultActions = mockMvc.perform(postRequest);

		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: "
				+ resultActions.andReturn().getResponse().getContentAsString());
	}
}
