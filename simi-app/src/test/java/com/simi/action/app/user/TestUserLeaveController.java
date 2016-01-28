package com.simi.action.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.meijia.utils.GsonUtil;
import com.simi.action.app.JUnitActionBase;
import com.simi.vo.card.LinkManVo;

public class TestUserLeaveController extends JUnitActionBase{

	
	/**
	 * 	提交卡片接口 单元测试
	 */
	@Test
    public void testPostCard() throws Exception {

		String url = "/app/user/post_leave.json";

     	MockHttpServletRequestBuilder postRequest = post(url);
     	
     	//新增
     	postRequest = postRequest.param("company_id", "15");
	    postRequest = postRequest.param("user_id", "278");
	    postRequest = postRequest.param("leave_type", "1");
	    postRequest = postRequest.param("start_date", "2016-01-28");
	    postRequest = postRequest.param("end_date", "2016-01-28");
	    postRequest = postRequest.param("total_days", "1");
	    postRequest = postRequest.param("remarks", "有事");
	    
	    //参会人员
	    List<LinkManVo> attendsList = new ArrayList<LinkManVo>();
	    LinkManVo a1 = new LinkManVo();
	    a1.setMobile("18612514665");
	    a1.setName("18612514665");
	    a1.setUser_id(18L);
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
	    
	    postRequest = postRequest.param("pass_users", attends);
	    
	    
	    ResultActions resultActions = mockMvc.perform(postRequest);

	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());


	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testGetList() throws Exception {

		String url = "/app/user/leave_list.json";
		String params = "?user_id=18&leave_from=1";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }	
	
	@Test
    public void testGetDetail() throws Exception {

		String url = "/app/user/leave_detail.json";
		String params = "?user_id=278&leave_id=1";
		MockHttpServletRequestBuilder getRequest = get(url + params);

	    ResultActions resultActions = this.mockMvc.perform(getRequest);
	    resultActions.andExpect(content().contentType(this.mediaType));
	    resultActions.andExpect(status().isOk());

	    System.out.println("RestultActons: " + resultActions.andReturn().getResponse().getContentAsString());

    }		
	
}
