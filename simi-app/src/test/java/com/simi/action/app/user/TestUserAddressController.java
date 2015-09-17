package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.eclipse.jetty.util.UrlEncoded;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;


public class TestUserAddressController extends JUnitActionBase  {

	/**
	 * 		地址提交接口
	 *     ​http://localhost:8080/onecare/app/user/post_addrs.json
	 *     http://182.92.160.194/trac/wiki/%E5%9C%B0%E5%9D%80%E6%8F%90%E4%BA%A4%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testSaveAddress() throws Exception {
		String url = "/app/user/post_addrs.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("user_id", "92");
	    postRequest = postRequest.param("addr_id", "0");

	    postRequest = postRequest.param("poi_type", "0");
	    postRequest = postRequest.param("name", "宇飞大厦");
	    postRequest = postRequest.param("address", "东城区东直门外大街42号。。。");
	    postRequest = postRequest.param("latitude","39.946130678559037" );
	    postRequest = postRequest.param("longitude", "116.44400998619697");
	    postRequest = postRequest.param("city", "城市");
	    postRequest = postRequest.param("uid", "93df0dc…");
	    postRequest = postRequest.param("phone", "(010)84608109");
	    postRequest = postRequest.param("post_code", "asd");
	    postRequest = postRequest.param("addr", UrlEncoded.encodeString("17号楼"));
	    postRequest = postRequest.param("is_default", "1");

	    postRequest = postRequest.param("city_id", "0");
	    postRequest = postRequest.param("cell_id", "0");
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }

	/**
	 * 		获取用户地址接口 单元测试
	 *     ​http://localhost:8080/onecare/app/user/get_addrs.json
	 *     http://182.92.160.194/trac/wiki/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9C%B0%E5%9D%80%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testGetAddress() throws Exception {

		String url = "/app/user/get_addrs.json";
		String params = "?mobile=13520256623";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }

	/**
	 * 		地址删除接口  单元测试
	 *     ​http://localhost:8080/onecare/app/user/post_del_addrs.json
	 *     http://182.92.160.194/trac/wiki/%E5%9C%B0%E5%9D%80%E5%88%A0%E9%99%A4%E6%8E%A5%E5%8F%A3
	 */
//	@Test
//    public void testDelAddress() throws Exception {
//
//		String url = "/app/user/post_del_addrs.json";
//
//     	MockHttpServletRequestBuilder postRequest = post(url);
//	    postRequest = postRequest.param("mobile", "18612514665");
//	    postRequest = postRequest.param("addr_id", "17");
//
//	    ResultActions resultActions = mockMvc.perform(postRequest);
//
//	    resultActions.andExpect(content().contentType(this.mediaType));
//	    resultActions.andExpect(status().isOk());
//
//
//	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());
//
//    }


}
