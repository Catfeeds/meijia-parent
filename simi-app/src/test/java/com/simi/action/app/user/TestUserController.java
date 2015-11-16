package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.simi.action.app.JUnitActionBase;

public class TestUserController extends JUnitActionBase {

	/**
	 * 获取验证码接口单元测试 ​http://localhost:8080/onecare/app/get_sms_token.json
	 * http://182.92.160.194/trac/wiki/%E8%
	 * 8E%B7%E5%8F%96%E9%AA%8C%E8%AF%81%E7%A0%81%E6%8E%A5%E5%8F%A3
	 */
	@Test
	public void testGetSmsToken() throws Exception {

		String url = "/app/user/get_sms_token.json";
		String params = "?mobile=19912514665&sms_type=0";
		MockHttpServletRequestBuilder getRequest = get(url + params);

		ResultActions resultActions = this.mockMvc.perform(getRequest);
		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	}

	/**
	 * 用户登陆接口 单元测试 ​http://localhost:8080/onecare/app/user/login.json
	 * http://182.92 .160.194/trac/wiki/%E7%94%A8%E6%88%B7%E7%99%BB%E9%99%86%E6%
	 * 8E%A5%E5%8F%A3
	 */
	@Test
	public void testPostLogin() throws Exception {

		String url = "/app/user/login.json";

		MockHttpServletRequestBuilder postRequest = post(url);
		postRequest = postRequest.param("mobile", "18912514665");
		// postRequest = postRequest.param("mobile", "18610807136");
		postRequest = postRequest.param("sms_token", "2936");
		// postRequest = postRequest.param("sms_token", "000000");
		postRequest = postRequest.param("login_from", "1");

		ResultActions resultActions = mockMvc.perform(postRequest);

		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

	}

	/**
	 * 获取验证码接口单元测试 ​http://localhost:8080/onecare/app/user/get_userinfo.json
	 * http://182.92.160.194/trac/wiki/%E8%B4%A6%E5%8F%B7%E4%BF%A1%E6%81%AF%E6%
	 * 8E%A5%E5%8F%A3
	 */
	@Test
	public void testGetUserInfo() throws Exception {

		String url = "/app/user/get_userinfo.json";
		String params = "?user_id=287";
		MockHttpServletRequestBuilder getRequest = get(url + params);

		ResultActions resultActions = this.mockMvc.perform(getRequest);
		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	}

	/**
	 * 设置用户信息已读接口
	 * ​http://www.yougeguanjia.com/onecare/app/user/post_msg_read.json
	 * http://182.92
	 * .160.194:8080/trac/wiki/%E8%AE%BE%E7%BD%AE%E7%94%A8%E6%88%B7%
	 * E6%B6%88%E6%81%AF%E5%B7%B2%E8%AF%BB%E6%8E%A5%E5%8F%A3
	 */
	@Test
	public void testPostMsgRead() throws Exception {

		String url = "/app/user/post_msg_read.json";

		MockHttpServletRequestBuilder postRequest = post(url);
		postRequest = postRequest.param("mobile", "18600283903");
		postRequest = postRequest.param("msg_id", "0");

		ResultActions resultActions = mockMvc.perform(postRequest);

		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	}

	/**
	 * 用户信息修改接口 ​http://182.92.160.194/simi/app/user/post_userinfo.json
	 * http://182.92
	 * .160.194:8080/trac/wiki/%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%
	 * E4%BF%AE%E6%94%B9%E6%8E%A5%E5%8F%A3
	 */
	@Test
	public void testUpdateUserInfo() throws Exception {

		String url = "/app/user/post_userinfo.json";

		MockHttpServletRequestBuilder postRequest = post(url);
		FileInputStream fis = new FileInputStream("D:/a.jpg");
		MockMultipartFile multipartFile = new MockMultipartFile("file", fis);

		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.fileUpload(url)
				/* .file(multipartFile) */
				.param("user_id", "92").param("mobile", "").param("name", "kerry").param("sex", "男士").contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		// ResultActions resultActions = mockMvc.perform(postRequest);

		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	}

	@Test
	public void testGetUserIndex() throws Exception {

		String url = "/app/user/get_user_index.json";
		String params = "?user_id=282&view_user_id=282";
		MockHttpServletRequestBuilder getRequest = get(url + params);

		ResultActions resultActions = this.mockMvc.perform(getRequest);
		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	}

	/**
	 * 用户信息修改接口 ​http://182.92.160.194/simi/app/user/post_userinfo.json
	 * http://182.92
	 * .160.194:8080/trac/wiki/%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%
	 * E4%BF%AE%E6%94%B9%E6%8E%A5%E5%8F%A3
	 */
	@Test
	public void testPostUserHeadImg() throws Exception {

		String url = "/app/user/post_user_head_img.json?user_id=1";
		
		HashMap<String, String> contentTypeParams = new HashMap<String, String>();
//		contentTypeParams.put("user_id", "1");
		contentTypeParams.put("boundary", "265001916915724");

        MockMultipartFile image = new MockMultipartFile("file", "1.png", "", imageToByteArray("/Users/lnczx/Desktop/tmp/1.png"));
        
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

		ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.fileUpload(url)
                .file(image)        
                .contentType(mediaType))
                .andExpect(status().isOk());

		resultActions.andExpect(content().contentType(this.mediaType));
		resultActions.andExpect(status().isOk());

		System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

	}

	public static byte[] imageToByteArray(String imgPath) {
		BufferedInputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(imgPath));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int size = 0;
			byte[] temp = new byte[1024];
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			in.close();
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
