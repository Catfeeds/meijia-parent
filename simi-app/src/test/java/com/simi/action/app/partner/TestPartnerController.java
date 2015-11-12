package com.simi.action.app.partner;

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

import com.meijia.utils.JsonUtil;
import com.simi.action.app.JUnitActionBase;
import com.simi.vo.card.LinkManVo;


public class TestPartnerController extends JUnitActionBase  {

	/**
	 * 	获取服务人员列表 单元测试
	 */
	@Test
    public void testGetUserList() throws Exception {

		String url = "/app/partner/get_user_list.json";
		String params = "?user_id=1&service_type_id=75";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testGetPartnerServicePriceDetail() throws Exception {

		String url = "/app/partner/get_partner_service_price_detail.json";
		String params = "?service_price_id=171";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }		
}
