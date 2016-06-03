package com.simi.action.app.feed;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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



public class TestFeedController extends JUnitActionBase  {

	/**
	 * 	提交动态接口 单元测试
	 */
	@Test
    public void testPostFeed() throws Exception {

		String url = "/app/feed/post_feed.json?user_id=18&title=问答测试2&feed_type=2&feed_extra=100&lat=31.156731&lng=121.810487&poi_name=上海浦东国际机场";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	HashMap<String, String> contentTypeParams = new HashMap<String, String>();
     	contentTypeParams.put("boundary", "265001916915724");
	   
	    MockMultipartFile image = new MockMultipartFile("feed_imgs", "1.png", "", imageToByteArray("/Users/lnczx/Desktop/tmp/1.png"));
	    
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

	@Test
    public void testPostZan() throws Exception {

		String url = "/app/feed/post_zan.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("fid", "1109");
	    postRequest = postRequest.param("user_id", "18");
	    postRequest = postRequest.param("action", "add");
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testPostComment() throws Exception {

		String url = "/app/feed/post_comment.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("fid", "325");
     	postRequest = postRequest.param("feed_type", "2");
	    postRequest = postRequest.param("user_id", "278");
	    postRequest = postRequest.param("comment", "还不错278");
	    
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testPostDone() throws Exception {

		String url = "/app/feed/post_done.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("fid", "325");
     	postRequest = postRequest.param("feed_type", "2");
	    postRequest = postRequest.param("user_id", "18");
	    postRequest = postRequest.param("comment_id", "116");
	    
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testPostClose() throws Exception {

		String url = "/app/feed/post_close.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("fid", "325");
     	postRequest = postRequest.param("feed_type", "2");
	    postRequest = postRequest.param("user_id", "18");
	    
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }		
	
	@Test
    public void testPostFeedDel() throws Exception {

		String url = "/app/feed/del.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("fid", "1");
	    postRequest = postRequest.param("user_id", "1");
	    
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }		
	
	@Test
    public void testPostCommentDel() throws Exception {

		String url = "/app/feed/del_comment.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("fid", "2");
	    postRequest = postRequest.param("user_id", "1");
	    postRequest = postRequest.param("comment_id", "3");
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

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
	
	/**
	 * 动态图片接口测试
	 * @throws Exception
	 */
	
	/*@Test
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

	}*/
}
