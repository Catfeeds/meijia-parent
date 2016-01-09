package com.simi.action.app.xcloud;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.util.UrlEncoded;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.meijia.utils.GsonUtil;
import com.simi.action.app.JUnitActionBase;
import com.simi.vo.card.LinkManVo;


public class TestCompanyCheckinController extends JUnitActionBase  {

	/**
	 * 	提交卡片接口 单元测试
	 */
	@Test
    public void testCheckin() throws Exception {

		String url = "/app/company/checkin.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("user_id", "18");
	    postRequest = postRequest.param("company_id", "6");
	    postRequest = postRequest.param("poi_name", "北京站");
	    postRequest = postRequest.param("poi_lat", "39.910286");
	    postRequest = postRequest.param("poi_lng", "116.433302");
	    postRequest = postRequest.param("checkin_type", "0");
	    postRequest = postRequest.param("checkin_net", "zrj5G");
	    postRequest = postRequest.param("remarks", "早上打开哦");
	    

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }

}
