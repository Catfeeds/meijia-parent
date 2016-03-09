package com.simi.action.app.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.JUnitActionBase;

public class TestOrderExtTeamController extends JUnitActionBase  {

	@Test
    public void testList() throws Exception {
		String url = "/app/order/get_list_team.json";
		
		String params = "?user_id=472";
		MockHttpServletRequestBuilder getRequest = get(url + params);
		
	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	}
	
/*	@Test
    public void testDetail() throws Exception {
		String url = "/app/order/get_detail_team.json";
		
		String params = "?user_id=18&order_id=141";
		MockHttpServletRequestBuilder getRequest = get(url + params);
		
	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	}	
	*/
	
	/*@Test
    public void testpostGreen() throws Exception {

		String url = "/app/order/post_add_team.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	//通用订单. 无需支付
	    postRequest = postRequest.param("user_id", "18");
	    postRequest = postRequest.param("city_id", "2");
	    postRequest = postRequest.param("service_days", "5");
	    postRequest = postRequest.param("attend_num", "15");
	    postRequest = postRequest.param("team_type", "1");
	    postRequest = postRequest.param("link_man", "张张");
	    postRequest = postRequest.param("link_tel", "18845710028");
	    postRequest = postRequest.param("remarks", "张张张张账号");
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	    Thread.sleep(200000); // 因为junit结束会结束jvm，所以让它等会异步线程  
    }*/
	
}
