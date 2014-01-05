//用户组  by zhouyu
package com.smart.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.core.Page;
import com.smart.po.Group;
import com.smart.dao.IGroupDao;

public class GroupDao extends SqlMapClientDaoSupport implements IGroupDao {

//用户组----删除对象
	public Integer deleteById(Integer groupId) {

		return getSqlMapClientTemplate().delete("Group.deleteById", groupId);
	}

//用户组----获得对象总数
	public int findByCount(Page page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("Group.findByCount", page);
	}

//用户组----获取一页对象
	public List findByPage(Page page) {

		return getSqlMapClientTemplate().queryForList("Group.findByPage", page);
	}

//用户组----添加一个对象
	public Object saveGroup(Group group) {

		return getSqlMapClientTemplate().insert("Group.save", group);
	}

//用户组----更新一个对象
	public Integer update(Group group) throws Exception {

		return getSqlMapClientTemplate().update("Group.update", group);
	}

}
