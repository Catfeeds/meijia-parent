package com.simi.action.app.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;

public class TestOrderDetailController extends JUnitActionBase {

	/**
	 * 19.订单详情接口 ​http://localhost:8080/onecare/app/order/get_detail.json
	 * http://182.92.160.194/trac/wiki/%E8%AE%A2%E5%8D%95%E6%8F%90%E4%BA%A4%E6%
	 * 8E%A5%E5%8F%A3
	 */
	@Test
	public void testDetail() throws Exception {
		String url = "/app/order/get_detail.json";
		String params = "?user_id=92&order_no=612858351222521856";
		MockHttpServletRequestBuilder getRequest = get(url + params);

		ResultActions resultActions = this.mockMvc.perform(getRequest);

		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.print("RestultActons: "
				+ resultActions.andReturn().getResponse().getContentAsString());
	}
}
