package com.simi.action.app.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;

public class TestOrderExtGreenController extends JUnitActionBase  {

	/**
	 * 		提交订单接口 单元测试
	 *     ​http://localhost:8080/onecare/app/order/get_list.json
	 *     http://182.92.160.194/trac/wiki/%E8%AE%A2%E5%8D%95%E6%8F%90%E4%BA%A4%E6%8E%A5%E5%8F%A3
	 */
	/*@Test
    public void testList() throws Exception {
		String url = "/app/order/get_list_green.json";
		
		String params = "?user_id=814";
		MockHttpServletRequestBuilder getRequest = get(url + params);
		
	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	}*/
	@Test
    public void testpostGreen() throws Exception {

		String url = "/app/order/post_add_green.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//通用订单. 无需支付
	    postRequest = postRequest.param("user_id", "77");
	    postRequest = postRequest.param("addr_id", "5");
	    postRequest = postRequest.param("service_type_id", "238");
	    postRequest = postRequest.param("mobile", "13146012753");
	    postRequest = postRequest.param("total_num", "3");
	    postRequest = postRequest.param("total_budget", "3");
	    postRequest = postRequest.param("remarks", "3");
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	    Thread.sleep(200000); // 因为junit结束会结束jvm，所以让它等会异步线程  
    }
	
}
