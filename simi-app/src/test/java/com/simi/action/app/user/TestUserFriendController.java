package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;


public class TestUserFriendController extends JUnitActionBase  {

	@Test
    public void testPpostFriendReq() throws Exception {

		String url = "/app/user/post_friend_req.json";
		MockHttpServletRequestBuilder postRequest = post(url);
		postRequest = postRequest.param("user_id", "18");
	    postRequest = postRequest.param("friend_id", "50");
	    postRequest = postRequest.param("status", "2");
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	/*@Test
    public void testgetFriend() throws Exception {

		String url = "/app/user/get_friend_reqs.json";
		String params = "?&user_id=32";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }*/
	/*@Test
    public void addFriend() throws Exception {

		String url = "/app/user/add_friend.json";
		String params = "?&user_id=23&friend_id=295";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
    }*/
}
