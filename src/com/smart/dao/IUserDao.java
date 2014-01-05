//用户管理  by zhouyu
package com.smart.dao;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.User;

public interface IUserDao {
	public Object saveUser(User user);

	public List findByPage(Page page);

	public int findByCount(Page page);
	
	public int findByGroup(Integer groupId);

	public Integer update(User user) throws Exception;

	public Integer deleteById(Integer userId);
	
	public User findUserByName(User user);

}
