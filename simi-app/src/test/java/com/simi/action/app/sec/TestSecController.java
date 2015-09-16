package com.simi.action.app.sec;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.JUnitActionBase;

public class TestSecController extends JUnitActionBase{
	
	/**
	 * 		秘书登陆接口 单元测试
	 *     ​http://localhost:8080/onecare/app/user/login.json
	 *     http://182.92.160.194/trac/wiki/%E7%94%A8%E6%88%B7%E7%99%BB%E9%99%86%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testPostLogin() throws Exception {

		String url = "/app/sec/login.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("mobile", "18610807136");
	    postRequest = postRequest.param("sms_token", " 000000");
	    postRequest = postRequest.param("login_from", "1");
	
	  
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	/**
	 *  秘书获取用户
	 * @throws Exception
	 */
	
	@Test
    public void testGetUsers() throws Exception {
		String url = "/app/sec/get_users.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("mobile", "18249516801");
	    postRequest = postRequest.param("sec_id", "1");
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

	
    }

	/**
	 * 秘书信息获取接口 
	 * @throws Exception
	 */
	@Test
    public void testSec() throws Exception {
		String url = "/app/sec/get_secinfo.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	postRequest = postRequest.param("sec_id", "1");
	    postRequest = postRequest.param("mobile", "13810002890");
	    
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

	
    }
	/**
	 * 秘书信息修改
	 * @throws Exception
	 */
	
	@Test
    public void testGetSec() throws Exception {
		String url = "/app/sec/post_secinfo.json";
     	MockHttpServletRequestBuilder postRequest = post(url);
     	postRequest = postRequest.param("sec_id", "1");
     	 
     	postRequest = postRequest.param("mobile", "13813452890");
     	postRequest = postRequest.param("name", "charles222");
     	postRequest = postRequest.param("nick_name", "哈雷2222");
     	postRequest = postRequest.param("sex", "男");
     	postRequest = postRequest.param("birth_day", "1980-07-01");
     	postRequest = postRequest.param("city_id", "5");
     	postRequest = postRequest.param("head_img", "");
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	/**
	 * 订单列表接口
	 * @throws Exception
	 */
	@Test
    public void testOrderList() throws Exception {
		String url = "/app/sec/get_orderlist.json";
		
		String params = "?sec_id=1&mobile=18249516809&page=1";
		MockHttpServletRequestBuilder getRequest = get(url + params);
		
	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	}

	
	/**
	 * 获取秘书列表
	 * @throws Exception
	 */
	@Test
    public void testSecList() throws Exception {
		String url = "/app/sec/get_list.json";
		
		String params = "?user_id=1&page=1";
		MockHttpServletRequestBuilder getRequest = get(url + params);
		
	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
	}	
}
