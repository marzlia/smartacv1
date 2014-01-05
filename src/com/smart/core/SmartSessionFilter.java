package com.smart.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class SmartSessionFilter implements Filter {

	static Logger logger = Logger.getLogger(SmartSessionFilter.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub

		logger.debug("In doFilter : ");
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;        
	    HttpServletResponse response = (HttpServletResponse) servletResponse;     
	    
	    logger.debug(request.getRequestURI());
	    if(request.getRequestURI().matches(".*login.action")){
	    	filterChain.doFilter(request, response);
	    	return;
	    }
	    
	    if(request.isRequestedSessionIdValid()){
	    	
	    	HttpSession session = request.getSession();   
	    	logger.debug("session: " + session.getId());
	    	
	    } else {
	    	logger.debug("session is not valid");
	    	logger.debug("x-requested-with: " + request.getHeader("x-requested-with"));
	    	if (request.getHeader("x-requested-with") != null  
	    			&& (request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")
	    					|| request.getHeader("x-requested-with").equalsIgnoreCase("Ext.basex"))) {
	    		logger.debug("Response: sessionstatus is timeout");
	    		response.addHeader("sessionstatus", "timeout");
	    		response.setContentType("text/javascript;charset=UTF-8");
	    		PrintWriter out = response.getWriter();
				out.write("{}");
	    		return;
	    	}else{
	    		
	    	}    
	    }    
		
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		logger.debug("In init : ");
		
	}

}
