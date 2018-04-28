package com.dingcheng.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.dingcheng.common.util.CookieUtil;
import com.dingcheng.common.util.SysConst;
import com.dingcheng.redis.util.RedisUtils;

// 后台路径拦截器
public class InvalidURLFilter implements Filter {
	
	private Logger logger = Logger.getLogger(InvalidURLFilter.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res  =(HttpServletResponse) response;
        String []noFilters = {"/user/login", "user/logout", "user/login-init"};
        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        logger.info("+++++++++=============="+uri);
        boolean dofilter = true;
    	for(String noFilter : noFilters) {
    		if(uri.endsWith(noFilter)) {
    			dofilter = false;
    			break;
    		}
    	}
        if(dofilter){// 如果不包含，就需要登录验证
        	String token = CookieUtil.newInstance().readCookie(req, res, SysConst.LOGIN_FLAG);
        	Object redis = RedisUtils.get(token);
        	logger.info(contextPath + "+++++++++=============="+token+ "============="+redis);
        	if(token == null || redis == null) {
                res.sendRedirect(contextPath + "/user/login-init");
                return;
            }
        }
        chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
