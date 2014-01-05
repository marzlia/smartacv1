package com.smart.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.smart.po.User;
import com.smart.service.ISysService;
import com.smart.dao.ISysDao;

public class SysService implements ISysService {

	static Logger logger = Logger.getLogger(SysService.class);
	
	private ISysDao sysDao;
	
	
	public ISysDao getSysDao() {
		return sysDao;
	}


	public void setSysDao(ISysDao sysDao) {
		this.sysDao = sysDao;
	}


	@Override
	public User login(User user) {
		// TODO 
		logger.debug("Call Dao to do Login Validation");
		
		return sysDao.login(user);
	}


	@Override
	public List findUserPriv(Integer userId) {
		// TODO Auto-generated method stub
		return sysDao.findUserPriv(userId);
	}

}
