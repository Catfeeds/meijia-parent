package com.simi.action.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.meijia.utils.TimeStampUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.vo.AppResultData;

public class BaseController {

	public static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /** 基于@ExceptionHandler异常处理 */
    @ExceptionHandler
    public AppResultData<String> exp(HttpServletRequest request, Exception ex) {

        request.setAttribute("ex", ex);
        
       
        long beginTime = TimeStampUtil.getNow();
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
			params.put(name, valueStr);
		}
		
		
        logger.error("Global exception found, Exception is: {}", ex);
        logger.error("request begin_time: "+ TimeStampUtil.timeStampToDateStr(beginTime));
		logger.error(request.getMethod() + " " + request.getRequestURL());
		logger.error(params.toString());
//        System.out.println("-----------------------message--------------------------------");
//        System.out.println(ex.getMessage());
//        System.out.println("-----------------------cause--------------------------------");
//        System.out.println(ex.getCause());
//        System.out.println("-----------------------print--------------------------------");
//        ex.printStackTrace();

		AppResultData<String> result = new AppResultData<String>(
				Constants.ERROR_100, ConstantMsg.ERROR_100_MSG, "");
		return result;
    }
}