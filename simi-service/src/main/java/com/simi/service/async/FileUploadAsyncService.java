package com.simi.service.async;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
* @author hulj 
* @date 2016年6月13日 上午11:57:28 
* @Description: 
*
*	多文件异步上传. 返回   <文件在表单中的属性名称 : 文件在文件服务器上的路径 >
*/
public interface FileUploadAsyncService {
	
	Future<Map<String, String>> multiFileUpLoadAsync(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException;
	
}
