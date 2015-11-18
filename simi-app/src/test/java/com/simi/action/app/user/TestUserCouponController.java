package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;


public class TestUserCouponController extends JUnitActionBase  {

	@Test
    public void testGetCoupons() throws Exception {

		String url = "/app/user/get_coupons.json";
		String params = "?user_id=100";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());
	    //打印所有的信息
//	    resultActions.andDo(MockMvcResultHandlers.print());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }

/*	@Test
    public void testPostCoupons() throws Exception {

		String url = "/app/user/post_coupon.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("user_id", "100");
	    postRequest = postRequest.param("card_passwd", "KTH5L363");

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());
	    //打印所有的信息
//	    resultActions.andDo(MockMvcResultHandlers.print());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }*/

}
