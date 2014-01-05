//用户管理  by zhouyu
package com.smart.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.core.Page;
import com.smart.po.User;
import com.smart.dao.IUserDao;

public class UserDao extends SqlMapClientDaoSupport implements IUserDao {

//用户管理----删除对象
	public Integer deleteById(Integer userId) {

		return getSqlMapClientTemplate().delete("User.deleteById", userId);
	}

//用户管理----获得对象总数
	public int findByCount(Page page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("User.findByCount", page);
	}

//用户管理----获取一页对象
	public List findByPage(Page page) {

		return getSqlMapClientTemplate().queryForList("User.findByPage", page);
	}

//用户管理----添加一个对象
	public Object saveUser(User user) {

		return getSqlMapClientTemplate().insert("User.save", user);
	}

//用户管理----更新一个对象
	public Integer update(User user) throws Exception {

		return getSqlMapClientTemplate().update("User.update", user);
	}

	@Override
	public int findByGroup(Integer groupId) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("User.findByGroup", groupId);
	}

	@Override
	public User findUserByName(User user) {
		// TODO Auto-generated method stub
		return (User)getSqlMapClientTemplate().queryForObject("User.findUserByName", user);
	}

}
