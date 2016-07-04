package com.simi.action.app.xcloud;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.eclipse.jetty.util.UrlEncoded;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.simi.action.app.JUnitActionBase;


public class TestCompanyController extends JUnitActionBase  {

	
	
	@Test
    public void testReg() throws Exception {

		String url = "/app/company/reg.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("user_name", "18189155536");
	    postRequest = postRequest.param("sms_token", "6118");
	    postRequest = postRequest.param("company_name", "陕西篝火能源集团有限公司");
	    postRequest = postRequest.param("short_name", "篝火能源");
	    postRequest = postRequest.param("password", "wn715400");

	    

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	/**
	 * 	 团队列表接口
	 */
/*	@Test
    public void testCompanyList() throws Exception {

		String url = "/app/company/get_by_user.json";
		String params = "?user_id=18";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
	*/
	/**
	 * 	 团队列表接口
	 */
	@Test
    public void testCompanyStaffList() throws Exception {

		String url = "/app/company/get_company_setting.json";
		String params = "?user_id=77&company_id=3&setting_type=meeting-room";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	

}
