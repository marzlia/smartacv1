//权限  by zhouyu
package com.smart.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.core.Page;
import com.smart.po.Priv;
import com.smart.dao.IPrivDao;

public class PrivDao extends SqlMapClientDaoSupport implements IPrivDao {

//权限----删除对象
	public Integer deleteById(Integer privId) {

		return getSqlMapClientTemplate().delete("Priv.deleteById", privId);
	}

//权限----获得对象总数
	public int findByCount(Page page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("Priv.findByCount", page);
	}

//权限----获取一页对象
	public List findByPage(Page page) {

		return getSqlMapClientTemplate().queryForList("Priv.findByPage", page);
	}

//权限----添加一个对象
	public Object savePriv(Priv priv) {

		return getSqlMapClientTemplate().insert("Priv.save", priv);
	}

//权限----更新一个对象
	public Integer update(Priv priv) throws Exception {

		return getSqlMapClientTemplate().update("Priv.update", priv);
	}

}
