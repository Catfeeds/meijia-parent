package com.simi.action.app.card;

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


public class TestCardController extends JUnitActionBase  {

	/**
	 * 	提交卡片接口 单元测试
	 */
	@Test
    public void testPostCard() throws Exception {

		String url = "/app/card/post_card.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("card_id", "0");
	    postRequest = postRequest.param("card_type", "3");
	    postRequest = postRequest.param("create_user_id", "139");
	    postRequest = postRequest.param("user_id", "139");
	    
	    //参会人员
	    List<LinkManVo> attendsList = new ArrayList<LinkManVo>();
	    LinkManVo a1 = new LinkManVo();
	    a1.setMobile("13146012753");
	    a1.setName("凯凯");
//	    LinkManVo a2 = new LinkManVo();
//	    a2.setMobile("18037338893");
//	    a2.setName("A");

	    
//	    LinkManVo a3 = new LinkManVo();
//	    a3.setMobiel("18610807136");
//	    a3.setName("马志");
	    attendsList.add(a1);
//	    attendsList.add(a2);	
//	    attendsList.add(a3);
	    
	    String attends = GsonUtil.GsonString(attendsList);
	    
	    postRequest = postRequest.param("attends", attends);
	    postRequest = postRequest.param("service_time", "1446544740");
//	    postRequest = postRequest.param("service_addr", "宇飞大厦612");
	    postRequest = postRequest.param("service_content", "测试");
	    postRequest = postRequest.param("set_remind", "1");
	    postRequest = postRequest.param("set_now_send", "1");
	    postRequest = postRequest.param("set_sec_do", "0");
//	    postRequest = postRequest.param("set_sec_remarks", "yuand请通知所有人员,一共2个人");
	    
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }

	@Test
    public void testPostZan() throws Exception {

		String url = "/app/card/post_zan.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("card_id", "1");
	    postRequest = postRequest.param("user_id", "93");
	    
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testPostComment() throws Exception {

		String url = "/app/card/post_comment.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("card_id", "2");
	    postRequest = postRequest.param("user_id", "93");
	    postRequest = postRequest.param("comment", "还不错");
	    
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	/**
	 * 卡片图片接口测试
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
