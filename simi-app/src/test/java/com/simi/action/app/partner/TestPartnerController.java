package com.simi.action.app.partner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.meijia.utils.GsonUtil;
import com.simi.action.app.JUnitActionBase;
import com.simi.vo.card.LinkManVo;


public class TestPartnerController extends JUnitActionBase  {

	/**
	 * 	获取服务人员列表 单元测试
	 */
	@Test
    public void testGetUserList() throws Exception {

		String url = "/app/partner/get_user_list.json";
		String params = "?user_id=472&service_type_ids=52";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testGetUserDetail() throws Exception {

		String url = "/app/partner/get_user_detail.json";
		String params = "?partner_user_id=533&service_type_id=79";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	/*
	@Test
    public void testGetPartnerServicePriceDetail() throws Exception {

		String url = "/app/partner/get_partner_service_price_detail.json";
		String params = "?service_price_id=171";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }		
	
	@Test
    public void testgetHotKeyword() throws Exception {

		String url = "/app/partner/get_hot_keyword.json";
		String params = "";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testSearch() throws Exception {

		String url = "/app/partner/search.json";
		String params = "?user_id=1&keyword=工商注册";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }		
	
	@Test
    public void testPartnerAdd() throws Exception {

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
    }*/
}
