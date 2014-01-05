//监视器  by zhouyu
package com.smart.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.core.Page;
import com.smart.po.Watcher;
import com.smart.dao.IWatcherDao;

public class WatcherDao extends SqlMapClientDaoSupport implements IWatcherDao {

//监视器----删除对象
	public Integer deleteById(Integer watcherId) {

		return getSqlMapClientTemplate().delete("Watcher.deleteById", watcherId);
	}

//监视器----获得对象总数
	public int findByCount(Page page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("Watcher.findByCount", page);
	}

//监视器----获取一页对象
	public List findByPage(Page page) {

		return getSqlMapClientTemplate().queryForList("Watcher.findByPage", page);
	}

//监视器----添加一个对象
	public Object saveWatcher(Watcher watcher) {

		return getSqlMapClientTemplate().insert("Watcher.save", watcher);
	}

//监视器----更新一个对象
	public Integer update(Watcher watcher) throws Exception {

		return getSqlMapClientTemplate().update("Watcher.update", watcher);
	}

}
