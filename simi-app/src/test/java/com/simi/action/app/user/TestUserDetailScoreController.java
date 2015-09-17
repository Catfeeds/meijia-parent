package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URLEncoder;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;


public class TestUserDetailScoreController extends JUnitActionBase  {

	/**
	 * 		我的积分明细接口单元测试
	 *     ​http://localhost:8080/onecare/app/user/get_score.json
	 *     http://182.92.160.194/trac/wiki/%E6%88%91%E7%9A%84%E7%A7%AF%E5%88%86%E6%98%8E%E7%BB%86%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testUserScoreDetail() throws Exception {

		String url = "/app/user/get_score.json";
		String params = "?mobile=15810635231&page=1";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }

	/**
	 * 		分享好友记录接口 单元测试
	 *     ​http://localhost:8080/onecare/app/user/share.json
	 *     http://182.92.160.194/trac/wiki/%E5%88%86%E4%BA%AB%E5%A5%BD%E5%8F%8B%E8%AE%B0%E5%BD%95%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testPostShare() throws Exception {

		String url = "/app/user/share.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("mobile", "18612514665");
	    postRequest = postRequest.param("share_type", "qq");
	    postRequest = postRequest.param("share_account", URLEncoder.encode("888888", "utf-8"));

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());
	    //打印所有的信息
//	    resultActions.andDo(MockMvcResultHandlers.print());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }

}
