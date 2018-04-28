package com.dingcheng.common.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * *********************************
* 异常处理返回
* @author: 315737546
* @createdAt: 2016年6月14日下午2:55:39
**********************************
 */
public class MyExceptionHandler implements HandlerExceptionResolver{
	private Logger logger = Logger.getLogger(MyExceptionHandler.class);
	
	/**
	 * 封装异常信息返回
	* @return
	* @Author: 315737546
	* @Date: 2016年6月14日
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		Map<String,Object> map = new HashMap<String,Object>();
		logger.error(ex.getMessage(),ex);
		if(ex instanceof MaxUploadSizeExceededException){
			map.put("msg", "文件大小超过限制!");
			return new ModelAndView(new MappingJackson2JsonView(),map);
		}
		map.put("msg", "系统错误!");
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}

}
