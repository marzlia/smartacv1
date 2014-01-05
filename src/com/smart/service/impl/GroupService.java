//用户组  by zhouyu
package com.smart.service.impl;

import org.apache.log4j.Logger;

import com.smart.core.Page;
import com.smart.po.Group;
import com.smart.service.IGroupService;
import com.smart.dao.IGroupDao;
import com.smart.dao.IUserDao;
import com.smart.dao.IPrivassignDao;

public class GroupService implements IGroupService {
	static Logger logger = Logger.getLogger(GroupService.class);
	
	private IGroupDao groupDao;
	private IUserDao userDao;
	private IPrivassignDao privassignDao;

	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}


	public void setPrivassignDao(IPrivassignDao privassignDao) {
		this.privassignDao = privassignDao;
	}



	//用户组----add
	public Object saveGroup(Group group) {

		return groupDao.saveGroup(group);
	}

//用户组----find
	public Page findByPage(Page page) {

		page.setRoot(groupDao.findByPage(page));
		page.setTotalProperty(groupDao.findByCount(page));
		return page;
	}

//用户组----update
	public boolean updateGroup(Group group) throws Exception {

		Integer flag = groupDao.update(group);
		if (flag != null) { return true; }
		return false;
	}

	public int findUserByGroup(Integer groupId) {
		return userDao.findByGroup(groupId);
	}
	
	public int findPrivByGroup(Integer groupId) {
		return privassignDao.findByGroup(groupId);
	}
	
//用户组----delete
	public boolean deleteGroup(Integer groupId) {
		
		Integer flag = groupDao.deleteById(groupId);
		if (flag != null) { return true; }
		return false;
	}

}
