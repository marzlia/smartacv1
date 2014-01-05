package com.smart.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport {
	
	static Logger logger = Logger.getLogger(BaseAction.class);

	public String jsonString;

	public void outJsonString(String str) {
		getResponse().setContentType("text/javascript;charset=UTF-8");
		outString(str);
	}

	/*
	 * public void outJson(Object obj) {
	 * outJsonString(JSONObject.fromObject(obj).toString()); }
	 * 
	 * public void outJsonArray(Object array) {
	 * outJsonArray(JSONArray.fromObject(array).toString()); }
	 */

	public void outString(String str) {
		try {
			PrintWriter out = getResponse().getWriter();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outXMLString(String xmlStr) {
		getResponse().setContentType("application/xml;charset=UTF-8");
		outString(xmlStr);
	}

	/**
	 * ���request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * servlet
	 * 
	 * @return
	 */
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	public String getRealyPath(String path) {
		return getServletContext().getRealPath(path);
	}
	
	public String requesetjson() {   
		StringBuffer jb = new StringBuffer();   
		String line = null;   
		try {   
			BufferedReader reader = this.getRequest().getReader();   
				while ((line = reader.readLine()) != null)   
					jb.append(line);   
			} catch (Exception e) {   
				logger.error(e.getMessage());
				//java.lang.StackTraceElement t[] = e.getStackTrace();
			}   
			return jb.toString();   
	}  
}
