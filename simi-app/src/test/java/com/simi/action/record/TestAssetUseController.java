package com.simi.action.record;

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
import com.simi.vo.card.LinkManVo;



public class TestAssetUseController extends JUnitActionBase  {

	@Test
    public void testPostAssets() throws Exception {
		
		String url = "/app/record/post_asset_use.json";
		MockHttpServletRequestBuilder postRequest = post(url);
     	
		HashMap<String, String> assetMap = new HashMap<String, String>();
		assetMap.put("asset_id", "1");
		assetMap.put("total", "1");
		List<HashMap> assets = new ArrayList<HashMap>();
		assets.add(assetMap);
		String assetJson = GsonUtil.GsonString(assets);
		
     	//新增
	    postRequest = postRequest.param("user_id", "18");
	    postRequest = postRequest.param("company_id", "6");
	    postRequest = postRequest.param("asset_json", assetJson);
	    postRequest = postRequest.param("purpose", "公用");
	    postRequest = postRequest.param("to_user_id", "18");
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());				

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	
	@Test
    public void testGetList() throws Exception {

		String url = "/app/record/get_asset_use.json";
		String params = "?user_id=18&company_id=6";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testGetDetail1() throws Exception {

		String url = "/app/record/get_use_detail.json";
		String params = "?user_id=18&company_id=6&id=1";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
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
