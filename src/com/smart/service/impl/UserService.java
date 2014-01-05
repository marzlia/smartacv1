//用户管理  by zhouyu
package com.smart.service.impl;

import com.smart.core.Page;
import com.smart.po.User;
import com.smart.service.IUserService;
import com.smart.dao.IUserDao;

public class UserService implements IUserService {
	private IUserDao userDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

//用户管理----add
	public Object saveUser(User user) {

		return userDao.saveUser(user);
	}

//用户管理----find
	public Page findByPage(Page page) {

		page.setRoot(userDao.findByPage(page));
		page.setTotalProperty(userDao.findByCount(page));
		return page;
	}

//用户管理----update
	public boolean updateUser(User user) throws Exception {

		Integer flag = userDao.update(user);
		if (flag != null) { return true; }
		return false;
	}

//用户管理----delete
	public boolean deleteUser(Integer userId) {

		Integer flag = userDao.deleteById(userId);
		if (flag != null) { return true; }
		return false;
	}

	@Override
	public User findUserByName(User user) {
		// TODO Auto-generated method stub
		return userDao.findUserByName(user);
	}

}
