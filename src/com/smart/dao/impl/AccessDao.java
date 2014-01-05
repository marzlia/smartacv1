//通关  by zhouyu
package com.smart.dao.impl;

import java.util.List;
import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.core.AccessData;
import com.smart.core.AccessPage;
import com.smart.core.Data;
import com.smart.core.Page;
import com.smart.po.Access;
import com.smart.po.Category;
import com.smart.dao.IAccessDao;

public class AccessDao extends SqlMapClientDaoSupport implements IAccessDao {

//通关----删除对象
	public Integer deleteById(Integer accessId) {

		return getSqlMapClientTemplate().delete("Access.deleteById", accessId);
	}

//通关----获得对象总数
	public int findByCount(AccessPage page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("Access.findByCount", page);
	}

//通关----获取一页对象
	public List findByPage(AccessPage page) {

		return getSqlMapClientTemplate().queryForList("Access.findByPage", page);
	}

//通关----添加一个对象
	public Object saveAccess(Access access) {

		return getSqlMapClientTemplate().insert("Access.save", access);
	}

//通关----更新一个对象
	public Integer update(Access access) throws Exception {

		return getSqlMapClientTemplate().update("Access.update", access);
	}

	@Override
	public int findAggCount(Data data) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("Access.findAggCount", data);
	}

	@Override
	public List findAggList(Data data) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("Access.findAggList", data);
	}

	@Override
	public List findCategory(Category category) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("Access.findCategory", category);
	}

	@Override
	public Object findAccessCount(AccessData data) {
		// TODO Auto-generated method stub
		return (HashMap)getSqlMapClientTemplate().queryForObject("Access.findAccessCount", data);
	}

}
