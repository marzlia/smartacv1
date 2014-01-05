package com.smart.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.dao.ISysDao;
import com.smart.po.User;

public class SysDao extends SqlMapClientDaoSupport implements ISysDao {

	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		return (User) getSqlMapClientTemplate().queryForObject("Sys.login",user);
	}

	@Override
	public List findUserPriv(Integer userId) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("Sys.findUserPriv", userId);
	}

}
