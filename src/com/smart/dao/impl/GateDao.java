//闸机  by zhouyu
package com.smart.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.core.Page;
import com.smart.po.Gate;
import com.smart.dao.IGateDao;

public class GateDao extends SqlMapClientDaoSupport implements IGateDao {

//闸机----删除对象
	public Integer deleteById(Integer gateId) {

		return getSqlMapClientTemplate().delete("Gate.deleteById", gateId);
	}

//闸机----获得对象总数
	public int findByCount(Page page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("Gate.findByCount", page);
	}

//闸机----获取一页对象
	public List findByPage(Page page) {

		return getSqlMapClientTemplate().queryForList("Gate.findByPage", page);
	}

//闸机----添加一个对象
	public Object saveGate(Gate gate) {

		return getSqlMapClientTemplate().insert("Gate.save", gate);
	}

//闸机----更新一个对象
	public Integer update(Gate gate) throws Exception {

		return getSqlMapClientTemplate().update("Gate.update", gate);
	}

}
