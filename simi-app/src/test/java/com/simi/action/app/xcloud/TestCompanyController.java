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
	
	@Test
    public void testRegApp() throws Exception {

		String url = "/app/company/reg_app.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("city_id", "2");
     	postRequest = postRequest.param("user_id", "1306");
	    postRequest = postRequest.param("company_name", "和谐");
	    postRequest = postRequest.param("short_name", "不");
	    postRequest = postRequest.param("company_size", "0");

	    

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
		String params = "?user_id=18&company_id=6&setting_type=checkin-net";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	

}
