package com.simi.action.app.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;

public class TestOrderExtCleanController extends JUnitActionBase  {

	@Test
    public void testList() throws Exception {
		String url = "/app/order/get_list_clean.json";
		
		String params = "?user_id=18";
		MockHttpServletRequestBuilder getRequest = get(url + params);
		
	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	}
	
	@Test
    public void testDetail() throws Exception {
		String url = "/app/order/get_detail_clean.json";
		
		String params = "?user_id=18&order_id=163";
		MockHttpServletRequestBuilder getRequest = get(url + params);
		
	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	}	
	
	
	@Test
    public void testpostClean() throws Exception {

		String url = "/app/order/post_add_clean.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//通用订单. 无需支付
	    postRequest = postRequest.param("user_id", "18");
	    postRequest = postRequest.param("company_name", "北京美家生活科技有限公司");
	    postRequest = postRequest.param("clean_area", "1");
	    postRequest = postRequest.param("clean_type", "1");
	    postRequest = postRequest.param("link_man", "张");
	    postRequest = postRequest.param("link_tel", "13810002890");
	    postRequest = postRequest.param("remarks", "请尽快回复");
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	    Thread.sleep(200000); // 因为junit结束会结束jvm，所以让它等会异步线程  
    }
	
}
