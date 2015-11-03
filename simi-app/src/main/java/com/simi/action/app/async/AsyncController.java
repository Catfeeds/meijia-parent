package com.simi.action.app.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.service.async.AsyncService;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/async")
public class AsyncController extends BaseController {
	@Autowired
	private AsyncService asyncService;

	@RequestMapping(value = "test", method = RequestMethod.GET)
	public AppResultData<Object> testAsync(
			) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		System.out.println("simi-app test async start.....");
		asyncService.async();
		System.out.println("simi-app test async end.....");
		return result;
	}
}
