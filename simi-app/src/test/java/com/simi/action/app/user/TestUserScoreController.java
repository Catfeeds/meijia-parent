package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
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

public class TestUserScoreController extends JUnitActionBase {

	/**
	 * 获取验证码接口单元测试 ​http://localhost:8080/simi/app/get_sms_token.json
	 * http://182.92.160.194/trac/wiki/%E8%
	 * 8E%B7%E5%8F%96%E9%AA%8C%E8%AF%81%E7%A0%81%E6%8E%A5%E5%8F%A3
	 */
	@Test
	public void testGetScore() throws Exception {

		String url = "/app/user/get_score.json";
		String params = "?user_id=273&page=1";
		MockHttpServletRequestBuilder getRequest = get(url + params);

		ResultActions resultActions = this.mockMvc.perform(getRequest);
		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	}
	
	@Test
    public void testDaySign() throws Exception {

		String url = "/app/user/day_sign.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	     	
	    postRequest = postRequest.param("user_id", "18");
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	    
	    Thread.sleep(60000); // 因为junit结束会结束jvm，所以让它等会异步线程  
    }
	
	@Test
    public void testNotifySocre() throws Exception {

		String url = "/app/user/notfiy_score.json";
		String params = "";
		MockHttpServletRequestBuilder getRequest = get(url + params);

		ResultActions resultActions = this.mockMvc.perform(getRequest);
		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	
	@Test
    public void testResultSocre() throws Exception {

		String url = "/app/user/result_score.do";
		String params = "";
		MockHttpServletRequestBuilder getRequest = get(url + params);

		ResultActions resultActions = this.mockMvc.perform(getRequest);
		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
}
