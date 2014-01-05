package com.smart.core;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionAttributeListener;
import org.apache.log4j.Logger;


public class SmartSessionListener implements HttpSessionListener, HttpSessionAttributeListener{

	static Logger logger = Logger.getLogger(SmartSessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent e) {
		// TODO Auto-generated method stub
		logger.debug("Session created: " + e.getSession().getId());

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		// TODO Auto-generated method stub
		logger.debug("Session destroyed: " + e.getSession().getId());
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent e) {
		// TODO Auto-generated method stub
		logger.debug("Session attribute added: " + e.getName());
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent e) {
		// TODO Auto-generated method stub
		logger.debug("Session attribute removed: " + e.getName());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent e) {
		// TODO Auto-generated method stub
		logger.debug("Session attribute replaced");
	}

}
