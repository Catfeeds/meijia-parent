package com.xcloud.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author :hulj
 * @Date : 2016年4月26日下午6:36:39
 * @Description: 
 * 
 * 		处理 中文参数乱码
 *			
 *		问题： springmvc 传值默认的是 ISO-8859-1
 *
 *			对于在列表页的中文搜索条件，会出现乱码。即使配置了强制 utf-8的 filter
 *		
 *		解决：如下。 在request里 处理  中文参数，解决乱码。
 *			
 *			！！ 同样需要在 web.xml配置。		  	
 *		
 */
public class ChineseParamFilter extends OncePerRequestFilter {
	
	public String filter(HttpServletRequest request, String input) {
		String ret = input;
                //ios客户端请求参数值可能为(null)服务端过滤掉当null处理即可
		if (input == null || input.trim().equals("(null)")) {
                        ret=null;
			return ret;
		}
		final String userAgent = request.getHeader("User-Agent");
		final String method = request.getMethod();
                //该处可以实现各种业务的自定义的过滤机制
		if (method.equalsIgnoreCase("get")
				|| userAgent.toLowerCase().indexOf("android") != -1) {
			try {
				ret = new String(input.getBytes("ISO8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		filterChain.doFilter(new HttpServletRequestWrapper(request) {
			@Override
			public String getParameter(String name) {
				String value = super.getParameter(name);
				return filter(this, value);
			}

			@Override
			public String[] getParameterValues(String name) {
				String[] values = super.getParameterValues(name);
				if (values == null) {
					return null;
				}
				for (int i = 0; i < values.length; i++) {
					values[i] = filter(this, values[i]);
				}
				return values;
			}

		}, response);
		
		
	}

}
