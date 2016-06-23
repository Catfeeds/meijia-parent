package com.simi.service.impl.async;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.ImgServerUtil;
import com.simi.common.Constants;
import com.simi.service.async.FileUploadAsyncService;

@Service
public class FileUploadAsyncServiceImpl implements FileUploadAsyncService {

	/**
	 * 针对springMVC 的 多图片异步上传
	 * 
	 * 返回 <文件vo字段属性名 : 图片服务器路径 >
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * 
	 */
	@Async
	@Override
	public Future<Map<String, String>> multiFileUpLoadAsync(HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		
		Map<String, String> returnMap = new HashMap<String,String>();
		
		
		// 处理图片上传
		// 创建一个通用的多部分解析器.
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			// 判断 request 是否有文件上传,即多部分请求...
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {

				CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile(iter.next());
				// MultipartFile file = multiRequest.getFile(iter.next());

				// 能够得到 表单字段的 属性名，进行对对应的设置
				String fieldName = file.getFileItem().getFieldName();
				
				String imgUrl = "";
				
				if (file != null && !file.isEmpty()) {
					String url = Constants.IMG_SERVER_HOST + "/upload/";
					String fileName = file.getOriginalFilename();
					String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
					fileType = fileType.toLowerCase();
					String sendResult = ImgServerUtil.sendPostBytes(url, file.getBytes(), fileType);
					
					ObjectMapper mapper = new ObjectMapper();

					HashMap<String, Object> o = mapper.readValue(sendResult, HashMap.class);

					String ret = o.get("ret").toString();

					HashMap<String, String> info = (HashMap<String, String>) o.get("info");

					imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();

				}
				
				returnMap.put(fieldName, imgUrl);
				
			}
		}

		return new AsyncResult<Map<String,String>>(returnMap);
	}

}
