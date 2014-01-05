//循环方式  by zhouyu
package com.smart.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.core.Page;
import com.smart.po.Cycle;
import com.smart.dao.ICycleDao;

public class CycleDao extends SqlMapClientDaoSupport implements ICycleDao {

//循环方式----删除对象
	public Integer deleteById(Integer cycleId) {

		return getSqlMapClientTemplate().delete("Cycle.deleteById", cycleId);
	}

//循环方式----获得对象总数
	public int findByCount(Page page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("Cycle.findByCount", page);
	}

//循环方式----获取一页对象
	public List findByPage(Page page) {

		return getSqlMapClientTemplate().queryForList("Cycle.findByPage", page);
	}

//循环方式----添加一个对象
	public Object saveCycle(Cycle cycle) {

		return getSqlMapClientTemplate().insert("Cycle.save", cycle);
	}

//循环方式----更新一个对象
	public Integer update(Cycle cycle) throws Exception {

		return getSqlMapClientTemplate().update("Cycle.update", cycle);
	}

}
