//用户管理  by zhouyu
package com.smart.service;

import com.smart.core.Page;
import com.smart.po.User;

public interface IUserService {
//用户管理----add an object
	Object saveUser(User user);

//用户管理----find a page of objects
	Page findByPage(Page page);

//用户管理----update an object
	boolean updateUser(User user) throws Exception;

//用户管理----delete an object by id
	boolean deleteUser(Integer userId);
	
	User findUserByName(User user);

}
