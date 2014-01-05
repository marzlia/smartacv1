//定时信息  by zhouyu
package com.smart.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.core.Page;
import com.smart.po.Timer;
import com.smart.dao.ITimerDao;

public class TimerDao extends SqlMapClientDaoSupport implements ITimerDao {

//定时信息----删除对象
	public Integer deleteById(Integer timerId) {

		return getSqlMapClientTemplate().delete("Timer.deleteById", timerId);
	}

//定时信息----获得对象总数
	public int findByCount(Page page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("Timer.findByCount", page);
	}

//定时信息----获取一页对象
	public List findByPage(Page page) {

		return getSqlMapClientTemplate().queryForList("Timer.findByPage", page);
	}

//定时信息----添加一个对象
	public Object saveTimer(Timer timer) {

		return getSqlMapClientTemplate().insert("Timer.save", timer);
	}

//定时信息----更新一个对象
	public Integer update(Timer timer) throws Exception {

		return getSqlMapClientTemplate().update("Timer.update", timer);
	}

}
