package com.simi.action.app.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simi.action.app.JUnitActionBase;
import com.meijia.utils.TimeStampUtil;


public class TestOrderAddController extends JUnitActionBase  {

	/**
	 * 		提交订单接口 单元测试
	 *     ​http://localhost:8080/onecare/app/order/post_add.json
	 *     http://182.92.160.194/trac/wiki/%E8%AE%A2%E5%8D%95%E6%8F%90%E4%BA%A4%E6%8E%A5%E5%8F%A3
	 */
	@Test
    public void testSaveOrder() throws Exception {

		String url = "/app/order/post_add.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
	    postRequest = postRequest.param("mobile", "13146012753");//13911489629
	    postRequest = postRequest.param("city_id", "2");
	    postRequest = postRequest.param("service_type", "4");

	    HashMap<String,String> item1 = new HashMap<String,String>();
	    item1.put("type", "403");
	    item1.put("value", "4");

//	    HashMap<String,String> item2 = new HashMap<String,String>();
//	    item2.put("type", "102");
//	    item2.put("value", "3");


	    ObjectMapper mapper = new ObjectMapper();
	    List<HashMap> sendDatas = new ArrayList<HashMap>();
	    sendDatas.add(item1);
//	    sendDatas.add(item2);
	    postRequest = postRequest.param("send_datas", mapper.writeValueAsString(sendDatas));

	    Long serviceDate = TimeStampUtil.getMillisOfDay("2015-04-20 13:00:00")/1000;
	  //  Long startTime = TimeStampUtil.getMillisOfDay("2015-04-20 12:00:00")/1000;
	    Long startTime = TimeStampUtil.getMillisOfDayFull("2015-04-20 13:00:00")/1000;

	    postRequest = postRequest.param("service_date", serviceDate.toString());
	    postRequest = postRequest.param("start_time", startTime.toString());
	    postRequest = postRequest.param("service_hour", "4");

	    postRequest = postRequest.param("addr_id", "22");
	    postRequest = postRequest.param("remarks", "");
	    postRequest = postRequest.param("order_from", "2");
	    postRequest = postRequest.param("agent_mobile", "13146012753");//18610807136
	    postRequest = postRequest.param("clean_tools", "0");

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }
}
