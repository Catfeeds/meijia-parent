package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.eclipse.jetty.util.UrlEncoded;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;

public class TestUserTrailController extends JUnitActionBase{
	@Test
    public void testTrailAddress() throws Exception {
		String url = "/app/user/post_trail.json";

     	MockHttpServletRequestBuilder postRequest = post(url);

	    postRequest = postRequest.param("mobile", "18600283903");
	    postRequest = postRequest.param("latitude","39.946130678559037" );
	    postRequest = postRequest.param("longitude", "116.44400998619697");

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
}
