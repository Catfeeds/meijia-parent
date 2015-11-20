package com.simi.action.app.job;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.simi.action.app.JUnitActionBase;

public class TestJobOrderController extends JUnitActionBase{
	
	/**
	 *  订单超过1个小时未支付,则关闭订单,
	 *  就是订单的状态变成 9 ,并且把相应的优惠劵的信息置为空
	 */

	/*@Test
    public void testOrderMore60min() throws Exception {

		String url = "/app/job/order/check_order_more_60min.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	*/
	/**
	 * 订单如果超过7天未评价，则默认给此订单默认5星的评价
	 * ，评价内容写的是  ： 订单超时未评价，系统默认给5星好评
	 * @param request
	 * @return
	 */
/*	@Test
    public void testOrderMore7Days() throws Exception {

		String url = "/app/job/order/check_order_more_7Day.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	*/
	/**
	 * 订单超时未支付通知用户， 找出当前未支付的订单， 
	 * 找到用户手机号，通知用户.
	 * @param request
	 * @return
	 */
	@Test
    public void OrderOrder1Hour() throws Exception {

		String url = "/app/job/order/check_order_more_1hour.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	

	/**
	 *  优惠劵即将过期通知， 如果优惠劵离过期还有一天，则发送短信.
	 * @param request
	 * @return
	 */
	/*@Test
    public void OrderCoupons1Day() throws Exception {

		String url = "/app/job/order/check_coupons_1Day.json";

     	MockHttpServletRequestBuilder postRequest = get(url);

	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActions: " + resultActions.andReturn().getResponse().getContentAsString());

    }	*/
	


	
}
